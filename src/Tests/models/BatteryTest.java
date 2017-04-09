package Tests.models;

import config.CarConfig;
import models.Battery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by broderick on 12/19/16.
 */
public class BatteryTest {
	@Before
	public void create() {
		CarConfig.loadCarConfig("config.properties");
	}
	
	@Test
	public void getCapacity() throws Exception {
		Assert.assertEquals(8078, Battery.getCapacity(100), 1);
		Assert.assertEquals(8078, Battery.getCapacity(0), 1);
	}

}