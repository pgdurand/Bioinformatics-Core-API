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
 * This class represents a position on a sequence.
 * 
 * @author Patrick G. Durand
 */
public class DLocation implements Comparable<DLocation> {
	private int from_;
	private int to_;
	private int strand_;
	
	public static final int STRAND_PLUS  = 0;
	public static final int STRAND_MINUS = 1;
	
	public DLocation(){}

	/**
	 * Constructor with from and to. Strand is plus by default.
	 */
	public DLocation(int from, int to){
		setFrom(from);
		setTo(to);
	}

	/**
	 * Constructor with from, to and strand.
	 * 
	 * @param strand one of STRAND_XXX constants defined in this class.
	 */
	public DLocation(int from, int to, int strand){
		this(from, to);
		setStrand(strand);
	}

	/**
	 * Returns the starting position of this location.
	 */
	public int getFrom() {
		return from_;
	}

	/**
	 * Sets the starting position of this location.
	 * 
	 * @param from zero-base value
	 */
	public void setFrom(int from) {
		this.from_ = from;
	}

	/**
	 * Returns the strand of this location.
	 * 
	 * @return one of STRAND_XXX constants defined in this class.
	 */
	public int getStrand() {
		return strand_;
	}

	/**
	 * Sets the strand of this location.
	 * 
	 * @param strand one of STRAND_XXX constants defined in this class.
	 */
	public void setStrand(int strand) {
		this.strand_ = strand;
	}

	/**
	 * Returns the ending position of this location.
	 */
	public int getTo() {
		return to_;
	}

	/**
	 * Sets the ending position of this location.
	 * 
	 * @param to zero-base value
	 */
	public void setTo(int to) {
		this.to_ = to;
	}

	public String toString(){
		return (from_+"-"+to_);
	}
	/**
     * Implements Comparable method so that two DLocation are
     * considered the same if they overlap.
     */
  @Override
  public int compareTo(DLocation o) {
    DLocation seq;
    if (o != null){
        seq = (DLocation) o;
        if (seq.from_<=this.to_ && seq.to_>=this.from_)
          return 0;
        else
          return this.from_ - seq.from_;
    }
    return 0;
  }
}
