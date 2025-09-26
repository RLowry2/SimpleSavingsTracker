package com.example.simplesavings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ExpenseFragment extends Fragment {
    private DBHelper dbHelper;
    private ListView listView;
    private EntryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        dbHelper = new DBHelper(getContext());
        listView = view.findViewById(R.id.expenseList);
        adapter = new EntryAdapter(getContext(), dbHelper.getExpenseEntries());
        listView.setAdapter(adapter);

        EditText amountInput = view.findViewById(R.id.expenseAmount);
        EditText noteInput = view.findViewById(R.id.expenseNote);
        Button addBtn = view.findViewById(R.id.addExpenseBtn);

        addBtn.setOnClickListener(v -> {
            String amtStr = amountInput.getText().toString();
            String note = noteInput.getText().toString();
            if (!amtStr.isEmpty()) {
                double amt = Double.parseDouble(amtStr);
                dbHelper.insertEntry(amt, note, "expense");
                adapter.updateEntries(dbHelper.getExpenseEntries());
                amountInput.setText("");
                noteInput.setText("");
                ((MainActivity)getActivity()).updateBalance();
            }
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            Entry entry = adapter.getItem(position);
            new android.app.AlertDialog.Builder(getContext())
                    .setTitle("Delete Transaction")
                    .setMessage("Delete this transaction?")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        dbHelper.deleteEntry(entry.id);
                        adapter.updateEntries(dbHelper.getExpenseEntries());
                        ((MainActivity)getActivity()).updateBalance();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });

        return view;
    }
}