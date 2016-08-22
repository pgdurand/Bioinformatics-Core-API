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
package bzh.plealog.bioinfo.api.data.sequence;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * This class manages families of symbols.
 * 
 * @author Patrick G. Durand
 */
public abstract class DSymbolFamilySystem {

	private static Hashtable<String, DSymbolFamily> _families = new Hashtable<String, DSymbolFamily>();
	
	//Proteic default families
	public static final String DF_ALIPHATIC = "Aliphatic";
	public static final String DF_AROMATIC = "Aromatic";
	public static final String DF_CHARGED = "Charged";
	public static final String DF_TINY = "Tiny";
	public static final String DF_ACID = "Acid";
	public static final String DF_BASIC = "Basic";
	
	//Nucleic default families
	public static final String DF_GC = "%GC";

	//String resources: future usage: a resource file with family description
	public static void initDefaultFamilies(String resources){
		DSymbolFamily family;
		DAlphabet     alph;
		
		//from: http://prowl.rockefeller.edu/aainfo/pchem.htm
		alph = DViewerSystem.getIUPAC_Protein_Alphabet();
		family = new DSymbolFamily(DF_ALIPHATIC, DAlphabet.PROTEIN_ALPHABET);
		family.addSymbol(alph.getSymbol('V'));
		family.addSymbol(alph.getSymbol('I'));
		family.addSymbol(alph.getSymbol('L'));
		DSymbolFamilySystem.addFamily(DF_ALIPHATIC, family);
		
		family = new DSymbolFamily(DF_AROMATIC, DAlphabet.PROTEIN_ALPHABET);
		family.addSymbol(alph.getSymbol('F'));
		family.addSymbol(alph.getSymbol('H'));
		family.addSymbol(alph.getSymbol('W'));
		family.addSymbol(alph.getSymbol('Y'));
		DSymbolFamilySystem.addFamily(DF_AROMATIC, family);

		family = new DSymbolFamily(DF_CHARGED, DAlphabet.PROTEIN_ALPHABET);
		family.addSymbol(alph.getSymbol('D'));
		family.addSymbol(alph.getSymbol('E'));
		family.addSymbol(alph.getSymbol('H'));
		family.addSymbol(alph.getSymbol('K'));
		family.addSymbol(alph.getSymbol('R'));
		DSymbolFamilySystem.addFamily(DF_CHARGED, family);

		family = new DSymbolFamily(DF_TINY, DAlphabet.PROTEIN_ALPHABET);
		family.addSymbol(alph.getSymbol('A'));
		family.addSymbol(alph.getSymbol('C'));
		family.addSymbol(alph.getSymbol('G'));
		family.addSymbol(alph.getSymbol('S'));
		family.addSymbol(alph.getSymbol('T'));
		DSymbolFamilySystem.addFamily(DF_TINY, family);

		family = new DSymbolFamily(DF_ACID, DAlphabet.PROTEIN_ALPHABET);
		family.addSymbol(alph.getSymbol('D'));
		family.addSymbol(alph.getSymbol('E'));
		DSymbolFamilySystem.addFamily(DF_ACID, family);
		
		family = new DSymbolFamily(DF_BASIC, DAlphabet.PROTEIN_ALPHABET);
		family.addSymbol(alph.getSymbol('R'));
		family.addSymbol(alph.getSymbol('K'));
		DSymbolFamilySystem.addFamily(DF_BASIC, family);

		alph = DViewerSystem.getIUPAC_DNA_Alphabet();
		family = new DSymbolFamily(DF_GC, DAlphabet.DNA_ALPHABET);
		family.addSymbol(alph.getSymbol('G'));
		family.addSymbol(alph.getSymbol('C'));
		DSymbolFamilySystem.addFamily(DF_GC, family);

	}
	/**
	 * Adds a new family. Since a family is uniquely identified by its name,
	 * adding a family with a name that already exists will erase the old family
	 * with the new one.
	 */
	public static void addFamily(String name, DSymbolFamily family){
		_families.put(name, family);	
	}
	
	/**
	 * gets a family by its name. Returns null if family is unknown.
	 */
	public static DSymbolFamily getFamily(String name){
		return _families.get(name);
	}
	/**
	 * Returns the complete list of families.
	 */
	public static List<String> getFamilyNames(){
		ArrayList<String> lst;
		Enumeration<String> keys;
		
		lst = new ArrayList<String>();
		keys = _families.keys();
		while(keys.hasMoreElements()){
			lst.add(keys.nextElement());
		}
		return lst;
	}

	/**
	 * Returns a list of families of a given type.
	 * 
	 * @param alphType one of DAlphabet.XXX_ALPHABET constants.
	 * @return a list of family names. May return an empty list.
	 */
	public static List<String> getFamilyNames(int alphType){
		ArrayList<String>   lst;
		Enumeration<String> keys;
		DSymbolFamily       family;
		String              key;
		
		lst = new ArrayList<String>();
		keys = _families.keys();
		while(keys.hasMoreElements()){
			key = keys.nextElement();
			family = _families.get(key);
			if (family.getType()==alphType)
				lst.add(key);
		}
		return lst;
	}
}
