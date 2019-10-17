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
 *         &lt;element name="BlastOutput2_report" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Report"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BlastOutput2_error" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Err"/>
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
    "blastOutput2Report",
    "blastOutput2Error"
})
@XmlRootElement(name = "BlastOutput2")
public class BlastOutput2 {

    @XmlElement(name = "report")
    protected BlastOutput2 .BlastOutput2Report blastOutput2Report;
    @XmlElement(name = "error")
    protected BlastOutput2 .BlastOutput2Error blastOutput2Error;

    /**
     * Obtient la valeur de la propriété blastOutput2Report.
     * 
     * @return
     *     possible object is
     *     {@link BlastOutput2 .BlastOutput2Report }
     *     
     */
    public BlastOutput2 .BlastOutput2Report getBlastOutput2Report() {
        return blastOutput2Report;
    }

    /**
     * Définit la valeur de la propriété blastOutput2Report.
     * 
     * @param value
     *     allowed object is
     *     {@link BlastOutput2 .BlastOutput2Report }
     *     
     */
    public void setBlastOutput2Report(BlastOutput2 .BlastOutput2Report value) {
        this.blastOutput2Report = value;
    }

    /**
     * Obtient la valeur de la propriété blastOutput2Error.
     * 
     * @return
     *     possible object is
     *     {@link BlastOutput2 .BlastOutput2Error }
     *     
     */
    public BlastOutput2 .BlastOutput2Error getBlastOutput2Error() {
        return blastOutput2Error;
    }

    /**
     * Définit la valeur de la propriété blastOutput2Error.
     * 
     * @param value
     *     allowed object is
     *     {@link BlastOutput2 .BlastOutput2Error }
     *     
     */
    public void setBlastOutput2Error(BlastOutput2 .BlastOutput2Error value) {
        this.blastOutput2Error = value;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Err"/>
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
        "err"
    })
    public static class BlastOutput2Error {

        @XmlElement(name = "Err", required = true)
        protected Err err;

        /**
         * Obtient la valeur de la propriété err.
         * 
         * @return
         *     possible object is
         *     {@link Err }
         *     
         */
        public Err getErr() {
            return err;
        }

        /**
         * Définit la valeur de la propriété err.
         * 
         * @param value
         *     allowed object is
         *     {@link Err }
         *     
         */
        public void setErr(Err value) {
            this.err = value;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Report"/>
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
        "report"
    })
    public static class BlastOutput2Report {

        @XmlElement(name = "Report", required = true)
        protected Report report;

        /**
         * Obtient la valeur de la propriété report.
         * 
         * @return
         *     possible object is
         *     {@link Report }
         *     
         */
        public Report getReport() {
            return report;
        }

        /**
         * Définit la valeur de la propriété report.
         * 
         * @param value
         *     allowed object is
         *     {@link Report }
         *     
         */
        public void setReport(Report value) {
            this.report = value;
        }

    }

}
