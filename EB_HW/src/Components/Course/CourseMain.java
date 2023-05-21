/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class CourseMain {
	static boolean checkID = false;

	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("CourseMain (ID:" + componentId + ") is successfully registered...");

		String courseID = null;
		
		CourseComponent coursesList = new CourseComponent("Courses.txt");
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
				case ListCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
					break;
				case RegisterCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case DeleteCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, removeCourseList(coursesList, event.getMessage())));
					break;
				case ListCoursesCompleted:
					printLogEvent("Get", event);
					courseID = event.getMessage();
					if (courseID != null)
						eventBus.sendEvent(new Event(EventId.GetprerequisiteCourses, getPrerequisite(coursesList, courseID)));
					break;
				case QuitTheSystem:
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static void getCourseID(CourseComponent coursesList, String message) {
		if (coursesList.isRegisteredCourse(message))
			checkID = true;
		else
			checkID = false;
	}
	private static String getPrerequisite(CourseComponent coursesList, String message) {
		getCourseID(coursesList, message);
		if (checkID = true) {
		String prerequisiteList = coursesList.getPrerequisite(message);
		return prerequisiteList;
		} else
			return "This course is not registered.";
	}
	private static String removeCourseList(CourseComponent coursesList, String message) {
		if (coursesList.isRegisteredCourse(message)) {
			for (int i = 0; i < coursesList.vCourse.size(); i++)
				if (coursesList.vCourse.get(i).match(message))
					coursesList.vCourse.remove(i);
			return "This course is successfully removed.";
			}
		else
			return "This course is not registered.";
	}
	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			return "This course is successfully added.";
		} else
			return "This course is already registered.";
	}
	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = "";
		for (int j = 0; j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
