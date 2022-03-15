package com.reliance.gstn.controller;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reliance.gstn.util.EncodingDecodingUtil;
import com.reliance.gstn.util.GSTNConstants;

@Controller
public class DownloadInvoiceController {
	
	private static final Logger logger = Logger.getLogger(DownloadInvoiceController.class);
	
	@Autowired
	private GenerateInvoiceController generateInvoiceController;
	
	@RequestMapping(value = "/downloadPdf/{encryptedInvId:.+}", method = RequestMethod.GET, produces=MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody FileSystemResource getGenerateInvoicePage(HttpServletResponse response, @PathVariable("encryptedInvId") String encryptedInvId) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
		
		logger.info("Entry");
		String invoiceId=EncodingDecodingUtil.decodeString(encryptedInvId);
		logger.info("Encrypted Invice id "+encryptedInvId+" invoice id "+invoiceId);

		Map<String, String> map = generateInvoiceController.getInvoicePdfPath(invoiceId);
		File file=new File(map.get(GSTNConstants.PDF_PATH));
		//File file = new File("C:/Users/dnyanshwar.borade/Desktop/a.pdf");

		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-Disposition",
				"inline; filename=" + file.getName());
		response.setHeader("Content-Length", String.valueOf(file.length()));
		logger.info("Exit");
		return new FileSystemResource(file);

	}

}
