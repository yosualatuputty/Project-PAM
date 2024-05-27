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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import yn.pam.project_pam.R;
import yn.pam.project_pam.model.CategoryModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<CategoryModel> categoryArrayList;
    private TextView categoryTextView;
    private RelativeLayout categoryDropdown;
    LayoutInflater categoryLayoutInflater;

    public CategoryAdapter(@NonNull Context context, @NonNull ArrayList<CategoryModel> categoryArrayList, TextView categoryTextView, RelativeLayout categoryDropdown) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.categoryTextView = categoryTextView;
        this.categoryDropdown = categoryDropdown;
        categoryLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_view_adapter, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel category = categoryArrayList.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.iv_icon_category);
            categoryName = itemView.findViewById(R.id.tv_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        categoryTextView.setText(categoryArrayList.get(getAdapterPosition()).getName());
                        categoryDropdown.setVisibility(View.GONE);
                        Toast.makeText(context, "Item " + categoryArrayList.get(getAdapterPosition()).getName() + " diklik", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

        public void bind(CategoryModel category) {
            String imageUrl = category.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(categoryImage);
            } else {
                categoryImage.setImageResource(android.R.color.holo_red_dark);
            }
            categoryName.setText(category.getName());
        }


    }
}

