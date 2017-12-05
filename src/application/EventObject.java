package application;

public class EventObject extends EventClass{
	private String type;
	private String venueName;
	private String preformanceName;
	private String displayName;
	private Object popularity;
	private Object date;
	
	
	public EventObject(String type, String venueName, String preformanceName, String displayName, Object popularity,
			Object date) {
		super(displayName, displayName, displayName, displayName, date, date);
		this.type = type;
		this.venueName = venueName;
		this.preformanceName = preformanceName;
		this.displayName = displayName;
		this.popularity = popularity;
		this.date = date;
	}
}
