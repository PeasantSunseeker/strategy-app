package Tests.models;

import config.CarConfig;
import models.Motor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class MotorTest {
	@Before
	public void create() {
		CarConfig.loadCarConfig();
	}
	
	@Test
	public void getEfficiency() throws Exception {
		Assert.assertEquals(0.94, Motor.getEfficiency(), .1);
	}

}