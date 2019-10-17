//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.10 at 12:48:55 PM GMT 
//


package bzh.plealog.bioinfo.data.blast.loader.ncbi2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Results_iterations">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Iteration"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Results_search">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Search"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Results_bl2seq">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Search"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "resultsIterations",
    "resultsSearch",
    "resultsBl2Seq"
})
@XmlRootElement(name = "Results")
public class Results {

    @XmlElement(name = "Results_iterations")
    protected Results.ResultsIterations resultsIterations;
    @XmlElement(name = "Results_search")
    protected Results.ResultsSearch resultsSearch;
    @XmlElement(name = "Results_bl2seq")
    protected Results.ResultsBl2Seq resultsBl2Seq;

    /**
     * Gets the value of the resultsIterations property.
     * 
     * @return
     *     possible object is
     *     {@link Results.ResultsIterations }
     *     
     */
    public Results.ResultsIterations getResultsIterations() {
        return resultsIterations;
    }

    /**
     * Sets the value of the resultsIterations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Results.ResultsIterations }
     *     
     */
    public void setResultsIterations(Results.ResultsIterations value) {
        this.resultsIterations = value;
    }

    /**
     * Gets the value of the resultsSearch property.
     * 
     * @return
     *     possible object is
     *     {@link Results.ResultsSearch }
     *     
     */
    public Results.ResultsSearch getResultsSearch() {
        return resultsSearch;
    }

    /**
     * Sets the value of the resultsSearch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Results.ResultsSearch }
     *     
     */
    public void setResultsSearch(Results.ResultsSearch value) {
        this.resultsSearch = value;
    }

    /**
     * Gets the value of the resultsBl2Seq property.
     * 
     * @return
     *     possible object is
     *     {@link Results.ResultsBl2Seq }
     *     
     */
    public Results.ResultsBl2Seq getResultsBl2Seq() {
        return resultsBl2Seq;
    }

    /**
     * Sets the value of the resultsBl2Seq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Results.ResultsBl2Seq }
     *     
     */
    public void setResultsBl2Seq(Results.ResultsBl2Seq value) {
        this.resultsBl2Seq = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Search"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "search"
    })
    public static class ResultsBl2Seq {

        @XmlElement(name = "Search")
        protected List<Search> search;

        /**
         * Gets the value of the search property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the search property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSearch().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Search }
         * 
         * 
         */
        public List<Search> getSearch() {
            if (search == null) {
                search = new ArrayList<Search>();
            }
            return this.search;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Iteration"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "iteration"
    })
    public static class ResultsIterations {

        @XmlElement(name = "Iteration")
        protected List<Iteration> iteration;

        /**
         * Gets the value of the iteration property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the iteration property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIteration().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Iteration }
         * 
         * 
         */
        public List<Iteration> getIteration() {
            if (iteration == null) {
                iteration = new ArrayList<Iteration>();
            }
            return this.iteration;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Search"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "search"
    })
    public static class ResultsSearch {

        @XmlElement(name = "Search", required = true)
        protected Search search;

        /**
         * Gets the value of the search property.
         * 
         * @return
         *     possible object is
         *     {@link Search }
         *     
         */
        public Search getSearch() {
            return search;
        }

        /**
         * Sets the value of the search property.
         * 
         * @param value
         *     allowed object is
         *     {@link Search }
         *     
         */
        public void setSearch(Search value) {
            this.search = value;
        }

    }

}
