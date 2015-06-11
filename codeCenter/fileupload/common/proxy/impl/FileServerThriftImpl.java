package com.moxian.common.proxy.impl;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.thrift.TException;

import com.moxian.common.bo.FileUploadConfig;
import com.moxian.common.bo.QrcodeToolKit;
import com.moxian.common.exception.MoXianFileException;
import com.moxian.common.proxy.service.FileServerThrift;

@Slf4j
public class FileServerThriftImpl implements FileServerThrift.Iface{
	
	private static final int QRCODE=4; // 在配置文件中表示二维码文件	
	private static final int QRCODESAVEPATH=5; // 个人二维码路径
	private static final int CRCODESAVEPATH=6; // 公司二维码码路径
	private static final String QRIMAGE = "qrcode.png";
	private static int USER_QRCODE_WIDGHT = 720;
	private static int USER_QRCODE_HEIGHT = 720;
	
	@Inject
	private FileUploadConfig fileUploadConfig;
	
	@Override
	public String generateQrCode(long userIdOrShopId, int type) throws TException {
		log.debug("User:"+userIdOrShopId+" 想要生成 "+ (type==1?"个人":"公司"+"二维码"));
		// 1表示个人二维码，2表示公司二维码：当前主要考虑1这种情况。				
		String content=getContent(userIdOrShopId, type);
		log.debug("内容:" + content);
		String basePath = getBasePath();
		log.debug("基本路径:"+basePath);
		String savePath = getSavePath(type);
		log.debug("保存路径:"+ savePath);
		String fileName =getFileName(userIdOrShopId);
		log.debug("文件名称:"+ fileName);
		String domainpath = getUrl();
		log.debug("域名："+domainpath);
		String url = null;
		try {
			url= QrcodeToolKit.textQrcodeKent(content, USER_QRCODE_WIDGHT, USER_QRCODE_HEIGHT, savePath, basePath, fileName, domainpath);
		} catch (Exception e) {
			log.warn(e.getMessage(),e);
			url=null;
		}		
		return url;
	}

	private String getUrl() {
		return fileUploadConfig.getDomain(QRCODE);
	}

	private String getSavePath(int type) {
		if(1== type)
			return fileUploadConfig.getClassificationPath(QRCODESAVEPATH);
		else
			return fileUploadConfig.getClassificationPath(CRCODESAVEPATH);
	}

	private String getFileName(long userId) {
		return userId+QRIMAGE;
	}

	private String getBasePath() {		
		return fileUploadConfig.getFileTypePath(QRCODE);
	}

	/*
	 * 得到二维码内容
	 */
	private String getContent(long userId, int type) {
		String  content = null;
		if(1 == type){
			content = "userId:"+userId;
		}else if(2==type){
			content = "companyId:"+userId;
		}else{
			content = "userId:"+userId; //这样写不好，这个留下做未来扩展修改。
		}
		return content;
	}

	@Override
	public String generateQrImageCode(long userId, int type) throws TException {
		// 1表示个人二维码，2表示公司二维码：当前主要考虑1这种情况。				
		log.debug("User:"+userId+" 想要生成 "+ (type==1?"个人":"公司"+"二维码"));
		// 1表示个人二维码，2表示公司二维码：当前主要考虑1这种情况。				
		String content=getContent(userId, type);
		log.debug("内容:" + content);
		String basePath = getBasePath();
		log.debug("基本路径:"+basePath);
		String savePath = getSavePath(type);
		log.debug("保存路径:"+ savePath);
		String fileName =getFileName(userId);
		log.debug("文件名称:"+ fileName);
		String domainpath = getUrl();
		log.debug("域名："+domainpath);
		String url = null;
		try {
			url= QrcodeToolKit.textQrcodeKent(content, USER_QRCODE_WIDGHT, USER_QRCODE_HEIGHT, savePath, basePath, fileName, domainpath);
		} catch (Exception e) {
			log.warn(e.getMessage(),e);
			url=null;
		}		
		return url;
	}

}
