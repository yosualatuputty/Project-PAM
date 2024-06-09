package yn.pam.project_pam;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeViewHolder extends RecyclerView.ViewHolder{

    public ImageView iv_logoList;
    public TextView tv_kategoriList, tv_deskripsiList, tv_nominalList, tv_sumberList;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_logoList = itemView.findViewById(R.id.iv_logoList);
        tv_kategoriList = itemView.findViewById(R.id.tv_kategoriList);
        tv_deskripsiList = itemView.findViewById(R.id.tv_deskripsiList);
        tv_nominalList = itemView.findViewById(R.id.tv_nominalList);
        tv_sumberList = itemView.findViewById(R.id.tv_sumberList);
    }
}