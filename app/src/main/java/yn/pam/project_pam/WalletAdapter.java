package yn.pam.project_pam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yn.pam.project_pam.R;
import yn.pam.project_pam.model.WalletModel;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {

    private Context context;
    private ArrayList<WalletModel> walletArrayList;
    private TextView walletTextView;
    private RelativeLayout walletDropdown;
    LayoutInflater walletLayoutInflater;

    public WalletAdapter(@NonNull Context context, @NonNull ArrayList<WalletModel> walletArrayList, TextView walletTextView, RelativeLayout walletDropdown) {
        this.context = context;
        this.walletArrayList = walletArrayList;
        this.walletTextView = walletTextView;
        this.walletDropdown = walletDropdown;
        walletLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_view_adapter, parent, false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        WalletModel wallet = walletArrayList.get(position);
        holder.bind(wallet);
    }

    @Override
    public int getItemCount() {
        return walletArrayList.size();
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder {

        private ImageView walletImage;
        private TextView walletName;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            walletImage = itemView.findViewById(R.id.iv_icon_category);
            walletName = itemView.findViewById(R.id.tv_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        walletTextView.setText(walletArrayList.get(getAdapterPosition()).getName());
                        walletDropdown.setVisibility(View.GONE);
                        Toast.makeText(context, "Item " + walletArrayList.get(getAdapterPosition()).getName() + " diklik", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

        public void bind(WalletModel wallet) {
            walletImage.setImageResource(wallet.getImage());
            walletName.setText(wallet.getName());
        }
    }
}
