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

public class FragmentTransactionList extends Fragment {
    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private TransactionRepository transactionRepository;

    public FragmentTransactionList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        recyclerView = view.findViewById(R.id.rv_transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        transactionRepository = new TransactionRepository(getContext());

        LiveData<List<Transaction>> transactionLiveData = transactionRepository.getAllTransactions();

        transactionAdapter = new TransactionAdapter(getContext());

        recyclerView.setAdapter(transactionAdapter);

        transactionLiveData.observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                transactionAdapter.setData(transactions);
            }
        });
        return view;
    }
}