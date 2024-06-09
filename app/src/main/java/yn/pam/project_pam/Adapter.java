package yn.pam.project_pam;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< Updated upstream
=======
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
>>>>>>> Stashed changes
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Item> items;

<<<<<<< Updated upstream
    public Adapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
=======
    DatabaseReference databaseReference;

    List<String> keys;
    DecimalFormat decimalFormat;

    public Adapter(Context context, List<Transaction> items, List<String> keys, EditClickListener listener, DatabaseReference databaseReference) {
        this.context = context;
        this.items = items;
        this.editClickListener = listener;
        this.databaseReference = databaseReference;
        this.keys = keys;
        this.decimalFormat = new DecimalFormat("Rp ###,###");
>>>>>>> Stashed changes
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

<<<<<<< Updated upstream
        Item currentItem = items.get(position);
        holder.tv_kategoriList.setText(items.get(position).getKategori());
        holder.tv_deskripsiList.setText(items.get(position).getDeskripsi());
        holder.tv_nominalList.setText(items.get(position).getNominal());
        holder.tv_sumberList.setText(items.get(position).getSumber());
        holder.iv_logoList.setImageResource(items.get(position).getLogo());
=======
        Transaction currentItem = items.get(position);
        holder.tv_kategoriList.setText(currentItem.getKategori());
        holder.tv_deskripsiList.setText(currentItem.getDeskripsi());
        holder.tv_nominalList.setText(decimalFormat.format(currentItem.getNominal()));
        holder.tv_sumberList.setText(currentItem.getSumber());
//        holder.iv_logoList.setImageResource(currentItem.getLogo());
>>>>>>> Stashed changes

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDialog(context, currentItem);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showDialog(Context context, Item item) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);

        //declare elements
        TextView tv_dialogKategori = dialog.findViewById(R.id.tv_dialogKategori);
        TextView tv_nominalDialog = dialog.findViewById(R.id.tv_nominalDialog);
        TextView tv_dateDialog = dialog.findViewById(R.id.tv_dateDialog);
        TextView tv_timeDialog = dialog.findViewById(R.id.tv_timeDialog);
        TextView tv_walletDialog = dialog.findViewById(R.id.tv_walletDialog);
        ImageView iv_logoDialog = dialog.findViewById(R.id.iv_logoDialog);

        //assign values
        tv_dialogKategori.setText(item.getKategori());
        tv_nominalDialog.setText(item.getNominal());
//        tv_dateDialog.setText();
//        tv_timeDialog.setText();
        tv_walletDialog.setText(item.getSumber());
        iv_logoDialog.setImageResource(R.drawable.food);

<<<<<<< Updated upstream
        Button editButton = dialog.findViewById(R.id.bt_editDialog);
        Button deleteButton = dialog.findViewById(R.id.bt_deleteDialog);
        ImageView closeButton = dialog.findViewById(R.id.closeButton_edit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String jsonItem = gson.toJson(item);

                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("itemData", jsonItem);

                    context.startActivity(intent);
=======
        tv_kategori.setText(item.getKategori());
        tv_nominal.setText(decimalFormat.format(item.getNominal()));
        tv_wallet.setText(item.getSumber());
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = key;
                String kategori = item.getKategori();
                String deksripsi = item.getDeskripsi();
                String nominal = String.valueOf(item.getNominal());
                String sumber = item.getSumber();
//                Intent i = new Intent(context, EditActivity.class);
//                i.putExtra("id", id);
//                context.startActivity(i);
//                int id = item.tid;
                editClickListener.onEditClicked(id, kategori, deksripsi, nominal, sumber);
>>>>>>> Stashed changes
                dialog.dismiss();
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("transactions").child(userId).child(key);
                transactionRef.removeValue();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}
