package com.epitomecl.kmp.blockexplorer.service;

import com.epitomecl.kmp.blockexplorer.dao.BlockExplorerDAOImpl;
import com.epitomecl.kmp.blockexplorer.domain.UTXO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BlockExplorerServiceImpl {

    @Inject
    private BlockExplorerDAOImpl dao;

    public List<UTXO> getBalanceEx(String address){
        return dao.getBalanceEx(address);
    }

    public int getSpendTXOCount(String address){
        return dao.getSpendTXOCount(address);
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
