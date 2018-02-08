/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation;



import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CreateBucketsView extends CreateView {
    public CreateBucketsView(ReviewViewAdapter<?> unexpanded, FactoryReviewView viewFactory) {
        super(viewFactory, unexpanded);
    }

    @Override
    protected ReviewView<?> createView(AdapterReviewNode<?> adapter) {
        return getViewFactory().newBucketsView(adapter.getNode());
    }
}
