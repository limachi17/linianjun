package com.moxian.common.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.moxian.common.exception.MoXianFileException;

@Slf4j
public class LogFile extends AbstractFile {

	public LogFile(InputStream uploadStream, FormDataContentDisposition filedetail, int fileType, int classfycation,FileUploadConfig fileUploadConfig) {
		this.uploadStream = uploadStream;
		this.fileDetail = filedetail;
		this.fileType = fileType;
		this.fileClassification = classfycation;
		this.fileUploadConfig = fileUploadConfig;
	}
	
	@Override
	public String saveFile( String typepath, String function, String fileName,
			String domain)
			throws MoXianFileException ,IOException{
		// TODO Auto-generated method stub
		List<String> textlist= getLogCentent(this.uploadStream);
		List<String> loglist = logFileTrans(textlist);
		writeLogUseLog4j(loglist);
		
		
		return null;
	}
	
	/**
	 * 应该弄个统一的验证器，大家只要把需要验证的字段，和验证规则给过去就行了。
	 * @Title: vlidateStream 
	 * @param: 
	 * @Description:  
	 * @return boolean
	 */
//	private boolean vlidateStream(List<String> list) {
	private List<String> logFileTrans(final List<String> list)throws MoXianFileException {
		// TODO Auto-generated method stub
		int line = 0 ;
		String[] logStr={"时间","网络类型","请求URL","参数","请求消耗时间","http响应码","异常信息"};
		List<String> listLog = new ArrayList<String>();
		StringBuilder sb=null;
		for(String aLog:list){
			line++; //想用来保留行号，帮助查日志的多少行出了问题
			sb=new StringBuilder("魔线日志：");			
			String[] arrStr=aLog.split("\\|");
			if(arrStr.length != logStr.length){
				log.error("arrStr.length= "+arrStr.length +" logStr.length"+logStr.length);// 需要删除
				throw new MoXianFileException("出错行"+line);
			}
			for(int i = 0 ; i < arrStr.length ;i++){	
				if(arrStr[i]==null){
					arrStr[i] = "null";
				}
				sb.append(logStr[i]).append(": ").append(arrStr[i]).append(" ");
			}
			listLog.add(sb.toString());
			
		}
		return listLog;
	}

	
	/*
	 * 迅速取到内容放入list当中
	 */
	private List<String> getLogCentent(InputStream uploadStream) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new  InputStreamReader(uploadStream));
		String line=null ;
		List<String> list=new ArrayList<String>();		
		try {
			while  ((line = br.readLine()) !=  null ) {
				list.add(line);
	        }			
		} catch (IOException e) {
			throw e;
		}finally{
			try {
				if(null != br){
					br.close();
				}				
			} catch (IOException e) {
				log.error("read file streadm close failed");
			}			
		}
		return list;
	}
	
	/**
	 * 写日志
	* @Title: writeLogUseLog4j 
	* @param: 
	* @Description:
	* @return void
	 */
	private void writeLogUseLog4j(List<String> list) {
		// TODO Auto-generated method stub
		log.info("======================log start ===================");
		for(String oneLog:list){
			log.info(oneLog);
		}
		log.info("====================== log the end =================");
	}

}

	
