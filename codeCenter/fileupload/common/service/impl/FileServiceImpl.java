package com.moxian.common.service.impl;

import javax.inject.Inject;

import com.moxian.common.dao.FileDao;
import com.moxian.common.domain.UploadFile;
import com.moxian.common.service.FileService;

public class FileServiceImpl implements FileService {

	@Inject
	private FileDao fileDao;

	@Override
	public UploadFile find(long fileID) {
		// TODO Auto-generated method stub
		return fileDao.find(fileID);
	}

	@Override
	public UploadFile save(UploadFile file) throws Exception{
		// TODO Auto-generated method stub
		return fileDao.save(file);
	}

//	@Override
//	public void update(UploadFile file) {
//		// TODO Auto-generated method stub
//		fileDao.update(file);
//	}

//	@Override
//	public void delete(long fileId) {
//		// TODO Auto-generated method stub
//		fileDao.delete(fileId);
//		
//	}

}
