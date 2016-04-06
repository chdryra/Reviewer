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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewModifier;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishButtonModifier implements ReviewViewModifier {
    private static final int PUBLISH_BUTTON = R.layout.review_banner_button;
    private static final int DIVIDER = R.layout.horizontal_divider;
    private static final int BUTTON_TEXT = R.string.button_publish;

    private PublishCommand mSharer;

    public PublishButtonModifier(PublishCommand sharer) {
        mSharer = sharer;
    }

    @Override
    public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = parent.getActivity();

        Button publishButton = (Button) inflater.inflate(PUBLISH_BUTTON, container, false);
        publishButton.setText(activity.getResources().getString(BUTTON_TEXT));
        publishButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        publishButton.setOnClickListener(buildAndShare(activity));

        View divider = inflater.inflate(DIVIDER, container, false);

        parent.addView(publishButton);
        parent.addView(divider);

        return v;
    }

    @NonNull
    private View.OnClickListener buildAndShare(final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationInstance app = ApplicationInstance.getInstance(activity);
                Review review = app.executeReviewBuilder();
                mSharer.publishReview(review);
            }
        };
    }
}
