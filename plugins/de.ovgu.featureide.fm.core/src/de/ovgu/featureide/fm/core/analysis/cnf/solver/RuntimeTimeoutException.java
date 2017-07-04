/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2016  FeatureIDE team, University of Magdeburg, Germany
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
package de.ovgu.featureide.fm.core.analysis.cnf.solver;

/**
 * Exception thrown when an {@link analysis IAnalysis} experiences a solver timeout.<br/>
 * Doesn't need to be caught explicitly.
 * 
 * @author Sebastian Krieter
 */
public class RuntimeTimeoutException extends RuntimeException {

	private static final long serialVersionUID = -6922001608864037759L;

	public RuntimeTimeoutException() {
		super();
	}

	public RuntimeTimeoutException(String message) {
		super(message);
	}

	public RuntimeTimeoutException(Throwable cause) {
		super(cause);
	}

	public RuntimeTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

}