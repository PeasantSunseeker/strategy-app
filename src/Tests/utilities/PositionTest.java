package Tests.utilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.Position;

import static org.junit.Assert.*;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 2/4/17
 * <p>
 * DESCRIPTION:
 */
public class PositionTest {
	private Position a, b, c, d;
	
	@Before
	public void create() {
		a = new Position(10, 10, 0);
		b = new Position(11, 10, 1000);
		c = new Position(10, 11, 0);
		d = new Position(11, 11, 0);
	}
	
	@Test
	public void getDistance() throws Exception {
		Assert.assertEquals(111.2, Position.getDistance(a, b), .1);
		Assert.assertEquals(109.5, Position.getDistance(a, c), .1);
	}
	
	@Test
	public void calculateAngle() throws Exception {
		Assert.assertEquals(0.51, Position.calculateAngle(a, b), .1);
		Assert.assertEquals(0, Position.calculateAngle(a, c), .1);
	}
	
	@Test
	public void calculateHeading() throws Exception {
		Assert.assertEquals(0, Position.calculateHeading(a, b), .1);
		Assert.assertEquals(89.9, Position.calculateHeading(a, c), .1);
		Assert.assertEquals(44.4, Position.calculateHeading(a, d), .1);
		Assert.assertEquals(180, Position.calculateHeading(b, a), .1);
		Assert.assertEquals(270, Position.calculateHeading(c, a), .1);
		Assert.assertEquals(224.6, Position.calculateHeading(d, a), .1);
	}
}