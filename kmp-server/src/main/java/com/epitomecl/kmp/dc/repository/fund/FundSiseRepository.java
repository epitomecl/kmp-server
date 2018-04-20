package com.epitomecl.kmp.dc.repository.fund;

import com.epitomecl.kmp.dc.entity.fund.FundSise;
import com.epitomecl.kmp.dc.entity.fund.FundSisePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundSiseRepository extends JpaRepository<FundSise, FundSisePk> {

//    @Query("select b " +
//            "from FundItem a, FundSise b " +
//            "where a.fundTabCode = ?1 and a.itemcode = b.pk.종목코드")
//    List<FundSise> findByFundTabCode(int fundTabCode);

}
