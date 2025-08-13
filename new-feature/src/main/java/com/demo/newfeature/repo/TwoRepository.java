package com.demo.newfeature.repo;

import com.demo.newfeature.entity.OneRec;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoRepository extends CrudRepository<OneRec, Integer> {
}
