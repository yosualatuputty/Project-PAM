package yn.pam.project_pam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

public class TransactionFragment extends Fragment implements Adapter.EditClickListener {

    List<Transaksi> items = new ArrayList<Transaksi>();

    private AppDatabase database;

    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        database = AppDatabase.getInstance(getContext());
        items.clear();
        items.addAll(database.transaksiDao().getAll());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(), items, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onEditClicked(int transactionId) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, EditActivityFragment.newInstance(transactionId));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
