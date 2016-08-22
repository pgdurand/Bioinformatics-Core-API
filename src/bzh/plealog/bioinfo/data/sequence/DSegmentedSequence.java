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
package bzh.plealog.bioinfo.data.sequence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceException;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This is an implementation of a segmented sequence. Such a sequence is made
 * of a set or ordered DSequence objects that can be separated by some gaps. 
 * Order is based upon positions given
 * that a segmented sequence has its own coordinate system ranging in [0..size-1],
 * where size is the size of the segmented sequence. It is worth noting that this
 * size may not be equal to the sum of the sizes of all DSequence objects of the
 * segmented sequence since some gaps may be found between the various DSequence 
 * objects.
 * 
 * @author Patrick G. Durand
 */
public class DSegmentedSequence implements DSequence {
  private TreeMap<DLocation, DSequenceSegment>          _segments;
  private TreeMap<DLocation, DSequenceSegment> _segmentsForSearch;
  private int              _size;
  private DLocation        _fakeLocation;
  private DAlphabet        _alphabet;
  private DSegmentedRulerModel  _rModel;
  private DSequenceInfo    _dsi;

  /**
   * Constructor with an alphabet.
   */
  public DSegmentedSequence(DAlphabet alphabet){
    //will contain the set of fragments ordered by their starting coordinates;
    //see MDSequenceSortComparator.
    _segments = new TreeMap<DLocation, DSequenceSegment>(new DSequenceSortComparator());
    //will contain the same data as _segments. However _segmentsForSearch is
    //intended to be exclusively used with method getSymbol().
    _segmentsForSearch = new TreeMap<DLocation, DSequenceSegment>();
    _fakeLocation = new DLocation();
    _alphabet = alphabet;
  }

