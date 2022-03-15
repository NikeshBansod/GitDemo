package com.reliance.gstn.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.reliance.gstn.model.AspLoginBean;

public class WebserviceCallUtil {
	private static final Logger logger = Logger.getLogger(WebserviceCallUtil.class);

	public static String callWebservice(String webServiceUrl, Map<String, String> headersMap, String inputData,
			Map<String, String> extraParams) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		logger.info("Entry: ");
		try {
			URL obj = new URL(webServiceUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setConnectTimeout(5000);
			con.setRequestMethod(extraParams.get("methodName"));
			setHeaders(con, headersMap);

			if (!extraParams.get("methodName").equalsIgnoreCase("GET")) {
				writeRequestBody(con, inputData);
			}

			int responseCode = con.getResponseCode();
			logger.debug("Response Code : " + responseCode);
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			logger.error("Error in webservice call ", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error in Stream closing ", e);
				}
			}
		}
		String result = sb.toString();
		logger.debug("result= " + result);
		logger.info("Exit: ");
		return result;
	}

	public static void writeRequestBody(HttpURLConnection con, String inputData) throws IOException {
		DataOutputStream wr = null;
		try {
			con.setDoOutput(true);
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(inputData);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			if (null != wr) {
				try {
					wr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw e;
				}
			}
		}
	}

	private static void setHeaders(HttpURLConnection con, Map<String, String> headersMap) {
		Iterator<String> itr = headersMap.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			String value = headersMap.get(key);
			con.setRequestProperty(key, value);
		}

	}

	@SuppressWarnings("unchecked")
	public static boolean validateAspLoginDetails(AspLoginBean aspLoginBean) throws Exception {
		boolean success = false;
		String USER_AGENT = "Mozilla/5.0";
		String json = new Gson().toJson(aspLoginBean);
		URL obj = new URL(aspLoginBean.getServiceURL());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(json);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) gson.fromJson(response.toString(), map.getClass());
		success = (boolean) map.get("success");
		return success;

	}

	public static String callHttpsWebservice(String loginUrl, Map<String, String> headersMap, String jsonInString,
			Map<String, String> extraParams) {
		logger.info("Entry:");
		disableSslVerification();
		StringBuilder responseString = new StringBuilder();
		try {
			HttpURLConnection connection = null;
			URL url = new URL(loginUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(extraParams.get("methodName"));
			connection.setRequestProperty("Content-Length", "0");
			connection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(jsonInString.toString());
			wr.flush();
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(content));

			String line = "";
			while ((line = breader.readLine()) != null) {
				responseString.append(line);
			}
			breader.close();
			logger.info("Result:=" + responseString);
		} catch (Exception e) {
			logger.error("Error in tiny url web service call:", e);
		}

		logger.info("Exit:");

		return responseString.toString();
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@SuppressWarnings("unused")
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				@SuppressWarnings("unused")
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub

				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		System.out.println(getStringDate("03-04-2018"));
		/*
		 * Map<String, String> headersMap = getAuthTokenHeaderMap(); Map<String,
		 * String> extraParams = new HashMap<String, String>();
		 * extraParams.put("methodName", "POST"); Map<String, String> map =
		 * getReqMap(); String requestJson = NICUtil.getRequestString(map);
		 * String jsonResponseString = WebserviceCallUtil.callWebservice(
		 * "http://10.144.113.138:8080/ewaybill/session", headersMap,
		 * requestJson, extraParams); System.out.println(jsonResponseString);
		 */
	}

	public static String getStringDate(Date date1) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(date1);
		return date;
	}

	public static String getStringDate(String date) {
		try {
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			return getStringDate(date1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static Map<String, String> getAuthTokenHeaderMap() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.CONTENT_TYPE, AspApiConstants.APPLICATION_JSON);
		map.put(AspApiConstants.EWB_USER_ID, "05AAACA5824B1ZC");
		map.put(AspApiConstants.EWB_SEC_KEY, "b82778dd-485e-4f6f-9cdd-b28c45b2e027");
		map.put(AspApiConstants.EWB_CLIENT_ID, "EWBSJU71X0PNGKH62X2L");
		map.put(AspApiConstants.GSTIN, "05AAACA5824B1ZC");
		return map;

	}

	public static Map<String, String> getReqMap() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("username", "05AAACA5824B1ZC");
		map.put("action", "auth");
		map.put("password",
				"zdlbdS497oujSvLhk7dho5XHkagNPQpo3YEnVqbvxHzPhLkyai30l/LpzWQuX47RVId0nho2/swYJJ2bB9UgKiDvnU9tfgdCvFnSaVY4y0qRKinduoJ9Ia43INpK8z6IBhKSTX53M9BD2+UEtOSmuoHUXVdvNLBt1AtjJLwm1sm/GR7Z5pOrY9oapKBoTAx9ckW6k8+P6uM20+V0W+R2hhqPG3s0yrgbObOdxIC7M+H5RLTIZUNwpBlqOmKMohmfZW4IwyATagb50T2lZzlNZcseol0uwtlFTCDCnevPNr+Owv1vcAoowtTEah1BJy5bCd+1FUPQhDIgeXYY54kFgQ\u003d\u003d");
		return map;

	}
}
