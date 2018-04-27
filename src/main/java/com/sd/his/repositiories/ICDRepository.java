package com.sd.his.repositiories;

import com.sd.his.model.ICD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICDRepository extends JpaRepository<ICD,Long> {


}