  /**
   * Implementation of DSequence interface.
   * 
   * @param idx value in the range [0..this.size()-1]. This range is the
   * segmented sequence coordinate system.
   */
  public DSymbol getSymbol(int idx){
    DSequenceSegment sBag;
    DSymbol          symbol;

    sBag = getSeqWrapper(idx);
    if (sBag==null)
      return _alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE);
    idx = getCorrectedPosition(sBag, idx);
    symbol = sBag.getSequence().getSymbol(idx);
    return (symbol);
  }
  /**
   * Adjust the idx value and convert it in the coordinate system of the sequence
   * contained in sBag. The returned value is in the range [0..sBag.getSeq().size()-1].
   * The method may return -1 if sBag is null or if idx does not match any symbol 
   * in a translated sequence.
   */
  protected int getCorrectedPosition(DSequenceSegment sBag, int idx){
    DSequence        seq;
    DLocation        loc;
    int              delta;

    if (sBag==null)
      return -1;
    loc = sBag.getLocation();
    seq = sBag.getSequence();
    delta = loc.getTo() - loc.getFrom() + 1;
    if (delta!=seq.size()){//translated sequence ?
      if (((idx-loc.getFrom())%3)==0){
        idx = (idx-loc.getFrom())/3;
      }
      else{
        idx = -1;
      }
    }
    else{
      idx = idx-sBag.getLocation().getFrom();
    }
    return idx;		
  }
  /**
   * Given a position in the segmented sequence coordinate system, returned a
   * DSequenceWrapper. This coordinate system is in the range [0..this.size()-1].
   * If idx does not match any DSequenceWrapper, the method returns null.
   */
  protected DSequenceSegment getSeqWrapper(int idx){
    /* This piece of code makes use of TreeMap keys to quickly locate
     * the fragments containing idx. For that purpose, I create an artificial
     * key (the reusable object _fakeRange of Class SegRange) that is used
     * to fetch the MDSequence containing idx. It is worth noting that 
     * _segmentsForSearch will invoke the compareTo() method of SegRange
     * objects.*/
    _fakeLocation.setFrom(idx);
    _fakeLocation.setTo(idx);

    /* The get() method of _segmentsForSearch will make a call to the compareTo()
     * method of _fakeRange (see TreeMap source code). In that way, the following 
     * call to get() will return the MDSequence which has a key (remember that 
     * those keys are objects from Class SegRange) that CONTAINS _fakeRange.*/
    return ((DSequenceSegment) _segmentsForSearch.get(_fakeLocation));
  }

  protected TreeMap<DLocation, DSequenceSegment> getSegments(){
    return _segments;
  }

  /**
   * Implementation of DSequence interface.
   */ 
  public int size(){
    return (_size);
  }

  /**
   * Sets the size of the segmented sequence.
   */ 
  protected void setSize(int size){
    _size = size;
  }

  /**
   * Adds a new segment to this segmented sequence. It is worth noting
   * that it is not required to add the various segments by ascending
   * positions: all segments added here are automatically sorted in this way
   * by this method.
   * 
   * @param seq the new segment to add.
   * @param loc the position of that segment in the coordinate system
   * of the segmented sequence.
   */ 
  protected void addSegment(DSequence seq, DLocation loc){
    DSequenceSegment  sBag;

    if (seq==null || loc==null)
      return;
    sBag = new DSequenceSegment(seq, loc);
    _segments.put(loc, sBag);
    _segmentsForSearch.put(loc, sBag);
  }

  /**
   * Implementation of DSequence interface. This implementation does not
   * make use of the parameters: the DRulerModel is created using the
   * various DRulerModel contained in the segments (which are DSequence
   * objects).
   * 
   */ 
  public DRulerModel createRulerModel(int startPos, int increment){
    return getRulerModel();
  }

  /**
   * Implementation of DSequence interface. Not used here throws a
   * DSequenceException. 
   */
  public DRulerModel createRulerModel(int[] coord){
    throw new DSequenceException("");
  }

  /**
   * Implementation of DSequence interface.
   */ 
  public DRulerModel getRulerModel(){
    if (_rModel==null)
      _rModel = new DSegmentedRulerModel(this);
    return _rModel;
  }
  public String toString(){
    DSequence    seq;
    StringBuffer szBuf;
    Iterator<DSequenceSegment>     iter;
    DSequenceSegment sBag;
    DRulerModel    rModel;
    szBuf = new StringBuffer();
    szBuf.append("  Segments: "+_segments.size()+"\n");
    iter = _segments.values().iterator();
    while(iter.hasNext()){
      sBag = iter.next();
      szBuf.append("  Segment [");
      szBuf.append(sBag.getLocation().getFrom()+"-");
      szBuf.append(sBag.getLocation().getTo()+"], seqSize [");
      szBuf.append(sBag.getSequence().size()+"], rModel [");
      rModel = sBag.getSequence().getRulerModel();
      szBuf.append(rModel!=null?"true":"false"+"]");
      if (rModel!=null){
        seq = sBag.getSequence();
        szBuf.append(", pos["+rModel.getSeqPos(0));   			
        szBuf.append("-"+rModel.getSeqPos(seq.size()-1)+"]");   			
      }
      szBuf.append("\n");
      //szBuf.append(seq.toString());
    }
    return szBuf.toString();
  }
  /**
   * Implementation of DSequence interface.
   */ 
  public DAlphabet getAlphabet(){
    return _alphabet;
  }
  /**
   * Implementation of DSequence interface.
   */ 
  public List<DLocation> getSequenceParts(){
    DSequenceSegment     sWrapper;
    DLocation            loc;
    ArrayList<DLocation> parts;
    List<DLocation>      partsTmp;
    DSequence            seq;
    Iterator<DSequenceSegment>  iter;
    int                  i, size, decal;

    parts = new ArrayList<DLocation>();
    iter = _segments.values().iterator();
    while(iter.hasNext()){
      sWrapper = (DSequenceSegment) iter.next();
      seq = sWrapper.getSequence();
      decal = sWrapper.getLocation().getFrom();
      partsTmp = seq.getSequenceParts();
      if (partsTmp!=null){
        size = partsTmp.size();
        for(i=0;i<size;i++){
          loc = (DLocation) partsTmp.get(i);
          loc.setFrom(loc.getFrom()+decal);
          loc.setTo(loc.getTo()+decal);
          parts.add(loc);
        }
      }
    }    	
    return parts;
  }
  /**
   * Implementation of DSequence interface.
   */ 
  public int getGapContent(){
    DSequence                   seq;
    Iterator<DSequenceSegment>  iter;
    int                         gaps = 0;

    iter = _segments.values().iterator();
    while(iter.hasNext()){
      seq = ((DSequenceSegment) iter.next()).getSequence();
      gaps += seq.getGapContent();
    }
    return gaps;
  }
  /**
   * Implementation of DSequence interface. Please note that this method is still not
   * implemented. Always return null.
   */ 
  public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse){
    return null;
  }

  /**
   * A Comparator used to sort segments in ascending order of their
   * starting coordinates.
   */
  private class DSequenceSortComparator implements Comparator<DLocation> {
    public int compare(DLocation o1, DLocation o2) {
      if (o1==null || o2==null)
        return 0;
      return ( o1.getFrom() - o2.getFrom() );
    }
  }

  public DSequenceInfo getSequenceInfo(){
    return _dsi;
  }

  public void setSequenceInfo(DSequenceInfo dsi){
    _dsi = dsi;
  }
}
