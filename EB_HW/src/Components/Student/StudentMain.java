/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class StudentMain {	
	public static void main(String args[]) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("** StudentMain(ID:" + componentId + ") is successfully registered. \n");

		String studentID = null;
		
		StudentComponent studentsList = new StudentComponent("Students.txt");
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeStudentList(studentsList)));
					break;
				case RegisterStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerStudent(studentsList, event.getMessage())));
					break;
				case DeleteStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, removeStudent(studentsList, event.getMessage())));
					break;
				case ListCoursesRegistered:
					printLogEvent("Get", event);
					studentID = event.getMessage();
					if (studentID != null)
						eventBus.sendEvent(new Event(EventId.GetCompletedCourses, getCompletedCourses(studentsList, studentID)));
					break;
				case QuitTheSystem:
					printLogEvent("Get", event);
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static boolean getStudentID(StudentComponent studentsList, String message) {
		if (studentsList.isRegisteredStudent(message)) {
			for (int i = 0; i < studentsList.vStudent.size(); i++)
				if (studentsList.vStudent.get(i).match(message))
					return true;
			return true;
			}
		else
			return false;
	}
	private static String getCompletedCourses(StudentComponent studentsList, String message) {
		if (getStudentID(studentsList, message)) {
			String returnString = studentsList.getCompletedCourses(message);
			return returnString;
		} else
			return "This student is not registered.";
	}
	private static String removeStudent(StudentComponent studentsList, String message) {
		if (studentsList.isRegisteredStudent(message)) {
			for (int i = 0; i < studentsList.vStudent.size(); i++)
				if (studentsList.vStudent.get(i).match(message))
					studentsList.vStudent.remove(i);
			return "This student is successfully removed.";
			}
		else
			return "This student is not registered.";
	}
	private static String registerStudent(StudentComponent studentsList, String message) {
		Student  student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			return "This student is successfully added.";
		} else
			return "This student is already registered.";
	}
	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = "";
		for (int j = 0; j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
