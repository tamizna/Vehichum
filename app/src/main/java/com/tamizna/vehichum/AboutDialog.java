package com.tamizna.vehichum;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AboutDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;

    public AboutDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.frame_about);
        yes = findViewById(R.id.ok_btn);
        yes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
