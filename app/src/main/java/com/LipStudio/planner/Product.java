package com.LipStudio.planner;

import android.content.Context;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private Context context;
    public Product(String name, int price, int quantity, Context context) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", context=" + context +
                '}';
    }
}
