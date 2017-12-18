package application;

import java.util.LinkedList;

public class NameSort extends FinalProjectSBController implements Runnable{

	public LinkedList<EventClass> eventList = new LinkedList<EventClass>();
	
	
	public NameSort(LinkedList<EventClass> eventList) {
		this.eventList = finalEventList;
	}
	
	
	@Override
	public void run() {
		
		for(int i = 1; i < eventList.size(); i++) {
			boolean inserted = false;
			int j = i;
			while((j >= 1) && (inserted == false)) {
				if (eventList.get(j-1).getPreformanceName().toString().compareTo(eventList.get(j).getPreformanceName())>0) {
					EventClass temp = eventList.get(j-1);
                    eventList.set(j-1, eventList.get(j));
                    eventList.set(j, temp);
				}
				else {
					inserted = true;
					
				}
				j--;
			}
		}
		return;
	}
	

}
