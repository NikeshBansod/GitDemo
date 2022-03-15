package com.reliance.gstn.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.reliance.gstn.master.excel.helper.ExcelUploadHelper;



public class ExcelUtil {
	final static Logger logger = Logger.getLogger(ExcelUtil.class);
	
	private static Map<String,List<String>> headerMap=new HashMap<String,List<String>>();
	
	public static List<Object[]> readExcel(String masterType, ExcelUploadHelper excelUploadHelper,MultipartFile file) throws IOException{		
		logger.info("Entry");		
		String [] headers=new String[excelUploadHelper.getColCount()];
		List<Object[]> data=new ArrayList<Object[]>();		
		XSSFWorkbook wb = null;
		XSSFSheet sheet = null;		
		int rowCounter = 1;
		int colCounter = 0;		
		try {
		/*	Long size = file.getSize();
			String contentType = file.getContentType();
            String extArray [] = contentType.split("/");
            String ext ="";
            if(extArray.length==2){
           	 ext = extArray[1];
                ext = ext.toLowerCase();
            }
            
            byte [] byteArr=file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            
            String mimeType = URLConnection.guessContentTypeFromStream(inputStream)==null?"": URLConnection.guessContentTypeFromStream(inputStream);
            String mimeArray [] = mimeType.split("/");
            String mime="NA";
            if(mimeArray.length==2){
           	 mime = mimeArray[1];
           	 mime = mime.toLowerCase();
            }
            String filename = file.getOriginalFilename().toLowerCase();
            filename.endsWith(".xlsx") ;*/
            
			// Get the workbook instance for XLSX file
			wb = new XSSFWorkbook(file.getInputStream());

			// Get first sheet from the workbook
			sheet = wb.getSheetAt(0);
			
			if(sheet.getLastRowNum()<ExcelTemplateConstant.MIN_NO_OF_ROW_IN_EXCEL){
				Exception e = new Exception("Excel File is empty.");
				throw e;
			}
			
			if(sheet.getRow(0).getLastCellNum()!=excelUploadHelper.getColCount()){
				Exception e = new Exception("Error in format: Excel format is not proper, No of Columns does not match with template.");
				throw e;
			}

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			// skip header and collect header
			if (rowIterator.hasNext()) {
				Row row=rowIterator.next();
				int colIndex = 0;
				
				while(colIndex<excelUploadHelper.getColCount()){
					Cell cell = row.getCell(colIndex);
					cell.setCellType(CellType.STRING);
					String heading1=cell.getStringCellValue();
					headers[colIndex]=heading1;
					if(null==heading1 || heading1.trim().equals("")){
						Exception e = new Exception("Error in data format: Excel format is not proper, first row has some blank column.");
						throw e;
					}
					colIndex++;
				}				
			}
			
			List<String> expectedHeaders=headerMap.get(masterType);
			for(String header :headers){
				if(!expectedHeaders.contains(header)){
					Exception e = new Exception("Select correct master template.");
					throw e;
				}
				
			}
			
			while (rowIterator.hasNext()) {
				rowCounter++;
				Row row;
				Cell cell;			
				row = rowIterator.next();				
				boolean isRowEmpty=isRowEmpty(row,excelUploadHelper);				
				if (isRowEmpty && rowCounter == 2) {
					Exception e = new Exception("Error in data format: Excel format is not proper, second row is blank.");
					throw e;
				} else if (isRowEmpty) {
					break;
				}				
				int colIndex = 0;
				colCounter=1;
				
				Object [] datarow=new Object[excelUploadHelper.getColCount()];
				
				while(colIndex<excelUploadHelper.getColCount()){
					cell = row.getCell(colIndex);					
					if(null==cell){
						colIndex++;
						colCounter++;
						continue;
					}						
					
					CellType type = cell.getCellTypeEnum();
					Object columnData=null;
					if(type==CellType.STRING){
						columnData=cell.getStringCellValue().trim();
					}else if(type==CellType.NUMERIC){
						columnData=getFormatedValue(cell.getNumericCellValue());
					}else if(type==CellType.BOOLEAN){
						columnData=cell.getBooleanCellValue();
					}
					datarow[colIndex]=columnData;
					colIndex++;
					colCounter++;
				}
				
				if(!excelUploadHelper.getMandatoryColumns().isEmpty()){
					validateMandatoryColumns(datarow,excelUploadHelper,rowCounter,headers);
				}
				
				if(!excelUploadHelper.getValidationRules().isEmpty()){
					validateBusinessRules(datarow, excelUploadHelper, rowCounter, headers);
				}				
				data.add(datarow);				
			}			
		} catch(IOException e){
			IOException ie = new IOException("Error in reading file in row number:"+rowCounter+" column no:"+colCounter+" " +e.getMessage());
			logger.error("error in row number= "+rowCounter+" column no= "+colCounter);
			logger.error("error in reading file= ",e);
			throw ie;
			
		}catch(Exception e){
			IOException ie = new IOException(e.getMessage());
			logger.error("error in row number= "+rowCounter+" column no= "+colCounter);
			logger.error("error in reading file= ",e);
			throw ie;
		}catch(Throwable e){
			IOException ie = new IOException("Error in reading file in: " +e.getMessage());
			logger.error("error in row number= "+rowCounter+" column no= "+colCounter);
			logger.error("error in reading file= ",e);
			throw ie;
		}finally {
			if (null != wb) {
				wb.close();
			}
		}
		logger.info("Exit");
		return data;
	}
	
