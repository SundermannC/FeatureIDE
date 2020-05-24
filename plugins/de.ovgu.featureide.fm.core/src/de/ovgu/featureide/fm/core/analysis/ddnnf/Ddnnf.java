package de.ovgu.featureide.fm.core.analysis.ddnnf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ddnnf {

	List<DdnnfNode> nodes;

	DdnnfNode root;

	// URS Variables
	Set<Integer> currentConfig;
	BigInteger randomNumber;

	int numberOfVariables;

	int[] coreDeadIndicators;

	Set<Integer> cores;

	Set<Integer> deads;

	public void readDdnnfFile(String path) {
		nodes = new ArrayList<>();
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				handleLine(line);
			}
			reader.close();
			finish();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	public void handleLine(String line) {
		final String[] split = line.split(" ");

		if (DdnnfParserUtils.isLiteral(split)) {
			if (DdnnfParserUtils.isNegativeLiteral(split)) {
				final int index = DdnnfParserUtils.getNegativeLiteral(split);
				nodes.add(new DdnnfNegativeLiteral(index));
			} else {
				nodes.add(new DdnnfPositiveLiteral(DdnnfParserUtils.getPositiveLiteral(split)));
			}
		} else if (DdnnfParserUtils.isAnd(split)) {
			final int[] childIndices = DdnnfParserUtils.getAndChildIndices(split);
			if (childIndices.length == 0) {
				nodes.add(new DdnnfTrue());
			} else {
				final DdnnfAnd node = new DdnnfAnd();
				for (final int childIndex : childIndices) {
					node.addChild(nodes.get(childIndex));
				}
				nodes.add(node);
			}
		} else if (DdnnfParserUtils.isOr(split)) {
			final int[] childIndices = DdnnfParserUtils.getOrChildIndices(split);
			if (childIndices.length == 0) {
				nodes.add(new DdnnfFalse());
			} else {
				final DdnnfOr node = new DdnnfOr();
				for (final int childIndex : childIndices) {
					node.addChild(nodes.get(childIndex));
				}
				nodes.add(node);
			}
		}
	}

	public void finish() {
		root = nodes.get(nodes.size() - 1);
	}

	public void readDdnnfFileAndSaveCoreDead(String path) {
		nodes = new ArrayList<>();
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				handleLineWithCoreDead(line);
			}
			reader.close();
			finishWithCoreDead();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void handleLineWithCoreDead(String line) {
		boolean foundEntry = false;
		final String[] split = line.split(" ");

		if (!foundEntry && DdnnfParserUtils.isEntry(split)) {
			numberOfVariables = Integer.valueOf(split[3]);
			coreDeadIndicators = new int[numberOfVariables + 1];
			foundEntry = true;
		} else if (DdnnfParserUtils.isLiteral(split)) {
			if (DdnnfParserUtils.isNegativeLiteral(split)) {
				final int index = DdnnfParserUtils.getNegativeLiteral(split);
				nodes.add(new DdnnfNegativeLiteral(index));
				if (coreDeadIndicators[index] == 0) {
					coreDeadIndicators[index] = -1;
				} else if (coreDeadIndicators[index] == 1) {
					coreDeadIndicators[index] = 2;
				}
			} else {
				final int index = DdnnfParserUtils.getPositiveLiteral(split);
				nodes.add(new DdnnfPositiveLiteral(index));
				if (coreDeadIndicators[index] == 0) {
					coreDeadIndicators[index] = 1;
				} else if (coreDeadIndicators[index] == -1) {
					coreDeadIndicators[index] = 2;
				}
			}
		} else if (DdnnfParserUtils.isAnd(split)) {
			final int[] childIndices = DdnnfParserUtils.getAndChildIndices(split);
			if (childIndices.length == 0) {
				nodes.add(new DdnnfTrue());
			} else {
				final DdnnfAnd node = new DdnnfAnd();
				for (final int childIndex : childIndices) {
					node.addChild(nodes.get(childIndex));
				}
				nodes.add(node);
			}
		} else if (DdnnfParserUtils.isOr(split)) {
			final int[] childIndices = DdnnfParserUtils.getOrChildIndices(split);
			if (childIndices.length == 0) {
				nodes.add(new DdnnfFalse());
			} else {
				final DdnnfOr node = new DdnnfOr();
				for (final int childIndex : childIndices) {
					node.addChild(nodes.get(childIndex));
				}
				nodes.add(node);
			}
		}
	}

	public void finishWithCoreDead() {
		deads = new HashSet<>();
		cores = new HashSet<>();
		root = nodes.get(nodes.size() - 1);
		for (int i = 1; i < coreDeadIndicators.length; i++) {
			if (coreDeadIndicators[i] == -1) {
				deads.add(i);
			} else if (coreDeadIndicators[i] == 1) {
				cores.add(i);
			}
		}
	}

	public BigInteger getNumberOfSolutions() {
		return root.overallModelCount;
	}

	// -------------------------- Commonality -----------------------

	public List<BigInteger> getCommonalities() {
		final List<BigInteger> commonalities = new ArrayList<>();
		for (int i = 1; i <= numberOfVariables; i++) {
			if (cores.contains(i)) {
				commonalities.add(root.overallModelCount);
			} else if (deads.contains(i)) {
				commonalities.add(BigInteger.ZERO);
			} else {
				for (final DdnnfNode node : nodes) {
					node.propagateCommonality(i);
				}
				commonalities.add(root.tempModelCount);
			}
		}
		return commonalities;
	}

	// -------------------------- Uniform Random Sampling -----------------------

	public Set<Integer> performUrs(BigInteger randomNumber) {
		ursInit(randomNumber);
		for (int i = 1; i <= numberOfVariables; i++) {
			ursHandleNextVariable(i);
		}
		return ursFinish();
	}

	public void ursInit(BigInteger randomNumber) {
		currentConfig = new HashSet<>();
		this.randomNumber = randomNumber;
	}

	public void ursHandleNextVariable(int variableIndex) {
		if (cores.contains(variableIndex)) {
			currentConfig.add(variableIndex);
		} else if (deads.contains(variableIndex)) {
			// Skip feature
		} else {
			final Set<Integer> excluded = new HashSet<>();
			excluded.add(variableIndex);
//			for (IterativeBUNode node : nodes) {
//				node.ursPropagateTempChange(variableIndex);
//			}
			final BigInteger result = getPartialConfigurationCount(currentConfig, excluded);
			if (result.compareTo(randomNumber) < 0) {
				randomNumber = randomNumber.subtract(result);
				currentConfig.add(variableIndex);
			}
//			if (root.tempModelCount.compareTo(randomNumber) < 0) {
//				randomNumber = randomNumber.subtract(root.tempModelCount);
//				for (IterativeBUNode node : nodes) {
//					node.ursPropagateChange(variableIndex);
//				}
//				currentConfig.add(variableIndex);
//			} else {
//				ursSaveTempRessults();
//			}

		}
	}

	public Set<Integer> ursFinish() {
		for (final DdnnfNode node : nodes) {
			node.resetCurrentModelCount();
		}
		return currentConfig;
	}

	public void ursSaveTempRessults() {
		for (final DdnnfNode node : nodes) {
			node.saveUnsureResult();
		}
	}

	public void resetTemps() {
		for (final DdnnfNode node : nodes) {
			node.resetCurrentModelCount();
		}
	}

	// Partial Configurations

	public BigInteger getPartialConfigurationCount(Set<Integer> included, Set<Integer> excluded) {
		for (final DdnnfNode node : nodes) {
			node.getPartialConfigurationCount(included, excluded);
		}
		return root.tempModelCount;
	}

}
