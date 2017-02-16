package Tests.models;

import models.Parasitic;
import org.junit.Assert;

/**
 * Created by broderick on 12/1/16.
 */
public class ParasiticTest {
	@org.junit.Test
	public void getPowerLossCharging() throws Exception {
		Assert.assertEquals(10.00, Parasitic.getPowerLossCharging(), 0.05);
	}

	@org.junit.Test
	public void getPowerLossDriving() throws Exception {
		Assert.assertEquals(30.00, Parasitic.getPowerLossDriving(), 0.05);
	}

}