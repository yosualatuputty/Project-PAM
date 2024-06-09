package yn.pam.project_pam.analytics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import yn.pam.project_pam.R;
import yn.pam.project_pam.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
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
        holder.tvCategory.setText(transaction.getKategori());
        holder.tvDescription.setText(transaction.getDeskripsi());
        holder.tvNominal.setText(decimalFormat.format(transaction.getNominal()));

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    private void deleteTransaction(Transaction transaction) {
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("transactions").child(userId).child(transaction.getId());
//        transactionRef.removeValue();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvNominal, tvDescription;
        ImageView ivDelete, ivDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_categoryrow);
            tvNominal = itemView.findViewById(R.id.tv_nominal);
            tvDescription = itemView.findViewById(R.id.tv_descriptionrow);
//            ivDelete = itemView.findViewById(R.id.iv_delete);
//            ivDetail = itemView.findViewById(R.id.iv_update);
        }
    }
}
