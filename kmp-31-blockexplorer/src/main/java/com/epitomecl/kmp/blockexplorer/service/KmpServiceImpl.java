package com.epitomecl.kmp.blockexplorer.service;

import com.epitomecl.kmp.blockexplorer.dao.KmpDAOImpl;
import com.epitomecl.kmp.blockexplorer.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KmpServiceImpl {

    @Autowired
    private KmpDAOImpl dao;

    public void setUserData(String id, String pw) {
        dao.setUserData(id, pw);
    }

    public UserVO getUserData(String id, String pw) {
        return dao.getUserData(id, pw);
    }

    public void setPassword(String id, String pw) {
        dao.setPassword(id, pw);
    }
}
