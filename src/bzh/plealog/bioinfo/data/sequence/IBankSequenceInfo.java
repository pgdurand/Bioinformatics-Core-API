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
package bzh.plealog.bioinfo.data.sequence;

import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;

/**
 * This is a default implementation of SequenceInfo interface.
 * 
 * @author Patrick G. Durand
 */
public class IBankSequenceInfo implements BankSequenceInfo {
	private static final long serialVersionUID = 3059424354509990621L;
	/*Important: be aware that this class is used for serialization using
	 * the XStream framework.*/
	/**
	 * @serial
	 */
    @SuppressWarnings("unused")
    private int    xmlVersion = 1;
	/**
	 * @serial
	 */
	private String type;
	/**
	 * @serial
	 */
	private String topo;
	/**
	 * @serial
	 */
	private String div;
	/**
	 * @serial
	 */
    private String org;
	/**
	 * @serial
	 */
	private String tax;
	/**
	 * @serial
	 */
	private int    cdate;
	/**
	 * @serial
	 */
	private int    udate;
	/**
	 * @serial
	 */
	private String id;
	/**
	 * @serial
	 */
	private String description;
	/**
	 * @serial
	 */
	private int    size;
	
	public String getMoltype(){
		return type;
	}
	public void setMoltype(String mtype){
		this.type = mtype;
	}

	public String getTopology(){
		return topo;
	}
	public void setTopology(String topo){
		this.topo = topo;
	}

	public String getDivision(){
		return div;
	}
	public void setDivision(String div){
		this.div = div;
	}

	public int getCreationDate() {
		return cdate;
	}

	public void setCreationDate(int d) {
		cdate=d;
	}

	public String getOrganism() {
		return org;
	}

	public void setOrganism(String org) {
		this.org=org;
	}

	public String getTaxonomy() {
		return tax;
	}

	public void setTaxonomy(String tax) {
		this.tax=tax;
	}

	public int getUpdateDate() {
		return udate;
	}

	public void setUpdateDate(int d) {
		udate=d;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Object clone(){
		IBankSequenceInfo isi = new IBankSequenceInfo();
		isi.copy(this);
		return isi;
	}
	
	public void copy(BankSequenceInfo src){
		this.setMoltype(src.getMoltype());
		this.setTopology(src.getTopology());
		this.setDivision(src.getDivision());
		this.setOrganism(src.getOrganism());
		this.setTaxonomy(src.getTaxonomy());
		this.setCreationDate(src.getCreationDate());
		this.setUpdateDate(src.getUpdateDate());
		this.setId(src.getId());
		this.setDescription(src.getDescription());
		this.setSequenceSize(src.getSequenceSize());
	}
	public int getSequenceSize() {
		return size;
	}
	public void setSequenceSize(int size) {
		this.size = size;
	}
}
