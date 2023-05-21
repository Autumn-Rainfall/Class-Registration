package Components.Utililty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String studentId;
    protected String lastName;
    protected String firstName;
    protected String fullName;
    protected String department;
    protected ArrayList<String> completedCoursesList;
 
    public Student(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.studentId = stringTokenizer.nextToken();
    	this.lastName = stringTokenizer.nextToken();
    	this.firstName = stringTokenizer.nextToken();
    	this.fullName = lastName + " " + firstName;
    	this.department = stringTokenizer.nextToken();
    	this.completedCoursesList = new ArrayList<String>();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.completedCoursesList.add(stringTokenizer.nextToken());
    	}
    }

    public boolean match(String studentId) {
        return this.studentId.equals(studentId);
    }

    public String getName() {
        return this.fullName;
    }

    public String getCompletedCourses() {
    	String CCourses = "";
        for (int i = 0; i < this.completedCoursesList.size(); i++) {
        	CCourses = CCourses + ", " + this.completedCoursesList.get(i).toString();
        }
        return CCourses;
    }

    public String toString() {
//        String stringReturn = this.studentId + " " + this.name + " " + this.department;
        String stringReturn = String.format(" %-10s %-15s %-5s", this.studentId , this.fullName , this.department);
        for (int i = 0; i < this.completedCoursesList.size(); i++) {
            stringReturn = stringReturn + " " + this.completedCoursesList.get(i).toString();
        }
        return stringReturn;
    }

}
