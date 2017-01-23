package Tests.models;

import models.Gravitational;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class GravitationalTest {
	@Test
	public void kineticPower() throws Exception {
		Assert.assertEquals(68.25, Gravitational.kineticPower(50,0,1,0,1000),.5);
	}
	
	@Test
	public void getRoadAngle() throws Exception {
		Assert.assertEquals(19, Gravitational.getRoadAngle(35),.5);
	}
	
	@Test
    public void gravityPower() throws Exception {
        Assert.assertEquals(1931, Gravitational.gravityPower(20, 2000, 10), .1);
    }

}