package com.moxian.common.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

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

Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
 Duplicate entry '1' for key 'user_file_upload_bt_ix1'

 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_file_upload_bt")
public class UploadFile {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="user_file_upload_bt_seq")
	private long fileId;// 文件ID
	
	@Column(name="user_user_base_bd_seq")
	private long userId;
	
	@Column(name="file_upload_filename" ,nullable = false)
	private String fileName;
	
	@Column(name="file_upload_path_url" ,nullable = false)
	private String filePath;
	
	@Column(name="user_user_base_bd_seq")
	private int fileClassification; //文件功能分类（Logo，Log，头像，证书）
	
	@Column(name="file_upload_entity_itype", nullable = false)
	private int filetype; //文件类型：图片，文本文件，Excel
	
	@Column(name="active_flag")
	private String status;// 
	
	@Column(name="create_time")
	private Timestamp createTime; //文件上传时间
	
	@Column(name="file_upload_original_filename")
	private String originalFileName;
	
}
