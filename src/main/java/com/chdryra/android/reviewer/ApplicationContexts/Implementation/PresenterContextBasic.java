/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext {
    private FactoryReviewView mFactoryReviewView;

    protected PresenterContextBasic() {
    }

    protected void setFactoryReviewView(FactoryReviewView factoryReviewView) {
        mFactoryReviewView = factoryReviewView;
    }

    @Override
    public FactoryReviewView getReviewViewFactory() {
        return mFactoryReviewView;
    }
}
