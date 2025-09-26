package com.example.simplesavings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView balanceView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceView = findViewById(R.id.balanceView);
        dbHelper = new DBHelper(this);

        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        updateBalance();
    }

    public void updateBalance() {
        double income = dbHelper.getTotalIncome();
        double expense = dbHelper.getTotalExpense();
        double balance = income - expense;
        balanceView.setText("Net Balance: $" + String.format("%.2f", balance));
    }
}