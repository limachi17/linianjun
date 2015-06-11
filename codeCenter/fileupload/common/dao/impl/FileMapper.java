package com.moxian.common.dao.impl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

//import com.moxian.common.user.domain.UserAvatar;

import com.moxian.common.domain.UploadFile;

public interface FileMapper {
	
	/**
	 * 根据ID查文件
	* @Title: find 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return UploadFile
	 */
//	@Select("SELECT * FROM uploadFile WHERE id = #{fileName}")
//	public UploadFile find(@Param("fileName") String fileName);	
	
	/**
	 * 
	* @Title: find 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return UploadFile
	 */
	@Select("SELECT * FROM uploadFile WHERE id = #{fileID}")
	public UploadFile find(@Param("fileID") long fileID);	
	
	/*
	 * user_file_upload_bt_seq INT NOT NULL AUTO_INCREMENT,
	user_user_base_bd_seq BIGINT NOT NULL,
	file_upload_filename VARCHAR(50) NOT NULL,
	file_upload_original_filename VARCHAR(50),
	file_upload_path_url VARCHAR(100) NOT NULL,
	file_upload_entity_itype TINYINT NOT NULL,
	file_upload_usage_itype TINYINT NOT NULL,
	create_by BIGINT NOT NULL,
	create_time TIMESTAMP(0) NOT NULL,
	update_by BIGINT,
	update_time TIMESTAMP(0),
	active_flag CHAR(1) NOT NULL,
	PRIMARY KEY (user_file_upload_bt_seq),
	UNIQUE user_file_upload_bt_ix1(user_user_base_bd_seq)

	 */

	/**
	 * 插入
	* @Title: insert 
	* @param: 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return int
	 */
	@Insert("INSERT INTO user_file_upload_bt(user_user_base_bd_seq, file_upload_filename,file_upload_path_url,"
			+ "file_upload_usage_itype,file_upload_entity_itype,active_flag,"
			+ "create_time,file_upload_original_filename,create_by)"
			+ "VALUES (#{userId},#{fileName},#{filePath},"
			+ "#{fileClassification},#{filetype},#{status},"
			+ "#{createTime},#{originalFileName},#{userId})")
	@SelectKey(keyProperty = "fileId", before = false, resultType = long.class, statement = { "SELECT LAST_INSERT_ID() AS fileId " })
    int insert(UploadFile uploadFile);
}
