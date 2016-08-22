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
package bzh.plealog.bioinfo.data.feature;

import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.feature.Qualifier;
import bzh.plealog.bioinfo.api.data.feature.utils.FeatureTableFactory;

/**
 * This class is a default implementation of a feature table factory. 
 * 
 * @author Patrick G. Durand
 */
public class IFeatureTableFactory implements FeatureTableFactory {

    /**
     * Implementation of FeatureTableFactory interface.
     */
	public FeatureTable getFTInstance() {
		return new IFeatureTable();
	}
    /**
     * Creates a new instance of a Feature.
     */
	public Feature getFInstance() {
		return new IFeature();
	}
    /**
     * Creates a new instance of a Qualifier.
     */
	public Qualifier getQInstance() {
		return new IQualifier();
	}

}
