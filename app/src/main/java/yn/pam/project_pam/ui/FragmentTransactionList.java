package yn.pam.project_pam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import yn.pam.project_pam.R;
import yn.pam.project_pam.db.Transaction;
import yn.pam.project_pam.repository.TransactionRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTransactionList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTransactionList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private TransactionRepository transactionRepository;

    public FragmentTransactionList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTransactionList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTransactionList newInstance(String param1, String param2) {
        FragmentTransactionList fragment = new FragmentTransactionList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        recyclerView = view.findViewById(R.id.rv_transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi TransactionRepository
        transactionRepository = new TransactionRepository(getContext());

        // Ambil LiveData<List<Transaction>> dari TransactionRepository
        LiveData<List<Transaction>> transactionLiveData = transactionRepository.getAllTransactions();

        // Buat TransactionAdapter
        transactionAdapter = new TransactionAdapter(getContext());

        // Set adapter untuk RecyclerView
        recyclerView.setAdapter(transactionAdapter);

        // Observe LiveData dan perbarui adapter saat ada perubahan data
        transactionLiveData.observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                transactionAdapter.setData(transactions);
            }
        });

        return view;
    }

}