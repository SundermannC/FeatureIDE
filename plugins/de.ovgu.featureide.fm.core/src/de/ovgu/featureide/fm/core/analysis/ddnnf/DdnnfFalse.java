package de.ovgu.featureide.fm.core.analysis.ddnnf;

import java.math.BigInteger;
import java.util.Set;

public class DdnnfFalse extends DdnnfNode {

	public DdnnfFalse() {
		overallModelCount = BigInteger.ZERO;
		tempModelCount = BigInteger.ZERO;
		currentModelCount = BigInteger.ZERO;
		changedValue = false;
	}

	@Override
	public void addChild(DdnnfNode child) {}

	@Override
	public void getPartialConfigurationCount(Set<Integer> included, Set<Integer> excluded) {}

	@Override
	public void ursPropagateTempChange(Integer variable) {}

	@Override
	public void ursPropagateChange(Integer variable) {}

	@Override
	public void propagateCommonality(int variable) {}

}
