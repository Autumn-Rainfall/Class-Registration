/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package Components.Reservation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReservationComponent {
    protected ArrayList<Reservation> vReservation;

    public ReservationComponent(String sReservationFileName) throws FileNotFoundException, IOException { 	
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(sReservationFileName));       
        this.vReservation  = new ArrayList<Reservation>();
        while (bufferedReader.ready()) {
            String reservationInfo = bufferedReader.readLine();
            if(!reservationInfo.equals("")) this.vReservation.add(new Reservation(reservationInfo));
        }    
        bufferedReader.close();
    }
    public ArrayList<Reservation> getReservationList() {
        return this.vReservation;
    }
    public boolean isRegisteredReservation(String message) {
        for (int i = 0; i < this.vReservation.size(); i++) {
            if(((Reservation) this.vReservation.get(i)).match(message)) return true;
        }
        return false;
    }
    public boolean checkCourse(String completedCourseList, ArrayList<String> prerequisite) {
		for (int i = 0; i < prerequisite.size(); i++) {
			if (!(completedCourseList.contains(prerequisite.get(i)))) {
				return false;
			}
		}
		return true;
	}
}
