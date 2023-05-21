/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import Components.AddFilter.AddFilter;
import Components.Middle.MiddleFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManager {
    public static void main(String[] args) {
        try {
            CommonFilter filterSource = new SourceFilter("Students.txt");
            CommonFilter filterSink = new SinkFilter("Output.txt");
            CommonFilter filterMiddle = new MiddleFilter();
            CommonFilter filterAdd = new AddFilter();
            
            filterSource.connectOutputTo(filterMiddle);
            filterMiddle.connectOutputTo(filterAdd);
            filterAdd.connectOutputTo(filterSink);
            
            Thread thread1 = new Thread(filterSource);
            Thread thread2 = new Thread(filterSink);
            Thread thread3 = new Thread(filterMiddle);
            Thread thread4 = new Thread(filterAdd);
            
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
