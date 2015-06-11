package com.moxian.common.bo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.moxian.common.domain.UploadFile;
import com.moxian.common.exception.MoXianFileException;
import com.moxian.common.util.StringUtil;

@Slf4j
public abstract class AbstractFile {
	
	protected InputStream uploadStream;
	protected FormDataContentDisposition fileDetail;
	protected UploadFile file;
	protected String filePath;
	protected int fileType;  // 文件类型 1，图像，2，音频，3，文件文件
	protected int fileClassification;//文件分类：1,头像，2资质 3，相片，4，留言，5，音乐
	FileUploadConfig fileUploadConfig;//配置文件
	
	protected String uploadPath;
	
	/**
	 * 
	 * @Title: doWithFile 
	 * @param: 
	 * @Description: 
	 * @return 返回文件保存的路径或者
	 */
	public String doWithFile()throws MoXianFileException, IOException{
		
		volidateSream(uploadStream);
		
		
		
		//1 根据文件类型得到路径
		String basePath = getBasePathByFileType(fileType);
		log.debug(basePath);
		
		//2 根据文件功能到文件夹
		String functionPath = getFileDirByFileClassification(fileClassification);
		log.debug(functionPath);
		
		//3 根据文件类型得到展示域名		
		String domain = getDomain(fileType);
		log.debug(domain);
		
		//4得到文件保存的文件名
		String savaname = getSaveNameByOrgName();
		log.debug(savaname);
		
		//5 建立要保存的路径
		String savePath = generatePath(basePath,functionPath);
		log.debug(savePath);
		
////		filePath = getFilePath(this.fileType,this.fileClassification);	
//		filePath = getFilePath2(fileType,fileClassification);		
//		
		String domainPath = generateDomainPath(domain,basePath,functionPath,savaname,fileType);
		log.debug(domainPath);
		
		if(hook()){
			return null;
		}
//		String fileName = fileDetail.getFileName();
//		String newFileName = 
//		String path = filePath + File.separatorChar + newFileName;
		String viewpath =saveFile(basePath,functionPath,savaname,domain);	
		
//		String viewPath=getDomain(fileType);

		return domainPath;		
	}

	private String generateDomainPath(String domain, String basePath,
			String functionPath,String savename,int fileType) {
		
		String domainview = null;
//		if(fileType==0){
			domainview ="http://"+ domain+"/"+ functionPath+"/"+savename;
//		}else{
//			domainview ="http://"+  domain+"/"+getLastWords(basePath)+"/"+functionPath+"/"+savename;
//		}
		
		
		
		// TODO Auto-generated method stub
		return domainview;
	}

	/**
	 * 副作用的东西啊啊啊啊啊，运维还没有给规则，只能先这么搞
	* @Title: getLastWords 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String
	 */
	private String getLastWords(String basePath) {
		int x=basePath.lastIndexOf("/");
		if(x>0)
			return basePath.substring(x+1);
		return basePath;
	}

	private String generatePath(String basePath, String functionPath) {
		String filePath = basePath + File.separatorChar + functionPath;
		File fileDir = new File(filePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		return filePath;
	}

	private String getSaveNameByOrgName() {
		// TODO Auto-generated method stub	
		return "" +ThreadLocalRandom.current().nextLong(10000) + System.currentTimeMillis() + "."
		+ StringUtil.getExtensionName(fileDetail.getFileName());
	}

	private String getFileDirByFileClassification(int fileClassification2) {
		// TODO Auto-generated method stub
		return fileUploadConfig.getClassificationPath(fileClassification2);
	}

	private String getBasePathByFileType(int fileType2) {
		// TODO Auto-generated method stub
		return fileUploadConfig.getFileTypePath(fileType2);
	}

	public String getFilePath(int fileType , int fileClassification)throws MoXianFileException{
		return MoXianFileHelp.getFilePath(fileType, fileClassification);
	}
	
	public String getFilePath2(int fileType , int fileClassification)throws MoXianFileException{
		return fileUploadConfig.getSavePath(fileType, fileClassification);
	}
	
	public String getDomain(int filetype){
		return fileUploadConfig.getDomain(filetype);
	}

	/**保留
	 * 
	 * @Title: hook 
	 * @param: 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @return boolean
	 */
	public boolean hook() {
		// TODO Auto-generated method stub
		return false;
	}

	public abstract String saveFile(String path,String function,String fileName,String domain)throws MoXianFileException, IOException;

	public void volidateSream(InputStream uploadStream) {
	}

}
