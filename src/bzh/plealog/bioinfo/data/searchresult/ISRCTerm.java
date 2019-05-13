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
package bzh.plealog.bioinfo.data.searchresult;

import bzh.plealog.bioinfo.api.data.searchresult.SRCTerm;

/**
 * This is a default implementation of interface SRCTerm.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRCTerm
 */
public class ISRCTerm implements SRCTerm {
  private static final long serialVersionUID = -6026541098625860919L;
  private String path;
  private String desc;
  
  public ISRCTerm() {
    
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public void setPath(String path) {
    this.path = path;
  }

  @Override
  public String getDescription() {
    return desc;
  }

  @Override
  public void setDescription(String desc) {
    this.desc = desc;
  }

}
