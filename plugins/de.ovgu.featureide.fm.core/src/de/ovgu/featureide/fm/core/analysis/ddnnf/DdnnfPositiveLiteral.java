package de.ovgu.featureide.fm.core.analysis.ddnnf;

import java.math.BigInteger;
import java.util.Set;

public class DdnnfPositiveLiteral extends DdnnfNode {

	int variable;

	public DdnnfPositiveLiteral(int variable) {
		this.variable = variable;
		overallModelCount = BigInteger.ONE;
		tempModelCount = overallModelCount;
	}

	@Override
	public void addChild(DdnnfNode child) {}

	@Override
	public void getPartialConfigurationCount(Set<Integer> included, Set<Integer> excluded) {
		if (excluded.contains(variable)) {
			tempModelCount = BigInteger.ZERO;
			changedValue = true;
		} else {
			tempModelCount = BigInteger.ONE;
			changedValue = false;
		}
	}

	@Override
	public void ursPropagateTempChange(Integer variable) {
		changedValue = true;
		tempModelCount = BigInteger.ZERO;
	}

	@Override
	public void ursPropagateChange(Integer variable) {
		changedValue = false;
	}

	@Override
	public void propagateCommonality(int variable) {
		changedValue = false;
	}

}
