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
 *         &lt;element name="Parameters_matrix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Parameters_expect" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Parameters_include" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Parameters_sc-match" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_sc-mismatch" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_gap-open" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_gap-extend" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Parameters_pattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Parameters_entrez-query" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Parameters_cbs" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_query-gencode" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_db-gencode" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Parameters_bl2seq-mode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "parametersMatrix",
    "parametersExpect",
    "parametersInclude",
    "parametersScMatch",
    "parametersScMismatch",
    "parametersGapOpen",
    "parametersGapExtend",
    "parametersFilter",
    "parametersPattern",
    "parametersEntrezQuery",
    "parametersCbs",
    "parametersQueryGencode",
    "parametersDbGencode",
    "parametersBl2SeqMode"
})
@XmlRootElement(name = "Parameters")
public class Parameters {

    @XmlElement(name = "matrix")
    protected String parametersMatrix;
    @XmlElement(name = "expect")
    protected double parametersExpect;
    @XmlElement(name = "include")
    protected Double parametersInclude;
    @XmlElement(name = "sc-match")
    protected Integer parametersScMatch;
    @XmlElement(name = "sc-mismatch")
    protected Integer parametersScMismatch;
    @XmlElement(name = "gap-open")
    protected Integer parametersGapOpen;
    @XmlElement(name = "gap-extend")
    protected Integer parametersGapExtend;
    @XmlElement(name = "filter")
    protected String parametersFilter;
    @XmlElement(name = "pattern")
    protected String parametersPattern;
    @XmlElement(name = "entrez-query")
    protected String parametersEntrezQuery;
    @XmlElement(name = "cbs")
    protected Integer parametersCbs;
    @XmlElement(name = "query-gencode")
    protected Integer parametersQueryGencode;
    @XmlElement(name = "db-gencode")
    protected Integer parametersDbGencode;
    @XmlElement(name = "bl2seq-mode")
    protected String parametersBl2SeqMode;

    /**
     * Obtient la valeur de la propriété parametersMatrix.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametersMatrix() {
        return parametersMatrix;
    }

    /**
     * Définit la valeur de la propriété parametersMatrix.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametersMatrix(String value) {
        this.parametersMatrix = value;
    }

    /**
     * Obtient la valeur de la propriété parametersExpect.
     * 
     */
    public double getParametersExpect() {
        return parametersExpect;
    }

    /**
     * Définit la valeur de la propriété parametersExpect.
     * 
     */
    public void setParametersExpect(double value) {
        this.parametersExpect = value;
    }

    /**
     * Obtient la valeur de la propriété parametersInclude.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getParametersInclude() {
        return parametersInclude;
    }

    /**
     * Définit la valeur de la propriété parametersInclude.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setParametersInclude(Double value) {
        this.parametersInclude = value;
    }

    /**
     * Obtient la valeur de la propriété parametersScMatch.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersScMatch() {
        return parametersScMatch;
    }

    /**
     * Définit la valeur de la propriété parametersScMatch.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersScMatch(Integer value) {
        this.parametersScMatch = value;
    }

    /**
     * Obtient la valeur de la propriété parametersScMismatch.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersScMismatch() {
        return parametersScMismatch;
    }

    /**
     * Définit la valeur de la propriété parametersScMismatch.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersScMismatch(Integer value) {
        this.parametersScMismatch = value;
    }

    /**
     * Obtient la valeur de la propriété parametersGapOpen.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersGapOpen() {
        return parametersGapOpen;
    }

    /**
     * Définit la valeur de la propriété parametersGapOpen.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersGapOpen(Integer value) {
        this.parametersGapOpen = value;
    }

    /**
     * Obtient la valeur de la propriété parametersGapExtend.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersGapExtend() {
        return parametersGapExtend;
    }

    /**
     * Définit la valeur de la propriété parametersGapExtend.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersGapExtend(Integer value) {
        this.parametersGapExtend = value;
    }

    /**
     * Obtient la valeur de la propriété parametersFilter.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametersFilter() {
        return parametersFilter;
    }

    /**
     * Définit la valeur de la propriété parametersFilter.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametersFilter(String value) {
        this.parametersFilter = value;
    }

    /**
     * Obtient la valeur de la propriété parametersPattern.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametersPattern() {
        return parametersPattern;
    }

    /**
     * Définit la valeur de la propriété parametersPattern.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametersPattern(String value) {
        this.parametersPattern = value;
    }

    /**
     * Obtient la valeur de la propriété parametersEntrezQuery.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametersEntrezQuery() {
        return parametersEntrezQuery;
    }

    /**
     * Définit la valeur de la propriété parametersEntrezQuery.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametersEntrezQuery(String value) {
        this.parametersEntrezQuery = value;
    }

    /**
     * Obtient la valeur de la propriété parametersCbs.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersCbs() {
        return parametersCbs;
    }

    /**
     * Définit la valeur de la propriété parametersCbs.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersCbs(Integer value) {
        this.parametersCbs = value;
    }

    /**
     * Obtient la valeur de la propriété parametersQueryGencode.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersQueryGencode() {
        return parametersQueryGencode;
    }

    /**
     * Définit la valeur de la propriété parametersQueryGencode.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersQueryGencode(Integer value) {
        this.parametersQueryGencode = value;
    }

    /**
     * Obtient la valeur de la propriété parametersDbGencode.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParametersDbGencode() {
        return parametersDbGencode;
    }

    /**
     * Définit la valeur de la propriété parametersDbGencode.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParametersDbGencode(Integer value) {
        this.parametersDbGencode = value;
    }

    /**
     * Obtient la valeur de la propriété parametersBl2SeqMode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametersBl2SeqMode() {
        return parametersBl2SeqMode;
    }

    /**
     * Définit la valeur de la propriété parametersBl2SeqMode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametersBl2SeqMode(String value) {
        this.parametersBl2SeqMode = value;
    }

}
