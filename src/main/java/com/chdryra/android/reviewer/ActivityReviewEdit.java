/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityReviewEdit extends Activity {
    public static final  int    FRAGMENT_ID = com.chdryra.android.mygenerallibrary.R.id
            .fragmentContainer;
    private static final String TYPE        = "com.chdryra.android.review" +
            ".activity_review_view_type";

    public static void packParameters(GvDataList.GvType dataType, Intent i) {
        i.putExtra(TYPE, dataType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.chdryra.android.mygenerallibrary.R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(FRAGMENT_ID);

        GvDataList.GvType dataType = (GvDataList.GvType) getIntent().getSerializableExtra(TYPE);
        if (fragment == null) {
            fragment = FactoryFragmentReviewView.newEditor(dataType);
            fm.beginTransaction().add(FRAGMENT_ID, fragment).commit();
        }
    }
}
