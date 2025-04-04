package com.sawaaid.malltemplate.model;

import android.widget.RelativeLayout;


import androidx.annotation.Keep;

import java.io.Serializable;

import me.grantland.widget.AutofitTextView;
@Keep
public class ViewArrayModel implements Serializable {
    AutofitTextView subSectionName;
    RelativeLayout lytParent;

    public ViewArrayModel() {
    }

    public AutofitTextView getSubSectionName() {
        return subSectionName;
    }

    public void setSubSectionName(AutofitTextView subSectionName) {
        this.subSectionName = subSectionName;
    }

    public RelativeLayout getLytParent() {
        return lytParent;
    }

    public void setLytParent(RelativeLayout lytParent) {
        this.lytParent = lytParent;
    }
}
