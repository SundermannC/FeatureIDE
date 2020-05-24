package de.ovgu.featureide.fm.core.analysis.ddnnf;

import java.math.BigInteger;
import java.util.Set;

public class DdnnfNegativeLiteral extends DdnnfNode {

	int variable;

	public DdnnfNegativeLiteral(int variable) {
		this.variable = variable;
		overallModelCount = BigInteger.ONE;
		tempModelCount = overallModelCount;
	}

	@Override
	public void addChild(DdnnfNode child) {}

	@Override
	public void getPartialConfigurationCount(Set<Integer> included, Set<Integer> excluded) {
		if (included.contains(variable)) {
			tempModelCount = BigInteger.ZERO;
			changedValue = true;
		} else {
			tempModelCount = BigInteger.ONE;
			changedValue = false;
		}
	}

	@Override
	public void ursPropagateTempChange(Integer variable) {
		changedValue = false;
	}

	@Override
	public void ursPropagateChange(Integer variable) {
		changedValue = true;
		currentModelCount = BigInteger.ZERO;
	}

	@Override
	public void propagateCommonality(int variable) {
		if (this.variable == variable) {
			changedValue = true;
			tempModelCount = BigInteger.ZERO;
		} else {
			changedValue = false;
		}
	}

}
