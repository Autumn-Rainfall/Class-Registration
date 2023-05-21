/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import Components.Middle.MiddleFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManager {
    public static void main(String[] args) {
        try {
            CommonFilter filterSource1 = new SourceFilter("Students.txt", 0);
            CommonFilter filterSink1 = new SinkFilter("Output1.txt", 0);
            CommonFilter filterMiddle1 = new MiddleFilter();
            CommonFilter filterSource2 = new SourceFilter("Courses.txt", 1);
            CommonFilter filterSink2 = new SinkFilter("Output2.txt", 1);
            CommonFilter filterMiddle2 = new MiddleFilter();
            
            filterSource1.connectOutputTo(filterMiddle1, 0);
            filterSource2.connectOutputTo(filterMiddle2, 1);
            filterMiddle1.connectOutputTo(filterMiddle2, 1);
            filterMiddle2.connectOutputTo(filterSink1, 0);
            filterMiddle2.connectOutputTo(filterSink2, 1);
            
            Thread thread1 = new Thread(filterSource1);
            Thread thread2 = new Thread(filterSource2);
            Thread thread3 = new Thread(filterSink1);
            Thread thread4 = new Thread(filterSink2);
            Thread thread5 = new Thread(filterMiddle1);
            Thread thread6 = new Thread(filterMiddle2);
            
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread6.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
