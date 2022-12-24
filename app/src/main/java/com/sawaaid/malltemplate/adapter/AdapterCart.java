package com.sawaaid.malltemplate.adapter;

import static com.sawaaid.malltemplate.utils.BottomMenuHelper.showBadge;
import static com.sawaaid.malltemplate.fragment.FragmentCart.BillPrice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityBasket;
import com.sawaaid.malltemplate.widget.ElegantNumberButton;

import java.util.List;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.MyViewHolder> {

    public List<Product> productList;
    public Context context;
    public Activity activity;
    RecyclerView recyclerView;
    AutofitTextView textView, noProductsInBasketTextView, fareTextView;
    BottomNavigationView bottomNavigationView;
    String Fare;
    String MinimumOrderPrice;
    LinearLayout parent;


    public AdapterCart(List<Product> productList, AutofitTextView textView,
                       RecyclerView recyclerView, BottomNavigationView bottomNavigationView,
                       AutofitTextView noProductsInBasketTextView, String Fare, String MinimumOrderPrice,
                       LinearLayout parent, AutofitTextView fareTextView, Activity activity) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.textView = textView;
        this.productList = productList;
        this.bottomNavigationView = bottomNavigationView;
        this.noProductsInBasketTextView = noProductsInBasketTextView;
        this.Fare = Fare;
        this.MinimumOrderPrice = MinimumOrderPrice;
        this.parent = parent;
        this.fareTextView = fareTextView;

    }


    @NonNull
    @Override
    public AdapterCart.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        context = parent.getContext();
        return new AdapterCart.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterCart.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        List<EntityBasket> productEntity = DataApp.dao().getEntityBasket();

        String productName = productList.get(position).getName();
        String productImage = productList.get(position).getPhoto();
        int mainId = productList.get(position).getId();
        double price = productList.get(position).getDollarPrice();
        String saleOption = productList.get(position).getPurchaseOption();
        double PriceAfterSale = productList.get(position).getPriceAfterSale();

        if (PriceAfterSale == 0) {
            holder.productPrice.setText("₺ " + price);
        } else {
            holder.productPrice.setText(PriceAfterSale + "");

        }

        holder.productName.setText(productName);


        if (productEntity.size() != 0) {
            if (saleOption.equals("piece")) {
                holder.productQuantity.setText("الكمية:" + " " + productEntity.get(position).getQuantity());
                holder.changeQuantityButton.setText("" + productEntity.get(position).getQuantity() * productEntity.get(position).getPrice());
            } else {
                holder.productQuantity.setText("الكمية:" + " " + productEntity.get(position).getQuantity() + " " + "كيلو");
                holder.changeQuantityButton.setText(String.valueOf(productEntity.get(position).getQuantity() * productEntity.get(position).getPrice()));
            }
        }

        try {
            String url_pic = productImage.trim();

            String Sthumb_url = url_pic.substring(0, url_pic.lastIndexOf("/") + 1) + "thumb/" + url_pic.substring(url_pic.lastIndexOf("/") + 1);
            String newurl = Sthumb_url;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                newurl = Sthumb_url.replace("https", "http");

            }
            Glide.with(context).load(newurl.trim()).error(url_pic)
                    .into(holder.productImage);

        } catch (Throwable s) {
            Log.d("TAG", "onBindViewHolder: " + s.getMessage());
        }


        holder.elegantNumberButton.setVisibility(View.INVISIBLE);
        holder.changeQuantityButton.setVisibility(View.VISIBLE);

        holder.productQuantity.setOnClickListener(view -> {
            if (!saleOption.equals("piece")) {
                View dialogView;
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    dialogView = inflater.inflate(R.layout.dialog_quantity_kilo, null);
                    builder.setView(dialogView);
                    EditText editText = dialogView.findViewById(R.id.QuantityTxt);
                    Button OkButton = dialogView.findViewById(R.id.OkBtn);
                    Button cancelBtn = dialogView.findViewById(R.id.cancel_action);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    cancelBtn.setOnClickListener(view13 -> alertDialog.hide());
                    OkButton.setOnClickListener(view14 -> {
                        String m_Text = editText.getText().toString();
                        try {
                            if (m_Text.trim().length() != 0) {

                                try {
                                    double quantity = Double.parseDouble(m_Text);
                                    productEntity.get(position).setQuantity(quantity);
                                    DataApp.dao().updateProductQuantity(productEntity.get(position).getProductId(), quantity);
                                    AdapterCart AdapterCart = new AdapterCart(productList, textView, recyclerView, bottomNavigationView, noProductsInBasketTextView, Fare, MinimumOrderPrice, parent, fareTextView, activity);
                                    recyclerView.setAdapter(AdapterCart);
                                    AdapterCart.notifyDataSetChanged();
                                    calculateBillPrice();
                                    alertDialog.hide();

                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }


                        } catch (Throwable throwable) {
                            Toast.makeText(context, "يرجى المحاولة مرة اخرى", Toast.LENGTH_LONG).show();

                        }


                        alertDialog.hide();

                    });

                } catch (Throwable e) {
                }
            }

        });
        holder.changeQuantityButton.setOnClickListener(view -> {
            View dialogView;
            try {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (saleOption.equals("piece")) {
                    dialogView = inflater.inflate(R.layout.dialogue_quantity, null);

                } else {
                    dialogView = inflater.inflate(R.layout.dialogue_quantity_kg, null);

                }
                builder.setView(dialogView);
                EditText editText = dialogView.findViewById(R.id.QuantityTxt);
                Button OkButton = dialogView.findViewById(R.id.OkBtn);
                Button cancelBtn = dialogView.findViewById(R.id.cancel_action);


                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                cancelBtn.setOnClickListener(view12 -> alertDialog.hide());
                OkButton.setOnClickListener(view1 -> {


                    String m_Text = editText.getText().toString();
                    try {
                        if (m_Text.trim().length() != 0) {

                            if (productList.get(position).getPurchaseOption().equals("piece")) {
                                productEntity.get(position).setQuantity(Double.parseDouble(m_Text));

                                DataApp.dao().updateProductQuantity(productEntity.get(position).getProductId(), Double.parseDouble(m_Text));
                                holder.changeQuantityButton.setText(m_Text);

                                AdapterCart AdapterCart = new AdapterCart(productList, textView, recyclerView, bottomNavigationView, noProductsInBasketTextView, Fare, MinimumOrderPrice, parent, fareTextView, activity);
                                recyclerView.setAdapter(AdapterCart);
                                AdapterCart.notifyDataSetChanged();

                                calculateBillPrice();
                            } else {
                                try {
                                    double allPrice = Double.valueOf(m_Text);

                                    productEntity.get(position).setQuantity(allPrice / productEntity.get(position).getPrice());
                                    DataApp.dao().updateProductQuantity(productEntity.get(position).getProductId(), allPrice / productEntity.get(position).getPrice());
                                    AdapterCart AdapterCart = new AdapterCart(productList, textView, recyclerView, bottomNavigationView, noProductsInBasketTextView, Fare, MinimumOrderPrice, parent, fareTextView, activity);
                                    recyclerView.setAdapter(AdapterCart);
                                    AdapterCart.notifyDataSetChanged();

                                    calculateBillPrice();
                                    alertDialog.hide();

                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    } catch (Throwable throwable) {
                        Toast.makeText(context, "يرجى المحاولة مرة اخرى", Toast.LENGTH_LONG).show();

                    }


                    alertDialog.hide();

                });

            } catch (Throwable e) {
            }
        });
        holder.elegantNumberButton.setOnValueChangeListener((elegantNumberButton, i, i1) -> {
            DataApp.dao().updateProductQuantity(mainId, (double) i1);

            Locale locale1 = new Locale("TR", "TR");
            holder.productPrice.setText("₺ " + String.format(locale1, "%.2f", i1 * price));

        });

        holder.buttonDelete.setOnClickListener(view -> showDialogDeleteProduct(position));


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        AutofitTextView productName, productPrice, productQuantity;
        ImageView productImage;
        Button buttonDelete;
        ElegantNumberButton elegantNumberButton;
        Button changeQuantityButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            buttonDelete = mView.findViewById(R.id.buttonDelete);
            elegantNumberButton = mView.findViewById(R.id.numberButton);
            productName = mView.findViewById(R.id.productNameTextView);
            productQuantity = mView.findViewById(R.id.quantityTextView);
            productPrice = mView.findViewById(R.id.productPriceTextView);
            productImage = mView.findViewById(R.id.productImage);
            changeQuantityButton = mView.findViewById(R.id.changeQuantityButton);
        }
    }

    private void deleteProductFromSqlite(int position) {
        List<EntityBasket> productEntity = DataApp.dao().getEntityBasket();
        DataApp.dao().deleteProductFromBasket(String.valueOf(productEntity.get(position).productId));
        productEntity.remove(position);
        productList.remove(position);
        if (productEntity.size() == 0) {
            noProductsInBasketTextView.setVisibility(View.VISIBLE);
            fareTextView.setVisibility(View.INVISIBLE);
        }
        calculateBillPrice();

        notifyItemRemoved(position);

        try {
            showBadge(context, bottomNavigationView, R.id.cart_menu);
        } catch (Throwable t) {
        }
        Toast.makeText(context, "تم حذف المنتج بنجاح", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    private void showDialogDeleteProduct(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_shutt_down, null);
        builder.setView(dialogView);
        TextView positive_button = dialogView.findViewById(R.id.positive_button);
        TextView negative_button = dialogView.findViewById(R.id.negative_button);
        TextView title_text_view = dialogView.findViewById(R.id.title_text_view);
        TextView message_text_view = dialogView.findViewById(R.id.message_text_view);
        ImageView img = dialogView.findViewById(R.id.img);

        img.setImageResource(R.drawable.ic_basket_bottom);
        positive_button.setTextColor(Color.parseColor("#F2641F"));
        negative_button.setTextColor(Color.parseColor("#232323"));

        title_text_view.setText("حذف منتج من السلة");
        message_text_view.setText("هل تريد بالتأكيد حذف هذا المنتج من السلة ؟");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        positive_button.setOnClickListener(view1 -> {
            deleteProductFromSqlite(position);
            alertDialog.dismiss();
        });

        negative_button.setOnClickListener(view -> alertDialog.dismiss());
    }

    public void calculateBillPrice() {
        List<EntityBasket> productsEntity = DataApp.dao().getEntityBasket();
        BillPrice = 0;
        for (int i = 0; i < productsEntity.size(); i++) {
            double a = productsEntity.get(i).getQuantity();
            double b = productsEntity.get(i).getPrice();
            BillPrice += a * b;
        }
        Locale locale = new Locale("TR", "TR");
        if (productsEntity.size() > 0) {
            textView.setText(("₺ " + String.format(locale, "%.2f", (BillPrice))));
        } else {
            textView.setText(("₺ " + "0"));

        }
        if (BillPrice > 0)
            checkDeliveryFare(Fare, MinimumOrderPrice);
    }


    private void checkDeliveryFare(String fare, String minimumOrderPrice) {
        if (BillPrice < Double.parseDouble(minimumOrderPrice)) {
            BillPrice = BillPrice + Double.parseDouble(fare);
            Locale locale = new Locale("TR", "TR");

            textView.setText(("₺ " + String.format(locale, "%.2f", (BillPrice))));

            fareTextView.setText("تم إضافة " + fare + " ليرة تركية على الطلب الأقل من " + minimumOrderPrice);
        } else {
            fareTextView.setText("");

        }
    }


}
