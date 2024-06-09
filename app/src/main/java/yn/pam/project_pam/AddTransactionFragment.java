//package yn.pam.project_pam;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;
//
//import java.util.ArrayList;
//
//public class AddTransactionFragment extends Fragment {
//
//    private Button continueButton;
//    private EditText amountEditText;
//    private EditText descriptionEditText;
//    private ArrayList<CategoryModel> categoryList;
//    private ArrayList<WalletModel> walletList;
//    private RecyclerView categoryRecycleView;
//    private RecyclerView walletRecycleView;
//    private CategoryAdapter categoryAdapter;
//    private WalletAdapter walletAdapter;
//    private TextView categoryTextView;
//    private RelativeLayout categoryDropdown;
//    private TextView walletTextView;
//    private RelativeLayout walletDropdown;
//    private ImageView closeIcon;
//    private TransactionDatabase transactionDatabase;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
//
//        transactionDatabase = Room.databaseBuilder(requireContext(),
//                TransactionDatabase.class, "transaction-db").build();
//
//        // Initialize views
//        categoryTextView = rootView.findViewById(R.id.tv_category_dropdown);
//        categoryDropdown = rootView.findViewById(R.id.category_dropdown_container);
//        categoryRecycleView = rootView.findViewById(R.id.rv_category);
//        walletTextView = rootView.findViewById(R.id.tv_wallet_dropdown);
//        walletDropdown = rootView.findViewById(R.id.wallet_dropdown_container);
//        walletRecycleView = rootView.findViewById(R.id.rv_wallet);
//        continueButton = rootView.findViewById(R.id.bt_continue);
//        amountEditText = rootView.findViewById(R.id.et_amount);
//        descriptionEditText = rootView.findViewById(R.id.et_description);
//        closeIcon = rootView.findViewById(R.id.iv_close);
//
//        // Setup category adapter and click listener
//        categoryAdapter = new CategoryAdapter(requireContext(), categoryList, categoryTextView, categoryDropdown);
//        categoryRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        categoryRecycleView.setAdapter(categoryAdapter);
//
//        categoryTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Load categories asynchronously
//                new CategoryLoader() {
//                    @Override
//                    protected void onPostExecute(ArrayList<CategoryModel> result) {
//                        super.onPostExecute(result);
//                        categoryList = result;
//                        categoryAdapter = new CategoryAdapter(requireContext(), categoryList, categoryTextView, categoryDropdown);
//                        categoryRecycleView.setAdapter(categoryAdapter);
//                        categoryDropdown.setVisibility(View.VISIBLE);
//                    }
//                }.execute();
//            }
//        });
//
//        // Setup wallet adapter and click listener
//        walletAdapter = new WalletAdapter(requireContext(), walletList, walletTextView, walletDropdown);
//        walletRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        walletRecycleView.setAdapter(walletAdapter);
//
//        walletTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                walletDropdown.setVisibility(View.VISIBLE);
//            }
//        });
//
//        // Close icon click listener
//        closeIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Close current fragment
//                requireActivity().getSupportFragmentManager().popBackStack();
//            }
//        });
//
//        // Continue button click listener
//        continueButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String category = categoryTextView.getText().toString().trim();
//                int logo = R.drawable.ic_food; // Default logo
//                // Determine logo based on category
//                switch (category) {
//                    case "Pet":
//                        logo = R.drawable.ic_pet;
//                        break;
//                    case "Social":
//                        logo = R.drawable.ic_social;
//                        break;
//                    case "Communication":
//                        logo = R.drawable.ic_communication;
//                        break;
//                    case "Health":
//                        logo = R.drawable.ic_health;
//                        break;
//                    case "Daily":
//                        logo = R.drawable.ic_daily;
//                        break;
//                    case "Education":
//                        logo = R.drawable.ic_education;
//                        break;
//                    case "Gift":
//                        logo = R.drawable.ic_gift;
//                        break;
//                    case "Fashion":
//                        logo = R.drawable.ic_fashion;
//                        break;
//                    case "Transport":
//                        logo = R.drawable.ic_transport;
//                        break;
//                    case "Beauty":
//                        logo = R.drawable.ic_beauty;
//                        break;
//                }
//                String wallet = walletTextView.getText().toString().trim();
//                String amount = amountEditText.getText().toString().trim();
//                String description = descriptionEditText.getText().toString().trim();
//
//                Toast.makeText(requireContext(), "Button Continue clicked", Toast.LENGTH_SHORT).show();
//
//                int finalLogo = logo;
//                // Save transaction asynchronously
//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        Log.d("AddTransaction", "doInBackground: Saving transaction to database");
//                        Transaction transaction = new Transaction(category, wallet, amount, description, finalLogo);
//                        transactionDatabase.transactionDao().insert(transaction);
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        super.onPostExecute(aVoid);
//                        Log.d("AddTransaction", "onPostExecute: Transaction saved");
//                        // Close current fragment
//                        requireActivity().getSupportFragmentManager().popBackStack();
//                    }
//                }.execute();
//            }
//        });
//
//        return rootView;
//    }
//}
