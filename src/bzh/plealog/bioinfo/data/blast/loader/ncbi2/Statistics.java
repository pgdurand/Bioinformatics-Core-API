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
 *         &lt;element name="Statistics_db-num" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Statistics_db-len" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Statistics_hsp-len" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Statistics_eff-space" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Statistics_kappa" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statistics_lambda" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statistics_entropy" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "statisticsDbNum",
    "statisticsDbLen",
    "statisticsHspLen",
    "statisticsEffSpace",
    "statisticsKappa",
    "statisticsLambda",
    "statisticsEntropy"
})
@XmlRootElement(name = "Statistics")
public class Statistics {

    @XmlElement(name = "db-num")
    protected Long statisticsDbNum;
    @XmlElement(name = "db-len")
    protected Long statisticsDbLen;
    @XmlElement(name = "hsp-len", required = true)
    protected Integer statisticsHspLen;
    @XmlElement(name = "eff-space")
    protected long statisticsEffSpace;
    @XmlElement(name = "kappa")
    protected double statisticsKappa;
    @XmlElement(name = "lambda")
    protected double statisticsLambda;
    @XmlElement(name = "entropy")
    protected double statisticsEntropy;

    /**
     * Obtient la valeur de la propriété statisticsDbNum.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStatisticsDbNum() {
        return statisticsDbNum;
    }

    /**
     * Définit la valeur de la propriété statisticsDbNum.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStatisticsDbNum(Long value) {
        this.statisticsDbNum = value;
    }

    /**
     * Obtient la valeur de la propriété statisticsDbLen.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStatisticsDbLen() {
        return statisticsDbLen;
    }

    /**
     * Définit la valeur de la propriété statisticsDbLen.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStatisticsDbLen(Long value) {
        this.statisticsDbLen = value;
    }

    /**
     * Obtient la valeur de la propriété statisticsHspLen.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatisticsHspLen() {
        return statisticsHspLen;
    }

    /**
     * Définit la valeur de la propriété statisticsHspLen.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatisticsHspLen(Integer value) {
        this.statisticsHspLen = value;
    }

    /**
     * Obtient la valeur de la propriété statisticsEffSpace.
     * 
     */
    public long getStatisticsEffSpace() {
        return statisticsEffSpace;
    }

    /**
     * Définit la valeur de la propriété statisticsEffSpace.
     * 
     */
    public void setStatisticsEffSpace(long value) {
        this.statisticsEffSpace = value;
    }

    /**
     * Obtient la valeur de la propriété statisticsKappa.
     * 
     */
    public double getStatisticsKappa() {
        return statisticsKappa;
    }

    /**
     * Définit la valeur de la propriété statisticsKappa.
     * 
     */
    public void setStatisticsKappa(double value) {
        this.statisticsKappa = value;
    }

    /**
     * Obtient la valeur de la propriété statisticsLambda.
     * 
     */
    public double getStatisticsLambda() {
        return statisticsLambda;
    }

    /**
     * Définit la valeur de la propriété statisticsLambda.
     * 
     */
    public void setStatisticsLambda(double value) {
        this.statisticsLambda = value;
    }

    /**
     * Obtient la valeur de la propriété statisticsEntropy.
     * 
     */
    public double getStatisticsEntropy() {
        return statisticsEntropy;
    }

    /**
     * Définit la valeur de la propriété statisticsEntropy.
     * 
     */
    public void setStatisticsEntropy(double value) {
        this.statisticsEntropy = value;
    }

}
