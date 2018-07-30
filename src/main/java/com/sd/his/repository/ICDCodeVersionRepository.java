package com.sd.his.repository;

import com.sd.his.model.ICDCodeVersion;
import com.sd.his.wrapper.ICDCodeVersionWrapper;
import com.sd.his.wrapper.ICDCodeWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICDCodeVersionRepository extends JpaRepository<ICDCodeVersion, Long> {


    @Query("SELECT new com.sd.his.wrapper.ICDCodeVersionWrapper(codeVersion,codeVersion.icd,codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion ")
    List<ICDCodeVersionWrapper> findAllByOrderByVersion_name(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDCodeVersionWrapper(codeVersion,codeVersion.icd,codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion")
    List<ICDCodeVersionWrapper> findAllByOrderByVersion_name();

    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(codeVersion,codeVersion.icd) FROM com.sd.his.model.ICDCodeVersion codeVersion where codeVersion.version.id=:id")
    List<ICDCodeWrapper> findAllByVersion_id(@Param("id") long iCDCVsById);

    List<Long> deleteAllByVersion_id(long id);


    @Query("SELECT new com.sd.his.wrapper.ICDCodeVersionWrapper(codeVersion,codeVersion.icd,codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion " +
            "where codeVersion.version.name LIKE CONCAT('%',:name,'%') or codeVersion.icd.code LIKE CONCAT('%',:code,'%') ")
    List<ICDCodeVersionWrapper> findAllByVersion_NameContainingOrIcd_CodeContaining(@Param("name") String versionName, @Param("code") String code, Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDCodeVersionWrapper(codeVersion,codeVersion.icd,codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion " +
            "where codeVersion.version.name LIKE CONCAT('%',:name,'%') or codeVersion.icd.code LIKE CONCAT('%',:code,'%') ")
    List<ICDCodeVersionWrapper> findAllByVersion_NameContainingOrIcd_CodeContaining(@Param("name") String versionName, @Param("code") String code);

}
