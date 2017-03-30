package utilities;

import com.lynden.gmapsfx.javascript.object.LatLong;
import javafx.application.Platform;
import javafx.concurrent.Task;
import ui.controllers.MainController;

import static java.lang.Thread.sleep;
import static ui.controllers.MainController.positionCircle;
import static ui.controllers.MainController.positions;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 3/27/17
 * <p>
 * DESCRIPTION:
 */
public class GPS{
	private static int positionIndex = 1;
	private static Task task;
	
	public static void updateLocation(LatLong location){
		positionCircle.setCenter(location);
	}
	
	public static void startTask(){
		task = new Task<Void>() {
			@Override public Void call() {
				System.out.println("Start GPS Task");
				for(int i = 1; i < positions.length; i++) {
//					System.out.println("Sleep");
					if(task.isCancelled()){
						return null;
					}
					try {
						sleep(200);
					} catch (InterruptedException e) {
						if(task.isCancelled()){
							return null;
						}
						e.printStackTrace();
					}
					Platform.runLater(GPS::updateGPS);
				}
				System.out.println("Task Finished");
				return null;
			}
		};
		
		new Thread(task).start();
	}
	
	private static void updateGPS() {
//		System.out.println("update GPS");
		int total = MainController.positions.length;
		Position next = MainController.positions[positionIndex];
		positionCircle.setCenter(new LatLong(next.getLatitude(),next.getLongitude()));
		positionIndex += 1;
		if(positionIndex == total){
			positionIndex = total;
		}
//		System.out.println("update finished");
	}
	
	public static void killTask(){
		task.cancel();
	}
	
//	@Override
//	public void run() {
//		int index = 1;
//		int total = MainController.positions.length;
//		while(true){
//			try {
//				sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			Position next = MainController.positions[index];
//			MainController.positionCircle.setCenter(new LatLong(next.getLatitude(),next.getLongitude()));
//			index += 1;
//			if(index == total){
//				index = total;
//			}
//		}
//	}
}
