package com.LipStudio.planner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class GuestManager extends Fragment implements PopupMenu.OnMenuItemClickListener{

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
    Dialog addGuestDialog;
    EditText moneyTextField;
    EditText lastNameTextField;
    EditText nameTextField;
    private int position;
    private int totalMoney = 0;
    TextView totalMoneyTextView;
    TextView totalGuests;
    private Context context;
    Button addGuest;
    View currentView;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_manager,container,false);
        context = this.getContext();
        guestsList = new ArrayList<Guest>();
        guestsListView = view.findViewById(R.id.guestsList);
        guestsAdapter = new GuestAdapter(this.getContext(),0,0,guestsList,this);
        guestsListView.setAdapter(guestsAdapter);

        editDialog = new Dialog(context);
        editDialog.setContentView(R.layout.edit_dialog_layout);
        dialogSaveChanges = editDialog.findViewById(R.id.saveChanges);
        dialogEditName = editDialog.findViewById(R.id.changeName);
        dialogEditLastName = editDialog.findViewById(R.id.changeLastName);
        dialogEditMoney = editDialog.findViewById(R.id.changeMoney);

        deleteDialog = new Dialog(context);
        deleteDialog.setContentView(R.layout.are_u_sure);
        dialogDeleteButton = deleteDialog.findViewById(R.id.deleteButton);
        dialogCancelButton = deleteDialog.findViewById(R.id.cancelButton);

        addGuestDialog = new Dialog(context);
        addGuestDialog.setContentView(R.layout.add_guest);
        nameTextField = addGuestDialog.findViewById(R.id.guestName);
        lastNameTextField = addGuestDialog.findViewById(R.id.guestLastName);
        moneyTextField = addGuestDialog.findViewById(R.id.guestsMoney);
        addGuest = addGuestDialog.findViewById(R.id.addGuest);

        totalGuests = view.findViewById(R.id.totalGuests);
        totalMoneyTextView = view.findViewById(R.id.totalMoney);
        totalGuests.setText("Total Guests: " + guestsList.size());
        totalMoneyTextView.setText("Total Money " + totalMoney);
        return view;
    }


    public void openPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(context,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.items_options);
        popupMenu.show();
        currentView = view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.editOption)
            editGuest(currentView);
        if (item.getItemId() == R.id.deleteOption)
            deleteGuest(currentView);
        if(item.getItemId() == R.id.add) {
            Toast.makeText(context, "AASOF", Toast.LENGTH_SHORT).show();
            addGuestDialog.show();
        }
        return true;
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
    }

    public void editGuest(View view){
        editDialog.setTitle("Change Details");
        editDialog.show();
        editDialog.setCancelable(true);
        dialogEditName.setText(((Guest)guestsList.get((int)view.getTag())).getName());
        dialogEditLastName.setText(((Guest)guestsList.get((int)view.getTag())).getLastName());
        dialogEditMoney.setText(Integer.toString(((Guest)guestsList.get((int)view.getTag())).getMoney()));
        position = (int)view.getTag();
    }

    public void saveChanges(){
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

    private void deleteGuest(View view) {
        deleteDialog.show();
        deleteDialog.setCancelable(true);
        TextView areYouSure = deleteDialog.findViewById(R.id.areYouSure);
        areYouSure.setText("Are you sure you want to delete " + ((Guest)guestsList.get((int)view.getTag())).getName()
            + " " +((Guest)guestsList.get((int)view.getTag())).getLastName());
        position = (int) view.getTag();
    }

    protected void delete(){
        totalMoney -= ((Guest)guestsList.get(position)).getMoney();
        totalMoneyTextView.setText("Total Money: " + totalMoney);
        guestsList.remove(position);
        totalGuests.setText("Total Guests: " + guestsList.size());
        guestsAdapter.notifyDataSetChanged();
        deleteDialog.dismiss();
    }

    protected void cancel(){
        deleteDialog.dismiss();
    }

}
