package Tests.models;

import config.CarConfig;
import models.Parasitic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by broderick on 12/1/16.
 */
public class ParasiticTest {
	@Before
	public void create() {
		CarConfig.loadCarConfig();
	}
	
	@Test
	public void getPowerLossCharging() throws Exception {
		Assert.assertEquals(10.00, Parasitic.getPowerLossCharging(), 0.05);
	}

	@Test
	public void getPowerLossDriving() throws Exception {
		Assert.assertEquals(30.00, Parasitic.getPowerLossDriving(), 0.05);
	}

}