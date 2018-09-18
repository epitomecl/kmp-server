package com.epitomecl.kmp.api.service;

import com.epitomecl.kmp.api.dao.SecretShareDAOImpl;
import com.epitomecl.kmp.api.domain.SecretSharingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretShareServiceImpl {

    @Autowired
    private SecretShareDAOImpl dao;

    public List<String> getSharingDataList(int index) {
        return dao.getSharingDataList(index);
    }

    public SecretSharingVO getSharingDataOne(int index, String label) {
        return dao.getSharingDataOne(index, label);
    }

    public SecretSharingVO getSharingDataTwo(int index, String label) {
        return dao.getSharingDataTwo(index, label);
    }

    public void backupSharingDataOne(int index, String label, String shareddata) {
        dao.backupSharingDataOne(index, label, shareddata);
    }

    public void backupSharingDataTwo(int index, String label, String shareddata) {
        dao.backupSharingDataTwo(index, label, shareddata);
    }
}
