package org.mobilett.version1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class About extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }
}
