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
package bzh.plealog.bioinfo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.plealog.genericapp.api.file.EZFileUtils;

/**
 * Utility class to handle ZIP compression.
 * 
 * @author Patrick G. Durand
 */
public class ZipUtil {
  public ZipUtil() {
  }

  private static void addFile(ZipOutputStream zos, File file) throws IOException {
    InputStream is;
    String name;
    ZipEntry e;
    byte[] buf;
    long size;
    int len;

    name = file.getName();
    size = file.length();
    e = new ZipEntry(name);
    e.setTime(file.lastModified());
    if (size == 0) {
      e.setMethod(ZipEntry.STORED);
      e.setSize(0);
      e.setCrc(0);
    }
    zos.putNextEntry(e);
    buf = new byte[1024];
    is = new BufferedInputStream(new FileInputStream(file));
    while ((len = is.read(buf, 0, buf.length)) != -1) {
      zos.write(buf, 0, len);
    }
    is.close();
    zos.closeEntry();
  }

  private static void addData(ZipOutputStream zos, byte[] data) throws IOException {
    ByteArrayInputStream is;
    ZipEntry e;
    byte[] buf;
    int len;

    e = new ZipEntry("data");
    zos.putNextEntry(e);
    buf = new byte[1024];
    is = new ByteArrayInputStream(data);
    while ((len = is.read(buf, 0, buf.length)) != -1) {
      zos.write(buf, 0, len);
    }
    zos.closeEntry();
  }

  public static void createFromMemory(OutputStream out, byte[] data) throws IOException {
    ZipOutputStream zos = new JarOutputStream(out);
    addData(zos, data);
    zos.flush();
    zos.close();
  }

  /**
   * Compress argument data in memory.
   * 
   * @return the zipped form of argument data
   */
  public static byte[] createFromMemory(byte[] data) throws IOException {
    ByteArrayOutputStream baos;

    baos = new ByteArrayOutputStream();
    ZipOutputStream zos = new JarOutputStream(baos);
    addData(zos, data);
    zos.flush();
    zos.close();
    return baos.toByteArray();
  }

  public static void create(OutputStream out, String file) throws IOException {
    ZipOutputStream zos = new JarOutputStream(out);
    addFile(zos, new File(file));
    zos.flush();
    zos.close();
  }

  public static File extract(InputStream in, String basePath) throws IOException {
    ZipInputStream zis;
    ZipEntry e;
    File f;
    OutputStream os;
    byte[] b;
    int len;

    zis = new ZipInputStream(in);
    e = zis.getNextEntry();
    f = new File(EZFileUtils.terminatePath(basePath) + e.getName());
    os = new FileOutputStream(f);
    b = new byte[512];
    while ((len = zis.read(b, 0, b.length)) != -1) {
      os.write(b, 0, len);
    }
    zis.closeEntry();
    os.flush();
    os.close();
    zis.close();
    return f;
  }

  public static byte[] extractInMemory(InputStream in) throws IOException {
    ZipInputStream zis;
    ByteArrayOutputStream baos;
    byte[] b;
    int len;

    zis = new ZipInputStream(in);
    zis.getNextEntry();
    baos = new ByteArrayOutputStream();
    b = new byte[512];
    while ((len = zis.read(b, 0, b.length)) != -1) {
      baos.write(b, 0, len);
    }
    zis.closeEntry();
    baos.flush();
    baos.close();
    zis.close();
    return baos.toByteArray();
  }

  /**
   * @param in
   *          a zipped data block
   * 
   * @return the unzipped content of argument in
   */
  public static byte[] extractInMemory(byte[] in) throws IOException {
    ZipInputStream zis;
    ByteArrayInputStream zin;
    ByteArrayOutputStream baos;
    byte[] b;
    int len;

    zin = new ByteArrayInputStream(in);
    zis = new ZipInputStream(zin);
    zis.getNextEntry();
    baos = new ByteArrayOutputStream();
    b = new byte[512];
    while ((len = zis.read(b, 0, b.length)) != -1) {
      baos.write(b, 0, len);
    }
    zis.closeEntry();
    baos.flush();
    baos.close();
    zis.close();
    zin.close();
    return baos.toByteArray();
  }

  /**
   * Extract the content of a zip stream into some directory. This method does not handle
   * directory tree structure, see unzip() for that purpose. 
   * 
   * @param in zipped input stream
   * @param basePath target directory
   * @param fExt file extension filter (will only retain those files). Cannot be null.
   * 
   * @see ZipUtil#unzip(InputStream, String)
   * */
  public static void extractAll(InputStream in, String basePath, String fExt) throws IOException {
    ZipInputStream zis;
    ZipEntry e;
    File f;
    OutputStream os;
    byte[] b;
    int len;

    zis = new ZipInputStream(in);
    while ((e = zis.getNextEntry()) != null) {
      if (e.getName().endsWith(fExt) && !e.getName().startsWith("__MACOSX")) {
        f = new File(EZFileUtils.terminatePath(basePath) + e.getName());
        os = new FileOutputStream(f);
        b = new byte[512];
        while ((len = zis.read(b, 0, b.length)) != -1) {
          os.write(b, 0, len);
        }
        os.flush();
        os.close();
      }
      zis.closeEntry();
    }
    zis.close();
  }
  
  /**
   * Extracts a zip file specified by zipIN to a directory specified by
   * destDirectory (will be created if does not exists).
   * @param zipIN zip input stream
   * @param destDirectory destination directory
   * @throws IOException
   */
  public static void unzip(InputStream zipIN, String destDirectory) throws IOException {
      File destDir = new File(destDirectory);
      if (!destDir.exists()) {
          destDir.mkdir();
      }
      ZipInputStream zipIn = new ZipInputStream(zipIN);
      ZipEntry entry = zipIn.getNextEntry();
      // iterates over entries in the zip file
      while (entry != null) {
          String filePath = destDirectory + File.separator + entry.getName();
          if (!entry.isDirectory()) {
              // if the entry is a file, extracts it
              extractFile(zipIn, filePath);
          } else {
              // if the entry is a directory, make the directory
              File dir = new File(filePath);
              dir.mkdir();
          }
          zipIn.closeEntry();
          entry = zipIn.getNextEntry();
      }
      zipIn.close();
  }
  /**
   * Extracts a zip entry (file entry)
   * @param zipIn
   * @param filePath
   * @throws IOException
   */
  private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
      byte[] bytesIn = new byte[4096];
      int read = 0;
      while ((read = zipIn.read(bytesIn)) != -1) {
          bos.write(bytesIn, 0, read);
      }
      bos.close();
  }

  public static boolean isZippedFile(String filePath) {
    boolean isZipped = false;
    try(FileInputStream fis = new FileInputStream(filePath)){
      isZipped = new ZipInputStream(fis).getNextEntry() != null;
    } catch (Throwable e) {
      isZipped = false;
    }
    return isZipped;
  }
}
