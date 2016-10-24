/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.RatingBar;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarUi extends ViewUi<RatingBar>{
    private final FloatUpdater mRating;

    public RatingBarUi(FloatUpdater rating, RatingBar view) {
        super(view);
        mRating = rating;
    }

    public float getRating() {
        return getView().getRating();
    }

    public void update() {
        setRating(mRating.getRating());
    }

    void setRating(float rating) {
        getView().setRating(rating);
    }

    public static interface FloatUpdater {
        float getRating();
    }
}
