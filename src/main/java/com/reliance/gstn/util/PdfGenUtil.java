package com.reliance.gstn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEventAfterSplit;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;

public class PdfGenUtil {
	private static final Logger logger = Logger.getLogger(PdfGenUtil.class);

	class MyFooter extends PdfPageEventHelper {

		public void onEndPage(PdfWriter writer, Document document) {
			Font ffont = FontFactory.getFont("Times Roman", 5, Font.BOLD, BaseColor.BLACK);
			PdfContentByte cb = writer.getDirectContent();
			Phrase footer = new Phrase(" This e-way bill is generated using API response from the prescribed site ",
					ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		}

	}

	public static Paragraph getCellFont9Bold(String msg) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 9, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont);
		return subTitle;
	}

	public static PdfPCell getCellFont9BoldTable(String msg) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 9, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getCellFont7BoldTable(String msg) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 7, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getCellFont7(String msg) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 7, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getCellFont7(String msg, String msg2) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 7, BaseColor.BLACK);
		Font subtitleFont2 = FontFactory.getFont("Times Roman", 7, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph();
		Chunk chunk = new Chunk(msg, subtitleFont);
		Chunk chunk2 = new Chunk(msg2, subtitleFont2);
		subTitle.add(chunk);
		subTitle.add(chunk2);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static void main(String[] args) throws Exception {
		PdfReader reader = new PdfReader(
				"D:/apache-tomcat-7.0.59/ewaybill/341001020140_05AAACA9690D1ZS_1524799240353.pdf");
		PdfStamper pdfStamper = new PdfStamper(reader,
				new FileOutputStream("D:/apache-tomcat-7.0.59/ewaybill/" + "NewHelloWorld.pdf"));
		Image image = Image.getInstance("D:/cancelled.jpg");
		PdfContentByte content = pdfStamper.getUnderContent(1);
		image.setAbsolutePosition(10f, 450f);
		image.setAlignment(PdfPCell.ALIGN_CENTER);
		content.addImage(image);
		pdfStamper.close();

	}

	public static Phrase addElement(String msg) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 9, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont);
		return new Phrase(subTitle);
	}

	public static PdfPCell getCellFont9(String msg) {
		Font subtitleFont11 = FontFactory.getFont("Times Roman", 9, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont11);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getCellFont9Bold2(String msg) {
		Font subtitleFont11 = FontFactory.getFont("Times Roman", 9, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(msg, subtitleFont11);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public String generatePdfFile(EwayBillWITransaction ewayBillWITransaction, String location)
			throws EwayBillApiException {
		Document document = new Document();
		BorderEvent event1 = new BorderEvent();
		String fileName = ewayBillWITransaction.getEwaybillNo() + ".pdf";
		try {
			PdfGenUtil p = new PdfGenUtil();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(location + fileName));
			document.open();
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			table1.addCell(getCellFont9(getTodayDate()));
			table1.addCell(getCellFont9Bold2("E-Way Bill"));
			table1.addCell(getCell());
			//table1.addCell(getCell());
			document.add(table1);
			document.add(new Paragraph(" "));
			BarcodeQRCode barcodeQRCode = new BarcodeQRCode(ewayBillWITransaction.getEwaybillNo() + "/"
					+ ewayBillWITransaction.getFromGstin() + "/" + ewayBillWITransaction.getEwaybill_date(), 1000, 1000,
					null);
			Image codeQrImage = barcodeQRCode.getImage();
			codeQrImage.scaleAbsolute(100, 100);
			PdfPCell cellqr = new PdfPCell(codeQrImage, true);
			cellqr.setBorder(PdfPCell.NO_BORDER);
			cellqr.setFixedHeight(70);

			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(getCellFont9Bold("1. E-WAY BILL Details"));
			document.add(new Paragraph(" "));
			PdfPTable table3 = new PdfPTable(4);
			table3.setWidthPercentage(100);
			table3.addCell(getCellFont7("eWay Bill No: ", ewayBillWITransaction.getEwaybillNo()));
			table3.addCell(
					getCellFont7("Generated Date: ", ewayBillWITransaction.getEwaybill_date().replace(":00", "")));

			if (ewayBillWITransaction.getSupplyType().equals("O"))
				table3.addCell(getCellFont7("Generated By: ", ewayBillWITransaction.getFromGstin()));
			else
				table3.addCell(getCellFont7("Generated By: ", ewayBillWITransaction.getToGstin()));
			table3.addCell(
					getCellFont7("Valid Upto: ", ewayBillWITransaction.getEwaybill_valid_upto().replace(":00", "")));
			document.add(table3);
			document.add(new Paragraph(" "));
			PdfPTable table4 = new PdfPTable(4);
			table4.setWidthPercentage(100);
			table4.addCell(getCellFont7("Mode: ", ewayBillWITransaction.getTransModeDesc()));
			table4.addCell(getCellFont7("Approx Distance: ", ewayBillWITransaction.getTransDistance() + " km"));
			table4.addCell(getCell());
			table4.addCell(getCell());
			document.add(table4);
			document.add(new Paragraph(" "));
			PdfPTable table5 = new PdfPTable(4);
			table5.setWidthPercentage(100);
			table5.addCell(getCellFont7("Type: ",
					ewayBillWITransaction.getSupplyTypeDesc() + " - " + ewayBillWITransaction.getSubSupplyDesc()));
			PdfPCell subTitle8Cell = getCellFont7("Document Details: ",
					ewayBillWITransaction.getDocumentTypeDesc() + " - " + ewayBillWITransaction.getDocumentNo() + " - "
							+ NICUtil.getSQLDate(ewayBillWITransaction.getTransDocDate()));
			subTitle8Cell.setColspan(3);
			table5.addCell(subTitle8Cell);
			document.add(table5);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(getCellFont9Bold("2. Address Details"));
			document.add(new Paragraph(" "));
			PdfPTable table6 = new PdfPTable(3);
			table6.setWidthPercentage(100);
			table6.addCell(getCellFont7BoldTable("FROM"));
			table6.addCell(getCell());
			table6.addCell(getCellFont7BoldTable("TO"));
			table6.addCell(p.getCell("GSTIN : " + ewayBillWITransaction.getFromGstin() + "\n"
					+ ewayBillWITransaction.getFromTraderName() + "\n" + ewayBillWITransaction.getFromAddress1() + "\n"
					+ ewayBillWITransaction.getFromPlace() + "\n" + ewayBillWITransaction.getFromPincode() + " "));
			table6.addCell(getCell());
			table6.addCell(p.getCell("GSTIN : " + ewayBillWITransaction.getToGstin() + "\n"
					+ ewayBillWITransaction.getToTraderName() + "\n" + ewayBillWITransaction.getToAddress1().trim() + "\n"
					+ ewayBillWITransaction.getToPlace() + "\n" + ewayBillWITransaction.getToPincode()));

			document.add(table6);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(new Paragraph(""));
			document.add(getCellFont9Bold("3. Goods Details"));
			document.add(new Paragraph(" "));
			PdfPTable table = new PdfPTable(5);
			table.setTotalWidth(520);
			table.setLockedWidth(true);
			table.setTableEvent(event1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setSplitLate(false);
			table.addCell(getCellBottomBold("HSN Code"));
			table.addCell(getCellBottomBold("Product Descripition"));
			table.addCell(getCellBottomBold("Quantity"));
			table.addCell(getCellBottomBold("Taxable Amount Rs."));
			table.addCell(getCellBottomBold("Tax Rate"));
			for (EwayBillWIItem ewayBillWIItem : ewayBillWITransaction.getEwayBillWIItem()) {
				table.addCell(getCellBottom(ewayBillWIItem.getHsnCode()));
				table.addCell(getCellBottom("" + ewayBillWIItem.getProductName()));
				table.addCell(getCellBottom("" + ewayBillWIItem.getQuantity()));
				table.addCell(getCellBottom("" + getFormatedValue(ewayBillWIItem.getTaxableAmount())));
				table.addCell(getCellBottom("" + (ewayBillWIItem.getCgstRate() + ewayBillWIItem.getIgstRate()
						+ ewayBillWIItem.getSgstRate())));
			}

			document.add(table);
			document.add(new Paragraph(" "));

			PdfPTable table31 = new PdfPTable(4);
			table31.setWidthPercentage(100);
			table31.addCell(getCellFont7("Total Tax'ble Amount  ",
					getFormatedValue(ewayBillWITransaction.getTotalValue()) + ""));
			table31.addCell(getCellFont7("Total Document Amount ",
					getFormatedValue(ewayBillWITransaction.getTotInvValue()) + ""));
			table31.addCell(getCellFont7("CGST Amount  ", getFormatedValue(ewayBillWITransaction.getCgstValue()) + ""));
			table31.addCell(getCellFont7("SGST Amount  ", getFormatedValue(ewayBillWITransaction.getSgstValue()) + ""));
			table31.addCell(getCellFont7("IGST Amount  ", getFormatedValue(ewayBillWITransaction.getIgstValue()) + ""));
			table31.addCell(getCellFont7("CESS Advol Amount  ",
					getFormatedValue(ewayBillWITransaction.getTotalCessadvolValue()) + ""));
			table31.addCell(getCellFont7("CESS Non Advol Amount ",
					getFormatedValue(ewayBillWITransaction.getTotalCessnonadvolValue()) + ""));
			table31.addCell(getCellFont7("Other Amount(+/-) ", ewayBillWITransaction.getOtherValue() + ""));

			document.add(table31);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(getCellFont9Bold("4. Transportation Details"));
			document.add(new Paragraph(" "));
			PdfPTable table10 = new PdfPTable(4);
			table10.setWidthPercentage(100);

			if (ewayBillWITransaction.getTransMode().equals("1")) {
				table10.addCell(getCellFont7("Transporter ID : ", ewayBillWITransaction.getTransId()));
				table10.addCell(getCellFont7("Transporter Doc. No : ", ewayBillWITransaction.getTransDocNo()));
				table10.addCell(getCellFont7("Transporter Doc Date : ",
						NICUtil.getSQLDate(ewayBillWITransaction.getTransDocDate())));
				table10.addCell(getCellFont7("Transaction Type : ", "Regular"));
				document.add(table10);
				document.add(new Paragraph(" "));
				PdfPTable table11 = new PdfPTable(1);
				table11.setWidthPercentage(100);
				table11.addCell(getCellFont7("Transporter Name: ", ewayBillWITransaction.getTransName()));
				document.add(table11);
			} else {
				String lable1 = null;
				String lable2 = null;
				if (ewayBillWITransaction.getTransMode().equals("2")) {
					lable1 = "RR No ";
					lable2 = "RR Date ";
				} else if (ewayBillWITransaction.getTransMode().equals("3")) {
					lable1 = "Airway Bill No ";
					lable2 = "Airway Bill Date ";
				} else if (ewayBillWITransaction.getTransMode().equals("4")) {
					lable1 = "Bill of lading No ";
					lable2 = "Bill of lading Date ";
				}
				table10.addCell(getCellFont7(lable1 + ": ", ewayBillWITransaction.getTransDocNo()));
				table10.addCell(
						getCellFont7(lable2 + ": ", NICUtil.getSQLDate(ewayBillWITransaction.getTransDocDate())));

				table10.addCell(getCellFont7("Transporter ID: ", ewayBillWITransaction.getTransId()));
				table10.addCell(getCellFont7("Transporter Name: ", ewayBillWITransaction.getTransName()));
				document.add(table10);
			}

			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(new Paragraph(""));
			document.add(getCellFont9Bold("5. Vehicle Details"));
			document.add(new Paragraph(" "));
			PdfPTable table8 = new PdfPTable(6);
			table8.setTotalWidth(520);
			table8.setLockedWidth(true);
			table8.setTableEvent(event1);
			table8.setWidthPercentage(100);
			table8.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table8.setSplitLate(false);
			table8.addCell(getCellBottomBold("Mode"));
			table8.addCell(getCellBottomBold("Vehicle / TransDoc No     & Dt."));
			table8.addCell(getCellBottomBold("From"));
			table8.addCell(getCellBottomBold("Entered Date"));
			table8.addCell(getCellBottomBold("Entered By"));
			table8.addCell(getCellBottomBold("CEWB No.(If any)"));
			table8.addCell(getCellBottom(ewayBillWITransaction.getTransModeDesc()));
			if (ewayBillWITransaction.getTransMode().equals("1"))
				table8.addCell(getCellBottom(ewayBillWITransaction.getVehicleNo()));
			else
				table8.addCell(getCellBottom("-"));
			table8.addCell(getCellBottom(ewayBillWITransaction.getFromPlace() + ""));
			table8.addCell(getCellBottom(ewayBillWITransaction.getEwaybill_date()));
			if (ewayBillWITransaction.getSupplyType().equals("O"))
				table8.addCell(getCellBottom(ewayBillWITransaction.getFromGstin()));
			else
				table8.addCell(getCellBottom(ewayBillWITransaction.getToGstin()));

			if (ewayBillWITransaction.getCancelRemark() != null)
				table8.addCell(getCellBottom(ewayBillWITransaction.getEwaybillNo()));
			else
				table8.addCell(getCellBottom("-"));
			document.add(table8);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(new Paragraph(" "));
			PdfContentByte cb = pdfWriter.getDirectContent();
			Barcode128 code128 = new Barcode128();
			code128.setCode("" + ewayBillWITransaction.getEwaybillNo());
			code128.setCodeType(Barcode128.CODE128);
			Image code128Image = code128.createImageWithBarcode(cb, null, null);
			PdfPCell cell2 = new PdfPCell(code128Image, true);
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setFixedHeight(30);
			PdfPTable table9 = new PdfPTable(4);
			table9.setWidthPercentage(100);
			table9.addCell(cell2);
			table9.addCell(getCell());
			table9.addCell(getCell());
			table9.addCell(cellqr);
			document.add(table9);
			MyFooter event = new MyFooter();
			pdfWriter.setPageEvent(event);
			document.close();
			if (ewayBillWITransaction.getCancelRemark() != null) {
				String imgloc = p.loadResource("/cancelled.jpg");
				PdfReader reader = new PdfReader(location + fileName);
				String fname = ewayBillWITransaction.getEwaybillNo() + "_" + System.currentTimeMillis() + ".pdf";
				PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(location + fname));
				Image image = Image.getInstance(imgloc);
				PdfContentByte content = pdfStamper.getUnderContent(1);
				image.setAbsolutePosition(10f, 450f);
				image.setAlignment(PdfPCell.ALIGN_CENTER);
				content.addImage(image);
				pdfStamper.close();
				File dir = new File(location + fileName);
				dir.delete();
				return fname;
			} else {
				return fileName;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("generatePdfFile ::: error " + e);
			NICUtil.getExceptionMsg("PDF File Generation::: Error");
		}
		return fileName;

	}

	public String generatePdfFile(String location, InvoiceDetails invoiceDetails, EWayBill eWayBill,
			GSTINDetails gstinDetails) {
		Document document = new Document();
		BorderEvent event1 = new BorderEvent();
		String fileName = eWayBill.getEwaybillNo() + ".pdf";
		try {
			PdfGenUtil p = new PdfGenUtil();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(location + fileName));
			document.open();
			PdfPTable table1 = new PdfPTable(4);
			table1.setWidthPercentage(100);
			table1.addCell(getCellFont9(getTodayDate()));
			table1.addCell(getCellFont9Bold2("E-Way Bill"));
			table1.addCell(getCell());
			table1.addCell(getCell());
			document.add(table1);
			document.add(new Paragraph(" "));
			BarcodeQRCode barcodeQRCode = new BarcodeQRCode(
					eWayBill.getEwaybillNo() + "/" + eWayBill.getGstin() + "/" + eWayBill.getEwaybill_date(), 1000,
					1000, null);
			Image codeQrImage = barcodeQRCode.getImage();
			codeQrImage.scaleAbsolute(100, 100);
			PdfPCell cellqr = new PdfPCell(codeQrImage, true);
			cellqr.setBorder(PdfPCell.NO_BORDER);
			cellqr.setFixedHeight(70);

			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(getCellFont9Bold("1. E-WAY BILL Details"));
			document.add(new Paragraph(" "));
			PdfPTable table3 = new PdfPTable(4);
			table3.setWidthPercentage(100);
			table3.addCell(getCellFont7("eWay Bill No: ", eWayBill.getEwaybillNo()));
			table3.addCell(getCellFont7("Generated Date: ", eWayBill.getEwaybill_date().replace(":00", "")));
			table3.addCell(getCellFont7("Generated By: ", eWayBill.getGstin()));
			table3.addCell(getCellFont7("Valid Upto: ", eWayBill.getEwaybill_valid_upto().replace(":00", "")));
			document.add(table3);
			document.add(new Paragraph(" "));
			PdfPTable table4 = new PdfPTable(4);
			table4.setWidthPercentage(100);
			table4.addCell(getCellFont7("Mode: ", eWayBill.getModeOfTransportDesc()));
			table4.addCell(getCellFont7("Approx Distance: ", eWayBill.getKmsToBeTravelled() + "km"));
			table4.addCell(getCell());
			table4.addCell(getCell());
			document.add(table4);
			document.add(new Paragraph(" "));
			PdfPTable table5 = new PdfPTable(4);
			table5.setWidthPercentage(100);
			String docType = null;
			if (invoiceDetails.getDocumentType().equals("billOfSupply")) {
				docType = "BillOfSupply";
			} else if (invoiceDetails.getDocumentType().equals("invoice")) {
				docType = "Tax Invoice";
			} else {
				docType = "Others";
			}
			table5.addCell(getCellFont7("Type: ", "Outward - Supply"));
			PdfPCell subTitle8Cell = getCellFont7("Document Details: ",
					docType + " - " + eWayBill.getDocNo() + " - " + getDocDate(eWayBill.getDocDate()));
			subTitle8Cell.setColspan(3);
			table5.addCell(subTitle8Cell);
			document.add(table5);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(getCellFont9Bold("2. Address Details"));
			document.add(new Paragraph(" "));
			PdfPTable table6 = new PdfPTable(3);
			table6.setWidthPercentage(100);
			table6.addCell(getCellFont7BoldTable("FROM"));
			table6.addCell(getCell());
			table6.addCell(getCellFont7BoldTable("TO"));
			table6.addCell(p.getCell(
					"GSTIN :" + gstinDetails.getGstinNo() + "\n" + gstinDetails.getGstinAddressMapping().getAddress()
							+ "\n" + gstinDetails.getGstinAddressMapping().getCity() + "\n"
							+ gstinDetails.getGstinAddressMapping().getPinCode() + "\n"
							+ gstinDetails.getGstinAddressMapping().getState()));
			table6.addCell(getCell());

			if (invoiceDetails.getBillToShipIsChecked().equals("Yes")) {

				table6.addCell(p.getCell("GSTIN :" + invoiceDetails.getCustomerDetails().getCustGstId() + "\n"
						+ invoiceDetails.getCustomerDetails().getCustAddress1() + "\n"
						+ invoiceDetails.getCustomerDetails().getCustCity() + "\n"
						+ invoiceDetails.getCustomerDetails().getPinCode() + "\n"
						+ invoiceDetails.getCustomerDetails().getCustState()));
			} else {

				table6.addCell(p.getCell("GSTIN :" + invoiceDetails.getCustomerDetails().getCustGstId() + "\n"
						+ invoiceDetails.getShipToAddress() + "\n" + invoiceDetails.getShipToCity() + "\n"
						+ invoiceDetails.getShipToState()));

			}

			document.add(table6);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(new Paragraph(""));
			document.add(getCellFont9Bold("3. Goods Details"));
			document.add(new Paragraph(" "));
			PdfPTable table = new PdfPTable(5);
			table.setTotalWidth(520);
			table.setLockedWidth(true);
			table.setTableEvent(event1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setSplitLate(false);
			table.addCell(getCellBottomBold("HSN Code"));
			table.addCell(getCellBottomBold("Product Descripition"));
			table.addCell(getCellBottomBold("Quantity"));
			table.addCell(getCellBottomBold("Taxable Amount Rs."));
			table.addCell(getCellBottomBold("Tax Rate"));
			double cgstAmount = 0;
			double sgstAmount = 0;
			double igstAmount = 0;
			double cess = 0;
			double totalTaxAmt = 0;
			for (InvoiceServiceDetails invoiceServiceDetails : invoiceDetails.getServiceList()) {
				cgstAmount = cgstAmount + invoiceServiceDetails.getCgstAmount();
				sgstAmount = sgstAmount + invoiceServiceDetails.getSgstAmount();
				igstAmount = igstAmount + invoiceServiceDetails.getIgstAmount();
				cess = cess + invoiceServiceDetails.getCess();
				double taxRate = invoiceServiceDetails.getCgstPercentage() + invoiceServiceDetails.getIgstPercentage()
						+ invoiceServiceDetails.getSgstPercentage();

				double total = invoiceServiceDetails.getCgstAmount() + invoiceServiceDetails.getSgstAmount()
						+ invoiceServiceDetails.getIgstAmount() + invoiceServiceDetails.getCess();
				totalTaxAmt = totalTaxAmt + (invoiceServiceDetails.getPreviousAmount()
						- invoiceServiceDetails.getOfferAmount() + invoiceServiceDetails.getAdditionalAmount());
				table.addCell(getCellBottom(invoiceServiceDetails.getHsnSacCode()));
				table.addCell(getCellBottom(invoiceServiceDetails.getServiceIdInString()));
				table.addCell(getCellBottom("" + invoiceServiceDetails.getQuantity()));
				table.addCell(getCellBottom("" + (invoiceServiceDetails.getPreviousAmount()
						- invoiceServiceDetails.getOfferAmount() + invoiceServiceDetails.getAdditionalAmount())));
				table.addCell(getCellBottom("" + taxRate));
			}

			document.add(table);
			document.add(new Paragraph(" "));

			PdfPTable table31 = new PdfPTable(4);
			table31.setWidthPercentage(100);
			table31.addCell(getCellFont7("Total Tax'ble Amount  ", totalTaxAmt + ""));
			table31.addCell(getCellFont7("Total Document Amount ", invoiceDetails.getInvoiceValueAfterRoundOff() + ""));
			table31.addCell(getCellFont7("CGST Amount  ", cgstAmount + ""));
			table31.addCell(getCellFont7("SGST Amount  ", sgstAmount + ""));
			table31.addCell(getCellFont7("IGST Amount  ", igstAmount + ""));
			table31.addCell(getCellFont7("CESS Amount  ", cess + ""));
			table31.addCell(getCell());
			table31.addCell(getCell());
			document.add(table31);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(getCellFont9Bold("4. Transportation Details"));
			document.add(new Paragraph(" "));
			PdfPTable table10 = new PdfPTable(4);
			table10.setWidthPercentage(100);
			if (eWayBill.getModeOfTransportDesc().equals("Road")) {
				table10.addCell(getCellFont7("Transporter ID: ", eWayBill.getTransporterId()));
				table10.addCell(getCellFont7("Transporter Doc. No: ", eWayBill.getDocNo()));
				table10.addCell(getCellFont7("Transporter Doc Date: ", getDocDate(eWayBill.getDocDate())));
				table10.addCell(getCell());
				document.add(table10);
				document.add(new Paragraph(" "));
				PdfPTable table11 = new PdfPTable(1);
				table11.setWidthPercentage(100);
				table11.addCell(getCellFont7("Transporter Name: ", eWayBill.getTransporterName()));
				document.add(table11);
			} else {
				String lable1 = null;
				String lable2 = null;
				if (eWayBill.getModeOfTransportDesc().equals("Rail")) {
					lable1 = "RR No";
					lable2 = "RR Date";
				} else if (eWayBill.getModeOfTransportDesc().equals("Air")) {
					lable1 = "Airway Bill No";
					lable2 = "Airway Bill Date";
				} else if (eWayBill.getModeOfTransportDesc().equals("Ship")) {
					lable1 = "Bill of lading No";
					lable2 = "Bill of lading Date";
				}
				table10.addCell(getCellFont7(lable1 + ": ", eWayBill.getDocNo()));
				table10.addCell(getCellFont7(lable2 + ": ", getDocDate(eWayBill.getDocDate())));
				table10.addCell(getCellFont7("Transporter ID: "));
				table10.addCell(getCellFont7("Transporter Name: "));
				document.add(table10);
			}

			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(new Paragraph(""));
			document.add(getCellFont9Bold("5. Vehicle Details"));
			document.add(new Paragraph(" "));
			PdfPTable table8 = new PdfPTable(6);
			table8.setTotalWidth(520);
			table8.setLockedWidth(true);
			table8.setTableEvent(event1);
			table8.setWidthPercentage(100);
			table8.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table8.setSplitLate(false);
			table8.addCell(getCellBottomBold("Mode"));
			table8.addCell(getCellBottomBold("Vehicle / TransDoc No & Dt."));
			table8.addCell(getCellBottomBold("From"));
			table8.addCell(getCellBottomBold("Entered Date"));
			table8.addCell(getCellBottomBold("Entered By"));
			table8.addCell(getCellBottomBold("CEWB No.(If any)"));
			table8.addCell(getCellBottom(eWayBill.getModeOfTransportDesc()));
			table8.addCell(getCellBottom(eWayBill.getVehicleNo()));
			table8.addCell(getCellBottom(gstinDetails.getGstinAddressMapping().getState() + ""));
			table8.addCell(getCellBottom(eWayBill.getEwaybill_date()));
			table8.addCell(getCellBottom(eWayBill.getGstin()));
			if (eWayBill.getCancelRmrk() != null)
				table8.addCell(getCellBottom(eWayBill.getEwaybillNo()));
			else
				table8.addCell(getCellBottom("-"));
			document.add(table8);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator());
			document.add(new Paragraph(" "));
			PdfContentByte cb = pdfWriter.getDirectContent();
			Barcode128 code128 = new Barcode128();
			code128.setCode("" + eWayBill.getEwaybillNo());
			code128.setCodeType(Barcode128.CODE128);
			Image code128Image = code128.createImageWithBarcode(cb, null, null);
			PdfPCell cell2 = new PdfPCell(code128Image, true);
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setFixedHeight(30);
			PdfPTable table9 = new PdfPTable(4);
			table9.setWidthPercentage(100);
			table9.addCell(cell2);
			table9.addCell(getCell());
			table9.addCell(getCell());
			table9.addCell(cellqr);
			document.add(table9);
			MyFooter event = new MyFooter();
			pdfWriter.setPageEvent(event);
			document.close();
			if (eWayBill.getCancelRmrk() != null) {
				String imgloc = p.loadResource("/cancelled.jpg");
				PdfReader reader = new PdfReader(location + fileName);
				String fname = eWayBill.getEwaybillNo() + "_" + System.currentTimeMillis() + ".pdf";
				PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(location + fname));
				Image image = Image.getInstance(imgloc);
				PdfContentByte content = pdfStamper.getUnderContent(1);
				image.setAbsolutePosition(10f, 450f);
				image.setAlignment(PdfPCell.ALIGN_CENTER);
				content.addImage(image);
				pdfStamper.close();
				File dir = new File(location + fileName);
				dir.delete();
				return fname;
			} else {
				return fileName;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;

	}

	class SpecialRoundedCell implements PdfPCellEvent {
		public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
			PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
			float llx = position.getLeft() + 2;
			float lly = position.getBottom() + 2;
			float urx = position.getRight() - 2;
			float ury = position.getTop() - 2;
			float r = 4;
			float b = 0.4477f;
			canvas.moveTo(llx, lly);
			canvas.lineTo(urx, lly);
			canvas.lineTo(urx, ury - r);
			canvas.curveTo(urx, ury - r * b, urx - r * b, ury, urx - r, ury);
			canvas.lineTo(llx + r, ury);
			canvas.curveTo(llx + r * b, ury, llx, ury - r * b, llx, ury - r);
			canvas.lineTo(llx, lly);
			canvas.stroke();
		}
	}

	public PdfPCell getCell(String content) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 7, BaseColor.BLACK);
		Paragraph subTitle9 = new Paragraph(content, subtitleFont);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle9));
		cell.setCellEvent(new SpecialRoundedCell());
		cell.setPadding(10);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
		Image img = Image.getInstance(path);
		img.setWidthPercentage(20);
		PdfPCell cell = new PdfPCell(img, true);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setFixedHeight(70);
		return cell;
	}

	public static PdfPCell getCellBottom(String data) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 7, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(data, subtitleFont);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorder(PdfPCell.BOTTOM);
		return cell;
	}

	public static PdfPCell getCellBottomBold(String data) {
		Font subtitleFont = FontFactory.getFont("Times Roman", 7, Font.BOLD, BaseColor.BLACK);
		Paragraph subTitle = new Paragraph(data, subtitleFont);
		PdfPCell cell = new PdfPCell(new Phrase(subTitle));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorder(PdfPCell.BOTTOM);
		return cell;
	}

	public static PdfPCell getCell() {
		PdfPCell cell = new PdfPCell(new Phrase(""));
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static String getTodayDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(new Date());
			return date;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static String getDocDate(java.sql.Timestamp time) {
		try {
			Date date = new Date();
			date.setTime(time.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date2 = sdf.format(date);
			return date2;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public Object getFormatedValue(Double d) {
		return BigDecimal.valueOf(d).toPlainString();
	}

	public String loadResource(String resource) throws IOException {
		URL u = this.getClass().getResource(resource);
		String path = null;
		if (u != null) {
			path = u.getPath();
			path = path.replaceFirst("^/(.:/)", "$1");
		}
		return path;

	}

}

class BorderEvent implements PdfPTableEventAfterSplit {

	protected boolean bottom = true;
	protected boolean top = true;

	public void splitTable(PdfPTable table) {
		bottom = false;
	}

	public void afterSplitTable(PdfPTable table, PdfPRow startRow, int startIdx) {
		top = false;
	}

	public void tableLayout(PdfPTable table, float[][] width, float[] height, int headerRows, int rowStart,
			PdfContentByte[] canvas) {
		float widths[] = width[0];
		float y1 = height[0];
		float y2 = height[height.length - 1];
		float x1 = widths[0];
		float x2 = widths[widths.length - 1];
		PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
		cb.moveTo(x1, y1);
		cb.lineTo(x1, y2);
		cb.moveTo(x2, y1);
		cb.lineTo(x2, y2);
		if (top) {
			cb.moveTo(x1, y1);
			cb.lineTo(x2, y1);
		}
		if (bottom) {
			cb.moveTo(x1, y2);
			cb.lineTo(x2, y2);
		}
		cb.stroke();
		cb.resetRGBColorStroke();
		bottom = true;
		top = true;
	}

}
