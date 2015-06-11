package com.moxian.common.dao.impl;

import com.moxian.common.dao.FileDao;
import com.moxian.common.domain.UploadFile;

public class FileDaoMyBatisImpl implements FileDao {

	final private FileMapper fileMapper;	

	public FileDaoMyBatisImpl(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}

	@Override
	public UploadFile find(long fileID) {
		// TODO Auto-generated method stub
		return fileMapper.find(fileID);
	}

	@Override
	public UploadFile save(UploadFile file)throws Exception {
		// TODO Auto-generated method stub
//		reurn 
		try{
			fileMapper.insert(file);
		}catch(Exception e){
			throw e;
		}
//		fileMapper.insert(file);
		
        return file;
//		return null;
	}

}
