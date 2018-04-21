package com.epitomecl.kmp.wallet;

import info.blockchain.wallet.bip44.HDWallet;
import info.blockchain.wallet.ethereum.EthereumWallet;

import javax.swing.*;
import java.awt.event.*;

public class WalletInfoDlg extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField walletLabel;
    private JTextField coinType;
    private JTextField receiveAddress;
    private JTextField changeAddress;

    public WalletInfoDlg(JDialog parent, String label, WalletData walletData) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setLocationRelativeTo(parent);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        walletLabel.setText(label);
        CryptoType coinType = walletData.getCoinType();
        this.coinType.setText(coinType.toString());

        String receiveAddr = "";
        String changeAddr = "";

        HDWallet wallet = walletData.getWallet();

        if (coinType == CryptoType.ETHEREUM) {
            //Create etherium wallet code from <== EthereumWalletTest.java
            EthereumWallet subject = new EthereumWallet(wallet.getMasterKey(), label);

            //etherium wallet has single account and one address
            receiveAddr = subject.getAccount().getAddress();
            changeAddr = subject.getAccount().getAddress();
        } else {
            receiveAddr = wallet.getAccounts().get(0).getReceive().getAddressAt(0).getAddressBase58();
            changeAddr = wallet.getAccounts().get(0).getChange().getAddressAt(0).getAddressBase58();
        }

        receiveAddress.setText(receiveAddr);
        changeAddress.setText(changeAddr);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
