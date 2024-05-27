package yn.pam.project_pam.model;

import java.util.ArrayList;

import yn.pam.project_pam.R;

public class WalletModel {

    private static ArrayList<WalletModel> walletArrayList = new ArrayList<>();
    private String id;
    private String name;

    public WalletModel(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void initWallet() {
        WalletModel wallet1 = new WalletModel("0", "Mandiri");
        walletArrayList.add(wallet1);

        WalletModel wallet2 = new WalletModel("1", "Cash");
        walletArrayList.add(wallet2);

        WalletModel wallet3 = new WalletModel("2", "Dana");
        walletArrayList.add(wallet3);

        WalletModel wallet4 = new WalletModel("3", "Shopeepay");
        walletArrayList.add(wallet4);
    }

    public int getImage() {
        switch (getId()) {
            case "0":
                return R.drawable.ic_bank;
            case "1":
                return R.drawable.ic_cash;
            case "2":
            case "3":
                return R.drawable.ic_e_money;
            default:
                return 0;
        }

    }

    public static ArrayList<WalletModel> getWalletArrayList() {
        return walletArrayList;
    }
}
