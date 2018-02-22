/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.widget.RatingBar;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarUi extends RatingUi<RatingBar> {
    public RatingBarUi(final RatingBar view, ReferenceValueGetter<Float> rating) {
        super(view, rating, new ViewValueGetter<Float>() {
            @Override
            public Float getValue() {
                return view.getRating();
            }
        },
        new ViewValueSetter<Float>() {
            @Override
            public void setValue(Float value) {
                view.setRating(value);
            }
        });
    }
}
