package com.moxian.common.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.moxian.common.bo.AbstractFile;
import com.moxian.common.bo.BoUtil;
import com.moxian.common.bo.FileFactory;
import com.moxian.common.bo.FileUploadConfig;
import com.moxian.common.domain.UploadFile;
import com.moxian.common.exception.MoXianFileException;
import com.moxian.common.service.FileService;
import com.moxian.common.util.ErrorCode;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Slf4j
@Path(FileuploadResource.BASE_PATH)
@Api(value = FileuploadResource.BASE_PATH, description = "FileUpload API")
public class FileuploadResource {
	public static final String BASE_PATH = "upload";

	private final FileService fileService;
	private final FileUploadConfig fileUploadConfig;
	private AbstractFile file;

	@Inject
	public FileuploadResource(
			@SuppressWarnings("SpringJavaAutowiringInspection") final @NonNull FileService fileService,@NonNull FileUploadConfig fileUploadConfig ) {
//			@SuppressWarnings("SpringJavaAutowiringInspection") final @NonNull FileUploadConfig fileUploadConfig
		this.fileService = fileService;
		this.fileUploadConfig = fileUploadConfig;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation("upload log file")
	public BoUtil fileUpload(
			@FormDataParam("uploadFile") InputStream uploadStream,
			@FormDataParam("uploadFile") FormDataContentDisposition filedetail,
			@FormDataParam("userId") long userId,
			@FormDataParam("fileType") int fileType,
			@FormDataParam("fileClassfycation")int classfycation ){
		
		log.debug("用户："+userId +"上传 "+fileUploadConfig.getFileTypePath(fileType)+"类型的"+fileUploadConfig.getClassificationPath(classfycation)+"的文件！");
		
		/**
		 * 用户不能空
		 */
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		
		if(null == uploadStream){
			log.warn("输入流不能为空");			
			boUtil.setCode(ErrorCode.FILEISNULL);
			boUtil.setMsg("输入流不能为空");
			return boUtil;
		}
		if(null == filedetail){
			log.warn("FormDataContentDisposition 不能为空");			
			boUtil.setCode(ErrorCode.FILEISNULL);
			boUtil.setMsg("输入文件信息为空");
			return boUtil;
		}else{
			if(null == filedetail.getFileName()){
				log.warn("上传文件名不能为空");			
				boUtil.setCode(ErrorCode.FILENAMEISNULL);
				boUtil.setMsg("上传文件名不能为空");
				return boUtil;
			}
		}
				
		
		if (userId == 0) {
			log.warn("UserId cannot be load");			
			boUtil.setCode(ErrorCode.USERNOTNULL);
			boUtil.setMsg("UserId cannot be load");
			return boUtil;
		}	
		
		int[] types=fileUploadConfig.getAllFileType();
		
		if(null == types){
			log.warn("fileType 为空");			
			boUtil.setCode(ErrorCode.FILETYPEISNULL);
			boUtil.setMsg("读取配置文件失败！");
			return boUtil;			
		}
		
		//对文件类型进行验证：
		if(!contain(types ,fileType)){
			log.warn("不支持的文件类型");	
			boUtil.setCode(ErrorCode.NOTSUPPOTFILETYPE);
			boUtil.setMsg("不支持的文件类型");
			return boUtil;
		}
		
		
		/**
		 * 根据用户上传文件的类型，用相应的文件进行处理
		 */
//		try {
			file = FileFactory.createFile(uploadStream,filedetail,fileType,classfycation, fileUploadConfig);
//		} catch (MoXianFileException e1) {
//			// TODO Auto-generated catch block
//			boUtil.setCode(ErrorCode.USERNOTNULL);
//			log.warn(e1.getMessage(),e1);
//			boUtil.setMsg("上传文件名为空！");
//			return boUtil;
//			
//		}	
		
		String filename=null;
		try {
			filename =file.doWithFile();
		} catch (MoXianFileException e) {
			boUtil.setCode(ErrorCode.SAVEFILEMOXIANEXCEPTION);
			boUtil.setMsg(e.getMessage());			
			log.warn(e.getMessage());
			return boUtil;
		} catch (IOException e) {
			boUtil.setCode(ErrorCode.SAVEFILEIOEXCEPTION);
			boUtil.setMsg(e.getMessage());			
			log.warn(e.getMessage(),e);
			return boUtil;
		}
		
		log.debug("文件"+filename+"保存成功");		
		UploadFile uploadFile = new UploadFile();
		uploadFile.setFileName(filename);
		uploadFile.setFileClassification(classfycation);
		uploadFile.setFilePath(filename);
		uploadFile.setFiletype(fileType);
		uploadFile.setStatus("y");// 0表示正常
		uploadFile.setUserId(userId);
		uploadFile.setCreateTime(new Timestamp(System.currentTimeMillis()) );
		uploadFile.setOriginalFileName(filedetail.getFileName());
		
		UploadFile uf = null;
		try {
			uf =fileService.save(uploadFile);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			boUtil.setCode(ErrorCode.SAVEFILEINDBEXCEPTION);
			boUtil.setMsg(e.getMessage());
			boUtil.setData(e);
			return boUtil;
		}
		
		if(uf == null){
			boUtil.setCode(ErrorCode.SAVEFILEINDBFAILT);
			boUtil.setMsg("文件保存数据库失败");			
			log.warn("文件保存数据库失败");
			return boUtil;
		}
		
		log.debug("文件"+filedetail.getFileName()+"上传成功");
		
		boUtil=BoUtil.getDefaultTrueBo();
		boUtil.setCode(ErrorCode.SUCCESS);
		boUtil.setData(uploadFile.getFilePath());		
		return boUtil;		
	}
	
	/**
	 * 判断 fileType 是否包含在types中，如是是返回真，否则返回false
	* @Title: contain 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return boolean
	 */
	private boolean contain(int[] types, int fileType) {
		// TODO Auto-generated method stub
		for(int i: types){
			if(i == fileType){
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	@POST
	@Path("uploadFiles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation("upload files")
	public BoUtil fileUploads(
			FormDataMultiPart form,
			@FormDataParam("userId") long userId,
			@FormDataParam("fileType") int fileType,
			@FormDataParam("fileClassfycation")int classfycation ){
		
		log.debug("用户："+userId +"上传 "+fileUploadConfig.getFileTypePath(fileType)+"类型的"+fileUploadConfig.getClassificationPath(classfycation)+"的文件！");
		
		/**
		 * 用户不能空
		 */
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		if (userId == 0) {
			log.warn("UserId cannot be load");			
			boUtil.setCode(ErrorCode.USERNOTNULL);
			boUtil.setMsg("UserId cannot be load");
			return boUtil;
		}
		
		List<FormDataBodyPart> list= form.getFields("uploadFile");
		FilesStaus[] fileStaus =new FilesStaus[list.size()];		
			
		int i= -1;
		for(FormDataBodyPart p:list){
			++i;
			InputStream is=p.getValueAs(InputStream.class);
		    FormDataContentDisposition detail=p.getFormDataContentDisposition();
		    /*
		     * 增加这一段对null指针的处理。
		     */
		    if(detail.getFileName().isEmpty()){
		    	fileStaus[i] = null;
		    	continue;
		    }
		    String filename = null;		    
			try {
				filename = new String(detail.getFileName().getBytes("iso8859-1"),"gbk");
			} catch (UnsupportedEncodingException e1) {
				log.warn(e1.getMessage());
			}
		    fileStaus[i] = new FilesStaus(filename);
		    
		    file = FileFactory.createFile(is,detail,fileType,classfycation, fileUploadConfig);
		   

			String filepath=null;
			try {
				filepath =file.doWithFile();
			} catch (MoXianFileException e) {
				log.warn(e.getMessage());
				break;
			} catch (IOException e) {		
				log.warn(e.getMessage());
				break;
			}		
			log.debug("文件"+filepath+"保存成功");
			
			UploadFile uploadFile = new UploadFile();
			uploadFile.setFileName(filepath);
			uploadFile.setFileClassification(classfycation);
			uploadFile.setFilePath(filepath);
			uploadFile.setFiletype(fileType);
			uploadFile.setStatus("y");// 0表示正常
			uploadFile.setUserId(userId);
			uploadFile.setCreateTime(new Timestamp(System.currentTimeMillis()));
			uploadFile.setOriginalFileName(detail.getFileName());
			
			UploadFile uf=null;
			try {
				uf = fileService.save(uploadFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.warn(e.getMessage(),e);
			}
			if(uf == null){	
				log.warn("文件保存数据库失败");
				break;
			}	
			fileStaus[i].setUploadStaus(true);	
			fileStaus[i].setFilepath(filepath);	
		}
		
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<fileStaus.length;j++){
			if(null == fileStaus[j]){
				continue;
			}
			sb.append(fileStaus[j].toString());
		}
		BoUtil rboUtil = BoUtil.getDefaultTrueBo();
		rboUtil.setData(sb.toString());
		rboUtil.setMsg("文件上传成功");		
		return rboUtil;
		
	}
}

/**
 * 
  * @ClassName: FilesStaus
  * @Description: TODO
  * @author kent
  * @Company moxian
  * @date 2015年3月30日 下午5:20:47
  *
 */
class FilesStaus{
	String filename;
	Boolean uploadStaus;
	String filepath;
	
	public FilesStaus(String filename, Boolean uploadStaus) {
		this.filename = filename;
		this.uploadStaus = uploadStaus;
	}
	
	public FilesStaus(String filename){
//		super().FilesStaus(filename,false);
		this.filename = filename;
		this.uploadStaus = false;;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Boolean getUploadStaus() {
		return uploadStaus;
	}

	public void setUploadStaus(Boolean uploadStaus) {
		this.uploadStaus = uploadStaus;
	}
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + getFilename()+"上传"+ (uploadStaus==true?"成功":"失败")+","+filepath+"]" ;
	}
	
}

