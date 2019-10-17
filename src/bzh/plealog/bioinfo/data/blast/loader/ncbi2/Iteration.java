//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.10.17 à 11:24:31 AM CEST 
//


package bzh.plealog.bioinfo.data.blast.loader.ncbi2;

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
 *       &lt;sequence>
 *         &lt;element name="Iteration_iter-num" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Iteration_search">
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
    "iterationIterNum",
    "iterationSearch"
})
@XmlRootElement(name = "Iteration")
public class Iteration {

    @XmlElement(name = "iter-num", required = true)
    protected Integer iterationIterNum;
    @XmlElement(name = "search", required = true)
    protected Iteration.IterationSearch iterationSearch;

    /**
     * Obtient la valeur de la propriété iterationIterNum.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIterationIterNum() {
        return iterationIterNum;
    }

    /**
     * Définit la valeur de la propriété iterationIterNum.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIterationIterNum(Integer value) {
        this.iterationIterNum = value;
    }

    /**
     * Obtient la valeur de la propriété iterationSearch.
     * 
     * @return
     *     possible object is
     *     {@link Iteration.IterationSearch }
     *     
     */
    public Iteration.IterationSearch getIterationSearch() {
        return iterationSearch;
    }

    /**
     * Définit la valeur de la propriété iterationSearch.
     * 
     * @param value
     *     allowed object is
     *     {@link Iteration.IterationSearch }
     *     
     */
    public void setIterationSearch(Iteration.IterationSearch value) {
        this.iterationSearch = value;
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
    public static class IterationSearch {

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
