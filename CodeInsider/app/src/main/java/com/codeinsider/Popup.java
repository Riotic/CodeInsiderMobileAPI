package com.codeinsider;

import android.app.Activity;
import android.app.Dialog;
import android.widget.CheckBox;

import org.json.JSONStringer;

public class Popup extends Dialog {
    private String title;
    private CheckBox check1,check2;


    public Popup(Activity activity){
        super(activity, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        setContentView(R.layout.filtre_popup);
        this.title = "Filtre";
        this.check1 = findViewById(R.id.checkBox);
        this.check2 = findViewById(R.id.checkBox2);

    }

    public void setTitle(String title){this.title = title; }

    public CheckBox getCheck1(){return check1;};
    public CheckBox getCheck2(){return check2;};

    public void build(){
        show();
    }
}
