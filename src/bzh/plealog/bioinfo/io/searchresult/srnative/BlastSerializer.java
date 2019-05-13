/* Copyright (C) 2006-2016 Patrick G. Durand
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
	private static XStream streamer;

	static {
		streamer = new XStream(new DomDriver("ISO-8859-1"));
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

	public static void toXML(SROutput bo, OutputStream os){
		streamer.toXML(bo, os);
	}
	public static SROutput fromXML(InputStream is){
		return (SROutput) streamer.fromXML(is);
	}
	public static void save(SROutput bo, File f) throws BlastSerializerException{
		FileOutputStream      fos = null;
		ByteArrayOutputStream baos;
		String          path;

		path = f.getAbsolutePath();
		File f2 = new File(path);
		try {
			baos= new ByteArrayOutputStream();
			streamer.toXML(bo,baos);
			baos.flush();
			baos.close();
			fos = new FileOutputStream(f2);
			ZipUtil.createFromMemory(fos, baos.toByteArray());
		} catch (Throwable e) {
			throw new BlastSerializerException(ERR_1+f.getAbsolutePath()+": "+e);
		}
		finally{
			if (fos!=null)
				try {
					fos.close();
				} catch (IOException e) {}
		}
	}

	public static byte[] convertToByteArray(SROutput bo) throws BlastSerializerException{
		ByteArrayOutputStream baos;
		byte[] data=null; 
		try {
			baos= new ByteArrayOutputStream();
			streamer.toXML((SROutput)bo,baos);
			baos.flush();
			baos.close();
			data=baos.toByteArray();
		} catch (Throwable e) {
			throw new BlastSerializerException(ERR_1+": "+e);
		}
		return data;
	}

	public static SROutput convertFromByteArray(byte[] data) throws BlastSerializerException{
		SROutput bo = null;
		try {
			bo = (SROutput) streamer.fromXML(new ByteArrayInputStream(data));
			if (bo!=null)
				bo.initialize();
		} catch (Throwable e) {
			throw new BlastSerializerException(ERR_1+": "+e);
		}
		return (SROutput)bo;
	}

	public static SROutput load(File f)throws BlastSerializerException{
		SROutput         bo = null;
		FileInputStream fis = null;

		//try to read XML zipped file
		try{
			fis=new FileInputStream(f);  
			byte[] data = ZipUtil.extractInMemory(fis);
			bo = (SROutput) streamer.fromXML(new ByteArrayInputStream(data));
			if (bo!=null)
				bo.initialize();
		}
		catch(Throwable ex){
			bo=null;
		}
		finally{
			if (fis!=null)
				try {
					fis.close();
				} catch (IOException e) {}
		}
		if (bo!=null)
			return bo;
		//read plain XML
		try {
			fis=new FileInputStream(f);	
			bo = (SROutput) streamer.fromXML(fis);
			if (bo!=null)
				bo.initialize();
		} catch (Throwable e) {
			throw new BlastSerializerException(ERR_2+f.getAbsolutePath()+": "+e);
		}
		finally{
			if (fis!=null)
				try {
					fis.close();
				} catch (IOException e) {}
		}
		return bo;
	}
}
