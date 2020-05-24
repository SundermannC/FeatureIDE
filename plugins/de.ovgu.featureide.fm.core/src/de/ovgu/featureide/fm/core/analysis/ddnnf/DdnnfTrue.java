package de.ovgu.featureide.fm.core.analysis.ddnnf;

import java.math.BigInteger;
import java.util.Set;

public class DdnnfTrue extends DdnnfNode {

	public DdnnfTrue() {
		overallModelCount = BigInteger.ONE;
		tempModelCount = BigInteger.ONE;
		currentModelCount = BigInteger.ONE;
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
