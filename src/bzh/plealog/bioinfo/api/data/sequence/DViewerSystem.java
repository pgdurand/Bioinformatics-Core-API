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

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.util.DAlphabetUtils;

/**
 * This class can be used as a unique wrapper for implementations of
 * DAlphabetFactory, DSymbolFactory and DSequenceFactory interfaces.
 * 
 * @deprecated
 * @author Patrick G. Durand
 */
public class DViewerSystem {

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#setSequenceAlignmentFactory(DSequenceAlignmentFactory)
   */
  public static void setSequenceAlignmentFactory(DSequenceAlignmentFactory factory) {
    CoreSystemConfigurator.setSequenceAlignmentFactory(factory);
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#getSequenceAlignmentFactory()
   */
  public static DSequenceAlignmentFactory getSequenceAlignmentFactory() {
    return CoreSystemConfigurator.getSequenceAlignmentFactory();
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#setSequenceAlignedFactory(DSequenceAlignedFactory)
   */
  public static void setSequenceAlignedFactory(DSequenceAlignedFactory factory) {
    CoreSystemConfigurator.setSequenceAlignedFactory(factory);
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#getSequenceAlignedFactory()
   */
  public static DSequenceAlignedFactory getSequenceAlignedFactory() {
    return CoreSystemConfigurator.getSequenceAlignedFactory();
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#setAlphabetFactory(DAlphabetFactory)
   */
  public static void setAlphabetFactory(DAlphabetFactory factory) {
    CoreSystemConfigurator.setAlphabetFactory(factory);
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#setSymbolFactory(DSymbolFactory)
   */
  public static void setSymbolFactory(DSymbolFactory factory) {
    CoreSystemConfigurator.setSymbolFactory(factory);
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#setSequenceFactory(DSequenceFactory)
   */
  public static void setSequenceFactory(DSequenceFactory factory) {
    CoreSystemConfigurator.setSequenceFactory(factory);
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#getAlphabetFactory()
   */
  public static DAlphabetFactory getAlphabetFactory() {
    return CoreSystemConfigurator.getAlphabetFactory();
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#getSymbolFactory()
   */
  public static DSymbolFactory getSymbolFactory() {
    return (CoreSystemConfigurator.getSymbolFactory());
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#getSequenceFactory()
   */
  public static DSequenceFactory getSequenceFactory() {
    return (CoreSystemConfigurator.getSequenceFactory());
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#setBankSequenceInfoFactory(BankSequenceInfoFactory)
   */
  public static void setBankSequenceInfoFactory(BankSequenceInfoFactory bsiFactory) {
    CoreSystemConfigurator.setBankSequenceInfoFactory(bsiFactory);
  }

  /**
   * @deprecated
   * 
   * @see CoreSystemConfigurator#getBankSequenceInfoFactory()
   */

  public static BankSequenceInfoFactory getBankSequenceInfoFactory() {
    return CoreSystemConfigurator.getBankSequenceInfoFactory();
  }

  /**
   * @deprecated
   * @see DAlphabetUtils#getIUPAC_Protein_Alphabet()
   */
  public static DAlphabet getIUPAC_Protein_Alphabet() {
    return DAlphabetUtils.getIUPAC_Protein_Alphabet();
  }

  /**
   * @deprecated
   * @see DAlphabetUtils#getIUPAC_DNA_Alphabet()
   */
  public static DAlphabet getIUPAC_DNA_Alphabet() {
    return DAlphabetUtils.getIUPAC_DNA_Alphabet();
  }

  /**
   * @deprecated
   * @see DAlphabetUtils#getComparer_Alphabet()
   */
  public static DAlphabet getComparer_Alphabet() {
    return DAlphabetUtils.getComparer_Alphabet();
  }

  /**
   * @deprecated
   * @see DAlphabetUtils#getSpecialSymbols()
   */
  public static String getSpecialSymbols(DAlphabet alphabet) {
    return DAlphabetUtils.getSpecialSymbols(alphabet);
  }

  /**
   * @deprecated
   * @see DAlphabetUtils#isSpecialSymbol()
   */
  public static boolean isSpecialSymbol(DAlphabet alphabet, DSymbol symbol) {
    return DAlphabetUtils.isSpecialSymbol(alphabet, symbol);
  }

}
