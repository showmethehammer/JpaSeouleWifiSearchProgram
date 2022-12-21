package sqliteDB;

public class Pythagoras {
	public double cal (String x, String y, String lat, String lnt) {
		double X_1 = Double.parseDouble(x);
		double Y_1 = Double.parseDouble(y);
		double lat_1 = Double.parseDouble(lat);
		double lnt_1 = Double.parseDouble(lnt);
		
		
		double theta = Y_1 - lnt_1;
		if(theta == 0) {
			if(X_1 == lat_1) {
				System.out.println("들어갔음.");
				return 0;
			}
		}
		double dist = Math.sin(deg2rad(X_1)) * Math.sin(deg2rad(lat_1)) + Math.cos(deg2rad(X_1)) * Math.cos(deg2rad(lat_1)) * Math.cos(deg2rad(theta));
		
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609304;
		dist = Math.round(dist * 10000) / 10000.0;;
		return dist;
	}
	
	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
	return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
	return (rad * 180 / Math.PI);
	}
	
}


