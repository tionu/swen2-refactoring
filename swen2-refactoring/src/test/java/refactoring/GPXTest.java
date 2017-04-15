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
		gpx.setFile(new File("Spessartbogen1.gpx")); // Quelle: http://www.spessartbogen.de/de/download/gps/
		gpx.readFile();
		assertEquals("Spessartbogen1", gpx.getName());
		assertEquals(18788.568, gpx.getDistance(), 0.001);
		assertEquals(112.0, gpx.getDuration(), 0.1);
		assertEquals(10.07, gpx.getAvarageSpeed(), 0.01);
		assertEquals(5.96, gpx.getMinutePerKm(), 0.01);
		assertEquals(234.03, gpx.getMeanHeight(), 0.01);
		assertEquals(346.0, gpx.getMaxHeight(), 0.1);
		assertEquals(139.0, gpx.getMinHeight(), 0.1);
	}

}
