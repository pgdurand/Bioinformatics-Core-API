//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.10.17 à 11:24:31 AM CEST 
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
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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

    @XmlElement(name = "iterations")
    protected Results.ResultsIterations resultsIterations;
    @XmlElement(name = "search")
    protected Results.ResultsSearch resultsSearch;
    @XmlElement(name = "bl2seq")
    protected Results.ResultsBl2Seq resultsBl2Seq;

    /**
     * Obtient la valeur de la propriété resultsIterations.
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
     * Définit la valeur de la propriété resultsIterations.
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
     * Obtient la valeur de la propriété resultsSearch.
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
     * Définit la valeur de la propriété resultsSearch.
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
     * Obtient la valeur de la propriété resultsBl2Seq.
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
     * Définit la valeur de la propriété resultsBl2Seq.
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
         * Obtient la valeur de la propriété search.
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
         * Définit la valeur de la propriété search.
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
