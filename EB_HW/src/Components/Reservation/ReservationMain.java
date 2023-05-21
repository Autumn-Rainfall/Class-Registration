/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Reservation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class ReservationMain {
//	private Semaphore semaphore;

	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException, InterruptedException {
		Thread thread = new Thread();
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("ReservationMain (ID:" + componentId + ") is successfully registered...");

		ReservationComponent reservationList = new ReservationComponent("Reservation.txt");
		Event event = null;
		String completedCourses = null;
		String prerequisiteCourses = null;
		String SID = null;
		String CID = null;

		boolean done = false;
		while (!done) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListReservations:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeReservationList(reservationList)));
					break;
				case GetCompletedCourses:
					printLogEvent("Get", event);
					completedCourses = event.getMessage();
					Thread.sleep(200);
					break;
				case GetprerequisiteCourses:
//					thread.join();
					printLogEvent("Get", event);
					prerequisiteCourses = event.getMessage();
					if (!completedCourses.equals("This student is not registered.") && !prerequisiteCourses.equals("This course is not registered.")) {
						if (isCompleted(reservationList, completedCourses, prerequisiteCourses))
							eventBus.sendEvent(new Event(EventId.ClientOutput, registration(reservationList, SID, CID)));
						else
							System.out.println("You failed to register for the course.");
					} else
						System.out.println("You failed to register for the course.");
					System.out.println("Student ID : " + SID + "\n" + "Course ID : " + CID);
					break;
				case GetSID:
					printLogEvent("Get", event);
					SID = event.getMessage();
					Thread.sleep(300);
//					eventBus.sendEvent(new Event(EventId.ListCoursesRegistered, SID));
					break;
				case GetCID:
					printLogEvent("Get", event);
					CID = event.getMessage();
					TimeUnit.SECONDS.sleep(1);
//					eventBus.sendEvent(new Event(EventId.ListCoursesCompleted, CID));
					break;
				case Unregistration:
					printLogEvent("Get", event);
					eventBus.sendEvent(
					new Event(EventId.ClientOutput, unregistration(reservationList, event.getMessage())));
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

	private static boolean isCompleted(ReservationComponent reservationList, String completedCourses, String prerequisiteCourses) {
		String[] preArr = prerequisiteCourses.split("\\s+");
		ArrayList<String> preList = new ArrayList<String>(Arrays.asList(preArr));
		if (reservationList.checkCourse(completedCourses, preList)) {
			System.out.println("You have completed an prerequisite courses");
			return true;
		} else {
			System.out.println("Prerequisites have not been completed");
			return false;
		}
	}

	private static String unregistration(ReservationComponent reservationList, String message) {
		if (reservationList.isRegisteredReservation(message)) {
			for (int i = 0; i < reservationList.vReservation.size(); i++)
				if (reservationList.vReservation.get(i).match(message))
					reservationList.vReservation.remove(i);
			return "This reservation is successfully removed.";
		} else
			return "This reservation is not registered.";
	}

	private static String registration(ReservationComponent reservationList, String SID, String CID) {
		String message = SID + " " + CID;
		Reservation reservation = new Reservation(message);
		if (reservationList.isRegisteredReservation(message)) {
			for (int i = 0; i < reservationList.vReservation.size(); i++)
				if (reservationList.vReservation.get(i).match(message))
					return "This reservation is already registered.";
			return "This reservation is already registered.";
		} else {
			reservationList.vReservation.add(reservation);
			return "This reservation is successfully added.";
		}
	}

	private static String makeReservationList(ReservationComponent reservationList) {
		String returnString = "";
		for (int j = 0; j < reservationList.vReservation.size(); j++) {
			returnString += reservationList.getReservationList().get(j).getString() + "\n";
		}
		return returnString;
	}

	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
