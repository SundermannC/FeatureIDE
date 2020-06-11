package de.ovgu.featureide.fm.core.analysis.ddnnf.solver;

import java.math.BigInteger;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;

public interface IComparableSolver {

	public static final BigInteger TIMEOUT_FLAG = BigInteger.valueOf(-1);

	public static final BigInteger MEMORYOUT_FLAG = BigInteger.valueOf(-2);

	public static final BigInteger UNEXPECTED_ERROR = BigInteger.valueOf(-3);

	public static final String DDNNF_TEMP_PATH = "temp.dimacs.nnf";

	public BinaryResult executeSolver(String dimacsPath, long timeout, boolean saveResultingFormat) throws InterruptedException;

	public BinaryResult executeSolver(String dimacsPath, long timeout) throws InterruptedException;

	public SolverResult getResult(BinaryResult binaryResult);

	public String getComputedMetaData(String output);

	public String getIdentifier();

	public String getBinaryName();

	/**
	 * @param cnf
	 * @param timeout
	 * @param saveResultingFormat
	 * @return
	 * @throws InterruptedException
	 */
	SolverResult executeSolver(CNF cnf, long timeout, boolean saveResultingFormat) throws InterruptedException;

}
