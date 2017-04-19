package refactoring;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class GPXTest {

	private GPX gpx;

	@Before
	public void setUp() throws Exception {
		gpx = new GPX();
	}

	@Test
	public void testReadFile() throws Exception {
		gpx.parseFile(new File("Spessartbogen1.gpx")); // Quelle:
														// http://www.spessartbogen.de/de/download/gps/
		assertEquals("Spessartbogen1", gpx.getName());
		assertEquals(18788.597, gpx.getDistance(), 0.001);
		assertEquals(112.59, gpx.getDurationMinutes(), 0.1);
		assertEquals(18.78, gpx.getAvarageSpeedKmH(), 0.01);
		assertEquals(5.96, gpx.getMinutePerKm(), 0.01);
		assertEquals(234.14, gpx.getMeanHeight(), 0.01);
		assertEquals(346.0, gpx.getMaxHeight(), 0.1);
		assertEquals(139.0, gpx.getMinHeight(), 0.1);
	}

}
