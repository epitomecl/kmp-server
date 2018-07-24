package com.epitomecl.kmp.blockexplorer.dao;

import com.epitomecl.kmp.blockexplorer.domain.UTXO;
import com.epitomecl.kmp.blockexplorer.domain.UTXORaw;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlockExplorerDAOImpl {

    @Autowired
    private SqlSessionTemplate session;

    private static String namespace = "com.epitomecl.kmp.mapper.BlockExplorerMapper";

    public List<UTXORaw> getBalanceEx(String address) {
        return session.selectList(namespace + ".getBalance", address);
    }

    public int getSpendTXOCount(String address) {
        return session.selectOne(namespace + ".getSpendTXOCount", address);
    }

}
