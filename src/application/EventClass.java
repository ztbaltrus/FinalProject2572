package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventClass extends FinalProjectSBController{
	private static String type;
	private static String venueName;
	private static String preformanceName;
	private static String displayName;
	private static Object popularity;
	private static Object date;
	private static HashMap<Integer, EventClass> eventMap = new HashMap<Integer, EventClass>(50);
	private static String APIKey = new String("xVIIgyI7NpxazqrQ");
	
	
	public EventClass(String eventType, String eventVenueName, String eventPerformanceName, String eventDisplayName,
			Object eventPopularity, Object eventDate) {
		eventType = type;
		eventVenueName = venueName;
		eventPerformanceName = preformanceName;
		eventDisplayName = displayName;
		eventPopularity = popularity;
		eventDate = date;
	}
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		EventClass.type = type;
	}
	public static String getVenueName() {
		return venueName;
	}
	public static void setVenueName(String venueName) {
		EventClass.venueName = venueName;
	}
	public static String getPreformanceName() {
		return preformanceName;
	}
	public static void setPreformanceName(String preformanceName) {
		EventClass.preformanceName = preformanceName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public static void setDisplayName(String displayName) {
		EventClass.displayName = displayName;
	}
	public static Object getPopularity() {
		return popularity;
	}
	public static void setPopularity(Object popularity) {
		EventClass.popularity = popularity;
	}
	public static Object getDate() {
		return date;
	}
	public static void setDate(Object date) {
		EventClass.date = date;
	}
	public static HashMap<Integer, EventClass> getEventMap() {
		return eventMap;
	}
	public static void setEventMap(HashMap<Integer, EventClass> locationSet) {
		EventClass.eventMap = locationSet;
	}
	public static String getAPIKey() {
		return APIKey;
	}
	public static void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}
	public static void apiEventSearch() {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		
		try {
			URL url = new URL("http://api.songkick.com/api/3.0/metro_areas/" + LocationClass.getAreaID().toString() + "/calendar.json?apikey=" + APIKey);
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
			JSONArray event = results.getJSONArray("event");
			for(int j=0; j <= event.length(); j++) {
				JSONObject eventSearch = event.getJSONObject(j);
				JSONObject venue = eventSearch.getJSONObject("venue");
				JSONArray performance = eventSearch.getJSONArray("performance");
				JSONObject performanceSearch = performance.getJSONObject(0);
				JSONObject start = eventSearch.getJSONObject("start");
				
				String eventType = eventSearch.getString("type");
				EventClass.setType(eventType);
				Object eventPopularity = eventSearch.get("popularity");
				EventClass.setPopularity(eventPopularity);
				String eventDisplayName = eventSearch.getString("displayName");
				EventClass.setDisplayName(eventDisplayName);
				
				String eventVenueName = venue.getString("displayName");
				EventClass.setVenueName(eventVenueName);
				
				String eventPerformanceName = performanceSearch.getString("displayName");
				EventClass.setPreformanceName(eventPerformanceName);
				
				Object eventDate = start.get("date");
				EventClass.setDate(eventDate);
				
				
				EventClass eventObject = new EventClass(eventType, eventVenueName, eventPerformanceName, eventDisplayName, eventPopularity, eventDate);
				
				eventMap.put(j, eventObject);
			}
			connection.disconnect();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			StringBuffer bufferedError = new StringBuffer();
			bufferedError.append(LocationClass.getAreaID().toString() + " is not in our database");
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

