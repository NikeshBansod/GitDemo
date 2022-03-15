package com.reliance.gstn.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class MyFooter extends PdfPageEventHelper{
	private String footerText;

	Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

    public MyFooter(String footerText) {
        super();
        this.footerText = footerText;
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfContentByte cb = writer.getDirectContent();
        Phrase footer = new Phrase(footerText, ffont);

        /* Footer */
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);

    }
}
