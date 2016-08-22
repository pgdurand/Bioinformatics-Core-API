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

public class DSequenceSegmented implements DSequence {
	private TreeMap<DLocation, DSequenceWrapper>          _segments;
	private TreeMap<DLocation, DSequenceWrapper>          _segmentsForSearch;
	private int              _size;
	private DLocation        _fakeLocation;
	private DAlphabet        _alphabet;
	private DSequenceInfo    _dsi;

	public DSequenceSegmented(DAlphabet alphabet){
		//will contain the set of fragments ordered by their starting coordinates;
		//see MDSequenceSortComparator.
		_segments = new TreeMap<DLocation, DSequenceWrapper>(new DSequenceSortComparator());
		//will contain the same data as _segments. However _segmentsForSearch is
		//intended to be exclusively used with method getSymbol().
		_segmentsForSearch = new TreeMap<DLocation, DSequenceWrapper>();
		_fakeLocation = new DLocation();
		_alphabet = alphabet;
	}

	public DSymbol getSymbol(int idx){
		DSequence seq;
		DSequenceWrapper   sBag;

		/* This piece of code makes use of TreeMap keys to quickly locate
		 * the fragments containing idx. For that purpose, I create an artificial
		 * key (the reusable object _fakeRange of Class SegRange) that is used
		 * to fetch the MDSequence containing idx. It is worth noting that 
		 * _segmentsForSearch will invoke the compareTo() method of SegRange
		 * objects.*/
		_fakeLocation.setFrom(idx);
		_fakeLocation.setTo(idx);

		//the get() method of _segmentsForSearch will make a call to the compareTo()
		//method of _fakeRange (see TreeMap source code). In that way, the following 
		//call to get() will return the MDSequence which has a key (remember that 
		//those keys are objects from Class SegRange) that CONTAINS _fakeRange.
		sBag = (DSequenceWrapper) _segmentsForSearch.get(_fakeLocation);
		if (sBag==null)
			return _alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE);
		seq = sBag.getSeq();
		return (seq.getSymbol(idx-sBag.getLocation().getFrom()));
	}

	public int size(){
		return (_size);
	}

	protected void setSize(int size){
		_size = size;
	}

	protected void addSegment(DSequence seq, DLocation loc){
		DSequenceWrapper  sBag;

		if (seq==null || loc==null)
			return;
		sBag = new DSequenceWrapper(seq, loc);
		_segments.put(loc, sBag);
		_segmentsForSearch.put(loc, sBag);
	}

	public DRulerModel createRulerModel(int startPos, int increment){
		return null;
	}
	/**
	 * Implementation of DSequence interface. Not used here throws a
	 * DSequenceException. 
	 */
	public DRulerModel createRulerModel(int[] coord){
		throw new DSequenceException("");
	}
	public DRulerModel getRulerModel(){
		return null;
	}
	public String toString(){
		DSequence    seq;
		StringBuffer szBuf;
		Iterator<DSequenceWrapper>     iter;

		szBuf = new StringBuffer();
		iter = _segments.values().iterator();
		while(iter.hasNext()){
			seq = iter.next().getSeq();
			szBuf.append(seq.toString());
		}
		return szBuf.toString();
	}
	public DAlphabet getAlphabet(){
		return _alphabet;
	}
	public List<DLocation> getSequenceParts(){
		DSequenceWrapper     sWrapper;
		DLocation            loc;
		ArrayList<DLocation> parts;
		List<DLocation>      partsTmp;
		DSequence            seq;
		Iterator<DSequenceWrapper> iter;
		int                  i, size, decal;

		parts = new ArrayList<DLocation>();
		iter = _segments.values().iterator();
		while(iter.hasNext()){
			sWrapper = iter.next();
			seq = sWrapper.getSeq();
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
	public int getGapContent(){
		DSequence                  seq;
		Iterator<DSequenceWrapper> iter;
		int                        gaps = 0;

		iter = _segments.values().iterator();
		while(iter.hasNext()){
			seq = iter.next().getSeq();
			gaps += seq.getGapContent();
		}
		return gaps;
	}
	public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse){
		return null;
	}

	private class DSequenceWrapper {
		private DSequence seq_;
		private DLocation loc_;

		public DSequenceWrapper(DSequence seq, DLocation loc){
			setSeq(seq);
			setLocation(loc);
		}

		public DSequence getSeq() {
			return seq_;
		}

		public void setSeq(DSequence seq) {
			this.seq_ = seq;
		}

		public DLocation getLocation() {
			return loc_;
		}

		public void setLocation(DLocation loc) {
			this.loc_ = loc;
		}

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
