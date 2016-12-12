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
package bzh.plealog.bioinfo.api.core.config;

import bzh.plealog.bioinfo.api.data.feature.utils.FeatureSystem;
import bzh.plealog.bioinfo.api.data.sequence.DSymbolFamilySystem;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.data.feature.IFeatureTableFactory;
import bzh.plealog.bioinfo.data.sequence.DAlphabetFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSequenceAlignedFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSequenceAlignmentFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSequenceFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSymbolFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.IBankSequenceInfoFactory;

/**
 * This is the Core system. Always call initializeSystem method before using the API.
 * 
 * @author Patrick G. Durand
 */
public class CoreSystemConfigurator {
	private static boolean  _bInited = false;

	static{
		initializeSystem();
	}

	/**
	 * Initialize core system.
	 */
	public static final void initializeSystem(){
		if (_bInited)
			return;

		DViewerSystem.setAlphabetFactory(new DAlphabetFactoryImplem());
		DViewerSystem.setSequenceFactory(new DSequenceFactoryImplem());
		DViewerSystem.setSymbolFactory(new DSymbolFactoryImplem());
		DViewerSystem.setSequenceAlignedFactory(new DSequenceAlignedFactoryImplem());
		DViewerSystem.setSequenceAlignmentFactory(new DSequenceAlignmentFactoryImplem());
		DViewerSystem.setBankSequenceInfoFactory(new IBankSequenceInfoFactory());
		
		DSymbolFamilySystem.initDefaultFamilies(null);

		FeatureSystem.setFeatureTableFactory(new IFeatureTableFactory());
		
		_bInited = true;
	}
}
