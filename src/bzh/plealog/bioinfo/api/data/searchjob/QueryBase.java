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
package bzh.plealog.bioinfo.api.data.searchjob;

import java.util.Enumeration;

import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.resources.Messages;

/**
 * This class is a BlastQuery.
 * 
 * @author Patrick G. Durand
 */
public abstract class QueryBase {

  public static final int WAITING = SearchStatus.WAITING;
  public static final int RUNNING = SearchStatus.RUNNING;
  public static final int OK = SearchStatus.OK;
  public static final int ERROR = SearchStatus.ERROR;
  public static final int STOPPED = SearchStatus.STOPPED;
  public static final int UNKNOWN = SearchStatus.UNKNOWN;
  public static final int TIMEOUT = SearchStatus.TIMEOUT;
  public static final int WANT_STOP = SearchStatus.WANT_STOP;
  public static final int CANCELED = SearchStatus.CANCELED;

  // only used for UI, not for serialization
  public static final String[] EXEC_STATUS = { Messages.getString("BlastQuery.status.waiting"),
      Messages.getString("BlastQuery.status.running"), Messages.getString("BlastQuery.status.done"),
      Messages.getString("BlastQuery.status.error"), Messages.getString("BlastQuery.status.stopped"),
      Messages.getString("BlastQuery.status.unknown"), Messages.getString("BlastQuery.status.timeout"),
      Messages.getString("BlastQuery.status.stopping"), Messages.getString("BlastQuery.status.canceled") };

  // public static final String NO_FNAME = "-";
  public static final String STATUS_UNKNWON = "?";

  // following Strings MUST NOT be set to the Strings reported
  // in the EXEC_STATUS table. The following are used for serialization.
  public static final String STATUS_OK = "Ok";
  public static final String STATUS_ERROR = "Error";
  public static final String STATUS_STOP = "Stopped";
  public static final String STATUS_TIMEOUT = "Time out";
  public static final String STATUS_WAITING = "Waiting";
  public static final String STATUS_RUNNING = "Running";
  public static final String STATUS_CANCELED = "Canceled";

  

  public QueryBase() {
  }

  /* == Global query section  ===================================================*/
  /**
   * Return the path to the place containing the data of this query.
   */
  public abstract String getQueryPath();

  /**
   * Return the name of the Blast Job.
   */
  public abstract String getJobName();

  /**
   * Return the name of the databank.
   **/
  public abstract String getDatabankName();

  /**
   * Return the name of the search engine.
   */
  public abstract String getEngineSysName();

  /**
   * Returns true if all sequences have been indexed.
   */
  public abstract boolean allSequencesIndexed();

  /**
   * Returns number of sequences contained in this QueryBase.
   */
  public abstract int sequences();

  /**
   * Returns the unique Request ID of this QueryBase.
   */
  public abstract String getRID();

  /**
   * Returns query status.
   */
  public abstract int getStatus();

  /**
   * Returns an enumeration over all query result summary objects.
   */
  public abstract Enumeration<SJFileSummary> getSummaries();
  
  /**
   * Count how many queries has a particular status.
   */
  public abstract int countStatuses(byte status);
  
  /* == Per query section  ===================================================*/
  /**
   * Returns a sequence.
   */
  public abstract DSequence getSequence(int queryNum);

  /**
   * Returns a summary.
   */
  public abstract SJFileSummary getSummary(int queryNum);

  /**
   * Returns specific query execution status.
   */
  public abstract String getStatus(int queryNum);
  
  /**
   * Returns true if a specific query got some hits.
   */
  public abstract boolean hasHits(int queryNum);
  
  /**
   * Returns results for a specific query.
   */
  public abstract SROutput getResult(int queryNum);
  

}