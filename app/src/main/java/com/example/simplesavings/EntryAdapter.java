package com.example.simplesavings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class EntryAdapter extends ArrayAdapter<Entry> {
    public EntryAdapter(Context context, List<Entry> entries) {
        super(context, 0, entries);
    }

    public void updateEntries(List<Entry> newEntries) {
        clear();
        addAll(newEntries);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Entry entry = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_item, parent, false);
        }
        TextView amount = convertView.findViewById(R.id.entryAmount);
        TextView note = convertView.findViewById(R.id.entryNote);
        amount.setText("$" + String.format("%.2f", entry.amount));
        note.setText(entry.note);
        return convertView;
    }
}