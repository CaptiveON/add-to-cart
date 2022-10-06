package com.example.cartit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductDetail extends AppCompatActivity {
    ImageView productImage;
    TextView productName, productPrice, productDescription;
    Button addToCart;
    int cartProductIndex;
    ArrayList<Product> cartList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        getSupportActionBar().setTitle("Product Details");

        //Receiving item's detail from MainActivity
        Intent intent = getIntent();
        Product productItem = intent.getParcelableExtra("ProductItem");

        productImage = findViewById(R.id.ProductImage);
        productDescription = findViewById(R.id.ProductDescription);
        productName = findViewById(R.id.ProductName);
        productPrice = findViewById(R.id.ProductPrice);

        //Binding Views with item's data
        productImage.setImageResource(productItem.productImage);
        productDescription.setText(productItem.productDescription);
        productName.setText(productItem.productName);
        productPrice.setText(String.valueOf(productItem.productPrice));

        cartProductIndex = intent.getIntExtra("ProductPosition",0);

        //Add to cart buttom OnClick implementation
        addToCart = findViewById(R.id.buyButton);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addToCartFun();
            }
        });
    }

    //If clicked then sending back the Position of that item to be added to the cartList in MainActivity
    public void addToCartFun()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("CartProductIndex",cartProductIndex);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

}
