package com.employeedatabse.management;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

     SQLiteDatabase mDatabase;
    private Context mCtx;

    //we are storing all the products in a list
    private List<EmployeeDataModel> employeeList;

    //getting the context and product list with constructor
    public EmployeeAdapter(Context mCtx, List<EmployeeDataModel> employeeList, SQLiteDatabase mDatabase) {
        this.mCtx = mCtx;
         this.mDatabase = mDatabase;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.employee_list_item, null);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        //getting the product of the specified position
        final EmployeeDataModel product = employeeList.get(position);

        //binding the data with the viewholder views
        holder.textViewName.setText(product.getName());
        holder.textViewAddress.setText(product.getUsername());
        holder.textViewEmail.setText(product.getEmail());
        holder.textViewPhone.setText(product.getPhno());

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(product);
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM Employee WHERE id = ?";
                        mDatabase.execSQL(sql, new Integer[]{product.getId()});
                        Toast.makeText(mCtx, "Deleted successfully!", Toast.LENGTH_SHORT).show();

                          reloadEmployeesFromDatabase(); //Reload List
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
    void reloadEmployeesFromDatabase() {
        Cursor cursorproduct1 = mDatabase.rawQuery("SELECT * FROM Employee", null);
        employeeList.clear();
        if (cursorproduct1.moveToFirst()) {
            do {
                employeeList.add(new EmployeeDataModel(
                        cursorproduct1.getInt(0),
                        cursorproduct1.getString(1),
                        cursorproduct1.getString(2),
                        cursorproduct1.getString(3),
                        cursorproduct1.getString(4)   ));
            } while (cursorproduct1.moveToNext());
        }
        cursorproduct1.close();
        notifyDataSetChanged();
        if (employeeList.isEmpty()) {
            Toast.makeText(mCtx, "No items Found in database", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateEmployee(final EmployeeDataModel product) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(view);


        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editAddress = view.findViewById(R.id.editAddress);
        final EditText editemail = view.findViewById(R.id.editEmail);
        final EditText editphno = view.findViewById(R.id.editTextphno);



        editTextName.setText(product.getName());
        editAddress.setText(product.getUsername());
        editemail.setText(product.getEmail());
        editphno.setText(product.getPhno());

        final AlertDialog dialog = builder.create();
        dialog.show();

        // CREATE METHOD FOR EDIT THE FORM
        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String email = editemail.getText().toString().trim();
                String address = editAddress.getText().toString().trim();
                String phno = editphno.getText().toString().trim();



                if (name.isEmpty()) {
                    editTextName.setError("Name can't be blank");
                    editTextName.requestFocus();
                    return;
                }

                if (address.isEmpty()) {
                    editAddress.setError("Address can't be blank");
                    editAddress.requestFocus();
                    return;
                }//Name, Email, UserName, PhoneNo

                String sql = "UPDATE Employee \n" +
                        "SET Name = ?, \n" +
                        "Email = ?,\n"+
                        "Username = ?,\n"+
                        " PhoneNO= ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{name, email,address,phno, String.valueOf(product.getId())});
                Toast.makeText(mCtx, "Student Updated", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewAddress, textViewEmail, textViewPhone;
        ImageView editbtn, deletebtn;

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewUsername);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);

            deletebtn = itemView.findViewById(R.id.buttonDeleteStudent);
            editbtn = itemView.findViewById(R.id.buttonEditstudent);
        }
    }
}
