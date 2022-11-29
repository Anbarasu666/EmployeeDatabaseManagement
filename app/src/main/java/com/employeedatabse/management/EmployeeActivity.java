package com.employeedatabse.management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmployeeActivity extends AppCompatActivity {
    EditText e_name, e_email, e_address, e_phnumber;
    Button bt_save,viewdata;
    public static final String DATABASE_NAME = "EmployeeDatabases.db";
    SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_activity);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createEmployeeTable();


        //FindById (Button and Edittxt)
        e_name = (EditText) findViewById(R.id.e_name);
        e_email = (EditText) findViewById(R.id.e_email);
        e_address = (EditText) findViewById(R.id.e_address);
        e_phnumber = (EditText) findViewById(R.id.e_phnumber);

        bt_save = (Button) findViewById(R.id.btn_save);




        viewdata = (Button) findViewById(R.id.viewdata);
        viewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(EmployeeActivity.this, EmployeeDetailActivity.class);
                startActivity(intent);
            }
        });

        //Onclick Btn
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the Enter data
                String name = e_name.getText().toString().trim();
                String email = e_email.getText().toString().trim();
                String address = e_address.getText().toString();
                String phone = e_phnumber.getText().toString();


                if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {

                    Toast.makeText(EmployeeActivity.this, "Fil the form", Toast.LENGTH_SHORT).show();

                } else {

                    String insertSQL = "INSERT INTO Employee \n" +
                            "(Name, Email, Address, PhoneNo)\n" +
                            "VALUES \n" +
                            "(?, ?, ?, ?);";

                    //using the same method execsql for inserting values
                    //this time it has two parameters
                    //first is the sql string and second is the parameters that is to be binded with the query
                    mDatabase.execSQL(insertSQL, new String[]{name, email, address, phone});

                    Toast.makeText(EmployeeActivity.this, "Great! Data Saved", Toast.LENGTH_SHORT).show();

                    e_name.setText("");
                    e_email.setText("");
                    e_address.setText("");
                    e_phnumber.setText("");
                }


            }
        });


    }

    private void createEmployeeTable() {
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS Employee " +
                "(\n" +
                "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    Name varchar(200) NOT NULL,\n" +
                "    Email varchar(200) NOT NULL,\n" +
                "    Address varchar(200) NOT NULL,\n" +
                "    PhoneNo Varchar(200) NOT NULL\n" +
                ");"

        );
    }
}
