package com.epitomecl.kmp.blockexplorer.dao;

import com.epitomecl.kmp.blockexplorer.domain.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class KmpDAOImpl {

    @Autowired
    @Qualifier("partnerSqlSessionTemplateFour")
    private SqlSessionTemplate sessionFour;

    private static String namespace = "com.epitomecl.kmp.mapper.KmpMapper";

    public void setUserData(String id, String pw) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id", id);
        param.put("pw", pw);
        sessionFour.insert(namespace + ".setUserData", param);
    }

    public UserVO getUserData(String id, String pw) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id", id);
        param.put("pw", pw);
        return sessionFour.selectOne(namespace + ".getUserData", param);
    }

    public void setPassword(String id, String pw) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id", id);
        param.put("pw", pw);
        sessionFour.update(namespace + ".setPassword", param);
    }
}
