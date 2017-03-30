package utilities;

import com.lynden.gmapsfx.javascript.object.LatLong;
import ui.controllers.MainController;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 3/27/17
 * <p>
 * DESCRIPTION:
 */
public class GPS {
	public static void updateLocation(LatLong location){
		MainController.positionCircle.setCenter(location);
	}
}
