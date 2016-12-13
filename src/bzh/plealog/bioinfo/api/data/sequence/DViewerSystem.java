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
 * This class can be used as a unique wrapper for implementations 
 * of DAlphabetFactory, DSymbolFactory and DSequenceFactory interfaces.
 * 
 * @author Patrick G. Durand
 */
public class DViewerSystem {
    private static DAlphabetFactory          _AFactory;
    private static DSymbolFactory            _SFactory;
    private static DSequenceFactory          _SeqFactory;
    private static DSequenceAlignmentFactory _MSAFactory;
    private static DSequenceAlignedFactory   _SAFactory;
    private static BankSequenceInfoFactory   _BSIFactory;
    
    public static void setSequenceAlignmentFactory(DSequenceAlignmentFactory factory){
        if (factory!=null)
        	_MSAFactory=factory;
    }
    
    public static DSequenceAlignmentFactory getSequenceAlignmentFactory(){
    	return _MSAFactory;
    }
    
    public static void setSequenceAlignedFactory(DSequenceAlignedFactory factory){
        if (factory!=null)
        	_SAFactory=factory;
    }
    
    public static DSequenceAlignedFactory getSequenceAlignedFactory(){
    	return _SAFactory;
    }

    public static void setAlphabetFactory(DAlphabetFactory factory) {
        if (factory!=null)
            _AFactory=factory;
    }

    public static void setSymbolFactory(DSymbolFactory factory) {
        if (factory!=null)
            _SFactory=factory;
    }

    public static void setSequenceFactory(DSequenceFactory factory) {
        if (factory!=null)
        _SeqFactory=factory;
    }

    public static DAlphabetFactory getAlphabetFactory() {
        return (_AFactory);
    }

    public static DSymbolFactory getSymbolFactory() {
        return (_SFactory);
    }
    
    public static DSequenceFactory getSequenceFactory() {
        return (_SeqFactory);
    }
    public static void setBankSequenceInfoFactory(BankSequenceInfoFactory bsiFactory){
      _BSIFactory = bsiFactory;
    }
    public static BankSequenceInfoFactory getBankSequenceInfoFactory(){
      return _BSIFactory;
    }
 }
