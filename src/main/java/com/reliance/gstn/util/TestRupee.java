package com.reliance.gstn.util;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class TestRupee {

	public static void main(String[] args) throws IOException, DocumentException {
		// TODO Auto-generated method stub
		createPdf( DEST);
	}
	
	public static final String DEST = "C:/apps/gstn/invoice/2019/May/225/123/rupee.pdf";
	public static final String FONT1 = "/fonts/PlayfairDisplay-Regular.ttf";
	public static final String FONT2 = "/fonts/PT_Sans-Web-Regular.ttf";
	public static final String FONT3 = "/fonts/FreeSans.ttf";
	public static final String RUPEE =
	    "The Rupee character \u20B9 and the Rupee symbol \u20A8";

	public static  void createPdf(String dest) throws IOException, DocumentException {
	    Document document = new Document();
	    PdfWriter.getInstance(document, new FileOutputStream(DEST));
	    document.open();
	    Font f1 =
	        FontFactory.getFont(FONT1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
	    Font f2 =
	        FontFactory.getFont(FONT2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
	    Font f3 =
	        FontFactory.getFont(FONT3, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
	    Font f4 =
	        FontFactory.getFont(FONT3, BaseFont.WINANSI, BaseFont.EMBEDDED, 12);
	    document.add(new Paragraph(RUPEE, f1));
	    document.add(new Paragraph(RUPEE, f2));
	    document.add(new Paragraph(RUPEE, f3));
	    document.add(new Paragraph(RUPEE, f4));
	    document.close();
	}

}
