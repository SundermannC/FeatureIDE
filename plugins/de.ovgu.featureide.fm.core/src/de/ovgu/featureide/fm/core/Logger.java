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
package de.ovgu.featureide.fm.core;

/**
 *
 * @author Sebastian Krieter
 */
public class Logger {

	public static ILogger logger = new JavaLogger();

	public static void logInfo(String message) {
		logger.logInfo(message);
	}

	public static void logWarning(String message) {
		logger.logWarning(message);
	}

	public static void logError(String message) {
		logger.logError(message);
	}

	public static void logError(String message, Throwable exception) {
		logger.logError(message, exception);
	}

	public static void logError(Throwable exception) {
		logger.logError(exception);
	}

	public static void reportBug(int ticket) {
		logger.reportBug(ticket);
	}

}
