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

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;

public class FeatureSystem {

	/**
	 * @deprecated
	 * @see CoreSystemConfigurator#setFeatureTableFactory(FeatureTableFactory)
	 * */
  public static void setFeatureTableFactory(FeatureTableFactory f){
	  CoreSystemConfigurator.setFeatureTableFactory(f);
	}

  /**
   * @deprecated
   * @see CoreSystemConfigurator#getFeatureTableFactory()
   * */
public static FeatureTableFactory getFeatureTableFactory(){
		return CoreSystemConfigurator.getFeatureTableFactory();
	}
}
