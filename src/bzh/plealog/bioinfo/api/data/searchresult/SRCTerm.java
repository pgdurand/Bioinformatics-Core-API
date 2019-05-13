/* Copyright (C) 2019 Patrick G. Durand
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

import java.io.Serializable;

/**
 * A Classification Term.
 * 
 * @author Patrick G. Durand
 */
public interface SRCTerm extends Serializable {
  /**
   * Return a classification path. Can be null.
   */
  public String getPath();
  /**
   * Set a classification path.
   * 
   * @param path the path
   * */
  public void setPath (String path);
  /**
   * Return a description.
   */
  public String getDescription();
  /**
   * Set a classification term description.
   * 
   * @param desc the description
   * */
  public void setDescription(String desc);
}
