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
package de.ovgu.featureide.fm.ui.views.outline;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.ovgu.featureide.fm.core.configuration.Configuration;

/**
 * Every entry for the Configuration outline should implement this
 *
 * @author Chico Sundermann
 */
public interface IOutlineEntry {

	/**
	 * Returns the label of the entry
	 *
	 * @return label
	 */
	public String getLabel();

	/**
	 * Returns the image (if any) attached to the left side of the label
	 *
	 * @return label image
	 */
	public Image getLabelImage();

	/**
	 * Indicates whether the entry has children in the outline
	 *
	 * @return
	 */
	public boolean hasChildren();

	/**
	 *
	 * @return list of the children of the entry
	 */
	public List<IOutlineEntry> getChildren();

	/**
	 * Indicates whether entry supports a certain type
	 *
	 * @param extern element to check
	 * @return
	 */
	public boolean supportsType(Object element);

	/**
	 * Sets the intern Configuration
	 *
	 * @param config new Configuration
	 */
	public void setConfig(Configuration config);

	/**
	 * This method is called if the entry is double clicked
	 */
	public void handleDoubleClick();

}