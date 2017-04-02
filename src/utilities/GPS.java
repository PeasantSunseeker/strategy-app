package utilities;

import com.lynden.gmapsfx.javascript.object.LatLong;
import javafx.application.Platform;
import javafx.concurrent.Task;
import ui.controllers.MainController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;
import static ui.controllers.MainController.displayData;
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
	
	private MainController mainController;
	
	public GPS(MainController _mainController){
		mainController = _mainController;
	}
	
	public static void updateLocation(LatLong location){
		positionCircle.setCenter(location);
	}
	
	public void startTask(){
		
		task = new Task<Void>() {
			@Override public Void call() {
				System.out.println("Start GPS Task");
//				updateGPS();
				for(int i = 1; i < positions.length - 1; i++) {
//					System.out.println("Sleep");
					if(task.isCancelled()){
						return null;
					}
					try {
						sleep(1 * 10 * 1000 / positions.length);
					} catch (InterruptedException e) {
						if(task.isCancelled()){
							return null;
						}
						e.printStackTrace();
					}
					Platform.runLater(() -> updateGPS());
				}
				System.out.println("Task Finished");
				return null;
			}
		};
		
		new Thread(task).start();
	}
	
	private void updateGPS() {
		System.out.println("update GPS");
		int total = MainController.positions.length;
		Position next = MainController.positions[positionIndex];
		positionCircle.setCenter(new LatLong(next.getLatitude(),next.getLongitude()));
//		displayData.get(positionIndex).getBatteryCharge();
		MasterData data = displayData.get(positionIndex);
		Position position = data.getPosition();
		String totalCharge = data.getTotalCharge().getValue();
		double newCharge = Double.parseDouble(totalCharge) * 1.15;
		data.setActualTotalCharge(newCharge);
		mainController.updateEnergyGraph();
		
		FileWriter fw;
		boolean append = true;
		try {
			if(positionIndex == 1){
				append = false;
			}
			fw = new FileWriter("telemetry.csv", append);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(String.format("%f,%f,%4.2f",position.getLatitude(),position.getLongitude(),newCharge));
			bw.write("\n");
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
