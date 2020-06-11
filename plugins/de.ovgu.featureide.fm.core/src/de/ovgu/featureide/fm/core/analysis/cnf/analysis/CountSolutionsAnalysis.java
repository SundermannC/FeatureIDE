/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2019  FeatureIDE team, University of Magdeburg, Germany
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
package de.ovgu.featureide.fm.core.analysis.cnf.analysis;

import java.math.BigInteger;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.solver.ISatSolver;
import de.ovgu.featureide.fm.core.analysis.ddnnf.solver.ComparableDsharp;
import de.ovgu.featureide.fm.core.analysis.ddnnf.solver.IComparableSolver;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

/**
 * Attempts to count the number of possible solutions of a given {@link CNF}.
 *
 * @author Sebastian Krieter
 */
public class CountSolutionsAnalysis extends AbstractAnalysis<BigInteger> {

	public CountSolutionsAnalysis(ISatSolver solver) {
		super(solver);
	}

	public CountSolutionsAnalysis(CNF satInstance, int timeout) {
		super(satInstance);
		setTimeout(timeout);
	}

	@Override
	public BigInteger analyze(IMonitor<BigInteger> monitor) throws Exception {
		final IComparableSolver sharpsat = new ComparableDsharp(1024);
		final BigInteger result = sharpsat.executeSolver(solver.getSatInstance(), getTimeout(), false).result;

		return result;
	}

}
