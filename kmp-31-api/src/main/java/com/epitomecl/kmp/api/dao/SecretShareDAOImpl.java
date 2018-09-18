package com.epitomecl.kmp.api.dao;

import com.epitomecl.kmp.api.domain.SecretSharingVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SecretShareDAOImpl {

    @Autowired
    @Qualifier("partnerSqlSessionTemplateTwo")
    private SqlSessionTemplate sessionTwo;

    @Autowired
    @Qualifier("partnerSqlSessionTemplateThree")
    private SqlSessionTemplate sessionThree;

    private static String namespace = "com.epitomecl.kmp.mapper.SecretShareMapper";

    public List<String> getSharingDataList(int index) {
        return sessionTwo.selectList(namespace + ".getSharingDataList", index);
    }

    public SecretSharingVO getSharingDataOne(int index, String label) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userindex", index);
        param.put("label", label);
        return sessionTwo.selectOne(namespace + ".getSharingData", param);
    }

    public SecretSharingVO getSharingDataTwo(int index, String label) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userindex", index);
        param.put("label", label);
        return sessionThree.selectOne(namespace + ".getSharingData", param);
    }

    public void backupSharingDataOne(int index, String label, String shareddata) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userindex", index);
        param.put("label", label);
        param.put("shareddata", shareddata);
        sessionTwo.insert(namespace + ".setSharingData", param);
    }

    public void backupSharingDataTwo(int index, String label, String shareddata) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userindex", index);
        param.put("label", label);
        param.put("shareddata", shareddata);
        sessionThree.insert(namespace + ".setSharingData", param);
    }
}
