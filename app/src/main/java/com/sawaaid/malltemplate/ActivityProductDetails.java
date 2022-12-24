package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityProductDetailsBinding;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityBasket;
import com.sawaaid.malltemplate.utils.Tools;

import java.util.Locale;


public class ActivityProductDetails extends AppCompatActivity {

    public static void navigate(Activity activity, Product product) {
        Intent i = new Intent(activity, ActivityProductDetails.class);
        i.putExtra("OBJ", product);
        activity.startActivity(i);
    }

    Product product;
    Locale locale = new Locale("TR", "TR");
    Request request;
    ActivityProductDetailsBinding binding;
    double price;

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

        if (product.priceAfterSale == 0) {
            price = product.dollarPrice;
        } else {
            price = product.priceAfterSale;
        }

        binding.elegantNumberButton.setOnValueChangeListener((view, oldValue, newValue) -> {
            Locale locale = new Locale("TR", "TR");
            if (product.priceAfterSale == 0) {
                price = product.dollarPrice;
                binding.productTotalPriceTextView.setText("₺ " + String.format(locale, "%.2f", newValue * product.dollarPrice));
            } else {
                price = product.priceAfterSale;
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

            if (DataApp.dao().checkProduct(product.id) > 0) {
                DataApp.dao().updateProductQuantity(product.id, Double.parseDouble(numCur[0]));
                Toast.makeText(ActivityProductDetails.this, "المنتج مضاف سابقاًً" + "\r\n" + "وتم تعديل كمية المنتج" + "\r\n" + "حسب الكمية الجديدة", Toast.LENGTH_LONG).show();
            } else {
                Log.d("TAGvcq", "initComponent: " + Double.parseDouble(numCur[0]));
                EntityBasket entityBasket = new EntityBasket();
                entityBasket.setProductId(product.id);
                entityBasket.setQuantity(Double.parseDouble(numCur[0]));
                entityBasket.setPrice(price);
                DataApp.dao().insertEntityBasket(entityBasket);
                Toast.makeText(ActivityProductDetails.this, "تم إضافة المنتج إلى السلة", Toast.LENGTH_LONG).show();
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
                binding.quantityButton.setText("₺ " + String.format(locale, "%.2f", Double.valueOf(product.priceAfterSale)));
                binding.quantityButton.setVisibility(View.VISIBLE);
                binding.elegantNumberButton.setVisibility(View.GONE);
                binding.quantityText.setText("1");
            }
        }
    }

}
