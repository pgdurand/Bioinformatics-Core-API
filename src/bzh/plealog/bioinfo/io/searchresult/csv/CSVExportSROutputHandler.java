/* Copyright (C) 2006-2018 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.searchresult.csv;

/**
 * Utility interface to handle programmatic text formatting during CSVExportSROutput.
 * 
 * @author Patrick G. Durand
 */
public interface CSVExportSROutputHandler {
  /**
   * Implement this method to handle additional text formatting during CSVExportSROutput.
   * 
   * @param s a formatted text
   * @param colType that text corresponds to a particular column. 
   * One of TxtExportSROutput.XXX values (e.g. HIT_NUM, etc.) 
   * 
   * @return a string
   */
  public String handle(String s, int colType);
}
