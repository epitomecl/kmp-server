package com.epitomecl.kmp.api.service;

import com.epitomecl.kmp.api.dao.KmpDAOImpl;
import com.epitomecl.kmp.api.domain.UserVO;
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

    public void setEncrypted(int index, String label, String encrypted) {
        dao.setEncrypted(index, label, encrypted);
    }

    public String getEncrypted(int index, String label) {
        return dao.getEncrypted(index, label);
    }

}
