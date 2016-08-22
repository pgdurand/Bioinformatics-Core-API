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

package bzh.plealog.bioinfo.data.blast.loader.ncbi;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Iteration_hits.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Iteration_hits implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = 292583270121102880L;
    /**
     * Field _hitList.
     */
    private java.util.List<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit> _hitList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Iteration_hits() {
        super();
        this._hitList = new java.util.ArrayList<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vHit
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHit(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit vHit)
    throws java.lang.IndexOutOfBoundsException {
        this._hitList.add(vHit);
    }

    /**
     * 
     * 
     * @param index
     * @param vHit
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHit(
            final int index,
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit vHit)
    throws java.lang.IndexOutOfBoundsException {
        this._hitList.add(index, vHit);
    }

    /**
     * Method enumerateHit.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit> enumerateHit(
    ) {
        return java.util.Collections.enumeration(this._hitList);
    }

    /**
     * Method getHit.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.plealog.bioinfo.data.blast.loader.ncbi.Hit at the
     * given index
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit getHit(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._hitList.size()) {
            throw new IndexOutOfBoundsException("getHit: Index value '" + index + "' not in range [0.." + (this._hitList.size() - 1) + "]");
        }
        
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit) _hitList.get(index);
    }

    /**
     * Method getHit.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit[] getHit(
    ) {
        bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit[] array = new bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit[0];
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit[]) this._hitList.toArray(array);
    }

    /**
     * Method getHitCount.
     * 
     * @return the size of this collection
     */
    public int getHitCount(
    ) {
        return this._hitList.size();
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Method iterateHit.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit> iterateHit(
    ) {
        return this._hitList.iterator();
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, handler);
    }

    /**
     */
    public void removeAllHit(
    ) {
        this._hitList.clear();
    }

    /**
     * Method removeHit.
     * 
     * @param vHit
     * @return true if the object was removed from the collection.
     */
    public boolean removeHit(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit vHit) {
        boolean removed = _hitList.remove(vHit);
        return removed;
    }

    /**
     * Method removeHitAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit removeHitAt(
            final int index) {
        java.lang.Object obj = this._hitList.remove(index);
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vHit
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setHit(
            final int index,
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit vHit)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._hitList.size()) {
            throw new IndexOutOfBoundsException("setHit: Index value '" + index + "' not in range [0.." + (this._hitList.size() - 1) + "]");
        }
        
        this._hitList.set(index, vHit);
    }

    /**
     * 
     * 
     * @param vHitArray
     */
    public void setHit(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit[] vHitArray) {
        //-- copy array
        _hitList.clear();
        
        for (int i = 0; i < vHitArray.length; i++) {
                this._hitList.add(vHitArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * com.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
