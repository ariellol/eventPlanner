package com.LipStudio.planner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductManager extends Fragment {
    ArrayList productList;
    ProductAdapter productAdapter;
    ListView productsListView;

    int items= 0;
    int quantityItems= 0;
    int totalPrice = 0;
    int position;

    TextView totalItemsTextView;
    TextView totalPriceTextView;
    EditText nameEditText;
    EditText priceEditText;
    EditText quantityEditText;

    Dialog editDialog;
    EditText editNameDialog;
    EditText editPriceDialog;
    EditText editQuantityDialog;
    Button saveChangesDialog;

    Dialog deleteDialog;
    Button delete;
    Button cancel;
    TextView areYouSure;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = this.getContext();
        View view = inflater.inflate(R.layout.activity_product_manager,container,false);
        productList = new ArrayList<Product>();
        productAdapter = new ProductAdapter(context,0,0,productList);
        productsListView = view.findViewById(R.id.productListView);
        productsListView.setAdapter(productAdapter);

        totalItemsTextView = view.findViewById(R.id.totalItems);
        totalPriceTextView = view.findViewById(R.id.totalPrice);
        totalItemsTextView.setText("Total Items: " + items + " (" +quantityItems + ")");
        totalPriceTextView.setText("Total Price: " + totalPrice);

        nameEditText = view.findViewById(R.id.productNameEdit);
        priceEditText = view.findViewById(R.id.priceEdit);
        quantityEditText = view.findViewById(R.id.quantityEdit);

        editDialog = new Dialog(context);
        editDialog.setContentView(R.layout.edit_product);
        editDialog.setCancelable(true);

        deleteDialog = new Dialog(context);
        deleteDialog.setContentView(R.layout.delete_product_dialog);
        return view;
    }

    public void addProduct(View view){
        String name;
        int price = 0;
        int quantity = 1;
        EditText nameEditText = view.findViewById(R.id.productNameEdit);
        EditText priceEditText = view.findViewById(R.id.priceEdit);
        EditText quantityEditText = view.findViewById(R.id.quantityEdit);
        name = nameEditText.getText().toString();

        if(name.equals("")) {
            Toast.makeText(context, "INVAILD INPUT", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            price = Integer.parseInt(priceEditText.getText().toString());
        }
        catch (NumberFormatException nfe){
            price = 0;
        }
        try{
            quantity = Integer.parseInt(quantityEditText.getText().toString());
        }
        catch (NumberFormatException nfe){
            quantity = 1;
        }

        Product product = new Product(name,price,quantity,context);
        productList.add(product);
        items++;
        quantityItems += quantity;
        totalPrice += price*quantity;
        totalItemsTextView.setText("Total Items: " + items + " (" +quantityItems + ")");
        totalPriceTextView.setText("Total Price: " + totalPrice);
        nameEditText.setText("");
        priceEditText.setText("");
        quantityEditText.setText("");
        productAdapter.notifyDataSetChanged();
    }

    public void editProduct(View view){
        editDialog.show();

        editNameDialog = editDialog.findViewById(R.id.nameEditProduct);
        editPriceDialog = editDialog.findViewById(R.id.priceEditProduct);
        editQuantityDialog = editDialog.findViewById(R.id.quantityEditProduct);

        editNameDialog.setText(((Product) productList.get(((int)view.getTag()))).getName());
        editPriceDialog.setText(Integer.toString(((Product)productList.get((int)view.getTag())).getPrice()));
        editQuantityDialog.setText(Integer.toString(((Product)productList.get((int)view.getTag())).getQuantity()));
        position = (int) view.getTag();
    }

    public void saveProductChanges(View view){
        totalPrice -= ((Product) productList.get(position)).getPrice()*((Product) productList.get(position)).getQuantity();
        totalPrice += Integer.parseInt(editPriceDialog.getText().toString())* Integer.parseInt(editQuantityDialog.getText().toString());
        quantityItems -= ((Product) productList.get(position)).getQuantity();
        quantityItems += Integer.parseInt(editQuantityDialog.getText().toString());
        totalItemsTextView.setText("Total Items: " + items + " (" +quantityItems + ")");
        totalPriceTextView.setText("Total Price: " + totalPrice);

        ((Product)productList.get(position)).setName(editNameDialog.getText().toString());
        ((Product) productList.get(position)).setPrice(Integer.parseInt(editPriceDialog.getText().toString()));
        ((Product) productList.get(position)).setQuantity(Integer.parseInt(editQuantityDialog.getText().toString()));
        productAdapter.notifyDataSetChanged();

        if(editPriceDialog.getText().toString().equals("") && editNameDialog.getText().toString().equals("") &&
        editQuantityDialog.getText().toString().equals("")){
            Toast.makeText(context, "INVAILD INPUT", Toast.LENGTH_SHORT).show();
            return;
        }
        editDialog.cancel();
    }

    public void deleteProduct(View view){
        deleteDialog.show();
        deleteDialog.setCancelable(true);

        delete = deleteDialog.findViewById(R.id.deleteProduct);
        cancel = deleteDialog.findViewById(R.id.cancelDeleteProduct);
        areYouSure = deleteDialog.findViewById(R.id.youSureDeleteProduct);
        position = (int) view.getTag();
        areYouSure.setText("Are you sure you want to delete " + ((Product) productList.get(position)).getName());
    }

    public void delete(View view){
        totalPrice -= ((Product) productList.get(position)).getPrice();
        quantityItems -= ((Product) productList.get(position)).getQuantity();
        items--;
        productList.remove(position);
        productAdapter.notifyDataSetChanged();
        deleteDialog.cancel();
        totalItemsTextView.setText("Total Items: " + items + " (" +quantityItems + ")");
        totalPriceTextView.setText("Total Price: " + totalPrice);
    }

    public void cancel(View view){ deleteDialog.cancel(); }
}
