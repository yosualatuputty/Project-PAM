package yn.pam.project_pam;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class WalletLoader extends AsyncTask<Void, Void, ArrayList<WalletModel>> {

    private static final String SERVER_URL = "http://192.168.56.1/server-fundplus/wallets.php";

    @Override
    protected ArrayList<WalletModel> doInBackground(Void... voids) {
        ArrayList<WalletModel> walletList = new ArrayList<>();

        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String json = stringBuilder.toString();
                Gson gson = new Gson();
                WalletModel[] wallets = gson.fromJson(json, WalletModel[].class);
                walletList.addAll(Arrays.asList(wallets));

                bufferedReader.close();
                inputStream.close();
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return walletList;
    }
}

