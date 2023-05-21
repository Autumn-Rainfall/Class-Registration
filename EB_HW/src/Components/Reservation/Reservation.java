/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package Components.Reservation;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Reservation {
    protected String studentId;
    protected String courseId;
    protected String message;
//    protected ArrayList<String> prerequisiteCoursesList;
    
    public Reservation(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
        this.studentId = stringTokenizer.nextToken();
        this.courseId = stringTokenizer.nextToken();
        this.message = this.studentId + " " + this.courseId;
//        this.prerequisiteCoursesList = new ArrayList<String>();
//        while(stringTokenizer.hasMoreTokens()) {
//            this.prerequisiteCoursesList.add(stringTokenizer.nextToken());
//        }
    }
    public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
    public boolean match(String message) {
        return this.message.equals(message);
    }
    public String getString() {
        String stringReturn = this.studentId + " " + this.courseId;
//        for(int i = 0; i < this.prerequisiteCoursesList.size(); i++) {
//            stringReturn += " " + this.prerequisiteCoursesList.get(i).toString();
//        }
        return stringReturn;
    }
}
