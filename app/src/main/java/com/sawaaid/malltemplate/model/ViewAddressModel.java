package com.sawaaid.malltemplate.model;

import android.widget.Button;

import androidx.cardview.widget.CardView;

import java.io.Serializable;

import me.grantland.widget.AutofitTextView;

public class ViewAddressModel implements Serializable {
    Button deleteButton;
    AutofitTextView locationName, locationString;
    CardView cardView;

    public ViewAddressModel() {
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public AutofitTextView getLocationName() {
        return locationName;
    }

    public void setLocationName(AutofitTextView locationName) {
        this.locationName = locationName;
    }

    public AutofitTextView getLocationString() {
        return locationString;
    }

    public void setLocationString(AutofitTextView locationString) {
        this.locationString = locationString;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
