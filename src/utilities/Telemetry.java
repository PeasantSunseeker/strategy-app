package utilities;

import javafx.application.Platform;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;
import static ui.controllers.MainController.positions;

/**
 * Created by broderick on 4/2/17.
 */
public class Telemetry {
	private static Task task;
	
	public static void startTask() {
		task = new Task<Void>() {
			@Override
			public Void call() {
				System.out.println("Start Telemetry Task");
//				updateGPS();
//				for(int i = 1; i < positions.length - 1; i++) {
				while (true) {
////					System.out.println("Sleep");
//					if(task.isCancelled()){
//						return null;
//					}
					try {
						sleep(1 * 5 * 1000);
					} catch (InterruptedException e) {
						if (task.isCancelled()) {
							return null;
						}
						e.printStackTrace();
					}
					System.out.println("Read");
//					Platform.runLater(() -> updateGPS());
				}
//				System.out.println("Task Finished");
//				return null;
			}
		};

//		new Thread(task).start();
	}
	
	public static void killTask() {
		if (task != null) {
			task.cancel();
		}
	}
}
