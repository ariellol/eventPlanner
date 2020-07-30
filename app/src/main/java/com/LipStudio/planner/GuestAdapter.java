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

public class GuestAdapter extends ArrayAdapter<Guest>{
    Context context;
    List<Guest> guests;
    GuestManager guestManager;
    ImageView editImageView;
    ImageView deleteImageView;
    public GuestAdapter(Context context, int resource, int viewResourceId, List<Guest> guests) {
        super(context, resource, viewResourceId, guests);
        this.context = context;
        this.guests = guests;
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
        Guest guest = this.guests.get(position);
        guestTextView.setText(guest.getName());
        guestLastNameTextView.setText(guest.getLastName());
        guestMoneyTextView.setText(String.valueOf(guest.getMoney()));
        editImageView.setImageResource(R.drawable.edit);
        deleteImageView.setImageResource(R.drawable.trash);
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestManager.editGuest(v);
            }
        });
        editImageView.setTag(position);
        deleteImageView.setTag(position);

        return view;
    }

}

