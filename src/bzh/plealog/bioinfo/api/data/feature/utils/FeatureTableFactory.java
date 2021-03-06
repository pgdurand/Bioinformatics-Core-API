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
package bzh.plealog.bioinfo.api.data.feature.utils;

import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.feature.Qualifier;

/**
 * This is a Feature table factory. It is recommended to use this factory to handle
 * Features objects.
 * 
 * @author Patrick G. Durand
 */
public interface FeatureTableFactory {
    /**
     * Creates a new instance of a Feature Table.
     */
	public FeatureTable getFTInstance();
    /**
     * Creates a new instance of a Feature.
     */
	public Feature getFInstance();
    /**
     * Creates a new instance of a Qualifier.
     */
	public Qualifier getQInstance();
}
