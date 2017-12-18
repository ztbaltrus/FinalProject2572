package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventClass extends FinalProjectSBController{
	private String type;
	private String venueName;
	private String preformanceName;
	private String displayName;
	private Object popularity;
	private Object date;
	private static LinkedList<EventClass> eventArray = new LinkedList<EventClass>();
	private static String APIKey = new String("xVIIgyI7NpxazqrQ");
	
	public EventClass() {
		
	}
	
	public EventClass(String eventType, String eventVenueName, String eventPerformanceName, String eventDisplayName,
			Object eventPopularity, Object eventDate) {
		this.type = eventType;
		this.venueName = eventVenueName;
		this.preformanceName = eventPerformanceName;
		this.displayName = eventDisplayName;
		this.popularity = eventPopularity;
		this.date = eventDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public  String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getPreformanceName() {
		return preformanceName;
	}
	public void setPreformanceName(String preformanceName) {
		this.preformanceName = preformanceName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Object getPopularity() {
		return popularity;
	}
	public void setPopularity(Object popularity) {
		this.popularity = popularity;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	public LinkedList<EventClass> getEventArray() {
		return eventArray;
	}
	public void setEventArray(LinkedList<EventClass> locationSet) {
		EventClass.eventArray = locationSet;
	}
	public String getAPIKey() {
		return APIKey;
	}
	public static void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}
	public void apiEventSearch() {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		eventArray.clear();
		
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
			for(int j=0; j < event.length(); j++) {
				
				EventClass eventObject = new EventClass();
				
				JSONObject eventSearch = event.getJSONObject(j);
				JSONObject venue = eventSearch.getJSONObject("venue");
				JSONArray performance = eventSearch.getJSONArray("performance");
				JSONObject performanceSearch = performance.getJSONObject(0);
				JSONObject start = eventSearch.getJSONObject("start");
				
				String eventType = eventSearch.getString("type");
				eventObject.setType(eventType);
				Object eventPopularity = eventSearch.get("popularity");
				eventObject.setPopularity(eventPopularity);
				String eventDisplayName = eventSearch.getString("displayName");
				eventObject.setDisplayName(eventDisplayName);
				
				String eventVenueName = venue.getString("displayName");
				eventObject.setVenueName(eventVenueName);
				
				String eventPerformanceName = performanceSearch.getString("displayName");
				eventObject.setPreformanceName(eventPerformanceName);
				
				Object eventDate = start.get("date");
				eventObject.setDate(eventDate);
				
				eventArray.add(eventObject);
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

