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
	private Position a, b, c;
	
	@Before
	public void create() {
		a = new Position(10, 10, 0);
		b = new Position(11, 10, 100);
		c = new Position(10, 11, 0);
	}
	
	@Test
	public void getDistance() throws Exception {
		Assert.assertEquals(111.2, Position.getDistance(a, b), .1);
		Assert.assertEquals(109.5, Position.getDistance(a, c), .1);
	}
	
	@Test
	public void calculateAngle() throws Exception {
		Assert.assertEquals(41.9, Position.calculateAngle(a, b), .1);
		Assert.assertEquals(0, Position.calculateAngle(a, c), .1);
	}
	
}