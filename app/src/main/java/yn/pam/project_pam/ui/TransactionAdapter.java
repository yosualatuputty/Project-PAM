package yn.pam.project_pam.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yn.pam.project_pam.*;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions; // Ganti tipe LiveData menjadi List<Transaction>
    private Context context;
    private DecimalFormat decimalFormat;

    public TransactionAdapter(Context context) {
        this.context = context;
        this.transactions = new ArrayList<>(); // Inisialisasi list kosong
        this.decimalFormat = new DecimalFormat("Rp ###,###");
    }

    public void setData(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.tvCategory.setText(transaction.getCategory());
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvNominal.setText(decimalFormat.format(transaction.getNominal()));

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionRepository transactionRepository = new TransactionRepository(context);
                transactionRepository.delete(transaction);
            }
        });

        holder.ivDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDataActivity.class);
                intent.putExtra("transaction", transaction);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvNominal, tvDescription;
        ImageView ivDelete, ivDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_categoryrow);
            tvNominal = itemView.findViewById(R.id.tv_nominal);
            tvDescription = itemView.findViewById(R.id.tv_descriptionrow);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivDetail = itemView.findViewById(R.id.iv_update);
        }
    }
}
