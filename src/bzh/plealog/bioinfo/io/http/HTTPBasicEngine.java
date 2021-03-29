/* Copyright (C) 2006-2021 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.http;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.io.Util;

import com.plealog.genericapp.api.log.EZLogger;

/**
 * A simple HTTP/HTTPS client to download content.
 * 
 * @author Patrick G. Durand
 */
public class HTTPBasicEngine {
  public static String TMP_FILE_PREFIX = "http";
  public static String TMP_FILE_SUFIX = ".tmp";

  public static int CONNECT_TIMEOUT = 5000; // 5 seconds
  public static int SOCKET_TIMEOUT = 60000; // 1 minute
  
  public static String RANGE_HTTP = "Range";
  public static String RANGE_HTTP_FORMAT = "bytes=%d-";
  
  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * 
   * @return file with content retrieved from remote server. It is worth noting that 
   * this file has deleteOnExit attribute set.
   * 
   * @throws HTTPEngineException in case of any errors
   */
  public static File doGet(String url) {
    return doGet(url, null, null, null);
  }

  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * @param listener copy stream listener
   * 
   * @return file with content retrieved from remote server. It is worth noting that 
   * this file has deleteOnExit attribute set.
   * 
   * @throws HTTPEngineException in case of any errors
   */
  public static File doGet(String url, CopyStreamListener listener) {
    return doGet(url, null, null, listener);
  }
  
  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * @param answerFile file with content retrieved from remote server. 
   * 
   * @return same as answerFile argument
   * @throws HTTPEngineException in case of any errors
   */
  public static File doGet(String url, File answerFile) {
    return doGet(url, null, answerFile, null);
  }
  
  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * @param answerFile file with content retrieved from remote server. 
   * @param listener copy stream listener
   * 
   * @return same as answerFile argument
   * @throws HTTPEngineException in case of any errors
   */
  public static File doGet(String url, File answerFile, CopyStreamListener listener) {
    return doGet(url, null, answerFile, listener);
  }

