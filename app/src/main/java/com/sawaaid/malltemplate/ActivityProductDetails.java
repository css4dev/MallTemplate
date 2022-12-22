package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityProductDetailsBinding;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityBasket;
import com.sawaaid.malltemplate.utils.Tools;
import com.sawaaid.malltemplate.widget.ElegantNumberButton;

import java.text.NumberFormat;
import java.util.Locale;


public class ActivityProductDetails extends AppCompatActivity {

    public static void navigate(Activity activity, Product product) {
        Intent i = new Intent(activity, ActivityProductDetails.class);
        i.putExtra("OBJ", product);
        activity.startActivity(i);
    }

    Product product;
    Button quantityButton;
    Locale locale = new Locale("TR", "TR");
    Request request;
    ActivityProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        product = (Product) getIntent().getSerializableExtra("OBJ");

        initComponent();
    }

    private void initComponent() {
        setVisibleComponents();

        request = new Request();

        Tools.displayImage(this, binding.image, product.photo);
        binding.backButton.setOnClickListener(view -> onBackPressed());

        binding.elegantNumberButton.setOnValueChangeListener((view, oldValue, newValue) -> {
            Locale locale = new Locale("TR", "TR");
            if (product.priceAfterSale == 0) {
                binding.productTotalPriceTextView.setText("₺ " + String.format(locale, "%.2f", newValue * product.dollarPrice));
            } else {
                binding.productTotalPriceTextView.setText("₺ " + String.format(locale, "%.2f", newValue * product.priceAfterSale));
            }
        });

        binding.addToBasket.setOnClickListener(view -> {
            String[] prodId = new String[1];
            prodId[0] = String.valueOf(product.id);
            String[] numCur = new String[1];

            try {
                if (product.purchaseOption.equals("piece") || product.purchaseOption.equals("")) {
                    numCur[0] = binding.elegantNumberButton.getNumber();
                } else {
                    numCur[0] = binding.quantityText.getText().toString().replace(",", ".");
                }
            } catch (Throwable e) {
                numCur[0] = binding.elegantNumberButton.getNumber();
            }
            try {
                EntityBasket entityBasket = new EntityBasket();
                entityBasket.setProductId(product.id);
                entityBasket.setQuantity(Double.parseDouble(numCur[0]));
                DataApp.dao().insertEntityBasket(entityBasket);
                Toast.makeText(ActivityProductDetails.this, "تم إضافة المنتج إلى السلة", Toast.LENGTH_LONG).show();

            } catch (Throwable e) {
                Toast.makeText(ActivityProductDetails.this, "الرجاء ادخال قيمة", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVisibleComponents() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.productDetailsTextView.setText(product.productDetails);
        binding.productNameTextView.setText(product.name);

        binding.elegantNumberButton.setVisibility(View.INVISIBLE);
        binding.elegantNumberButton.setRange(1, 20);

        binding.noPieseLinear.setVisibility(View.INVISIBLE);
        binding.noPieseLinear1.setVisibility(View.INVISIBLE);

        if (product.purchaseOption.trim().equals("piece")) {
            if (product.priceAfterSale == 0) {
                binding.elegantNumberButton.setVisibility(View.VISIBLE);
                binding.productTotalPriceTextView.setVisibility(View.VISIBLE);
                binding.productTotalPriceTextView.setText("₺ " + String.format(locale, "%.2f", Double.valueOf(product.dollarPrice)));
                binding.quantityButton.setVisibility(View.GONE);
                binding.noPieseLinear1.setVisibility(View.VISIBLE);
            } else {
                binding.elegantNumberButton.setVisibility(View.VISIBLE);
                binding.productTotalPriceTextView.setVisibility(View.VISIBLE);
                binding.productTotalPriceTextView.setText("₺ " + String.format(locale, "%.2f", Double.valueOf(product.priceAfterSale)));
                binding.quantityButton.setVisibility(View.GONE);
                binding.noPieseLinear1.setVisibility(View.VISIBLE);
            }
        } else {
            if (product.priceAfterSale == 0) {
                binding.noPieseLinear.setVisibility(View.VISIBLE);
                binding.noPieseLinear1.setVisibility(View.VISIBLE);
                binding.quantityButton.setText("₺ " + String.format(locale, "%.2f", Double.valueOf(product.dollarPrice)));
                binding.quantityButton.setVisibility(View.VISIBLE);
                binding.elegantNumberButton.setVisibility(View.GONE);
                binding.quantityText.setText("1");
            } else {
                binding.noPieseLinear.setVisibility(View.VISIBLE);
                binding.noPieseLinear1.setVisibility(View.VISIBLE);
                quantityButton.setText("₺ " + String.format(locale, "%.2f", Double.valueOf(product.priceAfterSale)));
                quantityButton.setVisibility(View.VISIBLE);
                binding.elegantNumberButton.setVisibility(View.GONE);
                binding.quantityText.setText("1");
            }
        }
    }

}
