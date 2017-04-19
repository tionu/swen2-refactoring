package refactoring;

import java.io.IOException;
import java.text.ParseException;

import org.jdom.JDOMException;

public class Starter {
	public static void main(String[] args) throws JDOMException, IOException, ParseException {
		GPX gpx = new GPX();
		gpx.parseFile(Util.selectFile("GPX"));
		System.out.println("Track-Name: " + gpx.getName());
		System.out.println("Distanz: " + gpx.getDistance());
		System.out.println("Dauer: " + gpx.getDuration());
		System.out.println("Durchschnittsgeschwindigkeit: " + gpx.getAvarageSpeedKmH());
		System.out.println("Tempo: " + gpx.getMinutePerKm());
		System.out.println("Durschnittshöhe: " + gpx.getMeanHeight());
		System.out.println("Maximale Höhe: " + gpx.getMaxHeight());
		System.out.println("Minimale Höhe: " + gpx.getMinHeight());
	}
}
