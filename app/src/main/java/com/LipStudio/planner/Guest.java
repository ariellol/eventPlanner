package com.LipStudio.planner;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.util.ValueIterator;
import android.media.Image;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Guest{
    private String name;
    private String lastName;
    private int money;
    private ImageView edit;
    private ImageView delete;
    private Context context;
    public Guest(String name,String lastName, int money,Context context){
        this.name = name;
        this.lastName = lastName;
        this.money = money;
        this.context = context;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
     public void setEdit(ImageView edit){
        this.edit = edit;
     }

     public ImageView getEdit(){
        return this.edit;
     }

     public void setDelete(ImageView delete){
        this.delete = delete;
     }

     public ImageView getDelete(){
        return this.delete;
     }

}
