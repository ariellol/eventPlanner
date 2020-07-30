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
    ImageView editImageView;
    ImageView deleteImageView;
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
        editImageView = view.findViewById(R.id.edit);
        deleteImageView = view.findViewById(R.id.delete);
        popUpImageView = view.findViewById(R.id.popUp);
        Guest guest = this.guests.get(position);
        guestTextView.setText(guest.getName());
        guestLastNameTextView.setText(guest.getLastName());
        guestMoneyTextView.setText(String.valueOf(guest.getMoney()));

        editImageView.setImageResource(R.drawable.edit);
        deleteImageView.setImageResource(R.drawable.trash);
        editImageView.setOnClickListener(this);
        deleteImageView.setOnClickListener(this);
        popUpImageView.setOnClickListener(this);
        guestManager.dialogSaveChanges.setOnClickListener(this);
        guestManager.dialogDeleteButton.setOnClickListener(this);
        guestManager.dialogCancelButton.setOnClickListener(this);
        editImageView.setTag(position);
        deleteImageView.setTag(position);
        popUpImageView.setTag(position);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                guestManager.editGuest(v);
                break;
            case R.id.delete:
                guestManager.deleteGuest(v);
                break;
            case R.id.saveChanges:
                guestManager.saveChanges();
                break;
            case R.id.deleteButton:
                guestManager.delete();
            case R.id.cancelButton:
                guestManager.cancel();
            case R.id.popUp:
                guestManager.openPopupMenu(v);
        }
    }
}

