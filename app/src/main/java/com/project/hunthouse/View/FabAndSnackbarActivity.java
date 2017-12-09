package com.project.hunthouse.View;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.project.hunthouse.R;

/**
 * Created by Gauri on 12/8/2017.
 */

public class FabAndSnackbarActivity extends AppCompatActivity {
    private Button mShowSnackbarButton;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabandsnackbar);


    }
}
