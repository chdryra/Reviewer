/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenModifier implements ReviewView.ViewModifier {
    private ReviewBuilder mBuilder;

    public BuildScreenModifier(ReviewBuilder builder) {
        mBuilder = builder;
    }

    @Override
    public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = parent.getActivity();
        parent.setBannerNotClickable();
        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
        Button shareButton = (Button) inflater.inflate(R.layout.review_banner_button, container,
                false);
        shareButton.setText(activity.getResources().getString(R.string.button_share));
        shareButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.getSubject().length() == 0) {
                    Toast.makeText(activity, R.string.toast_enter_subject,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mBuilder.getDataSize(GvDataList.GvType.TAGS) == 0) {
                    Toast.makeText(activity, R.string.toast_enter_tag,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                requestShareIntent(activity);
            }
        });

        parent.addView(shareButton);
        parent.addView(divider);

        return v;
    }

    private void requestShareIntent(Activity activity) {
        Intent i = new Intent(activity, ActivityReviewView.class);
        ActivityReviewView.packParameters(GvDataList.GvType.SHARE, false, i);
        activity.startActivity(i);
    }
}
