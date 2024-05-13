package yn.pam.project_pam;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private Context context;
    private List<Transaction> transactionList;

    public HomeAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Transaction currentItem = transactionList.get(position);
        holder.tv_kategoriList.setText(currentItem.getCategory());
        holder.tv_deskripsiList.setText(currentItem.getDescription());
        holder.tv_nominalList.setText(currentItem.getAmount());
        holder.tv_sumberList.setText(currentItem.getWallet());
        holder.iv_logoList.setImageResource(currentItem.getLogo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(context, currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void showDialog(Context context, Transaction item) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.home_dialog_layout);

        TextView tv_dialogKategori = dialog.findViewById(R.id.tv_dialogKategori);
        TextView tv_nominalDialog = dialog.findViewById(R.id.tv_nominalDialog);
//        TextView tv_dateDialog = dialog.findViewById(R.id.tv_dateDialog);
//        TextView tv_timeDialog = dialog.findViewById(R.id.tv_timeDialog);
        TextView tv_walletDialog = dialog.findViewById(R.id.tv_walletDialog);
        ImageView iv_logoDialog = dialog.findViewById(R.id.iv_logoDialog);

        tv_dialogKategori.setText(item.getCategory());
        tv_nominalDialog.setText(item.getAmount());
        tv_walletDialog.setText(item.getWallet());
        iv_logoDialog.setImageResource(R.drawable.ic_food);

        Button editButton = dialog.findViewById(R.id.bt_editDialog);
        Button deleteButton = dialog.findViewById(R.id.bt_deleteDialog);
        ImageView closeButton = dialog.findViewById(R.id.closeButton_edit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String jsonItem = gson.toJson(item);

                Intent intent = new Intent(context, EditTransactionActivity.class);
                intent.putExtra("itemData", jsonItem);
                context.startActivity(intent);

                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTransactionFromDatabase(item.getId());
                dialog.dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deleteTransactionFromDatabase(int transactionId) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                TransactionDatabase db = TransactionDatabase.getInstance(context);
                if (db != null) {
                    db.transactionDao().deleteTransactionById(transactionId);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Iterator<Transaction> iterator = transactionList.iterator();
                while (iterator.hasNext()) {
                    Transaction transaction = iterator.next();
                    if (transaction.getId() == transactionId) {
                        iterator.remove();
                        break;
                    }
                }

                notifyDataSetChanged();
            }
        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}