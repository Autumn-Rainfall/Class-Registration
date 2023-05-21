package Components.Utililty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String courseId;
    protected String profSur;
    protected String name;
    protected ArrayList<String> prerequisite;
     
    public Course(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.courseId = stringTokenizer.nextToken();
    	this.profSur = stringTokenizer.nextToken();
    	this.name = stringTokenizer.nextToken();
    	this.prerequisite = new ArrayList<String>();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.prerequisite.add(stringTokenizer.nextToken());
    	}
    }

    public boolean match(String courseId) {
        return this.courseId.equals(courseId);
    }

    public ArrayList<String> getPrerequisite() {return this.prerequisite;}

    public String toString() {
//        String stringReturn = newline + this.courseId + " " + this.profSur + " " + this.name;
        String stringReturn = String.format(" %-10s %-10s %-35s", this.courseId , this.profSur , this.name);
        for (int i = 0; i < this.prerequisite.size(); i++) {
            stringReturn = stringReturn + " " + this.prerequisite.get(i).toString();
        }
        return stringReturn;
    }

}
