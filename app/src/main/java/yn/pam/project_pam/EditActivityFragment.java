package yn.pam.project_pam;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import yn.pam.project_pam.database.AppDatabase;
import yn.pam.project_pam.database.entity.Transaksi;

public class EditActivityFragment extends Fragment {
    ImageView closeButton;
    Button continueButton;
    private AppDatabase database;

    String kategori, deskripsi, nominal, sumber;

    int id;

    public static EditActivityFragment newInstance(int transactionId) {
        EditActivityFragment fragment = new EditActivityFragment();
        Bundle args = new Bundle();
        args.putInt("Id", transactionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_layout, container, false);
        // Initialize views and handle editing
        database = AppDatabase.getInstance(getContext());
        if (getArguments() != null) {
            id = getArguments().getInt("Id", 0);
        }
        Transaksi transaksi = database.transaksiDao().get(id);

        closeButton = view.findViewById(R.id.closeButton_edit);
        continueButton = view.findViewById(R.id.bt_continueEdit);
//        String jsonItem = getIntent().getStringExtra("itemData");

        Spinner spinner_cat = view.findViewById(R.id.sp_category);
        Spinner spinner_wall = view.findViewById(R.id.sp_wallet);
        EditText amount = view.findViewById(R.id.et_amount);
        EditText desc = view.findViewById(R.id.et_desc);

        String[] category = {"Food", "Health", "Income", "Transport"};
        String[] wallet = {"Mandiri", "Cash"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, category );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, wallet );

        spinner_cat.setAdapter(adapter);
        spinner_wall.setAdapter(adapter2);
        amount.setText(transaksi.nominal);
        desc.setText(transaksi.deskripsi);

        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(transaksi.kategori)) {
                spinner_cat.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < adapter2.getCount(); i++) {
            if (adapter2.getItem(i).equals(transaksi.sumber)) {
                spinner_wall.setSelection(i);
                break;
            }
        }
//
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                database.transaksiDao().updateAll(spinner_cat.getSelectedItem().toString(), desc.getText().toString(),
                        amount.getText().toString(), spinner_wall.getSelectedItem().toString(), transaksi.tid );

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TransactionFragment())
                        .commit();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TransactionFragment())
                        .commit();
            }
        });


        return view;


    }
}