	private static boolean isRowEmpty(Row row,ExcelUploadHelper excelUploadHelper) {
		boolean isRowEmpty = true;
		int colIndex = 0;
		while (colIndex < excelUploadHelper.getColCount()) {
			Cell cell = row.getCell(colIndex);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
				cell.setCellType(CellType.STRING);
				if(!cell.getStringCellValue().trim().equals("")){
					isRowEmpty = false;
					break;
				}				
			}
			colIndex++;
		}

		return isRowEmpty;
	}
	
	public static Object getFormatedValue(Object columnData){		
		if(String.valueOf(columnData).endsWith(".0")){
			columnData=String.valueOf(columnData).replace(".0", "");
		}		
		return columnData;
	}
	
	private static void validateMandatoryColumns(Object [] row,ExcelUploadHelper excelUploadHelper,int rowCounter,String [] headers) throws Exception{	
		StringBuffer sb = new StringBuffer();
		for(Integer mandatoryColIndex:excelUploadHelper.getMandatoryColumns()){
			Object obj=row[mandatoryColIndex];
			
			if(null==obj||isBlankString(obj)){
				sb.append("Mandatory column "+headers[mandatoryColIndex]+" is blank for row no:"+rowCounter+" and coloumn no:"+(mandatoryColIndex+1));
				//throw new Exception("Mandatory column "+headers[mandatoryColIndex]+" is blank for row no:"+rowCounter+" and coloumn no:"+(mandatoryColIndex+1)+"<br>");
			}
		}		
		if(!sb.toString().isEmpty())
		{
			throw new Exception(sb.toString());
		}
	}
	
	private static boolean isBlankString(Object obj){
		boolean isBlankString=false;
		String val=String.valueOf(obj);
		if(val.trim().equalsIgnoreCase("")){
			isBlankString=true;
		}
		return isBlankString;
	}
		
	private static void validateBusinessRules(Object [] row,ExcelUploadHelper excelUploadHelper,int rowCounter,String [] headers) throws Exception{
		StringBuffer sb = new StringBuffer();
		for(Integer validationColumn:excelUploadHelper.getValidationRules().keySet()){
			String data=String.valueOf(row[validationColumn]);
			for(Pattern pattern:excelUploadHelper.getValidationRules().get(validationColumn).keySet()){
				if(!pattern.matcher(data).matches()){
					//sb.append(excelUploadHelper.getValidationRules().get(validationColumn).get(pattern)+" for column: "+headers[validationColumn]+" of value:"+data+" for "++"");
					sb.append("row no:"+rowCounter+" and column no:"+(validationColumn+1)+" value:"+data+" For column: "+headers[validationColumn]+" "+excelUploadHelper.getValidationRules().get(validationColumn).get(pattern));
					//throw new Exception("row no:"+rowCounter+" and column no:"+(validationColumn+1)+" value:"+data+" For Column "+headers[validationColumn]+excelUploadHelper.getValidationRules().get(validationColumn).get(pattern) );
				}
			}			
		}		
		if(!sb.toString().isEmpty())
		{
			throw new Exception(sb.toString());
		}
	}	
	
	public static void setHeaderMap(String key,String headers){		
		List<String> headersL=Arrays.asList(headers.split(","));
		headerMap.put(key, headersL);		
	}
	
	public static boolean isHeaderMapEmpty(){
		return headerMap.isEmpty();
	}
	
	public static List<String> getHeaders(String key){
		return headerMap.get(key);
	}
	
}
