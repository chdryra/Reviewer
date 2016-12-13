/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarTouchable extends RatingBarUi {
    public RatingBarTouchable(RatingBar view, final ReviewNode node, @Nullable final Command onTouch) {
        super(view, new ValueGetter<Float>() {
            @Override
            public Float getValue() {
                return node.getRating().getRating();
            }
        });
        if(onTouch != null) {
            getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    onTouch.execute();
                    return true;
                }
            });
        }
    }
}
