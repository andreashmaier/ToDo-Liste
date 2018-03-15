package com.example.mirko.todo_liste;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ListView lvMain;
    Button btnDelete;
    Button btnAdd;
    EditText etInput;

    List<ListEntry> lvMainData;
    ArrayAdapter<ListEntry> adapter;

    ListEntryDBHelper listEntryDBHelper;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvMain = (ListView) findViewById(R.id.lvMain);
        this.btnAdd = findViewById(R.id.btnAdd);
        this.btnDelete = findViewById(R.id.btnDelete);
        this.etInput = findViewById(R.id.etInput);

        this.lvMainData = new ArrayList<>();
        this.adapter = new ArrayAdapter<ListEntry>(this, R.layout.list_item, this.lvMainData);
        this.lvMain.setAdapter(this.adapter);
        this.lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        this.listEntryDBHelper = new ListEntryDBHelper(this);
        this.listEntryDBHelper.open();
        this.fillVisualComponents();
        this.initEvents();

    }

    private void fillVisualComponents() {
        this.adapter.addAll(listEntryDBHelper.getAllEntries());
        this.adapter.notifyDataSetChanged();
    }

    private void initEvents() {
        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText = etInput.getText().toString().trim();
                if (inputText != null && inputText.length() > 0) {
                    ListEntry listEntry = new ListEntry(inputText);
                    lvMainData.add(listEntry);
                    adapter.notifyDataSetChanged();
                    listEntryDBHelper.saveEntry(listEntry);
                    etInput.setText("");
                }

            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItemPositions = lvMain.getCheckedItemPositions();
                Log.d("MEINS", Integer.toString(checkedItemPositions.size()));
                for (int i = lvMainData.size() - 1; i >= 0; i--) {
                    if (checkedItemPositions.get(i)) {
                        ListEntry listEntry = lvMainData.get(i);
                        if (listEntryDBHelper.removeListEntry(listEntry)) {
                            lvMain.setItemChecked(i, false);
                            lvMainData.remove(i);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
