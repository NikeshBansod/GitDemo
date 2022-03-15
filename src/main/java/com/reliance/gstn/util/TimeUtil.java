package com.reliance.gstn.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static double getTimeDiffFromCurrentTimeInDouble(long fromTime,String unit){
		double timeDiff=0.0;
		
		if(unit.equalsIgnoreCase("seconds")){
			timeDiff=(System.currentTimeMillis()-fromTime)/1000;
		} else if(unit.equalsIgnoreCase("minutes")) {
			timeDiff=(System.currentTimeMillis()-fromTime)/(1000*60);
		}else if(unit.equalsIgnoreCase("hours")){
			timeDiff=(System.currentTimeMillis()-fromTime)/(1000*60*60);
		}
		
		
		return timeDiff;
	}
	
	public static long getTimeDiffFromCurrentTimeInLong(long fromTime,String unit){
		long timeDiff=0;
		
		if(unit.equalsIgnoreCase("seconds")){
			timeDiff=(System.currentTimeMillis()-fromTime)/1000;
		} else if(unit.equalsIgnoreCase("minutes")) {
			timeDiff=(System.currentTimeMillis()-fromTime)/(1000*60);
		}else if(unit.equalsIgnoreCase("hours")){
			timeDiff=(System.currentTimeMillis()-fromTime)/(1000*60*60);
		}
		
		
		return timeDiff;
	}
	
	public static String getmonthAndYear(Timestamp timeRef) {
		Date dateRef = new Date();
		dateRef.setTime(timeRef.getTime());
		String formattedDate = new SimpleDateFormat("MMyyyy").format(dateRef);
		return formattedDate;
	}
	
}
