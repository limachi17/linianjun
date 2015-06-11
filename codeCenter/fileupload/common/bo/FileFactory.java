package com.moxian.common.bo;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.moxian.common.exception.MoXianFileException;
import com.moxian.common.util.UploadFileConfig;

public class FileFactory {
	//image=0 | media=1|text=2 |mv=3
//	static Map<Integer,AbstractFile> map = new HashMap<Integer,AbstractFile>();
//	static{
//		map = new HashMap<Integer,AbstractFile>();
//		map.add(New)
//		
//	}

	public static AbstractFile createFile(InputStream uploadStream,
			FormDataContentDisposition filedetail,
			int fileType,int classfycation,FileUploadConfig fileUploadConfig){
		
		int[] arrtype = fileUploadConfig.getAllFileType();
		
		if(UploadFileConfig.FILETYPE_IMAGE == fileType){
//			return new LogFile(uploadStream,filedetail,fileType,classfycation, fileUploadConfig);
			return new ImageFile(uploadStream,filedetail,fileType,classfycation, fileUploadConfig);
		}else if(UploadFileConfig.FILETYPE_MEDIA == fileType){
			return new MediaFile(uploadStream,filedetail,fileType,classfycation, fileUploadConfig);
		}else{
			return new ImageFile(uploadStream,filedetail,fileType,classfycation, fileUploadConfig);
		}
	}

}
