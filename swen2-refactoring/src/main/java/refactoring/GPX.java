package refactoring;

import java.awt.FileDialog;
import java.awt.Frame;
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
	
	private File file;
	private String name;
	private double duration;
	private double distance;
	private double avarageSpeed;
	private double minutePerKm;
	private double meanHeight;
	private double maxHeight;
	private double minHeight;
	
	public void selectFile() {
		FileDialog fileDialog = new FileDialog((Frame)null, "GPX-Daeien auswählen", FileDialog.LOAD);
		fileDialog.setMultipleMode(false);
		fileDialog.setFile("*.gpx");
		fileDialog.setVisible(true);
		file = fileDialog.getFiles()[0];
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public void readFile() throws JDOMException, IOException, ParseException {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		SAXBuilder sax = new SAXBuilder();
				
		Document doc = sax.build(file);
		Element root = doc.getRootElement();
		Namespace namespace = root.getNamespace();
		Element trackElement = root.getChild("trk", namespace);
		name = trackElement.getChildText("name", namespace);
		Date time0 = null;
		double lat0 = 0;
		double lon0 = 0;
		distance = 0;
		long durSumSec = 0;
		maxHeight = 0;
		minHeight = 0;
		meanHeight = 0;
		double heightSum = 0;
		int pntCnt = 0;

		for(Object trkseg : root.getChild("trk", namespace).getChildren("trkseg", namespace)){
			time0 = null;
			for(Object trkptObj : ((Element)trkseg).getChildren("trkpt", namespace)){
				pntCnt++;
				Element trkpt = (Element)trkptObj;
				Date time = dateTimeFormat.parse(trkpt.getChildText("time", namespace));
				double lat = Double.valueOf(trkpt.getAttributeValue("lat"));
				double lon = Double.valueOf(trkpt.getAttributeValue("lon"));
				double latRad = lat / 180 * Math.PI;
				double lonRad = lon / 180 * Math.PI;
				double ele = Double.valueOf(trkpt.getChildText("ele", namespace));
				if(time0 == null){
					time0 = time;
					lat0 = latRad;
					lon0 = lonRad;
					continue;
				}
				if(time0.equals(time) || (lat0 == latRad && lon0 == lonRad))
					continue;						
				
				double distDelta = Math.acos(Math.sin(lat0) * Math.sin(latRad) + Math.cos(lat0) * Math.cos(latRad) * Math.cos(lonRad - lon0)) * 6378137;
				distance += distDelta;
				long durDelta = (time.getTime() - time0.getTime()) / 1000;
				durSumSec += durDelta;
				
				heightSum += ele;
				if(ele > maxHeight)
					maxHeight = ele;
				if(ele < minHeight || minHeight == 0)
					minHeight = ele;

				time0 = time;
				lat0 = latRad;
				lon0 = lonRad;
			}
		}
		duration = durSumSec / 60; // Dauer in Minuten
		avarageSpeed = (distance / 1000) / (duration / 60); // Geschwindigkeit in km/h
		minutePerKm = duration / (distance / 1000); // min/km
		meanHeight = heightSum / pntCnt;
	}

	public String getName() {
		return name;
	}

	public double getDuration() {
		return duration;
	}

	public double getDistance() {
		return distance;
	}

	public double getAvarageSpeed() {
		return avarageSpeed;
	}

	public double getMinutePerKm() {
		return minutePerKm;
	}

	public double getMeanHeight() {
		return meanHeight;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public double getMinHeight() {
		return minHeight;
	}

	public static void main(String[] args) throws JDOMException, IOException, ParseException {
		GPX gpx = new GPX();
		gpx.selectFile();
		gpx.readFile();
		System.out.println("Track-Name: " + gpx.getName());
		System.out.println("Distanz: " + gpx.getDistance());
		System.out.println("Dauer: " + gpx.getDuration());
		System.out.println("Durchschnittsgeschwindigkeit: " + gpx.getAvarageSpeed());
		System.out.println("Tempo: " + gpx.getMinutePerKm());
		System.out.println("Durschnittshöhe: " + gpx.getMeanHeight());
		System.out.println("Maximale Höhe: " + gpx.getMaxHeight());
		System.out.println("Minimale Höhe: " + gpx.getMinHeight());
	}
}
