package utilities;

import de.micromata.opengis.kml.v_2_2_0.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.List;

/**
 * Created by broderick on 09/04/17.
 */
public class KmlParser {
	public static void main(String[] args) {
		checkLegs();
//		getLegs("./kml/ASC 2016.kml");
	}
	
	public static void checkLegs() {
		File folder = new File("kml");
		if (!folder.exists()) {
			folder.mkdir();
			kmlError();
		}
		File[] files = folder.listFiles();
		if (files.length < 1) {
			kmlError();
		}
		for (File file : files) {
			String[] nameTokens = file.getName().split("\\.(?=[^\\.]+$)");
			File testFolder = new File("legs/" + nameTokens[0]);
//			System.out.println(testFolder.getName());
//			if(!testFolder.exists()){
			getLegs("./kml/" + file.getName());
//			}
		}
	}
	
	private static void getLegs(String filepath) {
		final Kml kml = Kml.unmarshal(new File(filepath));
		final Document document = (Document) kml.getFeature();
		String eventName = document.getName();
//		System.out.println(eventName);
		List<Feature> features = document.getFeature();
		for (Object object : features) {
			Folder folder = (Folder) object;
			String legName = folder.getName();
			legName = legName.replace(":", "");
			List<Feature> folderFeatures = folder.getFeature();
			for (Object folderFeature : folderFeatures) {
				Placemark placemark = (Placemark) folderFeature;
				Geometry geometry = placemark.getGeometry();
				if (geometry instanceof LineString) {
//					System.out.println(legName);
					LineString lineString = (LineString) geometry;
					List<Coordinate> coordinates = lineString.getCoordinates();
					String newFolderName = String.format("legs/%s", eventName);
					File directory = new File(newFolderName);
//					System.out.println(newFolderName);
//					if (!directory.exists()) {
					directory.mkdir();
//					}
					String newFileName = String.format("legs/%s/%s", eventName, legName);
					File file = new File(newFileName);
					Position.convertCoordinatesToPosition(coordinates, newFileName);
				}
			}
		}
	}
	
	private static void kmlError() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("KML Error");
		alert.setHeaderText("KML Error");
		alert.setContentText(".kml file must be placed inside kml folder");
		alert.showAndWait();
		Platform.exit();
		System.exit(1);
	}
}
