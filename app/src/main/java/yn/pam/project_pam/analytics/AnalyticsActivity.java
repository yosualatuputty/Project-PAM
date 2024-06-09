package yn.pam.project_pam.analytics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import yn.pam.project_pam.R;

public class AnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentTransactionList fragmentTransactionList = new FragmentTransactionList();
        transaction.replace(R.id.fl, fragmentTransactionList);
        transaction.commit();

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (tab.getPosition() == 0) {
                    transaction.replace(R.id.fl, new FragmentTransactionList());
                } else if (tab.getPosition() == 1) {
                    transaction.replace(R.id.fl, new FragmentPieChart());
                } else if (tab.getPosition() == 2){
//                    transaction.replace(R.id.fl, new FragmentDataAdd());
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}