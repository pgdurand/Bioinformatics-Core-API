/* Copyright (C) 2006-2018 Patrick G. Durand
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.txt
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 */
package bzh.plealog.bioinfo.io.searchresult.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.TreeMap;

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutput;

/**
 * This class is responsible for saving Table data in an OutputStream using the
 * standard CSV format. See http://www.wotsit.org/ for details on CSV.
 * 
 * @author Patrick G. Durand
 */
public class CSVExportSROutput {

  private char _separator = ',';
  private boolean _escapeString;// used for data only, not column headers
  private boolean _showQName;
  private boolean _addPctString;
  private boolean _bestHitOnly;
  private boolean _showColumnHeader;
  private boolean _showQueryId;
  private boolean _showQueryLength;
  private int[] _colIds;
  
  public CSVExportSROutput() {
    _escapeString = true;// used for data only, not column headers
    _showQName = true;
    _addPctString = true;
    _bestHitOnly = true;
    _showColumnHeader = true;
    _showQueryId = true;
    _showQueryLength = false;
    _colIds = TxtExportSROutput.getDefaultColumnIDs();
  }

  /**
   * Constructor.
   * 
   * @param separator
   *          the character used to separate data fields. Default is a comma.
   */
  public CSVExportSROutput(char separator) {
    this();
    _separator = separator;
  }

  /**
   * Figures out if parameter obj is a basic Java type.
   * 
   * @return true if obj is either a Number, a String, a Character or a Boolean.
   */
  protected boolean isBasicType(Object obj) {
    if (obj instanceof Number || obj instanceof String || obj instanceof Character || obj instanceof Boolean)
      return true;
    else
      return false;
  }

  /**
   * Figures out if parameter obj is a String.
   * 
   * @return true if obj is a String.
   */
  protected boolean isString(Object obj) {
    if (obj instanceof String)
      return true;
    else
      return false;
  }

  public void setSeparator(char separator) {
    _separator = separator;
  }

  public void escapeString(boolean escapeString) {
    _escapeString = escapeString;
  }

  public void showQueryName(boolean showQName) {
    _showQName = showQName;
  }

  public void setAddPctString(boolean addPctString) {
    _addPctString = addPctString;
  }
  
  public void showBestHitOnly(boolean bestHitOnly) {
    _bestHitOnly = bestHitOnly;
  }
  public void showColumnHeader(boolean columnHeader) {
    _showColumnHeader = columnHeader;
  }
  public void showQueryId(boolean querId) {
    _showQueryId = querId;
  }
  public void showQueryLength(boolean showQLength) {
    _showQueryLength = showQLength;
  }
  public void ssetColumnIds(int[] colIds) {
    this._colIds = colIds;
  }
  private void writeString(Writer writer, String data) throws IOException {
    if (_escapeString) {
      writer.write("\"");
    }
    writer.write(data);
    if (_escapeString) {
      writer.write("\"");
    }
  }

  public void export(Writer writer, SROutput output) throws Exception {
    if (writer == null || output == null)
      return;

    TreeMap<String, TreeMap<ExtractAnnotation.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap = null;
    TreeMap<ExtractAnnotation.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>> annotationDictionary = null;

    SRIteration iteration;
    SRHit hit;
    SRHsp hsp;
    String s, qId, qName, qLength;
    Object obj;
    int i, j, k, l, size, size2, size3, cols;

    cols = _colIds.length;
    if (_showColumnHeader) {
      if (_showQueryId) {
        writer.write("\"Query Id\"");
        writer.write(_separator);
        if (_showQName) {
          writer.write("\"Query Name\"");
          writer.write(_separator);
        }
      }
      if (_showQueryLength) {
        writer.write("\"Query Length\"");
        writer.write(_separator);
      }
      for (i = 0; i < cols; i++) {
        s = TxtExportSROutput.DATA_COL_HEADERS[_colIds[i]];
        writer.write("\"" + s + "\"");
        if ((i + 1) < cols)
          writer.write(_separator);
      }
      writer.write("\n");
    }

    // need to export Biological classification ?
    for (i = 0; i < cols; i++) {
      if (_colIds[i] == TxtExportSROutput.BIO_CLASSIF) {
        annotatedHitsHashMap = new TreeMap<String, TreeMap<ExtractAnnotation.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>>();
        annotationDictionary = new TreeMap<ExtractAnnotation.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>>();
        ExtractAnnotation.buildAnnotatedHitDataSet(output, 0, annotatedHitsHashMap, annotationDictionary);
        //HashSet<ExtractAnnotation.ANNOTATION_CATEGORY> DEF_CAT = new HashSet<ExtractAnnotation.ANNOTATION_CATEGORY>();
        //DEF_CAT.add(ANNOTATION_CATEGORY.GO);
        //ExtractAnnotation.buildAnnotatedHitDataSet(output, 0, annotatedHitsHashMap, annotationDictionary, DEF_CAT);
      }
    }

    obj = output.getRequestInfo().getValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY);
    if (obj == null)
      qId = "?";
    else
      qId = obj.toString();
    obj = output.getRequestInfo().getValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY);
    if (obj == null)
      qName = "?";
    else
      qName = obj.toString();
    obj = output.getRequestInfo().getValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY);
    if (obj == null)
      qLength = "?";
    else
      qLength = obj.toString();
    if (!output.isEmpty()) {
      size = output.countIteration();
      for (i = 0; i < size; i++) {// loop on iterations
        iteration = output.getIteration(i);
        size2 = iteration.countHit();
        for (j = 0; j < size2; j++) {// loop on hits
          hit = iteration.getHit(j);
          size3 = hit.countHsp();
          for (k = 0; k < size3; k++) {// loop on hsp
            hsp = hit.getHsp(k);
            if (_showQueryId) {
              writeString(writer, qId);
              writer.write(_separator);
              if (_showQName) {
                writeString(writer, qName);
                writer.write(_separator);
              }
            }
            if (_showQueryLength) {
              writer.write(qLength);
              writer.write(_separator);
            }
            for (l = 0; l < cols; l++) {// loop on data columns
              s = TxtExportSROutput.getFormattedData(annotatedHitsHashMap, hit, hsp, _colIds[l], _escapeString,
                  _addPctString);
              writer.write(s);
              if ((l + 1) < cols)
                writer.write(_separator);
            }
            writer.write("\n");
            if (_bestHitOnly) {
              if (annotatedHitsHashMap != null)
                annotatedHitsHashMap.clear();
              if (annotationDictionary != null)
                annotationDictionary.clear();
              return;
            }
          }
        }
      }
    } else {// empty results
      if (_showQueryId) {
        writeString(writer, qId);
        writer.write(_separator);
        if (_showQName) {
          writeString(writer, qName);
          writer.write(_separator);
        }
      }
      if (_showQueryLength) {
        writer.write(qLength);
        writer.write(_separator);
      }
      for (l = 0; l < cols; l++) {// loop on data columns
        writeString(writer, "n/a");

        if ((l + 1) < cols)
          writer.write(_separator);
      }
      writer.write("\n");
    }
    if (annotatedHitsHashMap != null)
      annotatedHitsHashMap.clear();
    if (annotationDictionary != null)
      annotationDictionary.clear();
  }
}
