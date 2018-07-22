package com.epitomecl.kmp.dc.repository;

import com.epitomecl.kmp.dc.entity.ThirdPartyBackup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ThirdPartyBackupRepository extends CrudRepository<ThirdPartyBackup, Long> {

    @Query("select tb from ThirdPartyBackup tb where tb.guid = :guid")
    ThirdPartyBackup findByGuid(@Param("guid") String guid);

}
