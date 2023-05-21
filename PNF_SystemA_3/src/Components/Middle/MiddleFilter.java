/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Middle;

import java.io.IOException;
import Framework.CommonFilterImpl;

public class MiddleFilter extends CommonFilterImpl{
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	int checkBlank13 = 1; 
    	int checkBlankCS = 4; 
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean is13 = false;    
        boolean isCS = true;    
        int byte_read = 0;
        
        while(true) {          
        	// check "CS" on byte_read from student information
            while(byte_read != '\n' && byte_read != -1) {
            	byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if (numOfBlank == checkBlank13 && buffer[idx-7] == '1' && buffer[idx-6] == '3')
            		is13 = true;
                if(numOfBlank == checkBlankCS && buffer[idx-3] == 'C' && buffer[idx-2] == 'S')
                	isCS = false;
            }      
            if(isCS == true && is13 == true) {
                for(int i = 0; i<idx; i++) 
                    out.write((char)buffer[i]);
        		is13 = false;
                isCS = true;
            } else {
            	is13 = false;
                isCS = true;
            }
            	
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }  
}
