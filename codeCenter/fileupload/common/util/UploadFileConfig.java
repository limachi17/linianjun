package com.moxian.common.util;


public interface UploadFileConfig {
	
	public static final String BASE_IMAGE_PATH = "/opt/imagedata";
	public static final String BASE_MIDI_PATH = "/opt/upload/mididata";
	public static final String BASE_TEXT_PATH = "/opt/upload/text ";
	public static final String BASE_MV_PATH = "/opt/upload/mv";
	
	public int FILETYPE_IMAGE = 0; // filetype 0 图片
	public int FILETYPE_MEDIA = 1; // filetype 1 音频
	public int FILETYPE_TEXT = 2;  // filetype 2 文本文件
	public int FILETYPE_MV = 3;    // filetype 3 视频
	public int FILETYPE_QR = 4;    // filetype 3 视频
	
	public int CLASSIFICATION_LOGO=0; // Logo
	public int CLASSIFICATION_CERTIFICATE=1; // 证书
	public int CLASSIFICATION_AVATAR=2;      // 头像
	public int CLASSIFICATION_MEDIA=3;       // 音频资料
	
	public String CLASSIFICATION_LOGO_PATH="logo"; 
	public String CLASSIFICATION_CERTIFICATE_PATH="certificate"; // 证书
	public String CLASSIFICATION_AVATAR_PATH="avatar";      // 头像
	public String CLASSIFICATION_MEDIA_PATH="media";  //声音路径
	
	
}
