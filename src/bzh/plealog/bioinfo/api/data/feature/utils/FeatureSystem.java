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

import org.apache.log4j.Logger;

public class FeatureSystem {
	private static FeatureTableFactory _factory;
	public static final Logger LOGGER = Logger.getLogger("kb."+"FeatureSystem");
	public static void setFeatureTableFactory(FeatureTableFactory f){
		_factory = f;
	}
	public static FeatureTableFactory getFeatureTableFactory(){
		return _factory;
	}
}
