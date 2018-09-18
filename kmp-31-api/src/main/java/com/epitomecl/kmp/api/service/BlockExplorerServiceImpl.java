package com.epitomecl.kmp.api.service;

import com.epitomecl.kmp.api.dao.BlockExplorerDAOImpl;
import com.epitomecl.kmp.api.domain.UTXORaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BlockExplorerServiceImpl {

    @Autowired
    private BlockExplorerDAOImpl dao;

    public List<UTXORaw> getBalanceEx(String address) {
        return dao.getBalanceEx(address);
    }

    public List<UTXORaw> getTX(byte[] hash) {
        return dao.getTX(hash);
    }

    public int getSpendTXOCount(String address) {
        return dao.getSpendTXOCount(address);
    }

    public boolean isUsedAddress(String address) {
        return dao.isUsedAddress(address);
    }

    public boolean isSpendAddress(String address) {
        return dao.isSpendAddress(address);
    }

    private void addUTXO(ResultSet rs) throws SQLException {
//        Sha256Hash hash = Sha256Hash.wrap(rs.getBytes(1));
//        Coin amount = Coin.valueOf(rs.getLong(2));
//        byte[] scriptBytes = rs.getBytes(3);
//        int height = rs.getInt(4);
//        int index = rs.getInt(5);
//        boolean coinbase = rs.getBoolean(6);
//        String toAddress = rs.getString(7);
//        UTXO output = new UTXO(hash, (long)index, amount, height, coinbase, new Script(scriptBytes), toAddress);
        //outputs.add(output);
    }

}
