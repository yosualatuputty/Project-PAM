package yn.pam.project_pam;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private Context context;
    private List<Transaction> transactionList;
    private DatabaseReference mDatabase;


    public HomeAdapter(Context context, List<Transaction> transactionList, DatabaseReference databaseReference) {
        this.context = context;
        this.transactionList = transactionList;
        this.mDatabase = databaseReference;
    }

//        public HomeAdapter() {
//
//        }

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
                deleteTransaction(item.getId()); // Gunakan getKey() jika ada
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

    public void deleteTransaction(String transactionKey) {
        Log.d(TAG, "Attempting to delete transaction with key: " + transactionKey);
        DatabaseReference transactionRef = mDatabase.child(transactionKey);
        transactionRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Menampilkan pesan jika berhasil dihapus
                        Log.d(TAG, "Transaction deleted successfully from Firebase.");
                        Toast.makeText(context, "Transaction deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Menampilkan pesan jika gagal menghapus
                        Log.d(TAG, "Failed to delete transaction from Firebase: " + e.getMessage());
                        // Tambahkan logika sesuai kebutuhan, misalnya menampilkan pesan error, dll.
                    }
                });
    }

//        public void updateTransactionList(Transaction updatedTransaction) {
//            for (int i = 0; i < transactionList.size(); i++) {
//                if (transactionList.get(i).getId().equals(updatedTransaction.getId())) {
//                    transactionList.set(i, updatedTransaction);
//                    notifyItemChanged(i);
//                    break;
//                }
//            }
//        }


}