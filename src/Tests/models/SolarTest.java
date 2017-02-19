package Tests.models;

import models.Solar;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by broderick on 12/19/16.
 */
public class SolarTest {
	@Test
	public void sunLatitudeLocation() throws Exception {
		LocalDateTime date = LocalDateTime.parse("2017-06-22T00:00:00");
		Assert.assertEquals(13.5, Solar.sunNoonAngle(date.getDayOfYear(), 37), .1);
		
		date = LocalDateTime.parse("2017-05-20T00:00:00");
		Assert.assertEquals(17.2, Solar.sunNoonAngle(date.getDayOfYear(), 37), .1);
		
		date = LocalDateTime.parse("2017-12-05T00:00:00");
		Assert.assertEquals(59.5, Solar.sunNoonAngle(date.getDayOfYear(), 37), .1);
	}
	
	@Test
	public void solarPower() throws Exception {
		Assert.assertEquals(1167.8, Solar.solarPower(224, 12.25, 37.0883, 0), .1);
	}
	
	@Test
	public void getAngle() throws Exception {
		Assert.assertEquals(22.96, Solar.getAngle(224, 12.25, 37.0883), .1);
	}
}