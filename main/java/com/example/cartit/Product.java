package com.example.cartit;

import android.os.Parcel;
import android.os.Parcelable;

//Product class with implemented Parcelable for intenting between the activities
public class Product implements Parcelable {
    String productName;
    String productDescription;
    int productPrice;
    int productImage;
    int itemCount = 0;
    //Constructor
    public Product(String productName,String productDescription, int productPrice, int productImage){
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }
    //Constructor by Parcelable
    protected Product(Parcel in) {
        productName = in.readString();
        productDescription = in.readString();
        productPrice = in.readInt();
        productImage = in.readInt();
        itemCount = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getProductPrice()
    {
        return this.productPrice;
    }

    public String getProductName(){
        return this.productName;
    }

    public int addNumItem()
    {
        this.itemCount = this.itemCount + 1;
        return this.itemCount;
    }

    public int getNumItem()
    {
        return this.itemCount;
    }

    public int removeNumItem(){
        if(this.itemCount > 0)
        {
            this.itemCount = this.itemCount - 1;
        }
        return this.itemCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeInt(productPrice);
        dest.writeInt(productImage);
        dest.writeInt(itemCount);
    }
}