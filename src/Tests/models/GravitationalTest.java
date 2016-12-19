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
    public void gravityPower() throws Exception {
        Assert.assertEquals(-89, Gravitational.gravityPower(20, 20, 180), .1);
    }

}