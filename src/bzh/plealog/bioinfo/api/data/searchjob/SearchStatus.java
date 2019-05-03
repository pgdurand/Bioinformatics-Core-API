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

/**
 * This interface defines the search status.
 * 
 * @author Patrick G. Durand
 */
public interface SearchStatus {
	//never change these values. Used for serialization.
	public static final int WAITING = 0;
	public static final int RUNNING = 1;
	public static final int OK = 2;
	public static final int ERROR = 3;
	public static final int STOPPED = 4;
	public static final int UNKNOWN = 5;
	public static final int TIMEOUT = 6;
	public static final int WANT_STOP = 7;
	public static final int CANCELED = 8;

}
