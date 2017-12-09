package com.project.hunthouse.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.hunthouse.R;

/**
 * Created by Gauri on 12/8/2017.
 */

public class MyCustomView extends AppCompatActivity {



    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);

        mCustomView = (CustomView) findViewById(R.id.customView);

        findViewById(R.id.btn_swap_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.swapColor();
            }
        });
    }

}
