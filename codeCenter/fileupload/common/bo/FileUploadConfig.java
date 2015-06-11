package com.moxian.common.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.moxian.common.BeanConfig.MxUploadFileConfig;
import com.moxian.common.exception.MoXianFileException;

@Slf4j
@Configuration
public class FileUploadConfig {
	
//	 @Value("${spring.fileupload.moxian.filetype}")
//	 public String filetype1;
//	 @Value("${spring.fileupload.moxian.basepath}")
//	 public String basepath1;
//	 @Value("${spring.fileupload.moxian.fileClassification}")
//	 public String fileClassification1;
    
	 @Inject
	 private  MxUploadFileConfig mxUploadFileConfig;
    
    /**
     * 返回要保存文件的路径
     * @Title: getSavePath 
     * @param: 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @return String
     */   
	public String getSavePath(int filetype,int fileClassification) throws MoXianFileException{
		log.debug("取路径的参数filetype="+filetype +": fileClassification"+fileClassification);
		
		String basePaths=mxUploadFileConfig.getBasepath();
		String fileTypes=mxUploadFileConfig.getFiletype();
		String fileClassifications=mxUploadFileConfig.getFileClassification();
		
		Map<String ,String> basePathMap=getMapformUploadConfig(basePaths);
		List<String[]> filetypeList=getListformUploadConfig(fileTypes);	
		String[] ftkeys = filetypeList.get(0);
		String[] ftvalues = filetypeList.get(1);
		List<String[]> fileClassList=getListformUploadConfig(fileClassifications);
		String[] fckeys = fileClassList.get(0);
		String[] fcvalues = fileClassList.get(1);
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < ftvalues.length ;i++){
			if(Integer.parseInt(ftvalues[i])==filetype){
				sb.append( basePathMap.get(ftkeys[i]));
				break;
			}				
		}
		
		if(sb.length() == 0){
			log.warn("不存在文件类型");
			throw new MoXianFileException("不存在文件类型");
		}
		
		String s = null;
		for(int i = 0 ; i <fcvalues.length ;i++) {
			if(Integer.parseInt(fcvalues[i])==fileClassification){
				s = fckeys[i];
				break;
			}			
		}
		
		if(s.isEmpty()){
			sb.append(File.separatorChar).append("other");
		}else{
			sb.append(File.separatorChar).append(s);
		}		
		return sb.toString();		
	}
	
	/**
	 * 
	 * @Title: getFileTypePath 
	 * @param: 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @return String
	 */
	public String getFileTypePath(int filetype){
		String basePaths=mxUploadFileConfig.getBasepath();
		String fileTypes=mxUploadFileConfig.getFiletype();
		
		Map<String ,String> basePathMap=getMapformUploadConfig(basePaths);
		List<String[]> filetypeList=getListformUploadConfig(fileTypes);	
		String[] ftkeys = filetypeList.get(0);
		String[] ftvalues = filetypeList.get(1);
		
		for(int i = 0 ; i < ftvalues.length ;i++){
			if(Integer.parseInt(ftvalues[i])==filetype){
				return  basePathMap.get(ftkeys[i]);
			}				
		}
		
		return null;
	}
	
	public int[] getAllFileType(){
		String fileTypes=mxUploadFileConfig.getFiletype();
		List<String[]> filetypeList=getListformUploadConfig(fileTypes);
		if(filetypeList.isEmpty())
			return null;
		String[] ftvalues = filetypeList.get(1);
		int[] arrint= new int[ftvalues.length];
		for(int i = 0 ; i<ftvalues.length ;i++ ){
			arrint[i] = Integer.parseInt(ftvalues[i]);
		}
		return arrint;		
	}
	

	/**
	 * 通过配置文件取到域名
	* @Title: getDomain 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String
	 */
	public String getDomain(int filetype){
		String fileTypes=mxUploadFileConfig.getFiletype();
		String domains = mxUploadFileConfig.getDomain();
		
		List<String[]> filetypeList=getListformUploadConfig(fileTypes);	
		String[] ftkeys = filetypeList.get(0);
		String[] ftvalues = filetypeList.get(1);
		
		Map<String,String> domainMap=getMapformUploadConfig(domains);
		
		for(int i = 0 ; i < ftvalues.length ;i++){
			if(Integer.parseInt(ftvalues[i])==filetype){
				return  domainMap.get(ftkeys[i]);
			}				
		}
		return null;		
	}
	
	/**
	 * 取到文件分类的路径
	 * @Title: getClassificationPath 
	 * @param: 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @return String
	 */
	public String getClassificationPath(int classification){
		String fileClassifications=mxUploadFileConfig.getFileClassification();
		
		List<String[]> fileClassList=getListformUploadConfig(fileClassifications);
		String[] fckeys = fileClassList.get(0);
		String[] fcvalues = fileClassList.get(1);
		
		for(int i = 0 ; i <fcvalues.length ;i++) {
			if(Integer.parseInt(fcvalues[i])==classification){
				return fckeys[i];
			}			
		}
		
		return "other"; // 如果此文件通过验证，但是找不到文件的分类，那么就直接返回other
		
	}
	
	
	
	/**
	 * 把上传文件的配置的字符串转成map	
	* @Title: getMapformUploadConfig 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return Map<String,String>
	 */
    
	 public static Map<String,String> getMapformUploadConfig(String s){
		 
	        if(s.isEmpty())
	            return null;
	        String arrstr[] = s.split("\\|");
	        if(arrstr.length <1){
	            return null;
	        }
	        Map<String,String> resultMap = new HashMap<String,String>();
	        
	        for(String ss:arrstr){
	            String test = ss.trim();
	            if(test.length() == 0)
	                continue;
	            int x = test.indexOf("=");
	            String frontstr = test.substring(0,x).trim();
	            log.debug(frontstr + "  ");
	            if(frontstr.length() == 0)
	                continue;
	            String backstr = test.substring(x+1).trim();
	            log.debug(backstr);
	            
	            resultMap.put(frontstr, backstr);            
	        }
	        
	        return resultMap;
	    }
	 
	 /**
		 * 把上传文件的配置的字符串转成map	
		* @Title: getMapformUploadConfig 
		* @param: 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @return Map<String,String>
		 */
    
	 public static List<String[]> getListformUploadConfig(String s){
	        if(s.isEmpty())
	            return null;
	        String arrstr[] = s.split("\\|");
	        if(arrstr.length <1){
	            return null;
	        }
	        String[] keys= new String[arrstr.length];
	        String[] values= new String[arrstr.length];
	        List<String[]> list = new ArrayList<String[]>();
	        for(int i = 0 ; i < arrstr.length ;i++){
	            String test = arrstr[i].trim();
	            if(test.length() == 0)
	                continue;
	            int x = test.indexOf("=");
	            String frontstr = test.substring(0,x).trim();
	            if(frontstr.length() == 0)
	                continue;
	            String backstr = test.substring(x+1).trim();
	            keys[i] =  frontstr;
	            values[i] = backstr;
	        }
	        list.add(0,keys);
	        list.add(1,values);
	        return list;
	    }


}
