//for capturing login events in google analytics

function captureEvent(event, reason){
	 dataLayer.push({
		 "event": "jiogst", 
		 "eventCategory": "JIO GST", 
		 "eventAction": event,
		 "eventLabel":  reason //"[Failure : '\${loginError}\' ]"
		
	 })
}

