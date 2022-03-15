package com.reliance.gstn.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogParserController {
	private static String patternString = "(^.+Exception: .+)|(^.+Error: .+)|(^\\s+at .+)|(^\\s+... \\d+ more)|(^\\s*Caused by:.+)";

	@RequestMapping(value = "/getlogdata", method = RequestMethod.GET)
	public void getLog(HttpServletRequest request, HttpServletResponse response)
			throws IOException, Exception {
		int backDay = Integer.valueOf(request.getParameter("backday")==null?0:Integer.valueOf(request.getParameter("backday")));
		int start = Integer.valueOf(request.getParameter("sindex")==null?0:Integer.valueOf(request.getParameter("sindex")));
		int nol = Integer.valueOf(request.getParameter("nol")==null?100: Integer.valueOf(request.getParameter("nol")));

		Date logDate = getLogDate(backDay);
		String logFolderPath = getLogFolderPath();

		File file = new File(logFolderPath);
		File[] files = file.listFiles();
		if (files.length > 0) {
			for (File file1 : files) {
				if ((file1.isFile()) && (isValidFile(file, logDate))) {
					printLog(file1, response, start, nol);			}
			}
		}
	}

	@RequestMapping(value = "/geterrorlog", method = RequestMethod.GET)
	public void getExceptionData(HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		int backDay = Integer.valueOf(request.getParameter("backday"));
		Date logDate = getLogDate(backDay);
		String logFolderPath = getLogFolderPath();

		File file = new File(logFolderPath);
		File[] files = file.listFiles();
		if (files.length > 0) {
			for (File file1 : files) {
				if ((file1.isFile()) && (isValidFile(file, logDate))) {
					scanError(file1, response);
				}
			}
		}
	}

	private boolean isValidFile(File file, Date logDate) {
		boolean isValidFile = false;
		Date modifiedDate = new Date(file.lastModified());
		long diff = modifiedDate.getTime() - logDate.getTime();
		long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if (noOfDays == 0) {
			isValidFile = true;
		}
		return isValidFile;
	}

	private Date getLogDate(int backDay) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -backDay);
		Date logDate = cal.getTime();
		return logDate;
	}

	private void scanError(File file, HttpServletResponse response)
			throws IOException {

		BufferedReader reader = null;
		String line = null;

		reader = new BufferedReader(new FileReader(file));
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("text/plain");
		out.println("Log for ip address : "
				+ Inet4Address.getLocalHost().getHostAddress());
		try {
			while ((line = reader.readLine()) != null) {
				Pattern pattern = Pattern.compile(patternString);
				Matcher m = pattern.matcher(line);
				while (m.find()) {
					out.println(line);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

	}

	private void printLog(File file, HttpServletResponse response, int start,
			int noOfLine) throws IOException {

		BufferedReader reader = null;
		String line = null;

		reader = new BufferedReader(new FileReader(file));
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("text/plain");
		out.println("Log for ip address : "
				+ Inet4Address.getLocalHost().getHostAddress());
		out.println("file : " + file.getName());
		int counter = 0;
		try {
			while ((line = reader.readLine()) != null) {
				counter++;
				if (counter < start)
					continue;
				if(counter>start+noOfLine)
					break;
				out.println(line);
			}
			
			out.println("Last line Count: "+counter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

	}

	private String getLogFolderPath() {
		Enumeration<?> e = Logger.getRootLogger().getAllAppenders();
		File file = null;
		String folderPath = "";
		while (e.hasMoreElements()) {
			Appender app = (Appender) e.nextElement();
			if (app instanceof FileAppender) {
				file = new File(((FileAppender) app).getFile());
				break;

			}
		}

		if ((null != file) && (file.isFile())) {
			String filePath = file.getAbsolutePath();
			int index = filePath.lastIndexOf(File.separator);
			if (index > 0) {
				folderPath = filePath.substring(0, index);
			}
		}

		return folderPath;
	}

}
