//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.11.01 à 06:32:58 PM CET 
//


package bzh.plealog.bioinfo.data.taxonomy.loader.ncbi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "taxId",
    "scientificName",
    "otherNames",
    "parentTaxId",
    "rank",
    "division",
    "geneticCode",
    "mitoGeneticCode",
    "lineage",
    "lineageEx",
    "citations",
    "modifiers",
    "properties",
    "createDate",
    "updateDate",
    "pubDate",
    "akaTaxIds"
})
@XmlRootElement(name = "Taxon")
public class Taxon {

    @XmlElement(name = "TaxId", required = true)
    protected TaxId taxId;
    @XmlElement(name = "ScientificName", required = true)
    protected String scientificName;
    @XmlElement(name = "OtherNames")
    protected OtherNames otherNames;
    @XmlElement(name = "ParentTaxId")
    protected String parentTaxId;
    @XmlElement(name = "Rank")
    protected String rank;
    @XmlElement(name = "Division")
    protected String division;
    @XmlElement(name = "GeneticCode")
    protected GeneticCode geneticCode;
    @XmlElement(name = "MitoGeneticCode")
    protected MitoGeneticCode mitoGeneticCode;
    @XmlElement(name = "Lineage")
    protected String lineage;
    @XmlElement(name = "LineageEx")
    protected LineageEx lineageEx;
    @XmlElement(name = "Citations")
    protected Citations citations;
    @XmlElement(name = "Modifiers")
    protected Modifiers modifiers;
    @XmlElement(name = "Properties")
    protected Properties properties;
    @XmlElement(name = "CreateDate")
    protected String createDate;
    @XmlElement(name = "UpdateDate")
    protected String updateDate;
    @XmlElement(name = "PubDate")
    protected String pubDate;
    @XmlElement(name = "AkaTaxIds")
    protected AkaTaxIds akaTaxIds;

    /**
     * Obtient la valeur de la propriété taxId.
     * 
     * @return
     *     possible object is
     *     {@link TaxId }
     *     
     */
    public TaxId getTaxId() {
        return taxId;
    }

    /**
     * Définit la valeur de la propriété taxId.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxId }
     *     
     */
    public void setTaxId(TaxId value) {
        this.taxId = value;
    }

    /**
     * Obtient la valeur de la propriété scientificName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScientificName() {
        return scientificName;
    }

    /**
     * Définit la valeur de la propriété scientificName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScientificName(String value) {
        this.scientificName = value;
    }

    /**
     * Obtient la valeur de la propriété otherNames.
     * 
     * @return
     *     possible object is
     *     {@link OtherNames }
     *     
     */
    public OtherNames getOtherNames() {
        return otherNames;
    }

    /**
     * Définit la valeur de la propriété otherNames.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherNames }
     *     
     */
    public void setOtherNames(OtherNames value) {
        this.otherNames = value;
    }

    /**
     * Obtient la valeur de la propriété parentTaxId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentTaxId() {
        return parentTaxId;
    }

    /**
     * Définit la valeur de la propriété parentTaxId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentTaxId(String value) {
        this.parentTaxId = value;
    }

    /**
     * Obtient la valeur de la propriété rank.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRank() {
        return rank;
    }

    /**
     * Définit la valeur de la propriété rank.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRank(String value) {
        this.rank = value;
    }

    /**
     * Obtient la valeur de la propriété division.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivision() {
        return division;
    }

    /**
     * Définit la valeur de la propriété division.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivision(String value) {
        this.division = value;
    }

    /**
     * Obtient la valeur de la propriété geneticCode.
     * 
     * @return
     *     possible object is
     *     {@link GeneticCode }
     *     
     */
    public GeneticCode getGeneticCode() {
        return geneticCode;
    }

    /**
     * Définit la valeur de la propriété geneticCode.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneticCode }
     *     
     */
    public void setGeneticCode(GeneticCode value) {
        this.geneticCode = value;
    }

    /**
     * Obtient la valeur de la propriété mitoGeneticCode.
     * 
     * @return
     *     possible object is
     *     {@link MitoGeneticCode }
     *     
     */
    public MitoGeneticCode getMitoGeneticCode() {
        return mitoGeneticCode;
    }

    /**
     * Définit la valeur de la propriété mitoGeneticCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MitoGeneticCode }
     *     
     */
    public void setMitoGeneticCode(MitoGeneticCode value) {
        this.mitoGeneticCode = value;
    }

    /**
     * Obtient la valeur de la propriété lineage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineage() {
        return lineage;
    }

    /**
     * Définit la valeur de la propriété lineage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineage(String value) {
        this.lineage = value;
    }

    /**
     * Obtient la valeur de la propriété lineageEx.
     * 
     * @return
     *     possible object is
     *     {@link LineageEx }
     *     
     */
    public LineageEx getLineageEx() {
        return lineageEx;
    }

    /**
     * Définit la valeur de la propriété lineageEx.
     * 
     * @param value
     *     allowed object is
     *     {@link LineageEx }
     *     
     */
    public void setLineageEx(LineageEx value) {
        this.lineageEx = value;
    }

    /**
     * Obtient la valeur de la propriété citations.
     * 
     * @return
     *     possible object is
     *     {@link Citations }
     *     
     */
    public Citations getCitations() {
        return citations;
    }

    /**
     * Définit la valeur de la propriété citations.
     * 
     * @param value
     *     allowed object is
     *     {@link Citations }
     *     
     */
    public void setCitations(Citations value) {
        this.citations = value;
    }

    /**
     * Obtient la valeur de la propriété modifiers.
     * 
     * @return
     *     possible object is
     *     {@link Modifiers }
     *     
     */
    public Modifiers getModifiers() {
        return modifiers;
    }

    /**
     * Définit la valeur de la propriété modifiers.
     * 
     * @param value
     *     allowed object is
     *     {@link Modifiers }
     *     
     */
    public void setModifiers(Modifiers value) {
        this.modifiers = value;
    }

    /**
     * Obtient la valeur de la propriété properties.
     * 
     * @return
     *     possible object is
     *     {@link Properties }
     *     
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Définit la valeur de la propriété properties.
     * 
     * @param value
     *     allowed object is
     *     {@link Properties }
     *     
     */
    public void setProperties(Properties value) {
        this.properties = value;
    }

    /**
     * Obtient la valeur de la propriété createDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Définit la valeur de la propriété createDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDate(String value) {
        this.createDate = value;
    }

    /**
     * Obtient la valeur de la propriété updateDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * Définit la valeur de la propriété updateDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDate(String value) {
        this.updateDate = value;
    }

    /**
     * Obtient la valeur de la propriété pubDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * Définit la valeur de la propriété pubDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubDate(String value) {
        this.pubDate = value;
    }

    /**
     * Obtient la valeur de la propriété akaTaxIds.
     * 
     * @return
     *     possible object is
     *     {@link AkaTaxIds }
     *     
     */
    public AkaTaxIds getAkaTaxIds() {
        return akaTaxIds;
    }

    /**
     * Définit la valeur de la propriété akaTaxIds.
     * 
     * @param value
     *     allowed object is
     *     {@link AkaTaxIds }
     *     
     */
    public void setAkaTaxIds(AkaTaxIds value) {
        this.akaTaxIds = value;
    }

}
