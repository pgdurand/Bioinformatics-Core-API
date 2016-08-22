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
 * Class BlastOutput_iterations.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class BlastOutput_iterations implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = 3027855021026119021L;
    /**
     * Field _iterationList.
     */
    private java.util.List<bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration> _iterationList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BlastOutput_iterations() {
        super();
        this._iterationList = new java.util.ArrayList<bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vIteration
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addIteration(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration vIteration)
    throws java.lang.IndexOutOfBoundsException {
        this._iterationList.add(vIteration);
    }

    /**
     * 
     * 
     * @param index
     * @param vIteration
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addIteration(
            final int index,
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration vIteration)
    throws java.lang.IndexOutOfBoundsException {
        this._iterationList.add(index, vIteration);
    }

    /**
     * Method enumerateIteration.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration> enumerateIteration(
    ) {
        return java.util.Collections.enumeration(this._iterationList);
    }

    /**
     * Method getIteration.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.plealog.bioinfo.data.blast.loader.ncbi.Iteration at
     * the given index
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration getIteration(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._iterationList.size()) {
            throw new IndexOutOfBoundsException("getIteration: Index value '" + index + "' not in range [0.." + (this._iterationList.size() - 1) + "]");
        }
        
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration) _iterationList.get(index);
    }

    /**
     * Method getIteration.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration[] getIteration(
    ) {
        bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration[] array = new bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration[0];
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration[]) this._iterationList.toArray(array);
    }

    /**
     * Method getIterationCount.
     * 
     * @return the size of this collection
     */
    public int getIterationCount(
    ) {
        return this._iterationList.size();
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
     * Method iterateIteration.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration> iterateIteration(
    ) {
        return this._iterationList.iterator();
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
    public void removeAllIteration(
    ) {
        this._iterationList.clear();
    }

    /**
     * Method removeIteration.
     * 
     * @param vIteration
     * @return true if the object was removed from the collection.
     */
    public boolean removeIteration(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration vIteration) {
        boolean removed = _iterationList.remove(vIteration);
        return removed;
    }

    /**
     * Method removeIterationAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration removeIterationAt(
            final int index) {
        java.lang.Object obj = this._iterationList.remove(index);
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vIteration
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setIteration(
            final int index,
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration vIteration)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._iterationList.size()) {
            throw new IndexOutOfBoundsException("setIteration: Index value '" + index + "' not in range [0.." + (this._iterationList.size() - 1) + "]");
        }
        
        this._iterationList.set(index, vIteration);
    }

    /**
     * 
     * 
     * @param vIterationArray
     */
    public void setIteration(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration[] vIterationArray) {
        //-- copy array
        _iterationList.clear();
        
        for (int i = 0; i < vIterationArray.length; i++) {
                this._iterationList.add(vIterationArray[i]);
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations.class, reader);
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
