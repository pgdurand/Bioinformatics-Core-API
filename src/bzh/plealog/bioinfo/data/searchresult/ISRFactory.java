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
package bzh.plealog.bioinfo.data.searchresult;

import bzh.plealog.bioinfo.api.data.searchresult.SRCTerm;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspPattern;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspScore;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRParameters;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.searchresult.SRStatistics;
import bzh.plealog.bioinfo.api.data.searchresult.utils.SRFactory;

public class ISRFactory implements SRFactory {

	public SROutput createBOutput() {
		return new ISROutput();
	}

	public SRIteration createBIteration() {
		return new ISRIteration();
	}

	public SRHit createBHit() {
		return new ISRHit();
	}

	public SRHsp createBHsp() {
		return new ISRHsp();
	}

	public SRHspScore createBHspScore() {
		return new ISRHspScore();
	}

	public SRHspSequence createBHspSequence() {
		return new ISRHspSequence();
	}

	public SRHspPattern createBHspPattern() {
		return null;
	}

	public SRParameters createBParameters() {
		return new ISRParameters();
	}

	public SRStatistics createBStatistics() {
		return new ISRStatistics();
	}
	public SRRequestInfo createBRequestInfo(){
		return new ISRRequestInfo();
	}

  @Override
  public SRClassification creationBClassification() {
    return new ISRClassification();
  }

  @Override
  public SRCTerm creationBTerm() {
    return new ISRCTerm();
  }
}
