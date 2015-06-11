package com.moxian.common.bo;

import java.io.File;

import com.moxian.common.exception.MoXianFileException;
import com.moxian.common.util.UploadFileConfig;

/**
 * 早期写死的方法：弄好配置文件之后，此类可以作废掉
  * @ClassName: MoXianFileHelp
  * @Description: TODO
  * @author Sam sam.liang@moxiangroup.com
  * @Company moxian
  * @date 2015年4月2日 下午4:50:34
  *
 */
@Deprecated
public class MoXianFileHelp {	
		
	public static String[] basePath={
		UploadFileConfig.BASE_IMAGE_PATH,
		UploadFileConfig.BASE_MIDI_PATH,
		UploadFileConfig.BASE_TEXT_PATH,
		UploadFileConfig.BASE_MV_PATH
	};
	
	public static String[] functionPath={
		UploadFileConfig.CLASSIFICATION_LOGO_PATH,
		UploadFileConfig.CLASSIFICATION_CERTIFICATE_PATH,
		UploadFileConfig.CLASSIFICATION_AVATAR_PATH,
		UploadFileConfig.CLASSIFICATION_MEDIA_PATH
	};

	public static String getFilePath(int fileType,int fileClassification)throws MoXianFileException{
		
		if( fileType < 0 || fileType >= basePath.length ){
			throw new MoXianFileException("文件类型不支持");
		}
		
		if(fileClassification < 0 || fileClassification >= functionPath.length ){
			throw new MoXianFileException("功能分类不存在");
		}
		
		return basePath[fileType] +  File.separatorChar + functionPath[fileClassification];
	}
	
	

}
