/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Actions;

import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewViewAction<T extends GvData> extends OptionSelectListener{
    void onAttachReviewView();

    void onDetachReviewView();

    void attachReviewView(ReviewView<T> reviewView);

    void detachReviewView();

    ReviewViewAdapter<T> getAdapter();

    ReviewView<T> getReviewView();

    CurrentScreen getCurrentScreen();
}
