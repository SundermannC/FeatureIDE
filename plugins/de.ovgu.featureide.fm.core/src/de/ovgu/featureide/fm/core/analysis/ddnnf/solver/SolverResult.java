package de.ovgu.featureide.fm.core.analysis.ddnnf.solver;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SolverResult {

	public SolverStatus status;

	public BigInteger result;

	public SolverResult() {}

	public SolverResult(SolverStatus status, BigInteger result) {
		this.status = status;
		this.result = result;
	}

	public static SolverResult getUnexpectedErrorResult() {
		return new SolverResult(SolverStatus.UNEXPECTED, new BigInteger("-1"));
	}

	public static SolverResult getMemoryLimitResult() {
		return new SolverResult(SolverStatus.MEMORY_LIMIT_REACHED, IComparableSolver.MEMORYOUT_FLAG);
	}

	public static SolverResult getTimeoutResult() {
		return new SolverResult(SolverStatus.TIMEOUT, IComparableSolver.TIMEOUT_FLAG);
	}

	public static SolverResult getSolvedResult(String numberOfSolutionsString) {
		BigInteger result;
		if (isScientificNotation(numberOfSolutionsString)) {
			final BigDecimal bd = new BigDecimal(numberOfSolutionsString);
			result = bd.toBigInteger();
		} else {
			result = new BigInteger(numberOfSolutionsString);
		}
		return new SolverResult(SolverStatus.SOLVED, result);
	}

	private static boolean isScientificNotation(String result) {
		return (result.contains("e") || result.contains("E"));
	}

}
