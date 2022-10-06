package com.example.cartit;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerProductAdapter.RecyclerProductInterface {
    ArrayList<Product> product = new ArrayList<>();
    ArrayList<Product> cartProduct = new ArrayList<>();
    RecyclerProductAdapter productAdapter;
    RecyclerView recyclerView;
    ActivityResultLauncher<Intent> addCartActivityLauncher;
    ActivityResultLauncher<Intent> myCartActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Populates dummy product data in ArrayList "product" of type "Product"
        populateData();

        //Go to cart activity button
        Button cartButton = (Button) findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Initializes Cart Activity on "cartButton" button click
                myCart();
            }
        });

        //RecyclerView to list the "product" items
        recyclerView = findViewById(R.id.recycleItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Adapter to bind ArrayList "product" of type "Product" as Views in the recyclerView
        productAdapter = new RecyclerProductAdapter(this,product,this);
        recyclerView.setAdapter(productAdapter);

        //Activity Launcher for ProductDetailActivity which expects Position of Product
        //to add into ArrayList "cartProduct" of type "Product"
        productDetailActivityLauncher();
        //Activity Launcher for CartActivity which expects updated ArrayList "cartList" of type "Product"
        //if one or more items' count changed by the user
        cartProductActivityLauncher();
    }

    //Menu having an Item "search" as SearchView to listen searched query
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem search = menu.findItem(R.id.search);
        //Search View Implemented
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //ProductDetailActivity Launcher
    public void productDetailActivityLauncher()
    {
        addCartActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();
                            int cartIndex;
                            //Index of item which user want to add in the cart
                            int index = data.getIntExtra("CartProductIndex",0);
                            //If the selected item is already in the cartList
                            //then increment to amount of that item in the cartList
                            if(cartProduct.contains(product.get(index))){
                                cartIndex = cartProduct.indexOf(product.get(index));
                                cartProduct.get(cartIndex).addNumItem();
                            }
                            //Else add the item in the cartlist
                            else {
                                cartProduct.add(product.get(index));
                                cartIndex = cartProduct.indexOf(product.get(index));
                                cartProduct.get(cartIndex).addNumItem();
                            }
                        }
                    }
                }
        );
    }

    //CartActivityLauncher
    public void cartProductActivityLauncher()
    {
        myCartActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            //Expecting cartList with updated count of indivitual items in the cartList
                            //so on going again to the CartActivity, previously selected cart item are not lost
                            Intent data = result.getData();
                            if (data!=null) {
                                cartProduct = data.getParcelableArrayListExtra("UpdatedCartList");
                            }
                        }
                    }
                }
        );
    }

    //Populating dummy data
    public void populateData()
    {
        product.add(new Product("Nike Shoe","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",500,R.drawable.a));
        product.add(new Product("Summer Menswear","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",250,R.drawable.d));
        product.add(new Product("Hand Bag","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",750,R.drawable.c));
        product.add(new Product("Adidas Shoe","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",250,R.drawable.e));
        product.add(new Product("Summer Fashion","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",1500,R.drawable.f));
        product.add(new Product("Men's Jacket","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",800,R.drawable.g));
        product.add(new Product("Apple","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",10,R.drawable.h));
        product.add(new Product("Puma Shoe","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",350,R.drawable.b));
        product.add(new Product("Strawberry","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",25,R.drawable.i));
        product.add(new Product("Toy Car","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",15,R.drawable.j));
        product.add(new Product("Color Tie","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",80,R.drawable.k));
        product.add(new Product("Football","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",125,R.drawable.l));
        product.add(new Product("Laptop","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",2500,R.drawable.m));
        product.add(new Product("School Bag","The product is for some specific use and launched by a specific brand. It has many features regardless of it's price. It is an affordable product verified by the authentic retailers.",25,R.drawable.n));
    }

    //On cartButton click go to CartActivity with cartProduct list
    public void myCart()
    {
        //If there is atleast one item add in the cart then go to CartActivity
        if(!cartProduct.isEmpty())
        {
            Intent intent = new Intent(this, Cart.class);
            intent.putExtra("CartList",cartProduct);
            myCartActivityLauncher.launch(intent);
        }
        //Else go to EmptyCartActivity
        else
        {
            Intent intent = new Intent(this, EmptyCart.class);
            startActivity(intent);
        }
    }

    //Interface implemented as making RecyclerView Views clickable
    //launching go to ProductDetailActivity with relevant data
    @Override
    public void onProductClick(int position) {
        Intent intent = new Intent(this,ProductDetail.class);
        intent.putExtra("ProductItem",product.get(position));
        intent.putExtra("ProductPosition",position); //Passing Position of each product item but expecting back of chosen ones for the cart
        addCartActivityLauncher.launch(intent);
    }
}