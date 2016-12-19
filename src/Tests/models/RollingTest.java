package Tests.models;

import models.Rolling;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class RollingTest {
    @Test
    public void rollingPower() throws Exception {
        Assert.assertEquals(3.4, Rolling.rollingPower(20,100), .1);
    }

}