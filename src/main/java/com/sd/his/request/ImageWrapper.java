package com.sd.his.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/* @author    : Qari Jamal
 * @Date      : 17-Apr-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.wrapper*
 * @FileName  : ImageWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class ImageWrapper {

    private long userId;
    /////// DEMOGRAPHY
    private long profileId;
    private File profileImg;

    ///////////////// INSURANCE
    private long insuranceId;
    private File photoFront;
    private File photoBack;

    private File photo;
    private MultipartFile file;

    public ImageWrapper() {
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public File getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(File profileImg) {
        this.profileImg = profileImg;
    }

    public long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public File getPhotoFront() {
        return photoFront;
    }

    public void setPhotoFront(File photoFront) {
        this.photoFront = photoFront;
    }

    public File getPhotoBack() {
        return photoBack;
    }

    public void setPhotoBack(File photoBack) {
        this.photoBack = photoBack;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
