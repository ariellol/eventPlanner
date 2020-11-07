package com.LipStudio.planner;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GuestAdapter extends ArrayAdapter<Guest> implements View.OnClickListener{
    Context context;
    List<Guest> guests;
    GuestManager guestManager;

    ImageView popUpImageView;
    public GuestAdapter(Context context, int resource, int viewResourceId, List<Guest> guests,GuestManager guestManager) {
        super(context, resource, viewResourceId, guests);
        this.context = context;
        this.guests = guests;
        this.guestManager = guestManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_guest_layout, parent, false);

        TextView guestTextView = view.findViewById(R.id.name);
        TextView guestLastNameTextView = view.findViewById(R.id.lastName);
        TextView guestMoneyTextView = view.findViewById(R.id.money);
        popUpImageView = view.findViewById(R.id.popup);
        Guest guest = this.guests.get(position);
        guestTextView.setText(guest.getName());
        guestLastNameTextView.setText(guest.getLastName());
        guestMoneyTextView.setText(String.valueOf(guest.getMoney()));

        popUpImageView.setOnClickListener(this);
        guestManager.dialogSaveChanges.setOnClickListener(this);
        guestManager.dialogDeleteButton.setOnClickListener(this);
        guestManager.dialogCancelButton.setOnClickListener(this);
        guestManager.addGuest.setOnClickListener(this);
        popUpImageView.setTag(position);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveChanges:
                guestManager.saveChanges();
                break;
            case R.id.addGuest:
                guestManager.addGuest();
            case R.id.deleteButton:
                guestManager.delete();
                break;
            case R.id.cancelButton:
                guestManager.cancel();
                break;
            case R.id.popup:
                guestManager.openPopupMenu(v);
                break;
        }
    }
}

