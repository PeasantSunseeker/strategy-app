package Tests.models;

import models.Battery;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by broderick on 12/19/16.
 */
public class BatteryTest {
	@Test
	public void getCapacity() throws Exception {
		Assert.assertEquals(5588.00, Battery.getCapacity(100), 1);
		//Assert.assertEquals(0, Battery.getCapacity(1), 1);
	}

}