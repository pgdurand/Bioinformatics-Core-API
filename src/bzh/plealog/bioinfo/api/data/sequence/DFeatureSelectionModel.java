/* Copyright (C) 2006-2016 Patrick G. Durand
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.txt
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 */
package bzh.plealog.bioinfo.api.data.sequence;

/**
 * This interface defines a Feature Selection model.
 * 
 * @author Patrick G. Durand
 */
public interface DFeatureSelectionModel {
    /**
     * Figures out if there is some features selected on a sequence.
     */
	public boolean isEmpty();
    /**
     * Removes all feature selections.
     */
	public void clear();
    /**
     * Adds a new feature selection.
     */
	public void addSelection(int from, int to);
    /**
     * Figures out if a particular position on a sequence is located within
     * a selected feature.
     */
	public boolean isSelected(int coord);
}
