package com.moxian.common.dao;

import com.moxian.common.domain.UploadFile;

public interface FileDao {
	/**
     * Find user by user Id
     *
     * @param userId user Id
     * @return user with specified Id, null if not found
     */
    UploadFile find(long fileID);

    /**
     * Retrieves all the users
     *
     * @return list of all users, empty list if there is no user
     */
//    Iterable<UploadFile> findAll();

    /**
     * Save a Upload
     *
     * @param user object to be saved
     * @return saved user object
     */
    UploadFile save(UploadFile file)throws Exception;

    /**
     * Update a user
     *
     * @param user object to be updated
     * @return updated user object
     */
//    void update(UploadFile file);

    /**
     * Delete a user
     *
     * @param userId user Id
     */
//    void delete(long fileId);

}
