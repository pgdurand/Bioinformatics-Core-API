/* Copyright (C) 2003-2019 Patrick G. Durand
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
package bzh.plealog.bioinfo.data.searchjob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import bzh.plealog.bioinfo.api.data.searchjob.QueryBase;
import bzh.plealog.bioinfo.api.data.searchjob.SJFileSummary;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;

/**
 * RAM based implementation of QueryBase.
 * 
 * @author Patrick G. Durand
 * */
public class InMemoryQuery extends QueryBase {

  private ArrayList<SROutput> _results;
  private ArrayList<SJFileSummary> _summaries;
  private ArrayList<String> _statuses;
  private ArrayList<Byte> _statuses_b;
  
  private String _queryPath = UNK;
  private String _jobName = UNK;
  private String _bankName = UNK;
  private String _engineSysName = UNK;
  private String _rid = UNK;
  private int _status = QueryBase.UNKNOWN;
  
  private static String UNK = "";
  
  /**
   * Constructor.
   */
  public InMemoryQuery() {
    _results = new ArrayList<>();
    _summaries = new ArrayList<>();
    _statuses = new ArrayList<>();
    _statuses_b = new ArrayList<>();
  }

  /**
   * Add new result to this query.
   * 
   * @param result a SROutput object. Never pass in a null value.
   */
  public void addResult(SROutput result) {
    _results.add(result);
    SJFileSummary bfs = new SJFileSummary();
    bfs.initialize(result);
    _summaries.add(bfs);
    _statuses.add(QueryBase.STATUS_OK);
    _statuses_b.add((byte) QueryBase.OK);
  }
  
  @Override
  public String getQueryPath() {
    return _queryPath;
  }

  public void setQueyPath(String str) {
    _queryPath = str;
  }
  
  @Override
  public String getJobName() {
    return _jobName;
  }

  public void setJobName(String str) {
    _jobName = str;
  }
  
  @Override
  public String getDatabankName() {
    return _bankName;
  }

  public void setDatabankName(String str) {
    _bankName = str;
  }
  
  @Override
  public String getEngineSysName() {
    return _engineSysName;
  }

  public void setEngineSysName(String str) {
    _engineSysName = str;
  }
  
  @Override
  public boolean allSequencesIndexed() {
    return true;
  }

  @Override
  public int sequences() {
    return _results.size();
  }

  @Override
  public String getRID() {
    return _rid;
  }

  public void setRID(String str) {
    _rid = str;
  }
  
  @Override
  public int getStatus() {
    return _status;
  }
  
  public void setStatus(int status) {
    _status = status;
  }

  @Override
  public Enumeration<SJFileSummary> getSummaries() {
    return Collections.enumeration(_summaries);
  }

  @Override
  public int countStatuses(byte status) {
    int counter = 0;
    for(int i=0;i<_statuses.size();i++) {
      if (status == _statuses_b.get(i)) {
        counter++;
      }
    }
    return counter;
  }

  @Override
  public DSequence getSequence(int queryNum) {
    return null;
  }

  @Override
  public SJFileSummary getSummary(int queryNum) {
    return _summaries.get(queryNum);
  }

  @Override
  public String getStatus(int queryNum) {
    return _statuses.get(queryNum);
  }

  @Override
  public boolean hasHits(int queryNum) {
    return _summaries.get(queryNum).getNHits()!=0;
  }

  @Override
  public SROutput getResult(int queryNum) {
    return _results.get(queryNum);
  }

}
