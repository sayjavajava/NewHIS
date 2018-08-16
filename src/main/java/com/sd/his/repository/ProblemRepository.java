package com.sd.his.repository;

import com.sd.his.model.Problem;
import com.sd.his.wrapper.ProblemWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 8/15/2018.
 */
@Repository
public interface ProblemRepository extends JpaRepository<Problem,Long> {

    Page<Problem> findAllByCreatedOnNotNull(Pageable page);
}
