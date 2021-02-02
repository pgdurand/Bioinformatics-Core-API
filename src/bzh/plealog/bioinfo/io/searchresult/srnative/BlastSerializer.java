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
package bzh.plealog.bioinfo.io.searchresult.srnative;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.binary.BinaryStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import bzh.plealog.bioinfo.api.data.feature.FPosition;
import bzh.plealog.bioinfo.api.data.feature.FRange;
import bzh.plealog.bioinfo.api.data.feature.FeatureLocation;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.data.feature.IFeature;
import bzh.plealog.bioinfo.data.feature.IFeatureTable;
import bzh.plealog.bioinfo.data.feature.IQualifier;
import bzh.plealog.bioinfo.data.searchresult.ISRCTerm;
import bzh.plealog.bioinfo.data.searchresult.ISRClassification;
import bzh.plealog.bioinfo.data.searchresult.ISRHit;
import bzh.plealog.bioinfo.data.searchresult.ISRHsp;
import bzh.plealog.bioinfo.data.searchresult.ISRHspScore;
import bzh.plealog.bioinfo.data.searchresult.ISRHspSequence;
import bzh.plealog.bioinfo.data.searchresult.ISRIteration;
import bzh.plealog.bioinfo.data.searchresult.ISROutput;
import bzh.plealog.bioinfo.data.searchresult.ISRParameters;
import bzh.plealog.bioinfo.data.searchresult.ISRRequestInfo;
import bzh.plealog.bioinfo.data.searchresult.ISRStatistics;
import bzh.plealog.bioinfo.data.sequence.IBankSequenceInfo;
import bzh.plealog.bioinfo.util.ZipUtil;

/**
 * This class defines an XML serializer framework aims at saving/loading
 * BOutput-based objects tree. 
 * 
 * @author Patrick G. Durand
 */
public class BlastSerializer {
  //XML: old system
  private static XStream streamerX = null;
  //Binary: new system, more suitable for large data set
  private static XStream streamerB = null;

	private static void initSR(XStream streamer) {
	  //setup security (new feature in recent XStream API)
	  streamer.addPermission(NoTypePermission.NONE);
	  streamer.addPermission(NullPermission.NULL);
	  streamer.addPermission(PrimitiveTypePermission.PRIMITIVES);
	  streamer.allowTypesByWildcard(new String[] {
	      "bzh.plealog.bioinfo.**","java.lang.**","java.util.**"
	  });
	  //setup aliases
	  streamer.alias("BOutput", ISROutput.class);
    streamer.alias("BIteration", ISRIteration.class);
    streamer.alias("BParamaters", ISRParameters.class);
    streamer.alias("BRequestInfo", ISRRequestInfo.class);
    streamer.alias("BStatistics", ISRStatistics.class);
    streamer.alias("BHit", ISRHit.class);
    streamer.alias("BHsp", ISRHsp.class);
    streamer.alias("BHspScore", ISRHspScore.class);
    streamer.alias("BHspSequence", ISRHspSequence.class);
    streamer.alias("FeatureTable", IFeatureTable.class);
    streamer.alias("Feature", IFeature.class);
    streamer.alias("Qualifier", IQualifier.class);
    streamer.alias("SequenceInfo", IBankSequenceInfo.class);
    streamer.alias("FeatureLocation", FeatureLocation.class);
    streamer.alias("FRange", FRange.class);
    streamer.alias("FPosition", FPosition.class);
    streamer.alias("BClassification", ISRClassification.class);
    streamer.alias("BCTerm", ISRCTerm.class);
	}
	private static final String ERR_1 = "Unable to save BOutput in: ";
	private static final String ERR_2 = "Unable to load BOutput from: ";

	private static XStream getSX() {
	  if (streamerX!=null)
	    return streamerX;
	  streamerX = new XStream(new DomDriver("ISO-8859-1"));
    initSR(streamerX);
    return streamerX;
	}
  private static XStream getSB() {
    if (streamerB!=null)
      return streamerB;
    streamerB = new XStream(new BinaryStreamDriver());
    initSR(streamerB);

    return streamerB;
  }
	public static void save(SROutput bo, File f) throws BlastSerializerException{
		String          path;

		//Step 1: generate XML on disk in a tmp file
    path = f.getAbsolutePath()+".tmp";
		try(BufferedOutputStream bos = 
		    new BufferedOutputStream(new FileOutputStream(path))){
		  getSB().toXML(bo, bos);
      bos.flush();
		} catch (Throwable e) {
      throw new BlastSerializerException(ERR_1+f.getAbsolutePath()+": "+e);
    }
		//Step 2: compress using ZIP
		try(FileOutputStream fos = new FileOutputStream(f.getAbsolutePath())){
		  ZipUtil.create(fos, path);
		} catch (Throwable e) {
			throw new BlastSerializerException(ERR_1+f.getAbsolutePath()+": "+e);
		}
		//delete tmp file
		new File(path).delete();
	}

	public static SROutput load(File f)throws BlastSerializerException{
		SROutput bo = null;
		File xFile = f;
		File uzFile = null;
		//try to read XML zipped file
		if(ZipUtil.isZippedFile(f.getAbsolutePath())) {
		  try(FileInputStream fis = new FileInputStream(f)){
		    uzFile = ZipUtil.extract(fis, f.getParent());
		    xFile = uzFile;
      } catch (Throwable e) {
        throw new BlastSerializerException(ERR_2+f.getAbsolutePath()+": "+e);
      }
		}
    //read binary XML first (new system)
	  try(BufferedInputStream bis = 
	      new BufferedInputStream(new FileInputStream(xFile))){
      bo = (SROutput) getSB().fromXML(bis);
    } catch (Throwable e) {
      bo = null;
    }
	  if (bo!=null) {
	    bo.initialize();
	    if (uzFile!=null) {
        uzFile.delete();
      }
	    return bo;
	  }
    //read plain XML (old system; backward compatibility)
    try(BufferedInputStream bis = 
        new BufferedInputStream(new FileInputStream(xFile))){
      bo = (SROutput) getSX().fromXML(bis);
      if (bo!=null) {
        bo.initialize();
      }
    }
    catch (Throwable e) {
      throw new BlastSerializerException(ERR_2+f.getAbsolutePath()+": "+e);
    }
    finally {
      if (uzFile!=null) {
        uzFile.delete();
      }
    }
    return bo;
	}
}
