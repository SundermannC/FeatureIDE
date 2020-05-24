package de.ovgu.featureide.fm.core.analysis.ddnnf.solver;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.FileLocator;

import de.ovgu.featureide.fm.core.FMCorePlugin;
import de.ovgu.featureide.fm.core.Logger;
import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.util.DIMACSUtils;

public class ComparableDsharp implements IComparableSolver {

	private final static String ID = "dsharp";

	private final static String BINARY_NAME = "dsharp";

	private final static String BINARY_PATH = getPath().toString();

	private final static String MEMORY_FLAG = "-cs";

	private final static String UNSAT_FLAG = "Theory is unsat.";

	private final int memoryLimit;

	public ComparableDsharp(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Override
	public SolverResult executeSolver(CNF cnf, long timeout, boolean saveResultingFormat) throws InterruptedException {
		DIMACSUtils.createTemporaryDimacs(cnf);
		final BinaryResult binaryResult = executeSolver(DIMACSUtils.TEMPORARY_DIMACS_PATH, timeout, saveResultingFormat);
		return getResult(binaryResult.stdout);

	}

	@Override
	public BinaryResult executeSolver(String dimacsPath, long timeout) throws InterruptedException {
		return executeSolver(dimacsPath, timeout, false);
	}

	@Override
	public BinaryResult executeSolver(String dimacsPath, long timeout, boolean saveResultingFormat) throws InterruptedException {
		final String command = buildCommand(dimacsPath, saveResultingFormat);
		final BinaryResult output = BinaryUtils.runBinary(command, timeout);
		return output;
	}

	@Override
	public SolverResult getResult(String output) {
		if (isUNSAT(output)) {
			return SolverResult.getSolvedResult("0");
		}
		final Pattern pattern = Pattern.compile("\\#SAT \\(full\\).*\\d*");
		final Matcher matcher = pattern.matcher(output);
		String result = "";
		if (matcher.find()) {
			result = matcher.group();
		} else {
			return SolverResult.getUnexpectedErrorResult();
		}
		final String[] split = result.split("\\s+");
		return SolverResult.getSolvedResult(split[split.length - 1]);
	}

	private boolean isUNSAT(String output) {
		return output.contains(UNSAT_FLAG);
	}

	@Override
	public String getComputedMetaData(String output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdentifier() {
		return ID;
	}

	private String buildCommand(String dimacsPath, boolean saveDdnnf) {
		final String saveDdnnfString = saveDdnnf ? " -Fnnf " + DDNNF_TEMP_PATH + " -smoothNNF" : "";
		return BINARY_PATH + saveDdnnfString + " " + MEMORY_FLAG + " " + memoryLimit + " " + dimacsPath;
	}

	@Override
	public String getBinaryName() {
		return BINARY_NAME;
	}

	public static Path getPath() {
		URL url = FileLocator.find(FMCorePlugin.getDefault().getBundle(), new org.eclipse.core.runtime.Path("lib/dsharp"), null);
		try {
			url = FileLocator.toFileURL(url);
			return Paths.get(url.getPath()).normalize();
		} catch (final IOException e) {
			Logger.logError(e);
			return null;
		}
	}

}
