//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.10 at 12:48:55 PM GMT 
//


package bzh.plealog.bioinfo.data.blast.loader.ncbi2;

import java.math.BigInteger;
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

    @XmlElement(name = "Hsp_num", required = true)
    protected BigInteger hspNum;
    @XmlElement(name = "Hsp_bit-score")
    protected double hspBitScore;
    @XmlElement(name = "Hsp_score")
    protected double hspScore;
    @XmlElement(name = "Hsp_evalue")
    protected double hspEvalue;
    @XmlElement(name = "Hsp_identity")
    protected BigInteger hspIdentity;
    @XmlElement(name = "Hsp_positive")
    protected BigInteger hspPositive;
    @XmlElement(name = "Hsp_density")
    protected BigInteger hspDensity;
    @XmlElement(name = "Hsp_pattern-from")
    protected BigInteger hspPatternFrom;
    @XmlElement(name = "Hsp_pattern-to")
    protected BigInteger hspPatternTo;
    @XmlElement(name = "Hsp_query-from", required = true)
    protected BigInteger hspQueryFrom;
    @XmlElement(name = "Hsp_query-to", required = true)
    protected BigInteger hspQueryTo;
    @XmlElement(name = "Hsp_query-strand")
    protected String hspQueryStrand;
    @XmlElement(name = "Hsp_query-frame")
    protected BigInteger hspQueryFrame;
    @XmlElement(name = "Hsp_hit-from", required = true)
    protected BigInteger hspHitFrom;
    @XmlElement(name = "Hsp_hit-to", required = true)
    protected BigInteger hspHitTo;
    @XmlElement(name = "Hsp_hit-strand")
    protected String hspHitStrand;
    @XmlElement(name = "Hsp_hit-frame")
    protected BigInteger hspHitFrame;
    @XmlElement(name = "Hsp_align-len")
    protected BigInteger hspAlignLen;
    @XmlElement(name = "Hsp_gaps")
    protected BigInteger hspGaps;
    @XmlElement(name = "Hsp_qseq", required = true)
    protected String hspQseq;
    @XmlElement(name = "Hsp_hseq", required = true)
    protected String hspHseq;
    @XmlElement(name = "Hsp_midline")
    protected String hspMidline;

    /**
     * Gets the value of the hspNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspNum() {
        return hspNum;
    }

    /**
     * Sets the value of the hspNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspNum(BigInteger value) {
        this.hspNum = value;
    }

    /**
     * Gets the value of the hspBitScore property.
     * 
     */
    public double getHspBitScore() {
        return hspBitScore;
    }

    /**
     * Sets the value of the hspBitScore property.
     * 
     */
    public void setHspBitScore(double value) {
        this.hspBitScore = value;
    }

    /**
     * Gets the value of the hspScore property.
     * 
     */
    public double getHspScore() {
        return hspScore;
    }

    /**
     * Sets the value of the hspScore property.
     * 
     */
    public void setHspScore(double value) {
        this.hspScore = value;
    }

    /**
     * Gets the value of the hspEvalue property.
     * 
     */
    public double getHspEvalue() {
        return hspEvalue;
    }

    /**
     * Sets the value of the hspEvalue property.
     * 
     */
    public void setHspEvalue(double value) {
        this.hspEvalue = value;
    }

    /**
     * Gets the value of the hspIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspIdentity() {
        return hspIdentity;
    }

    /**
     * Sets the value of the hspIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspIdentity(BigInteger value) {
        this.hspIdentity = value;
    }

    /**
     * Gets the value of the hspPositive property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspPositive() {
        return hspPositive;
    }

    /**
     * Sets the value of the hspPositive property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspPositive(BigInteger value) {
        this.hspPositive = value;
    }

    /**
     * Gets the value of the hspDensity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspDensity() {
        return hspDensity;
    }

    /**
     * Sets the value of the hspDensity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspDensity(BigInteger value) {
        this.hspDensity = value;
    }

    /**
     * Gets the value of the hspPatternFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspPatternFrom() {
        return hspPatternFrom;
    }

    /**
     * Sets the value of the hspPatternFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspPatternFrom(BigInteger value) {
        this.hspPatternFrom = value;
    }

    /**
     * Gets the value of the hspPatternTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspPatternTo() {
        return hspPatternTo;
    }

    /**
     * Sets the value of the hspPatternTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspPatternTo(BigInteger value) {
        this.hspPatternTo = value;
    }

    /**
     * Gets the value of the hspQueryFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspQueryFrom() {
        return hspQueryFrom;
    }

    /**
     * Sets the value of the hspQueryFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspQueryFrom(BigInteger value) {
        this.hspQueryFrom = value;
    }

    /**
     * Gets the value of the hspQueryTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspQueryTo() {
        return hspQueryTo;
    }

    /**
     * Sets the value of the hspQueryTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspQueryTo(BigInteger value) {
        this.hspQueryTo = value;
    }

    /**
     * Gets the value of the hspQueryStrand property.
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
     * Sets the value of the hspQueryStrand property.
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
     * Gets the value of the hspQueryFrame property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspQueryFrame() {
        return hspQueryFrame;
    }

    /**
     * Sets the value of the hspQueryFrame property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspQueryFrame(BigInteger value) {
        this.hspQueryFrame = value;
    }

    /**
     * Gets the value of the hspHitFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspHitFrom() {
        return hspHitFrom;
    }

    /**
     * Sets the value of the hspHitFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspHitFrom(BigInteger value) {
        this.hspHitFrom = value;
    }

    /**
     * Gets the value of the hspHitTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspHitTo() {
        return hspHitTo;
    }

    /**
     * Sets the value of the hspHitTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspHitTo(BigInteger value) {
        this.hspHitTo = value;
    }

    /**
     * Gets the value of the hspHitStrand property.
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
     * Sets the value of the hspHitStrand property.
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
     * Gets the value of the hspHitFrame property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspHitFrame() {
        return hspHitFrame;
    }

    /**
     * Sets the value of the hspHitFrame property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspHitFrame(BigInteger value) {
        this.hspHitFrame = value;
    }

    /**
     * Gets the value of the hspAlignLen property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspAlignLen() {
        return hspAlignLen;
    }

    /**
     * Sets the value of the hspAlignLen property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspAlignLen(BigInteger value) {
        this.hspAlignLen = value;
    }

    /**
     * Gets the value of the hspGaps property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHspGaps() {
        return hspGaps;
    }

    /**
     * Sets the value of the hspGaps property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHspGaps(BigInteger value) {
        this.hspGaps = value;
    }

    /**
     * Gets the value of the hspQseq property.
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
     * Sets the value of the hspQseq property.
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
     * Gets the value of the hspHseq property.
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
     * Sets the value of the hspHseq property.
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
     * Gets the value of the hspMidline property.
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
     * Sets the value of the hspMidline property.
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
