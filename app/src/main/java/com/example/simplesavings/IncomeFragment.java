package com.example.simplesavings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;

public class IncomeFragment extends Fragment {
    private DBHelper dbHelper;
    private ListView listView;
    private EntryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        dbHelper = new DBHelper(getContext());
        listView = view.findViewById(R.id.incomeList);
        adapter = new EntryAdapter(getContext(), dbHelper.getIncomeEntries());
        listView.setAdapter(adapter);

        EditText amountInput = view.findViewById(R.id.incomeAmount);
        EditText noteInput = view.findViewById(R.id.incomeNote);
        Button addBtn = view.findViewById(R.id.addIncomeBtn);

        addBtn.setOnClickListener(v -> {
            String amtStr = amountInput.getText().toString();
            String note = noteInput.getText().toString();
            if (!amtStr.isEmpty()) {
                double amt = Double.parseDouble(amtStr);
                dbHelper.insertEntry(amt, note, "income");
                adapter.updateEntries(dbHelper.getIncomeEntries());
                amountInput.setText("");
                noteInput.setText("");
                ((MainActivity)getActivity()).updateBalance();
            }
        });

        return view;
    }
}