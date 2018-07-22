package com.epitomecl.kmp.dc.repository;

import com.epitomecl.kmp.dc.entity.SharedBackup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SharedBackupRepository extends CrudRepository<SharedBackup, Long> {

    @Query("select sb from SharedBackup sb where sb.guid = :guid")
    SharedBackup findByGuid(@Param("guid") String guid);

}
