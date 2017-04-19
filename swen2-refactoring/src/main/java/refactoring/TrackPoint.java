package refactoring;

import java.util.Date;
import java.util.Objects;

public class TrackPoint {

	private double latitude;
	private double longitude;
	private Date timestamp;
	private double elevation;

	public TrackPoint(double latitude, double longitude, Date timestamp, double elevation) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.elevation = elevation;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public double getElevation() {
		return elevation;
	}
	
	public double calculateDistance(TrackPoint trackPointB) {
		double distanceDelta = Math.acos(Math.sin(latitude) * Math.sin(trackPointB.getLatitude())
				+ Math.cos(latitude) * Math.cos(trackPointB.getLatitude())
						* Math.cos(trackPointB.getLongitude() - longitude))
				* 6378137;
		return distanceDelta;
	}

	public boolean equals(Object compareTo) {
		if (this == compareTo)
			return true;
		if (compareTo == null)
			return false;
		if (getClass() != compareTo.getClass())
			return false;
		TrackPoint trackPoint = (TrackPoint) compareTo;
		return Objects.equals(latitude, trackPoint.getLatitude())
				&& Objects.equals(longitude, trackPoint.getLongitude())
				&& Objects.equals(timestamp, trackPoint.getTimestamp())
				&& Objects.equals(elevation, trackPoint.getElevation());
	}

}
