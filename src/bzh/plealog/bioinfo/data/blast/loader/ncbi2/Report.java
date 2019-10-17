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
 *         &lt;element name="Report_program" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Report_version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Report_reference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Report_search-target">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Target"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Report_params">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Parameters"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Report_results">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Results"/>
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
    "reportProgram",
    "reportVersion",
    "reportReference",
    "reportSearchTarget",
    "reportParams",
    "reportResults"
})
@XmlRootElement(name = "Report")
public class Report {

    @XmlElement(name = "program", required = true)
    protected String reportProgram;
    @XmlElement(name = "version", required = true)
    protected String reportVersion;
    @XmlElement(name = "reference", required = true)
    protected String reportReference;
    @XmlElement(name = "search-target", required = true)
    protected Report.ReportSearchTarget reportSearchTarget;
    @XmlElement(name = "params", required = true)
    protected Report.ReportParams reportParams;
    @XmlElement(name = "results", required = true)
    protected Report.ReportResults reportResults;

    /**
     * Obtient la valeur de la propriété reportProgram.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportProgram() {
        return reportProgram;
    }

    /**
     * Définit la valeur de la propriété reportProgram.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportProgram(String value) {
        this.reportProgram = value;
    }

    /**
     * Obtient la valeur de la propriété reportVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportVersion() {
        return reportVersion;
    }

    /**
     * Définit la valeur de la propriété reportVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportVersion(String value) {
        this.reportVersion = value;
    }

    /**
     * Obtient la valeur de la propriété reportReference.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportReference() {
        return reportReference;
    }

    /**
     * Définit la valeur de la propriété reportReference.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportReference(String value) {
        this.reportReference = value;
    }

    /**
     * Obtient la valeur de la propriété reportSearchTarget.
     * 
     * @return
     *     possible object is
     *     {@link Report.ReportSearchTarget }
     *     
     */
    public Report.ReportSearchTarget getReportSearchTarget() {
        return reportSearchTarget;
    }

    /**
     * Définit la valeur de la propriété reportSearchTarget.
     * 
     * @param value
     *     allowed object is
     *     {@link Report.ReportSearchTarget }
     *     
     */
    public void setReportSearchTarget(Report.ReportSearchTarget value) {
        this.reportSearchTarget = value;
    }

    /**
     * Obtient la valeur de la propriété reportParams.
     * 
     * @return
     *     possible object is
     *     {@link Report.ReportParams }
     *     
     */
    public Report.ReportParams getReportParams() {
        return reportParams;
    }

    /**
     * Définit la valeur de la propriété reportParams.
     * 
     * @param value
     *     allowed object is
     *     {@link Report.ReportParams }
     *     
     */
    public void setReportParams(Report.ReportParams value) {
        this.reportParams = value;
    }

    /**
     * Obtient la valeur de la propriété reportResults.
     * 
     * @return
     *     possible object is
     *     {@link Report.ReportResults }
     *     
     */
    public Report.ReportResults getReportResults() {
        return reportResults;
    }

    /**
     * Définit la valeur de la propriété reportResults.
     * 
     * @param value
     *     allowed object is
     *     {@link Report.ReportResults }
     *     
     */
    public void setReportResults(Report.ReportResults value) {
        this.reportResults = value;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Parameters"/>
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
        "parameters"
    })
    public static class ReportParams {

        @XmlElement(name = "Parameters", required = true)
        protected Parameters parameters;

        /**
         * Obtient la valeur de la propriété parameters.
         * 
         * @return
         *     possible object is
         *     {@link Parameters }
         *     
         */
        public Parameters getParameters() {
            return parameters;
        }

        /**
         * Définit la valeur de la propriété parameters.
         * 
         * @param value
         *     allowed object is
         *     {@link Parameters }
         *     
         */
        public void setParameters(Parameters value) {
            this.parameters = value;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Results"/>
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
        "results"
    })
    public static class ReportResults {

        @XmlElement(name = "Results", required = true)
        protected Results results;

        /**
         * Obtient la valeur de la propriété results.
         * 
         * @return
         *     possible object is
         *     {@link Results }
         *     
         */
        public Results getResults() {
            return results;
        }

        /**
         * Définit la valeur de la propriété results.
         * 
         * @param value
         *     allowed object is
         *     {@link Results }
         *     
         */
        public void setResults(Results value) {
            this.results = value;
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
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Target"/>
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
        "target"
    })
    public static class ReportSearchTarget {

        @XmlElement(name = "Target", required = true)
        protected Target target;

        /**
         * Obtient la valeur de la propriété target.
         * 
         * @return
         *     possible object is
         *     {@link Target }
         *     
         */
        public Target getTarget() {
            return target;
        }

        /**
         * Définit la valeur de la propriété target.
         * 
         * @param value
         *     allowed object is
         *     {@link Target }
         *     
         */
        public void setTarget(Target value) {
            this.target = value;
        }

    }

}
