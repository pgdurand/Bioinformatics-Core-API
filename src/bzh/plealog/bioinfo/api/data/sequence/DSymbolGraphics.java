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

import java.awt.Color;

/**
 * This class contains the graphical properties that can be associated to a
 * symbol.
 * 
 * @author Patrick G. Durand
 */
public class DSymbolGraphics {
    private Color   _bk;
    private Color   _text;
    private boolean _negative;
    
    public DSymbolGraphics(){
        setBkColor(Color.white);     
        setTextColor(Color.black);     
    }
    
    public DSymbolGraphics(Color bk, Color text){
        setBkColor(bk);     
        setTextColor(text);     
    }

    public void setBkColor(Color bk){
        if (bk!=null)
            _bk=bk;
        else
            _bk = Color.white;     
    }

    public void setTextColor(Color text){
        if (text!=null)
            _text=text;
        else
            _text = Color.black;     
    }
    
    public Color getBkColor(){
        return (_negative ? _text : _bk);
    }

    public Color getTextColor(){
        return (_negative ? _bk : _text);
    }
    
    public void setNegativeColor(boolean neg){
        _negative = neg;
    }
}
