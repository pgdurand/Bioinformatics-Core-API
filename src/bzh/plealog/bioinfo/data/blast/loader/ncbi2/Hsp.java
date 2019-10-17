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
 *         &lt;element name="Hsp_num" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hsp_bit-score" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Hsp_score" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Hsp_evalue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Hsp_identity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_positive" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_density" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_pattern-from" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_pattern-to" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_query-from" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hsp_query-to" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hsp_query-strand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hsp_query-frame" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_hit-from" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hsp_hit-to" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hsp_hit-strand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hsp_hit-frame" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_align-len" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_gaps" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Hsp_qseq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Hsp_hseq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Hsp_midline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "hspNum",
    "hspBitScore",
    "hspScore",
    "hspEvalue",
    "hspIdentity",
    "hspPositive",
    "hspDensity",
    "hspPatternFrom",
    "hspPatternTo",
    "hspQueryFrom",
    "hspQueryTo",
    "hspQueryStrand",
    "hspQueryFrame",
    "hspHitFrom",
    "hspHitTo",
    "hspHitStrand",
    "hspHitFrame",
    "hspAlignLen",
    "hspGaps",
    "hspQseq",
    "hspHseq",
    "hspMidline"
})
@XmlRootElement(name = "Hsp")
public class Hsp {

    @XmlElement(name = "num", required = true)
    protected Integer hspNum;
    @XmlElement(name = "bit-score")
    protected double hspBitScore;
    @XmlElement(name = "score")
    protected double hspScore;
    @XmlElement(name = "evalue")
    protected double hspEvalue;
    @XmlElement(name = "identity")
    protected Integer hspIdentity;
    @XmlElement(name = "positive")
    protected Integer hspPositive;
    @XmlElement(name = "density")
    protected Integer hspDensity;
    @XmlElement(name = "pattern-from")
    protected Integer hspPatternFrom;
    @XmlElement(name = "pattern-to")
    protected Integer hspPatternTo;
    @XmlElement(name = "query-from", required = true)
    protected Integer hspQueryFrom;
    @XmlElement(name = "query-to", required = true)
    protected Integer hspQueryTo;
    @XmlElement(name = "query-strand")
    protected String hspQueryStrand;
    @XmlElement(name = "query-frame")
    protected Integer hspQueryFrame;
    @XmlElement(name = "hit-from", required = true)
    protected Integer hspHitFrom;
    @XmlElement(name = "hit-to", required = true)
    protected Integer hspHitTo;
    @XmlElement(name = "hit-strand")
    protected String hspHitStrand;
    @XmlElement(name = "hit-frame")
    protected Integer hspHitFrame;
    @XmlElement(name = "align-len")
    protected Integer hspAlignLen;
    @XmlElement(name = "gaps")
    protected Integer hspGaps;
    @XmlElement(name = "qseq", required = true)
    protected String hspQseq;
    @XmlElement(name = "hseq", required = true)
    protected String hspHseq;
    @XmlElement(name = "midline")
    protected String hspMidline;

    /**
     * Obtient la valeur de la propriété hspNum.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspNum() {
        return hspNum;
    }

    /**
     * Définit la valeur de la propriété hspNum.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspNum(Integer value) {
        this.hspNum = value;
    }

    /**
     * Obtient la valeur de la propriété hspBitScore.
     * 
     */
    public double getHspBitScore() {
        return hspBitScore;
    }

    /**
     * Définit la valeur de la propriété hspBitScore.
     * 
     */
    public void setHspBitScore(double value) {
        this.hspBitScore = value;
    }

    /**
     * Obtient la valeur de la propriété hspScore.
     * 
     */
    public double getHspScore() {
        return hspScore;
    }

    /**
     * Définit la valeur de la propriété hspScore.
     * 
     */
    public void setHspScore(double value) {
        this.hspScore = value;
    }

    /**
     * Obtient la valeur de la propriété hspEvalue.
     * 
     */
    public double getHspEvalue() {
        return hspEvalue;
    }

    /**
     * Définit la valeur de la propriété hspEvalue.
     * 
     */
    public void setHspEvalue(double value) {
        this.hspEvalue = value;
    }

