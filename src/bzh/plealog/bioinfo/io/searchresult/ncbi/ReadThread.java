package bzh.plealog.bioinfo.io.searchresult.ncbi;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import bzh.plealog.bioinfo.data.blast.loader.ncbi.resources.Messages;

class ReadThread extends Thread implements Runnable {
    private Reader reader = null;
    private OutputStream os = null;    
    protected boolean bError = false;
    
    ReadThread(Reader reader, OutputStream os) {
        this.reader = reader;
        this.os = os;
    }

    public void run() {
        BufferedWriter writer = null;
        String         str;
        char           buf[];
        int            read, idx;
        boolean        bFirst = true;

        try {
        	buf = new char[2048];
            //read Tmp file and put data into the SAX parser
        	writer = new BufferedWriter(new OutputStreamWriter(os));
        	while((read=reader.read(buf, 0, 2048))!=-1){
        		if (bFirst){
            		str = new String(buf,0,read);
            		idx = str.indexOf("<BlastOutput");
            		if (idx==-1) {
            		  idx = str.indexOf("<BlastXML2");
            		  str = "<BlastXML2>\n";
            		}
            		if (idx!=-1){
            			writer.write("<?xml version=\"1.0\"?>\n");
            			str = str.substring(idx);
            			writer.write(str);
            		}
        			bFirst = false;
        		}
        		else{
        			writer.write(buf, 0, read);
        		}
        	}
        	
        } 
        catch (Exception e) {  
            bError = true;
            System.err.println(Messages.getString("BlastLoader.1")+e.toString());
        } 
        try{if (writer!=null){writer.flush();writer.close();}}catch(Exception e){}//not bad
        try{if (reader!=null){reader.close();}}catch(Exception e){}//not bad
    }
}