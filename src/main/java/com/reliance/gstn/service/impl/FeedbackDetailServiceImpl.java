package com.reliance.gstn.service.impl;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.FeedbackDAO;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.MasterDescDetails;
import com.reliance.gstn.service.FeedbackDetailService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;

@Service
public class FeedbackDetailServiceImpl implements FeedbackDetailService {

	private static final Logger logger = Logger.getLogger(FeedbackDetailServiceImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private FeedbackDAO feedbackDAO;
	
	@Override
	@Transactional
	public String addFeedback(FeedbackDetails feedbackDetails){
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		String response = GSTNConstants.FAILURE;
		try {
		//	Session session = sessionFactory.openSession();
			session.save(feedbackDetails);
			response = GSTNConstants.SUCCESS;
		} catch(ConstraintViolationException  e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
		}


	@Override
	@Transactional
	public List<FeedbackDetails> listFeedbackDetails(Integer uId) {
		// TODO Auto-generated method stub
		return feedbackDAO.listFeedbackDetails(uId);
	}


	@Override
	@Transactional
	public List<com.reliance.gstn.model.FeedbackDetails> FeedbackDetails(
			Integer id, Integer uId) {
		// TODO Auto-generated method stub
		return feedbackDAO.FeedbackDetails(id,uId);
	}


	@Override
	@Transactional
	public List<MasterDescDetails> getMasterDesc(Integer masterDescDetails) {
		// TODO Auto-generated method stub
		return feedbackDAO.getMasterDesc(masterDescDetails);
	}


	@Override
	@Transactional
	public List<MasterDescDetails> getAllMasterDesc() {
		// TODO Auto-generated method stub
		return feedbackDAO.getAllMasterDesc();
	}


	@Override
	public String getImageFile(String imagePath) throws Exception {
		logger.info("Entry");
		String b64=null;
		try {
		// TODO Auto-generated method stub
		BufferedImage bImage1 = ImageIO.read(new File(imagePath));//give the path of an image for 1st image.
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        String extension1=GSTNUtil.getFileExtension(new File(imagePath));
        ImageIO.write( bImage1, extension1, baos1 );
        baos1.flush();
        byte[] imageInByteArray1 = baos1.toByteArray();
        baos1.close();                                   
        b64 = DatatypeConverter.printBase64Binary(imageInByteArray1);
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return b64;
		
        
        
	}
		
		
}
