package Tests.models;

import models.Aerodynamic;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by broderick on 12/19/16.
 */
public class AerodynamicTest {
    @Test
    public void aerodynamicPower() throws Exception {
        Assert.assertEquals(12, Aerodynamic.aerodynamicPower(20), .1);
    }

}