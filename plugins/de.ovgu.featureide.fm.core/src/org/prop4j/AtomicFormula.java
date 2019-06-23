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
package org.prop4j;

/**
 * A constraint representing atomic formulas
 *
 * @author Joshua Sprey
 */
public abstract class AtomicFormula extends Node {

	public Term leftTerm;
	public Term rightTerm;

	public AtomicFormula(Term leftTerm, Term rightTerm) {
		this.leftTerm = leftTerm;
		this.rightTerm = rightTerm;
	}

	/*
	 * (non-Javadoc)
	 * @see org.prop4j.Node#isConjunctiveNormalForm()
	 */
	@Override
	public boolean isConjunctiveNormalForm() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.prop4j.Node#isClausalNormalForm()
	 */
	@Override
	public boolean isClausalNormalForm() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.prop4j.Node#eliminateNonCNFOperators(org.prop4j.Node[])
	 */
	@Override
	protected Node eliminateNonCNFOperators(Node[] newChildren) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.prop4j.Node#hashCode()
	 */
	@Override
	public int hashCode() {
		return leftTerm.hashCode() * rightTerm.hashCode();
	}
}