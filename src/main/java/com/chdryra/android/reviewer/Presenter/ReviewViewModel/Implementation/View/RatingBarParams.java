/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

/**
 * Created by: Rizwan Choudrey
 * On: 30/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarParams {
    private boolean mIsEditable;
    private boolean mIsVisible;

    public RatingBarParams() {
        mIsEditable = false;
        mIsVisible = true;
    }

    public RatingBarParams setEditable(boolean editable) {
        mIsEditable = editable;
        return this;
    }

    public RatingBarParams setVisible(boolean visible) {
        mIsVisible = visible;
        return this;
    }

    public boolean isEditable() {
        return mIsEditable;
    }

    public boolean isVisible() {
        return mIsVisible;
    }
}
