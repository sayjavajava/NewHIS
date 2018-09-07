package com.sd.his.repository;

import com.sd.his.model.Document;
import com.sd.his.wrapper.DocumentWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 9/3/2018.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {


    @Query("SELECT new com.sd.his.wrapper.DocumentWrapper(doc.id,doc.createdOn,doc.updatedOn,doc.name,doc.type,doc.description,doc.url,doc.patient.id) " +
            "FROM com.sd.his.model.Document doc where doc.id=:id")
    DocumentWrapper findDocumentById(@Param("id") long documentId);

    @Query("SELECT new com.sd.his.wrapper.DocumentWrapper(doc.id,doc.createdOn,doc.updatedOn,doc.name,doc.type,doc.description,doc.url,doc.patient.id) " +
            "FROM com.sd.his.model.Document doc")
    List<DocumentWrapper> getPaginatedDocuments(Pageable pageable);


    @Query("SELECT CASE WHEN COUNT (doc) > 0 THEN true ELSE false END FROM com.sd.his.model.Document doc WHERE doc.name=:name")
    boolean isNameExists(@Param("name") String nameDocument);

    @Query("SELECT CASE WHEN COUNT (doc) > 0 THEN true ELSE false END FROM com.sd.his.model.Document doc WHERE doc.name=:name AND doc.id <>:id")
    boolean isNameExistsAgainstId(@Param("name") String nameDocument, @Param("id") Long id);
}
