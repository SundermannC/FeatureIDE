/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2017  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 *
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.ddnnf.Ddnnf;
import de.ovgu.featureide.fm.core.analysis.ddnnf.solver.ComparableDsharp;
import de.ovgu.featureide.fm.core.analysis.ddnnf.solver.IComparableSolver;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

/**
 * Generates uniformly distributed random configurations using d-DNNFs
 *
 * @author Chico Sundermann
 */
public class UniformRandomSamplingConfigurationGenerator extends ARandomConfigurationGenerator {

	CNF cnf;

	int numberOfSamples;

	Set<BigInteger> alreadyUsedRandoms;

	List<Set<Integer>> samples;

	Random random = new Random();

	/**
	 * @param cnf
	 * @param maxNumber
	 */
	public UniformRandomSamplingConfigurationGenerator(CNF cnf, int maxNumber) {
		super(cnf, maxNumber);
		this.cnf = cnf;
		numberOfSamples = maxNumber;
		if (!allowDuplicates) {
			alreadyUsedRandoms = new HashSet<BigInteger>();
		}
	}

	@Override
	protected void generate(IMonitor<List<LiteralSet>> monitor) throws Exception {
		final IComparableSolver solver = new ComparableDsharp(8000); // TODO: fix memory
		solver.executeSolver(cnf, 60, true); // TODO: actual timeout
		final Ddnnf ddnnf = new Ddnnf();
		ddnnf.readDdnnfFileAndSaveCoreDead(IComparableSolver.DDNNF_TEMP_PATH);
		final BigInteger numberOfSolutions = ddnnf.getNumberOfSolutions();
		for (int i = 0; i < numberOfSamples; i++) {
			final BigInteger randomNumber = getNextRandomNumber(numberOfSolutions);
			final Set<Integer> literals = ddnnf.performUrs(randomNumber);
			System.out.println();
			addResult(new LiteralSet(literals.stream().mapToInt(j -> j).toArray()));
			monitor.step();
		}
	}

	private BigInteger getNextRandomNumber(BigInteger numberOfSolutions) {
		BigInteger randomNumber;
		do {
			randomNumber = new BigInteger(numberOfSolutions.bitLength(), random);
		} while ((randomNumber.compareTo(numberOfSolutions) >= 0) && (allowDuplicates || !alreadyUsedRandoms.contains(randomNumber)));
		alreadyUsedRandoms.add(randomNumber);
		return randomNumber;
	}

}
