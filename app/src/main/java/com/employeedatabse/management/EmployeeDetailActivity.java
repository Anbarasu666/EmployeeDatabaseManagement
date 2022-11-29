package com.employeedatabse.management;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase mDatabase;
    EmployeeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_detail_activity);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = openOrCreateDatabase(EmployeeActivity.DATABASE_NAME, MODE_PRIVATE, null);

        showEmployeesFromDatabase();
    }

    private void showEmployeesFromDatabase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorproduct = mDatabase.rawQuery("SELECT * FROM Employee", null);
        List<EmployeeDataModel> employeeList= new ArrayList<>();

        //if the cursor has some data
        if (cursorproduct.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                employeeList.add(new EmployeeDataModel(
                        cursorproduct.getInt(0),
                        cursorproduct.getString(1),
                        cursorproduct.getString(2),
                        cursorproduct.getString(3),
                        cursorproduct.getString(4)
                ));
            } while (cursorproduct.moveToNext());
        }
        if (employeeList.isEmpty()) {
            Toast.makeText(this, "No items Found in database", Toast.LENGTH_SHORT).show();
        }
        //closing the cursor
        cursorproduct.close();

        //creating the adapter object
        adapter = new EmployeeAdapter(this,employeeList, mDatabase);

        //adding the adapter to listview
        recyclerView.setAdapter(adapter);

        adapter.reloadEmployeesFromDatabase();
    }
}
