/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ActivitySplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }
}