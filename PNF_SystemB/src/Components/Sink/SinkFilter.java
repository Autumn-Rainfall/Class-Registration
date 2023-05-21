/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Sink;

import java.io.FileWriter;
import java.io.IOException;

import Framework.CommonFilterImpl;

public class SinkFilter extends CommonFilterImpl{
    private String sinkFile;
    private int PipeNo;
    
    public SinkFilter(String outputFile, int PipeNo) {
        this.sinkFile = outputFile;
        this.PipeNo = PipeNo;
    }
    @Override
    public boolean specificComputationForFilter() throws IOException {
        int byte_read = 0;
        FileWriter fw = new FileWriter(this.sinkFile);
        while(true) {
            in.get(byte_read); 
            if (byte_read == -1) {
            	 fw.close();
                 System.out.print( "::Filtering is finished; Output file is created." );  
                 return true;
            }
            fw.write((char)byte_read);
        }   
    }
}
