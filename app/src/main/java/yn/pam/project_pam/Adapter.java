package yn.pam.project_pam;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Transaction> items;
    EditClickListener editClickListener;

    DatabaseReference databaseReference;

    List<String> keys;

    private DecimalFormat decimalFormat;


    public Adapter(Context context, List<Transaction> items, List<String> keys, EditClickListener listener, DatabaseReference databaseReference) {
        this.context = context;
        this.items = items;
        this.editClickListener = listener;
        this.databaseReference = databaseReference;
        this.keys = keys;
        this.decimalFormat = new DecimalFormat("Rp ###,###");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transaction currentItem = items.get(position);
        holder.tv_kategoriList.setText(currentItem.getKategori());
        holder.tv_deskripsiList.setText(currentItem.getDeskripsi());
        holder.tv_nominalList.setText(decimalFormat.format(currentItem.getNominal()));
        holder.tv_sumberList.setText(currentItem.getSumber());
//        holder.iv_logoList.setImageResource(currentItem.getLogo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(context, currentItem, keys.get(holder.getAdapterPosition()));

            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface EditClickListener {
        void onEditClicked(String transactionId, String kategori, String deskripsi, String nominal, String sumber);
    }


    private void showDialog(Context context, Transaction item, String key) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);

        TextView tv_kategori, tv_nominal, tv_wallet;
        Button bt_edit = dialog.findViewById(R.id.bt_editDialog);
        Button bt_delete = dialog.findViewById(R.id.bt_deleteDialog);
        ImageView bt_close = dialog.findViewById(R.id.closeButton_edit);

        tv_kategori = dialog.findViewById(R.id.tv_dialogKategori);
        tv_nominal = dialog.findViewById(R.id.tv_nominalDialog);
        tv_wallet = dialog.findViewById(R.id.tv_walletDialog);

        tv_kategori.setText(item.getKategori());
        tv_nominal.setText(decimalFormat.format(item.getNominal()));
        tv_wallet.setText(item.getSumber());
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = key;
                String kategori = item.getKategori();
                String deksripsi = item.getDeskripsi();
                String nominal = Double.toString(item.getNominal());
                String sumber = item.getSumber();
//                Intent i = new Intent(context, EditActivity.class);
//                i.putExtra("id", id);
//                context.startActivity(i);
//                int id = item.tid;
                editClickListener.onEditClicked(id, kategori, deksripsi, nominal, sumber);
                dialog.dismiss();

            }
        });

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppDatabase.getInstance(context).transaksiDao().delete(item);
//                notifyDataSetChanged();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("transactions").child(userId).child(key);
                transactionRef.removeValue();
                dialog.dismiss();

            }
        });
        dialog.show();

    }


}
