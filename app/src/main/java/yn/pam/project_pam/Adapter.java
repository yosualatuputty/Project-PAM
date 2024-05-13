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

import com.google.gson.Gson;

import java.util.List;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Transaksi> items;


    public Adapter(Context context, List<Transaksi> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transaksi currentItem = items.get(position);
        holder.tv_kategoriList.setText(currentItem.kategori);
        holder.tv_deskripsiList.setText(currentItem.deskripsi);
        holder.tv_nominalList.setText(currentItem.nominal);
        holder.tv_sumberList.setText(currentItem.sumber);
//        holder.iv_logoList.setImageResource(currentItem.getLogo());

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



    private void showDialog(Context context, Transaksi item) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);

        TextView tv_kategori, tv_nominal, tv_wallet;
        Button bt_edit = dialog.findViewById(R.id.bt_editDialog);
        Button bt_delete = dialog.findViewById(R.id.bt_deleteDialog);
        ImageView bt_close = dialog.findViewById(R.id.closeButton_edit);

        tv_kategori = dialog.findViewById(R.id.tv_dialogKategori);
        tv_nominal = dialog.findViewById(R.id.tv_nominalDialog);
        tv_wallet = dialog.findViewById(R.id.tv_walletDialog);

        tv_kategori.setText(item.kategori);
        tv_nominal.setText(item.nominal);
        tv_wallet.setText(item.sumber);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditActivity.class);
                int id = item.tid;
                i.putExtra("id", id);
                context.startActivity(i);
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
                AppDatabase.getInstance(context).transaksiDao().delete(item);
                dialog.dismiss();

            }
        });
        dialog.show();

    }


}
