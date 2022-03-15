package com.reliance.gstn.pushnotification.service;
/**
 * @author aman1.bansal
 *
 */
import java.util.Map;

import com.reliance.gstn.model.PushNotificationProfile;
public interface PushNotificationDetailsService {

	String pushAddorUpdateDetialsMicroService(String contactNo, String getiMeiNo);

	PushNotificationProfile fetchUserProfile(String imeiNo, String dataSend);

}
