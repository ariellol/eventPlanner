package com.LipStudio.planner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class GuestManager extends Fragment implements View.OnClickListener {

    protected ArrayList guestsList;
    protected GuestAdapter guestsAdapter;
    protected ListView guestsListView;
    Dialog editDialog;
    EditText dialogEditName;
    EditText dialogEditMoney;
    EditText dialogEditLastName;
    Button dialogSaveChanges;
    Dialog deleteDialog;
    Button dialogCancelButton;
    Button dialogDeleteButton;
    EditText moneyTextField;
    EditText lastNameTextField;
    EditText nameTextField;
    int position;
    int totalMoney = 0;
    TextView totalMoneyTextView;
    TextView totalGuests;
    Context context;

    Button addGuest;
    ImageView editGuest;
    ImageView deleteGuest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_manager,container,false);
        context = this.getContext();
        guestsList = new ArrayList<Guest>();
        guestsListView = view.findViewById(R.id.guestsList);
        guestsAdapter = new GuestAdapter(this.getContext(),0,0,guestsList);
        guestsListView.setAdapter(guestsAdapter);
        editDialog = new Dialog(context);
        editDialog.setContentView(R.layout.edit_dialog_layout);
        deleteDialog = new Dialog(context);
        deleteDialog.setContentView(R.layout.are_u_sure);
        moneyTextField = view.findViewById(R.id.guestsMoney);
        lastNameTextField = view.findViewById(R.id.guestLastName);
        nameTextField = view.findViewById(R.id.guestName);
        totalGuests = view.findViewById(R.id.totalGuests);
        totalMoneyTextView = view.findViewById(R.id.totalMoney);
        totalGuests.setText("Total Guests: " + guestsList.size());
        totalMoneyTextView.setText("Total Money " + totalMoney);
        addGuest = view.findViewById(R.id.addGuest);
        addGuest.setOnClickListener(this);

        return view;
    }


    public void addGuest(){
        int money;
        String name;
        String lastName;
        name = nameTextField.getText().toString();
        lastName = lastNameTextField.getText().toString();
        if(name.equals("")) {
            Toast.makeText(context, "INVAILD INPUT", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            money = Integer.parseInt(moneyTextField.getText().toString());
        }
        catch(NumberFormatException nfe){
            money = 0;
        }
        guestsList.add(new Guest(name,lastName,money,context));
        guestsAdapter.notifyDataSetChanged();
        nameTextField.setText("");
        lastNameTextField.setText("");
        moneyTextField.setText("");
        totalMoney += money;
        totalMoneyTextView.setText("Total Money: " + totalMoney);
        totalGuests.setText("Total Guests " + guestsList.size());
        guestsAdapter.editImageView.setOnClickListener(this);
        guestsAdapter.deleteImageView.setOnClickListener(this);
    }

    public void editGuest(View view){
        editDialog.setTitle("Change Details");
        editDialog.show();
        editDialog.setCancelable(true);

        dialogEditMoney = editDialog.findViewById(R.id.changeMoney);
        dialogEditName = editDialog.findViewById(R.id.changeName);
        dialogSaveChanges = editDialog.findViewById(R.id.saveChanges);
        dialogEditLastName = editDialog.findViewById(R.id.changeLastName);

        dialogEditName.setText(((Guest)guestsList.get((int)view.getTag())).getName());
        dialogEditLastName.setText(((Guest)guestsList.get((int)view.getTag())).getLastName());
        dialogEditMoney.setText(Integer.toString(((Guest)guestsList.get((int)view.getTag())).getMoney()));
        position = (int)view.getTag();
    }

    public void saveChanges(View view){

        Log.d("totalMoney: " , ""+ totalMoney);
        totalMoney -= ((Guest) guestsList.get(position)).getMoney();
        Log.d("totalMoney: " , ""+ totalMoney);
        totalMoney += Integer.parseInt(dialogEditMoney.getText().toString());
        Log.d("totalMoney: " , ""+ totalMoney);
        totalMoneyTextView.setText("Total Money: " + totalMoney);

        ((Guest)guestsList.get(position)).setName(dialogEditName.getText().toString());
        ((Guest)guestsList.get(position)).setLastName(dialogEditLastName.getText().toString());
        ((Guest)guestsList.get(position)).setMoney(Integer.parseInt(dialogEditMoney.getText().toString()));
        if(dialogEditLastName.getText().toString().equals("") && dialogEditName.getText().toString().equals("") &&
            dialogEditMoney.getText().toString().equals("")){
            Toast.makeText(context, "INVAILD INPUT", Toast.LENGTH_LONG).show();
            return;
        }


        editDialog.cancel();
        guestsAdapter.notifyDataSetChanged();

    }

    public void deleteGuest(View view) {
        deleteDialog.show();
        deleteDialog.setCancelable(true);
        TextView areYouSure = deleteDialog.findViewById(R.id.areYouSure);
        areYouSure.setText("Are you sure you want to delete " + ((Guest)guestsList.get((int)view.getTag())).getName()
            + " " +((Guest)guestsList.get((int)view.getTag())).getLastName());
        dialogDeleteButton = deleteDialog.findViewById(R.id.deleteButton);
        dialogCancelButton = deleteDialog.findViewById(R.id.cancelButton);
        position = (int) view.getTag();
    }

    public void delete(View view){
        totalMoney -= ((Guest)guestsList.get(position)).getMoney();
        totalMoneyTextView.setText("Total Money: " + totalMoney);
        guestsList.remove(position);
        totalGuests.setText("Total Guests: " + guestsList.size());
        guestsAdapter.notifyDataSetChanged();
        deleteDialog.cancel();
    }

    public void cancel(View view){
        deleteDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if(v == null)
            return;
        switch (v.getId()){
            case R.id.addGuest:
                addGuest();
                return;
            case R.id.edit:
                editGuest(v);
                return;
            case R.id.delete:
                deleteGuest(v);
                return;
        }
    }
}
