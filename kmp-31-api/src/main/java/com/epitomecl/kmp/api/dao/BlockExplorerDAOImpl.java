package com.epitomecl.kmp.api.dao;

import com.epitomecl.kmp.api.domain.UTXORaw;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BlockExplorerDAOImpl {

    @Autowired
    @Qualifier("partnerSqlSessionTemplateOne")
    private SqlSessionTemplate session;

    private static String namespace = "com.epitomecl.kmp.mapper.BlockExplorerMapper";

    public List<UTXORaw> getBalanceEx(String address) {
        return session.selectList(namespace + ".getBalance", address);
    }

    public List<UTXORaw> getTX(byte[] hash) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hash", hash);
        return session.selectList(namespace + ".getTX", param);
    }

    public int getSpendTXOCount(String address) {
        return session.selectOne(namespace + ".getSpendTXOCount", address);
    }

    public boolean isUsedAddress(String address) {
        int count = session.selectOne(namespace + ".getUsedTXOCount", address);
        return (count > 0) ? true : false;
    }

    public boolean isSpendAddress(String address) {
        int count = session.selectOne(namespace + ".getSpendTXOCount", address);
        return (count > 0) ? true : false;
    }
}
