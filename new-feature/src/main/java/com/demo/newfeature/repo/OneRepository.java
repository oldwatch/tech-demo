package com.demo.newfeature.repo;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;
import com.demo.newfeature.management.DemoManagement;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneRepository extends CrudRepository<OneRec, Integer> {

    @Query("""
            select * from T_ONE 
            where NAME like :name and CREATED_DATE>:#{#pager.lastLocal} and IS_DELETED= false 
            order by CREATED_DATE desc limit :#{pager.limit} """)
    List<OneRec> findByWildName(@Param("name") String query, DemoManagement.Pager page);

    @Query("""
            select * from T_ONE 
            where NAME like :name and IS_DELETED= false  
            order by CREATED_DATE desc limit :limit """)
    List<OneRec> findByWildName(@Param("name") String query, int limit);


    @Modifying
    @Query("update T_ONE set IS_DELETED = true where id = :id and IS_DELETED = false ")
    Integer doDelete(@Param("id") Integer id);

    @Modifying
    @Query("update T_ONE set STATUS=:status where IS_DELETED = false and id = :id")
    Integer doUpdateStatus(@Param("id") Integer id, @Param("status") StatusType status);

    @Query("select * from T_ONE where IS_DELETED=false and ID= :id")
    OneRec getEntityById(Integer id);

}
