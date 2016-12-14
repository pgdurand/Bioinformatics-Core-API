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

import javax.swing.AbstractListModel;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;

/**
 * This class implements a sequence model. It is intended to wraps a DSequence
 * so that it can be viewed in a DSequenceListViewer.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceModel extends AbstractListModel<DSymbol> {
  private static final long serialVersionUID = 4987143569550379293L;
  private DSequence  _sequence;
  private DSymbol    _letter = CoreSystemConfigurator.getSymbolFactory().createDSymbol(0,' ');

  /**
   * Constructor from a DSequence.
   */
  public DSequenceModel(DSequence seq){
    _sequence = seq;
  }

  /**
   * Returns the DSeqeunce wraps in this model.
   */
  public DSequence getSequence(){
    return _sequence;
  }

  /**
   * Returns the maximum size of this model.
   */
  public int getSize() { 
    if (_sequence==null)
      return 0;
    return _sequence.size();
  }

  /**
   * Returns the element located at a particular position. 
   * 
   * @return actually it is a DSymbol.
   * @see javax.swing.ListModel#getElementAt(int)
   */
  public DSymbol getElementAt(int idx) {
    if (_sequence==null)
      return _letter;
    if (idx>=0 && idx<_sequence.size()){
      return(_sequence.getSymbol(idx));
    }
    else{
      return (_letter);
    }
  }

}
