package com.sawaaid.malltemplate.fragment;

import static android.os.Looper.getMainLooper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sawaaid.malltemplate.ActivityContactUs;
import com.sawaaid.malltemplate.ActivityMain;
import com.sawaaid.malltemplate.ActivityNotificationHistory;
import com.sawaaid.malltemplate.ActivityOrders;
import com.sawaaid.malltemplate.ActivityProfile;
import com.sawaaid.malltemplate.ActivitySawaaid;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.FragmentUserBinding;
import com.sawaaid.malltemplate.utils.Tools;


public class FragmentUser extends Fragment {

    FragmentUserBinding binding;
    View rootView;
    Context context;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    public FragmentUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();

        initComponent();

        return rootView;
    }

    private void initComponent() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.myOrdersLinearLayout.setOnClickListener(view -> startActivity(new Intent(context, ActivityOrders.class)));
        binding.myProfileLinearLayout.setOnClickListener(view -> {
            if (DataApp.global().isLogin())
                startActivity(new Intent(context, ActivityProfile.class));
            else
                Toast.makeText(context, "سجل دخولك أولاً", Toast.LENGTH_SHORT).show();
        });
        binding.notificationLinearLayout.setOnClickListener(view -> startActivity(new Intent(context, ActivityNotificationHistory.class)));
        binding.linearShareApp.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(null);
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
            new Handler(getMainLooper()).postDelayed(() -> Tools.methodShare(getActivity(), binding.progressBar, alertDialog), 1500);
        });
        binding.storeInfoLinearLayout.setOnClickListener(view -> startActivity(new Intent(context, ActivityContactUs.class)));
        binding.aboutAppLinearLayout.setOnClickListener(view -> startActivity(new Intent(context, ActivitySawaaid.class)));
        binding.logoutLinearLayout.setOnClickListener(view -> showDialogLogout());
    }

    private void showDialogLogout() {

        builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_shutt_down, null);
        builder.setView(dialogView);
        TextView positive_button = dialogView.findViewById(R.id.positive_button);
        TextView negative_button = dialogView.findViewById(R.id.negative_button);
        TextView title_text_view = dialogView.findViewById(R.id.title_text_view);
        TextView message_text_view = dialogView.findViewById(R.id.message_text_view);
        title_text_view.setText("تأكيد");
        message_text_view.setText("تأكيد تسجيل الخروج");
        positive_button.setText("تأكيد");
        negative_button.setText("الغاء");
        alertDialog = builder.create();

        alertDialog.show();

        positive_button.setOnClickListener(view1 -> {
            DataApp.global().logout();
            Intent intent = new Intent(context, ActivityMain.class);
            requireActivity().startActivity(intent);
            Toast.makeText(getContext(), "تم تسجيل الخروج بنجاح", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        });

        negative_button.setOnClickListener(view1 -> alertDialog.dismiss());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}