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
 *       &lt;sequence>
 *         &lt;element name="Search_query-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Search_query-title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Search_query-len" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Search_query-masking" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Range"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Search_hits" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Hit"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Search_stat" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Statistics"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Search_message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "searchQueryId",
    "searchQueryTitle",
    "searchQueryLen",
    "searchQueryMasking",
    "searchHits",
    "searchStat",
    "searchMessage"
})
@XmlRootElement(name = "Search")
public class Search {

    @XmlElement(name = "query-id")
    protected String searchQueryId;
    @XmlElement(name = "query-title")
    protected String searchQueryTitle;
    @XmlElement(name = "query-len")
    protected Integer searchQueryLen;
    @XmlElement(name = "query-masking")
    protected Search.SearchQueryMasking searchQueryMasking;
    @XmlElement(name = "hits")
    protected Search.SearchHits searchHits;
    @XmlElement(name = "stat")
    protected Search.SearchStat searchStat;
    @XmlElement(name = "message")
    protected String searchMessage;

    /**
     * Obtient la valeur de la propriété searchQueryId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchQueryId() {
        return searchQueryId;
    }

    /**
     * Définit la valeur de la propriété searchQueryId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchQueryId(String value) {
        this.searchQueryId = value;
    }

    /**
     * Obtient la valeur de la propriété searchQueryTitle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchQueryTitle() {
        return searchQueryTitle;
    }

    /**
     * Définit la valeur de la propriété searchQueryTitle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchQueryTitle(String value) {
        this.searchQueryTitle = value;
    }

    /**
     * Obtient la valeur de la propriété searchQueryLen.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSearchQueryLen() {
        return searchQueryLen;
    }

    /**
     * Définit la valeur de la propriété searchQueryLen.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSearchQueryLen(Integer value) {
        this.searchQueryLen = value;
    }

    /**
     * Obtient la valeur de la propriété searchQueryMasking.
     * 
     * @return
     *     possible object is
     *     {@link Search.SearchQueryMasking }
     *     
     */
    public Search.SearchQueryMasking getSearchQueryMasking() {
        return searchQueryMasking;
    }

    /**
     * Définit la valeur de la propriété searchQueryMasking.
     * 
     * @param value
     *     allowed object is
     *     {@link Search.SearchQueryMasking }
     *     
     */
    public void setSearchQueryMasking(Search.SearchQueryMasking value) {
        this.searchQueryMasking = value;
    }

    /**
     * Obtient la valeur de la propriété searchHits.
     * 
     * @return
     *     possible object is
     *     {@link Search.SearchHits }
     *     
     */
    public Search.SearchHits getSearchHits() {
        return searchHits;
    }

    /**
     * Définit la valeur de la propriété searchHits.
     * 
     * @param value
     *     allowed object is
     *     {@link Search.SearchHits }
     *     
     */
    public void setSearchHits(Search.SearchHits value) {
        this.searchHits = value;
    }

    /**
     * Obtient la valeur de la propriété searchStat.
     * 
     * @return
     *     possible object is
     *     {@link Search.SearchStat }
     *     
     */
    public Search.SearchStat getSearchStat() {
        return searchStat;
    }

    /**
     * Définit la valeur de la propriété searchStat.
     * 
     * @param value
     *     allowed object is
     *     {@link Search.SearchStat }
     *     
     */
    public void setSearchStat(Search.SearchStat value) {
        this.searchStat = value;
    }

    /**
     * Obtient la valeur de la propriété searchMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchMessage() {
        return searchMessage;
    }

    /**
     * Définit la valeur de la propriété searchMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchMessage(String value) {
        this.searchMessage = value;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Hit"/>
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
        "hit"
    })
    public static class SearchHits {

        @XmlElement(name = "Hit")
        protected List<Hit> hit;

        /**
         * Gets the value of the hit property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hit property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHit().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Hit }
         * 
         * 
         */
        public List<Hit> getHit() {
            if (hit == null) {
                hit = new ArrayList<Hit>();
            }
            return this.hit;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Range"/>
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
        "range"
    })
    public static class SearchQueryMasking {

        @XmlElement(name = "Range")
        protected List<Range> range;

        /**
         * Gets the value of the range property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the range property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRange().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Range }
         * 
         * 
         */
        public List<Range> getRange() {
            if (range == null) {
                range = new ArrayList<Range>();
            }
            return this.range;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Statistics"/>
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
        "statistics"
    })
    public static class SearchStat {

        @XmlElement(name = "Statistics", required = true)
        protected Statistics statistics;

        /**
         * Obtient la valeur de la propriété statistics.
         * 
         * @return
         *     possible object is
         *     {@link Statistics }
         *     
         */
        public Statistics getStatistics() {
            return statistics;
        }

        /**
         * Définit la valeur de la propriété statistics.
         * 
         * @param value
         *     allowed object is
         *     {@link Statistics }
         *     
         */
        public void setStatistics(Statistics value) {
            this.statistics = value;
        }

    }

}
