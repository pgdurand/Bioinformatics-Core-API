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

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;

/**
 * This interface defines a sequence descriptor. 
 * 
 * @author Patrick G. Durand
 */
public class BankSequenceDescriptor {
	private FeatureTable ft;
	private BankSequenceInfo si;
	private DSequence    se;
	
	public BankSequenceDescriptor(FeatureTable ft, BankSequenceInfo si){
		setFeatureTable(ft);
		setSequenceInfo(si);
	}
	public BankSequenceDescriptor(FeatureTable ft, BankSequenceInfo si, DSequence se){
		setFeatureTable(ft);
		setSequenceInfo(si);
		setSequence(se);
	}
	/**
	 * Returns a FeatureTable.
	 */
	public FeatureTable getFeatureTable() {
		return ft;
	}
	/**
	 * Sets a FeatureTable.
	 */
	public void setFeatureTable(FeatureTable ft) {
		this.ft = ft;
	}
	/**
	 * Returns a SequenceInfo.
	 */
	public BankSequenceInfo getSequenceInfo() {
		return si;
	}
	/**
	 * Sets a SequenceInfo.
	 */
	public void setSequenceInfo(BankSequenceInfo si) {
		this.si = si;
	}
	/**
	 * Returns a sequence.
	 */
	public DSequence getSequence() {
		return se;
	}
	/**
	 * Sets a sequence.
	 */
	public void setSequence(DSequence se) {
		this.se = se;
	}
	
}
