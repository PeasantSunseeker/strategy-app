package Tests.models;

import config.CarConfig;
import models.Rolling;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class RollingTest {
	@Before
	public void create() {
		CarConfig.loadCarConfig("config.properties");
	}
	
	@Test
	public void rollingPower() throws Exception {
		Assert.assertEquals(3.4, Rolling.rollingPower(20, 100), .1);
	}

}