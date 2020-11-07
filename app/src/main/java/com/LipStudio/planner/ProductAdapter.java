package com.LipStudio.planner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product>{
    Context context;
    List<Product> products;

     public ProductAdapter(Context context, int resource, int resId, List<Product> products){
         super(context,resource,resId,products);
         this.context = context;
         this.products = products;
     }

     public View getView(int position, View convertView, ViewGroup parent){
         LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
         View view = layoutInflater.inflate(R.layout.product_layout,parent,false);

         TextView productName = view.findViewById(R.id.productName);
         TextView price = view.findViewById(R.id.price);
         TextView quantity = view.findViewById(R.id.quantity);


         Product product = this.products.get(position);
         productName.setText(product.getName());
         price.setText(String.valueOf(product.getPrice()));
         quantity.setText(String.valueOf(product.getQuantity()));

         return view;
     }



}
