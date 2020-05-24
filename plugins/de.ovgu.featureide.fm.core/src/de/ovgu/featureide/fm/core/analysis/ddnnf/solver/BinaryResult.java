package de.ovgu.featureide.fm.core.analysis.ddnnf.solver;

public class BinaryResult {

	public String stdout;
	public SolverStatus status;

	public BinaryResult(String stdout, SolverStatus status) {
		this.stdout = stdout;
		this.status = status;
	}
}
