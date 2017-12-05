package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationClass extends FinalProjectSBController{
	private static Object lat;
	private static Object lng;
	private static Object areaID;
	private static String searchText;
	private static String countryName;
	private static String stateName;
	private static String displayName;
	private static Set<LocationClass> locationSet = new LinkedHashSet<LocationClass>();
	private static String APIKey = new String("xVIIgyI7NpxazqrQ");
	
	public static Set<LocationClass> getLocationSet() {
		return locationSet;
	}
	public static void setLocationSet(Set<LocationClass> locationSet) {
		LocationClass.locationSet = locationSet;
	}
	public static Object getLat() {
		return lat;
	}
	public static void setLat(Object lat) {
		LocationClass.lat = lat;
	}
	public static Object getLng() {
		return lng;
	}
	public static void setLng(Object lng) {
		LocationClass.lng = lng;
	}
	public static Object getAreaID() {
		return areaID;
	}
	public static void setAreaID(Object areaID) {
		LocationClass.areaID = areaID;
	}
	public static String getSearchText() {
		return searchText;
	}
	public static void setSearchText(String searchText) {
		LocationClass.searchText = searchText;
	}
	public static String getCountryName() {
		return countryName;
	}
	public static void setCountryName(String countryName) {
		LocationClass.countryName = countryName;
	}
	public static String getStateName() {
		return stateName;
	}
	public static void setStateName(String stateName) {
		LocationClass.stateName = stateName;
	}
	public static String getDisplayName() {
		return displayName;
	}
	public static void setDisplayName(String displayName) {
		LocationClass.displayName = displayName;
	}
	
	public static void apiLocationSearch() {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		
		try {
			URL url = new URL("http://api.songkick.com/api/3.0/search/locations.json?query=" + searchText + "&apikey=" + APIKey);
			connection = (HttpURLConnection)url.openConnection();
			connection.connect();
			
			InputStream stream = connection.getInputStream();
			
			reader = new BufferedReader(new InputStreamReader(stream));
			
			StringBuffer buffer = new StringBuffer();
			
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			
			String fullJson = buffer.toString();
			
			JSONObject parentJSON = new JSONObject(fullJson);
			JSONObject resultsPage = parentJSON.getJSONObject("resultsPage");
			JSONObject results = resultsPage.getJSONObject("results");
			JSONArray location = results.getJSONArray("location");
			
			//Full of the first value in the JSON file -------------------
			JSONObject areaSearch = location.getJSONObject(0);
			JSONObject metroArea = areaSearch.getJSONObject("metroArea");
			JSONObject country = metroArea.getJSONObject("country");
			JSONObject state = metroArea.getJSONObject("state");
			
			LocationClass.setLat(metroArea.get("lat"));
			LocationClass.setLng(metroArea.get("lng"));
			LocationClass.setAreaID(metroArea.get("id"));
			LocationClass.setDisplayName(metroArea.getString("displayName"));
			
			LocationClass.setCountryName(country.getString("displayName"));
			LocationClass.setStateName(state.getString("displayName"));
			//Full of the first value in the JSON file -------------------
			
			//setFinalBufferedData(finalBufferedData);
			connection.disconnect();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			StringBuffer bufferedError = new StringBuffer();
			bufferedError.append(searchText + " is not in our database");
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
