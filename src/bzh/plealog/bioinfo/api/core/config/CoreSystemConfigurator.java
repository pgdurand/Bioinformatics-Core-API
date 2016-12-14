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

import bzh.plealog.bioinfo.api.data.feature.utils.FeatureTableFactory;
import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfoFactory;
import bzh.plealog.bioinfo.api.data.sequence.DAlphabetFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignedFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignmentFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSymbolFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSymbolFamilySystem;
import bzh.plealog.bioinfo.data.feature.IFeatureTableFactory;
import bzh.plealog.bioinfo.data.sequence.DAlphabetFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSequenceAlignedFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSequenceAlignmentFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSequenceFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.DSymbolFactoryImplem;
import bzh.plealog.bioinfo.data.sequence.IBankSequenceInfoFactory;

/**
 * This is the Core system.
 * 
 * @author Patrick G. Durand
 */
public class CoreSystemConfigurator {
  private static boolean _bInited = false;
  private static DAlphabetFactory _AFactory;
  private static DSymbolFactory _SFactory;
  private static DSequenceFactory _SeqFactory;
  private static DSequenceAlignmentFactory _MSAFactory;
  private static DSequenceAlignedFactory _SAFactory;
  private static BankSequenceInfoFactory _BSIFactory;
  private static FeatureTableFactory _factory;

  static {
    initializeSystem();
  }

  /**
   * Initialize core system. Always call this method before using the API.
   */
  public static final void initializeSystem() {
    if (_bInited)
      return;

    CoreSystemConfigurator.setAlphabetFactory(new DAlphabetFactoryImplem());
    CoreSystemConfigurator.setSequenceFactory(new DSequenceFactoryImplem());
    CoreSystemConfigurator.setSymbolFactory(new DSymbolFactoryImplem());
    CoreSystemConfigurator.setSequenceAlignedFactory(new DSequenceAlignedFactoryImplem());
    CoreSystemConfigurator.setSequenceAlignmentFactory(new DSequenceAlignmentFactoryImplem());
    CoreSystemConfigurator.setBankSequenceInfoFactory(new IBankSequenceInfoFactory());

    DSymbolFamilySystem.initDefaultFamilies(null);

    CoreSystemConfigurator.setFeatureTableFactory(new IFeatureTableFactory());

    _bInited = true;
  }

  /**
   * Set a DSequenceAlignmentFactory.
   * 
   * @param factory the new factory.
   */
  public static void setSequenceAlignmentFactory(DSequenceAlignmentFactory factory) {
    if (factory != null)
      _MSAFactory = factory;
  }

  /**
   * Set a DSequenceAlignedFactory.
   * 
   * @param factory the new factory.
   */
  public static void setSequenceAlignedFactory(DSequenceAlignedFactory factory) {
    if (factory != null)
      _SAFactory = factory;
  }

  /**
   * Set a DAlphabetFactory.
   * 
   * @param factory the new factory.
   */
  public static void setAlphabetFactory(DAlphabetFactory factory) {
    if (factory != null)
      _AFactory = factory;
  }

  /**
   * Set a DSymbolFactory.
   * 
   * @param factory the new factory.
   */
  public static void setSymbolFactory(DSymbolFactory factory) {
    if (factory != null)
      _SFactory = factory;
  }

  /**
   * Set a DSequenceFactory.
   * 
   * @param factory the new factory.
   */
  public static void setSequenceFactory(DSequenceFactory factory) {
    if (factory != null)
      _SeqFactory = factory;
  }

  /**
   * Set a BankSequenceInfoFactory.
   * 
   * @param factory the new factory.
   */
  public static void setBankSequenceInfoFactory(BankSequenceInfoFactory bsiFactory) {
    if (bsiFactory!=null)
      _BSIFactory = bsiFactory;
  }

  /**
   * Set a FeatureTableFactory.
   * 
   * @param factory the new factory.
   */
  public static void setFeatureTableFactory(FeatureTableFactory f) {
    if (f!=null)
      _factory = f;
  }


  /**
   * Get a DSequenceAlignmentFactory.
   * 
   * @return  the factory.
   */
  public static DSequenceAlignmentFactory getSequenceAlignmentFactory() {
    return _MSAFactory;
  }

  /**
   * Get a DSequenceAlignedFactory.
   * 
   * @return  the factory.
   */
  public static DSequenceAlignedFactory getSequenceAlignedFactory() {
    return _SAFactory;
  }

  /**
   * Get a DAlphabetFactory.
   * 
   * @return  the factory.
   */
  public static DAlphabetFactory getAlphabetFactory() {
    return (_AFactory);
  }

  /**
   * Get a DSymbolFactory.
   * 
   * @return  the factory.
   */
  public static DSymbolFactory getSymbolFactory() {
    return (_SFactory);
  }

  /**
   * Get a DSequenceFactory.
   * 
   * @return the factory.
   */
  public static DSequenceFactory getSequenceFactory() {
    return (_SeqFactory);
  }

  /**
   * Get a BankSequenceInfoFactory.
   * 
   * @return the factory.
   */
  public static BankSequenceInfoFactory getBankSequenceInfoFactory() {
    return _BSIFactory;
  }

  /**
   * Get a FeatureTableFactory.
   * 
   * @return the factory.
   */
  public static FeatureTableFactory getFeatureTableFactory() {
    return _factory;
  }

}
