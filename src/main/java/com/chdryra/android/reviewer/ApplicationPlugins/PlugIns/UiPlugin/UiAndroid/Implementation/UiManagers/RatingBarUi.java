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
public class RatingBarUi extends ViewUi<RatingBar, Float>{
    public RatingBarUi(RatingBar view, ValueGetter<Float> rating) {
        super(view, rating);
    }

    public float getRating() {
        return getView().getRating();
    }

    @Override
    public void update() {
        setRating(getValue());
    }

    public void setRating(float rating) {
        getView().setRating(rating);
    }
}
