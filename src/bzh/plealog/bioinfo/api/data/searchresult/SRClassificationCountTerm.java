/* Copyright (C) 2020 Patrick G. Durand
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
package bzh.plealog.bioinfo.api.data.searchresult;

import bzh.plealog.bioinfo.api.data.searchjob.SJTermSummary;

public class SRClassificationCountTerm implements Comparable<SRClassificationCountTerm>{
  private SJTermSummary term;
  private int hCount;
  private int qCount;
  private double percent;
  
  public enum TYPE {QUERY, HIT}
  
  public SRClassificationCountTerm(SJTermSummary term, int hCount, int qCount) {
    super();
    this.term = term;
    this.hCount = hCount;
    this.qCount = qCount;
  }

  public SJTermSummary getTerm() {
    return term;
  }

  public void setTerm(SJTermSummary term) {
    this.term = term;
  }

  public int getHitCount() {
    return hCount;
  }

  public void setHitCount(int count) {
    this.hCount = count;
  }

  public int getQueryCount() {
    return qCount;
  }

  public void setQueryCount(int count) {
    this.qCount = count;
  }

  public int getCount() {
    return getHitCount()+getQueryCount();
  }
  
  public double getPercent() {
    return percent;
  }

  public void setPercent(double percent) {
    this.percent = percent;
  }
  
  @Override
  public int compareTo(SRClassificationCountTerm t) {
    return Integer.compare(getCount(), t.getCount());
  }
}