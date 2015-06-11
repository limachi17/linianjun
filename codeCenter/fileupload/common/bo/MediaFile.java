package com.moxian.common.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.moxian.common.exception.MoXianFileException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MediaFile extends AbstractFile {

	public MediaFile(InputStream uploadStream, FormDataContentDisposition filedetail, int fileType, int classfycation,FileUploadConfig fileUploadConfig) {
		this.uploadStream = uploadStream;
		this.fileDetail = filedetail;
		this.fileType = fileType;
		this.fileClassification = classfycation;
		this.fileUploadConfig = fileUploadConfig;
	}

	@Override
	public String saveFile(String typepath, String function, String fileName,
			String domain) throws MoXianFileException,IOException {
		
		String path = typepath + File.separatorChar + function+File.separatorChar+fileName;
		
		try {
			OutputStream out = new FileOutputStream(new File(
					path));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(path));
			while ((read = uploadStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			return path;
		} catch (IOException e) {
			log.warn("文件保存异常");
			throw e;
		}
	}
}
