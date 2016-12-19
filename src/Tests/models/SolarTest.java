package Tests.models;

import models.Solar;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class SolarTest {
    @Test
    public void solarPower() throws Exception {
        Assert.assertEquals(1171.6, Solar.solarPower(720), .1);
    }

    @Test
    public void getAngle() throws Exception {
        Assert.assertEquals(22.6, Solar.getAngle(720), .1);
    }

}