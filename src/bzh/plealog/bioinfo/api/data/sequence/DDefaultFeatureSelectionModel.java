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

import java.util.ArrayList;

/**
 * This class defines a Feature Selection model.
 * 
 * @author Patrick G. Durand
 */
public class DDefaultFeatureSelectionModel implements DFeatureSelectionModel {
  private ArrayList<FeatIntervalSelection> intervals_;

  public DDefaultFeatureSelectionModel(){
    intervals_ = new ArrayList<FeatIntervalSelection>();
  }
  /**
   * @see bzh.plealog.bioinfo.api.data.sequence.DFeatureSelectionModel#isEmpty()
   */
  public boolean isEmpty() {
    return intervals_.isEmpty();
  }

  /**
   * @see bzh.plealog.bioinfo.api.data.sequence.DFeatureSelectionModel#clear()
   */
  public void clear() {
    intervals_.clear();
  }

  /** 
   * @param from sequence coordinates
   * @param to sequence coordinates
   * @see bzh.plealog.bioinfo.api.data.sequence.DFeatureSelectionModel#addSelection(int, int)
   */
  public void addSelection(int from, int to) {
    intervals_.add(new FeatIntervalSelection(from, to));
  }

  /** 
   * @param coord sequence coordinates
   * @see bzh.plealog.bioinfo.api.data.sequence.DFeatureSelectionModel#isSelected(int)
   */
  public boolean isSelected(int coord) {
    FeatIntervalSelection interval;
    int                   i, size;

    size = intervals_.size();
    for(i=0;i<size;i++){
      interval = (FeatIntervalSelection) intervals_.get(i);
      if (interval.contains(coord))
        return true;
    }
    return false;
  }

  private class FeatIntervalSelection{
    private int from_;//seq coord   
    private int to_;//seq coord


    public FeatIntervalSelection(int from, int to) {
      this.from_ = from;
      this.to_ = to;
    }
    public boolean contains(int val){
      return ((val>=from_ && val<=to_) ? true : false);
    }
  }
}