    /**
     * Obtient la valeur de la propriété hspIdentity.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspIdentity() {
        return hspIdentity;
    }

    /**
     * Définit la valeur de la propriété hspIdentity.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspIdentity(Integer value) {
        this.hspIdentity = value;
    }

    /**
     * Obtient la valeur de la propriété hspPositive.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspPositive() {
        return hspPositive;
    }

    /**
     * Définit la valeur de la propriété hspPositive.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspPositive(Integer value) {
        this.hspPositive = value;
    }

    /**
     * Obtient la valeur de la propriété hspDensity.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspDensity() {
        return hspDensity;
    }

    /**
     * Définit la valeur de la propriété hspDensity.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspDensity(Integer value) {
        this.hspDensity = value;
    }

    /**
     * Obtient la valeur de la propriété hspPatternFrom.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspPatternFrom() {
        return hspPatternFrom;
    }

    /**
     * Définit la valeur de la propriété hspPatternFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspPatternFrom(Integer value) {
        this.hspPatternFrom = value;
    }

    /**
     * Obtient la valeur de la propriété hspPatternTo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspPatternTo() {
        return hspPatternTo;
    }

    /**
     * Définit la valeur de la propriété hspPatternTo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspPatternTo(Integer value) {
        this.hspPatternTo = value;
    }

    /**
     * Obtient la valeur de la propriété hspQueryFrom.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspQueryFrom() {
        return hspQueryFrom;
    }

    /**
     * Définit la valeur de la propriété hspQueryFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspQueryFrom(Integer value) {
        this.hspQueryFrom = value;
    }

    /**
     * Obtient la valeur de la propriété hspQueryTo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspQueryTo() {
        return hspQueryTo;
    }

    /**
     * Définit la valeur de la propriété hspQueryTo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspQueryTo(Integer value) {
        this.hspQueryTo = value;
    }

    /**
     * Obtient la valeur de la propriété hspQueryStrand.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHspQueryStrand() {
        return hspQueryStrand;
    }

    /**
     * Définit la valeur de la propriété hspQueryStrand.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHspQueryStrand(String value) {
        this.hspQueryStrand = value;
    }

    /**
     * Obtient la valeur de la propriété hspQueryFrame.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspQueryFrame() {
        return hspQueryFrame;
    }

    /**
     * Définit la valeur de la propriété hspQueryFrame.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspQueryFrame(Integer value) {
        this.hspQueryFrame = value;
    }

    /**
     * Obtient la valeur de la propriété hspHitFrom.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspHitFrom() {
        return hspHitFrom;
    }

    /**
     * Définit la valeur de la propriété hspHitFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspHitFrom(Integer value) {
        this.hspHitFrom = value;
    }

    /**
     * Obtient la valeur de la propriété hspHitTo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspHitTo() {
        return hspHitTo;
    }

    /**
     * Définit la valeur de la propriété hspHitTo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspHitTo(Integer value) {
        this.hspHitTo = value;
    }

    /**
     * Obtient la valeur de la propriété hspHitStrand.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHspHitStrand() {
        return hspHitStrand;
    }

    /**
     * Définit la valeur de la propriété hspHitStrand.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHspHitStrand(String value) {
        this.hspHitStrand = value;
    }

    /**
     * Obtient la valeur de la propriété hspHitFrame.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspHitFrame() {
        return hspHitFrame;
    }

    /**
     * Définit la valeur de la propriété hspHitFrame.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspHitFrame(Integer value) {
        this.hspHitFrame = value;
    }

    /**
     * Obtient la valeur de la propriété hspAlignLen.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspAlignLen() {
        return hspAlignLen;
    }

    /**
     * Définit la valeur de la propriété hspAlignLen.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspAlignLen(Integer value) {
        this.hspAlignLen = value;
    }

    /**
     * Obtient la valeur de la propriété hspGaps.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHspGaps() {
        return hspGaps;
    }

    /**
     * Définit la valeur de la propriété hspGaps.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHspGaps(Integer value) {
        this.hspGaps = value;
    }

    /**
     * Obtient la valeur de la propriété hspQseq.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHspQseq() {
        return hspQseq;
    }

    /**
     * Définit la valeur de la propriété hspQseq.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHspQseq(String value) {
        this.hspQseq = value;
    }

    /**
     * Obtient la valeur de la propriété hspHseq.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHspHseq() {
        return hspHseq;
    }

    /**
     * Définit la valeur de la propriété hspHseq.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHspHseq(String value) {
        this.hspHseq = value;
    }

    /**
     * Obtient la valeur de la propriété hspMidline.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHspMidline() {
        return hspMidline;
    }

    /**
     * Définit la valeur de la propriété hspMidline.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHspMidline(String value) {
        this.hspMidline = value;
    }

}
