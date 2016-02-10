/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewModifier;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
//TODO remove android activity and Intent dependency
public class PublishButtonModifier implements ReviewViewModifier {

    public static final int PUBLISH_BUTTON = R.layout.review_banner_button;
    private Class<? extends Activity> mFeedActivity;

    public PublishButtonModifier(Class<? extends Activity> feedActivity) {
        mFeedActivity = feedActivity;
    }

    @Override
    public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = parent.getActivity();

        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);

        Button publishButton = (Button) inflater.inflate(PUBLISH_BUTTON, container, false);
        publishButton.setText(activity.getResources().getString(R.string.button_publish));
        publishButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        publishButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                ApplicationInstance.getInstance(activity).publishReviewBuilder();
                Intent intent = new Intent(activity, mFeedActivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.startActivity(intent);
            }
        });

        parent.addView(publishButton);
        parent.addView(divider);

        return v;
    }
}
