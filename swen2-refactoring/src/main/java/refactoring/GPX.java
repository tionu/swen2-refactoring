package refactoring;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class GPX {

	private String name;
	private long durationMilliseconds;
	private double distanceMeter;
	private double maxHeight;
	private double minHeight;
	private double heightSum;
	private int pointsCount;

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	public GPX() {
		distanceMeter = 0;
		durationMilliseconds = 0;
		maxHeight = 0;
		minHeight = 0;
		heightSum = 0;
		pointsCount = 0;
	}

	public void parseFile(File file) throws JDOMException, IOException, ParseException {

		SAXBuilder sax = new SAXBuilder();

		Document doc = sax.build(file);
		Element root = doc.getRootElement();
		Namespace namespace = root.getNamespace();
		Element trackElement = root.getChild("trk", namespace);
		name = trackElement.getChildText("name", namespace);
		TrackPoint trackPointLast;

		for (Object trkseg : trackElement.getChildren("trkseg", namespace)) {
			trackPointLast = null;
			for (Object trkptObj : ((Element) trkseg).getChildren("trkpt", namespace)) {
				pointsCount++;

				TrackPoint trackPoint = parseXmlTrackPoint((Element) trkptObj);

				if (trackPointLast == null) {
					trackPointLast = trackPoint;
					continue;
				}
				if (trackPointLast.equals(trackPoint))
					continue;

				distanceMeter += trackPoint.calculateDistance(trackPointLast);
				durationMilliseconds += trackPoint.getTimestamp().getTime() - trackPointLast.getTimestamp().getTime();
				heightSum += trackPoint.getElevation();

				if (trackPoint.getElevation() > maxHeight)
					maxHeight = trackPoint.getElevation();
				if (trackPoint.getElevation() < minHeight || minHeight == 0)
					minHeight = trackPoint.getElevation();

				trackPointLast = trackPoint;
			}
		}
	}

	private TrackPoint parseXmlTrackPoint(Element xmlTrackPoint) throws ParseException {
		double latitude = Double.valueOf(xmlTrackPoint.getAttributeValue("lat")) / 180 * Math.PI;
		double longitude = Double.valueOf(xmlTrackPoint.getAttributeValue("lon")) / 180 * Math.PI;
		Date timestamp = DATE_TIME_FORMAT.parse(xmlTrackPoint.getChildText("time", xmlTrackPoint.getNamespace()));
		double elevation = Double.valueOf(xmlTrackPoint.getChildText("ele", xmlTrackPoint.getNamespace()));
		return new TrackPoint(latitude, longitude, timestamp, elevation);
	}

	public String getName() {
		return name;
	}

	public long getDuration() {
		return durationMilliseconds;
	}

	public double getDurationMinutes() {
		return (double) durationMilliseconds / 1000 / 60;
	}

	public double getDistance() {
		return distanceMeter;
	}

	public double getAvarageSpeedKmH() {
		return (distanceMeter / 1000) / (durationMilliseconds / (1000 * 60 * 60));
	}

	public double getMinutePerKm() {
		return (durationMilliseconds / 1000 / 60) / (distanceMeter / 1000);
	}

	public double getMeanHeight() {
		return heightSum / pointsCount;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public double getMinHeight() {
		return minHeight;
	}

}
