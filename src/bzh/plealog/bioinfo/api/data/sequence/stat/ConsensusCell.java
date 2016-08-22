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
package bzh.plealog.bioinfo.api.data.sequence.stat;

import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This class stores the data related to a single position within
 * a consensus sequence.
 * 
 * @author Patrick G. Durand
 */
public class ConsensusCell {
	private int           iColumnPos_;
    private String        columnPos_;
	private List<DSymbol> consensus_;
	private DSymbol       mainSymbol_;
	
	/**
	 * Constructor.
	 * @param colPos position within the consensus. Returned value is one-based.
	 * @param consensus List of DSymbol at that position.
	 */
	public ConsensusCell(int colPos, List<DSymbol> consensus){
        iColumnPos_ = colPos;
        columnPos_ = String.valueOf(colPos);
		setConsensus(consensus);
	}

	/**
	 * Returns position within the consensus. Returned value is one-based.
	 */
	public String getColumnPos() {
		return columnPos_;
	}
    /**
     * Returns position within the consensus. Returned value is one-based.
     */
    public int getColumnPos_i() {
        return iColumnPos_;
    }
	/**
	 * Sets position within the consensus. Parameter columnPos is String
	 * for performance issue related to GUI display.
	 */
	public void setColumnPos(String columnPos) {
		this.columnPos_ = columnPos;
	}
	
	/**
	 * Returns the list of DSymbol at the position of this cell.
	 */
	public List<DSymbol> getConsensus() {
		return consensus_;
	}
	
	/**
	 * Sets the list of DSymbol at the position of this cell.
	 */
	public void setConsensus(List<DSymbol> consensus) {
		this.consensus_ = consensus;
	}

	public DSymbol getMainSymbol() {
		return mainSymbol_;
	}

	public void setMainSymbol_(DSymbol mainSymbol) {
		mainSymbol_ = mainSymbol;
	}
	
}
