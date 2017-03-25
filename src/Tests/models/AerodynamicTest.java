package Tests.models;

import config.CarConfig;
import models.Aerodynamic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class AerodynamicTest {
	@Before
	public void create() {
		CarConfig.loadCarConfig("config.properties");
	}
	
	@Test
	public void aerodynamicPower() throws Exception {
		Assert.assertEquals(12, Aerodynamic.aerodynamicPower(20), .1);
	}
	
	@Test
	public void aerodynamicPowerWind() throws Exception {
		Assert.assertEquals(1500, Aerodynamic.aerodynamicPowerWind(50, 0, 50, 0), .1);
		Assert.assertEquals(0, Aerodynamic.aerodynamicPowerWind(50, 180, 50, 0), .1);
		Assert.assertEquals(187.5, Aerodynamic.aerodynamicPowerWind(50, 0, 50, 90), .1);
	}
}