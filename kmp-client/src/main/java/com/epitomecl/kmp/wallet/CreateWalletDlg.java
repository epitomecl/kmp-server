package com.epitomecl.kmp.wallet;

import info.blockchain.wallet.bip44.HDWallet;
import info.blockchain.wallet.bip44.HDWalletFactory;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.BitcoinCashMainNetParams;
import org.bitcoinj.params.BitcoinMainNetParams;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateWalletDlg extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField walletLabel;
    private JLabel createMsg;

    private HDWallet wallet;
    private CryptoType coinType;

    private static final int DEFAULT_MNEMONIC_LENGTH = 12;
    private static final int DEFAULT_NEW_WALLET_SIZE = 1;
    private static final String DEFAULT_PASSPHRASE = "";

    private final String create = "Press button it will make '%s' wallet";

    public CreateWalletDlg(JDialog parent, boolean modal, CryptoType coinType) {
        super(parent,modal);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setLocationRelativeTo(parent);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
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

        this.coinType = coinType;
        createMsg.setText(String.format(create, coinType.toString()));
    }

    private void onOK() {
        // add your code here
        if(isvalidLabel())
        {
            NetworkParameters param = null;

            if (coinType == CryptoType.ETHEREUM) {
                //Create etherium wallet code from <== EthereumWalletTest.java
                param = BitcoinMainNetParams.get();
            } else {
                switch (coinType) {
                    case BITCOIN:
                        param = BitcoinMainNetParams.get();
                        break;
                    case BITCOIN_CASH:
                        param = BitcoinCashMainNetParams.get();
                        break;
                }
            }

            try {
                wallet = HDWalletFactory
                        .createWallet(param, HDWalletFactory.Language.US,
                                DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, DEFAULT_NEW_WALLET_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MnemonicException.MnemonicLengthException e) {
                e.printStackTrace();
            }

            WalletDlg.Instance().AddWallet(walletLabel.getText(), coinType, wallet);

            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private boolean isvalidLabel()
    {
        if (walletLabel.getText().length() > 0){
            return true;
        }

        JOptionPane.showMessageDialog(this,
                "wallet label is too short.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);

        return false;
    }
}
