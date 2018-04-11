package com.epitomecl.kmp.wallet;


import info.blockchain.wallet.bip44.HDWallet;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class WalletDlg extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTree wallets;
    private JButton btnCreateWalletBTC;
    private JButton btnCreateWalletBCH;
    private JButton btnCreateWalletETH;
    private JButton btnSaveWallet;
    private JButton btnLoadWallet;

    private HashMap<String, WalletData> mapWallet = new HashMap<String, WalletData>();

    private static WalletDlg instance = null;

    public static WalletDlg Instance() {
        if (instance == null) {
            throw new RuntimeException("WalletDlg instance is null");
        }
        return instance;
    }

    public WalletDlg() {
        if (instance == null) {
            instance = this;
        }

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        createUIComponents();

        btnCreateWalletBTC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreateWalletDlg(CryptoType.BITCOIN);
            }
        });
        btnCreateWalletBCH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreateWalletDlg(CryptoType.BITCOIN_CASH);
            }
        });
        btnCreateWalletETH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreateWalletDlg(CryptoType.ETHEREUM);
            }
        });

        wallets.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            wallets.getLastSelectedPathComponent();
                    if (node == null) return;
                    String label = (String) node.getUserObject();
                    WalletData walletData = GetWallet(label);
                    ShowWalletInfo(label, walletData);
                }
            }
        });
        btnSaveWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(contentPane);
            }
        });
        btnLoadWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        WalletDlg dialog = new WalletDlg();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        RetsetWalletTree();
    }

    private void RetsetWalletTree() {
        DefaultTreeModel model = (DefaultTreeModel) wallets.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();

        root = new DefaultMutableTreeNode("wallet");
        model.setRoot(root);
        model.reload();
    }

    private void AddWalletTreeChild(String label) {
        DefaultTreeModel model = (DefaultTreeModel) wallets.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        DefaultMutableTreeNode child = new DefaultMutableTreeNode(label);
        root.add(child);

        model.reload();
    }

    private void updateWalletTree() {
        RetsetWalletTree();

        for (Map.Entry<String, WalletData> entry : mapWallet.entrySet()) {
            String label = entry.getKey();
            AddWalletTreeChild(label);
        }
    }

    private void onCreateWalletDlg(CryptoType type) {
        JDialog dialog = new CreateWalletDlg(this, true, type);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void ShowWalletInfo(String label, WalletData walletData) {
        if (walletData != null) {
            WalletInfoDlg dialog = new WalletInfoDlg(this, label, walletData);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    public void AddWallet(String label, CryptoType coinType, HDWallet wallet) {
        WalletData walletData = new WalletData(coinType, wallet);
        mapWallet.put(label, walletData);
        updateWalletTree();
    }

    public WalletData GetWallet(String label) {
        return mapWallet.containsKey(label) ? mapWallet.get(label) : null;
    }

}
