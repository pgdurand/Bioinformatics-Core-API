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
package bzh.plealog.bioinfo.io.searchresult.srnative;

/**
 * This class defines the excpetion thrown by IXMLSerializer load and save
 * mthods. 
 * 
 * @author Patrick G. Durand
 */
public class BlastSerializerException extends RuntimeException {

	private static final long serialVersionUID = 2913484106085059956L;

	public BlastSerializerException(String msg){
		super(msg);
	}
}
