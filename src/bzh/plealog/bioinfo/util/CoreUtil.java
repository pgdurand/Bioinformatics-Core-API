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
package bzh.plealog.bioinfo.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class CoreUtil {
	public static final DecimalFormat EVALUE_FORMATTER1 = new DecimalFormat("0.####E0");
	public static final DecimalFormat EVALUE_FORMATTER2 = new DecimalFormat("##.##");
	public static final DecimalFormat PCT_FORMATTER = new DecimalFormat("###.#");
	public static final DecimalFormat SCORE_FORMATTER = new DecimalFormat("####");
	/**
	 * Cleanup string for display or for XML transmission.  
	 * Replace all known chars to cause XML problems with their XML safe equivalents
	 * or replace the XML safe codes with their ASCII equivalents when displaying to users
	 * depending on the value of decode. The method also looks for char < Ascii 32 and replace
	 * with space chars.
	 *
	 * @param str string to be cleaned up
	 * @param decode true if you are converting FROM XML, false if converting TO XML
	 * @return the result of the conversion
	 */
	public static String Cleanup(String str, boolean decode) {
		String saXMLEquivalent[] = {"&amp;", "&apos;", "&quot;", "&lt;", "&gt;"};
		String saSpecialChars[] = {"&", "\'", "\"", "<", ">"};
		//String saSpecialChars[] = {"and", "|", "|", ".:", ":."};
		String sFind;
		String sReplace;
		boolean bFound;
		int iPos = -1;
		int i = 0;
		if (str==null)
			return null;
		while (i < saXMLEquivalent.length) {
			String newStr = "";
			if (decode) {
				//Search for XML encodeded string and convert it back to plain ASCII
				sFind = saXMLEquivalent[i];
				sReplace = saSpecialChars[i];
			} else {
				//Search for special chars in ASCII and replace with XML safe chars
				sFind = saSpecialChars[i];
				sReplace = saXMLEquivalent[i];
			}
			do {
				iPos = str.indexOf(sFind, ++iPos);
				if (iPos > -1) {
					newStr = newStr + str.substring(0, iPos) + sReplace + str.substring(iPos+sFind.length(),str.length());
					str = newStr;
					newStr = "";
					bFound = true;
				} else {
					bFound = false;
				}
			} while ( bFound );
			i++;
		}
		//this has been added since some strings from the PDB file contains 0x01 characters !
		boolean handle = false;
		int size = str.length();
		for(i=0;i<size;i++){
			if (str.charAt(i)<(char)32){
				handle=true;
				break;
			}
		}
		if(handle){
			StringBuffer buf = new StringBuffer();
			char         ch;
			for(i=0;i<size;i++){
				if ((ch=str.charAt(i))>=(char)32){
					buf.append(ch);
				}
				else{
					buf.append(" ");
				}
			}
			str = buf.toString();
		}
		return(str);
	}
	public static String replaceAll(String str, String sFind, String sReplace) {
		boolean bFound;
		int iPos = -1;

		String newStr = "";
		do {
			iPos = str.indexOf(sFind, ++iPos);
			if (iPos > -1) {
				newStr = newStr + str.substring(0, iPos) + sReplace + str.substring(iPos+sFind.length(),str.length());
				str = newStr;
				newStr = "";
				iPos += (sReplace.length()-1);
				bFound = true;
			} else {
				bFound = false;
			}
		} while ( bFound );
		return(str);
	}
	/**
	 * Replace first occurrence of sFind by sReplace within str. If sFind cannot be found
	 * the method returns null. In comparison with the same method from the String class,
	 * this implementation does not use RegExp.
	 */
	public static String replaceFirst(String str, String sFind, String sReplace) {
		int iPos = str.indexOf(sFind);
		if (iPos > -1) {
			return str.substring(0, iPos) + sReplace + str.substring(iPos+sFind.length(),str.length());
		}
		else{
			return null;
		}
	}

	public static String[] tokenize(String input) {
		return tokenize(input, ",\t\n\r\f");
	}

	public static String[] tokenize(String input, String delim) {
		StringTokenizer tokenizer;
		String          str[];
		int             i = 0;

		if (input == null)
			return new String[]{};

		tokenizer = new StringTokenizer(input, delim);

		if (tokenizer.countTokens() == 0)
			return new String[]{};

		str = new String[tokenizer.countTokens()];
		while (tokenizer.hasMoreTokens()){
			str[i] = (String) tokenizer.nextToken().trim();
			i++;
		}

		return str;
	}
	
	public static void closeQuietly(final Closeable closeable) {
    try {
        if (closeable != null) {
            closeable.close();
        }
    } catch (final IOException ioe) {
        // ignore
    }
	}
	
	public static boolean deleteQuietly(final File file) {
    if (file == null) {
        return false;
    }

    try {
        return file.delete();
    } catch (final Exception ignored) {
        return false;
    }
	}
}