  /**
   * Utility method to quickly create a temporay file.
   * 
   * @param deleteOnExit set whether or not vcreated file will have 
   * deleteOnExit attribute set to on
   * 
   * @return a file
   * 
   * @throws HTTPEngineException in case of any errors
   * */
  public static File prepareTmpFile(boolean deleteOnExit) {
    File answerFile = null;

    try {
      answerFile = File.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SUFIX);
      if (deleteOnExit) {
        answerFile.deleteOnExit();
      }
      EZLogger.debug(answerFile.getAbsolutePath());
    } catch (IOException e) {
      EZLogger.warn(e.toString());
      throw new HTTPEngineException("Failed to create response file", "", 
          HTTPEngineException.HTTPEX_TMP_FILE_ERROR);
    }
    return answerFile;
  }
  
  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * @param header_attrs
   *          attributes to set in header connection
   *          
   * @return a file containing the result. Returned file is set to deleteOnExit,
   *         so you do not have to worry about deleting it.
   * 
   * @throws HTTPEngineException
   *           if something wrong occurs.
   */
  public static File doGet(String url, Map<String, String> header_attrs) {
    return doGet(url, header_attrs, null, null);
  }

  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * @param header_attrs
   *          attributes to set in header connection
   * @param listener copy stream listener
   * 
   * @return a file containing the result. Returned file is set to deleteOnExit,
   *         so you do not have to worry about deleting it.
   * 
   * @throws HTTPEngineException
   *           if something wrong occurs.
   */
  public static File doGet(String url, Map<String, String> header_attrs, CopyStreamListener listener) {
    return doGet(url, header_attrs, null, listener);
  }

  /**
   * Download content from a given URL to a file.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * @param header_attrs
   *          attributes to set in header connection
   * @param answerFile file with content retrieved from remote server. 
   * @param listener copy stream listener
   * 
   * @return same as answerFile.
   * 
   * @throws HTTPEngineException
   *           if something wrong occurs.
   */
  public static File doGet(String url, Map<String, String> header_attrs, 
      File answerFile, CopyStreamListener listener) {
    InputStream ins = null;
    OutputStream output = null;
    boolean resume =false;
    
    // this is a very, very basic implementation to handle HTTP(S) Get transactions
    // using URL APIs (e.g. NCBI eUtils, Ensembl, etc.). May need optimization
    // for more powerful needs...
    // Possible upgrade: use Jersey to deal with web services?

    // 1. prepare a temporary file to receive answer
    EZLogger.debug(url);
    if (answerFile==null) {
      answerFile = prepareTmpFile(true);
    }

    // 2. run the HTTP GET method
    try { 
      // open connection to the remote server
      URL myurl = new URL(url);
      HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
      if (header_attrs!=null){
        Iterator<String> attrIter = header_attrs.keySet().iterator();
        String key;
        while(attrIter.hasNext()){
          key = attrIter.next();
          con.setRequestProperty(key, header_attrs.get(key));
        }
        if (header_attrs.containsKey(RANGE_HTTP)) {
          resume = true;
        }
      }
      
      //set connection timeout
      con.setConnectTimeout(CONNECT_TIMEOUT);
      con.setReadTimeout(SOCKET_TIMEOUT);
      
      // ensembl and ebi provides additional header fields.
      // see
      // http://www.ebi.ac.uk/Tools/webservices/services/eb-eye_rest#additional_information_in_http_response_header
      // https://github.com/Ensembl/ensembl-rest/wiki/HTTP-Response-Codes
      // some of these header values can be used to adapt connection to remote
      // server. For now, we just monitor them... TODO: use them!
      Map<String, List<String>> headerFields = con.getHeaderFields();
      EZLogger.debug(headerFields.toString());
      long remoteFSize = Long.valueOf(headerFields.get("Content-Length").get(0));
      
      // response code is checked before opening input stream
      if ( ! (con.getResponseCode() == HttpURLConnection.HTTP_OK ||
              con.getResponseCode() == HttpURLConnection.HTTP_PARTIAL)) {
        throw new HTTPEngineException("Failed to connect to server", url, con.getResponseCode());
      }

      // 200 OK / 206 PARTIAL: read server answer
      output = new BufferedOutputStream(new FileOutputStream(answerFile, resume));
      ins = con.getInputStream();
      Util.copyStream(ins, output, Util.DEFAULT_COPY_BUFFER_SIZE, remoteFSize, listener);
      output.flush();
    } catch (HTTPEngineException hee) {
      throw hee;
    } catch (SocketTimeoutException ste) {
      throw new HTTPEngineException("Server does not answer (time out)", url, HTTPEngineException.HTTPEX_TIMEOUT);
    } catch (Exception e) {
      // we Log the HTTP or IO error since message is usually out of concern
      // for the end user. However, a log trace is always useful.
      EZLogger.debug(e.toString());
      // then raises a "generic" exception
      throw new HTTPEngineException("Unable to write in response file: "+e.getMessage(), 
          url, HTTPEngineException.HTTPEX_WRITE_FILE_ERROR);
    }
    finally {
      // 3. close connections    
      closeStream(ins);
      closeStream(output);
    }
    // 4. return answer
    return answerFile;
  }
  
  private static void closeStream(Closeable is) {
    if (is==null)
      return;
    try {
      is.close();
    } catch (IOException e) {
    }
  }
  /**
   * Figures out whether or not a particular web server is available.
   * 
   * @param url accept HTTP and HTTPS based URLs
   * 
   * @return true if server is reachable, false otherwise.
   */
  public static boolean isServerAvailable(String url) {
    try {
      URL myurl = new URL(url);
      HttpURLConnection httpConn = (HttpURLConnection)myurl.openConnection();
      httpConn.setInstanceFollowRedirects(false);
      httpConn.setRequestMethod("HEAD");
      httpConn.setConnectTimeout(CONNECT_TIMEOUT);
      httpConn.connect();
      EZLogger.debug(httpConn.getHeaderFields().toString());
    } catch (Exception e) {
      EZLogger.warn(e.toString());
      return false;
    }
    //if we hit this line, server is available
    return true;
  }
}
