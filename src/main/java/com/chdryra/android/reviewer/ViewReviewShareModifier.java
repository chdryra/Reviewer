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

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewReviewShareModifier implements ViewReview.ViewModifier {

    @Override
    public View modify(final FragmentViewReview parent, View v, LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = parent.getActivity();
        parent.setBannerNotClickable();
        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
        Button publishButton = (Button) inflater.inflate(R.layout.review_banner_button,
                container, false);
        publishButton.setText(activity.getResources().getString(R.string.button_publish));
        publishButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Administrator.get(activity).publishReviewBuilder();
                Intent intent = new Intent(activity, ActivityFeed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.startActivity(intent);
            }
        });

        parent.addView(publishButton);
        parent.addView(divider);

        return v;
    }
}
