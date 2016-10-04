/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext {
    private final ModelContext mModelContext;
    private final ViewContext mViewContext;
    private final SocialContext mSocialContext;
    private final NetworkContext mNetworkContext;
    private final PersistenceContext mPersistenceContext;

    private FactoryReviewView mFactoryReviewView;

    protected PresenterContextBasic(ModelContext modelContext,
                                    ViewContext viewContext,
                                    SocialContext socialContext,
                                    NetworkContext networkContext,
                                    PersistenceContext persistenceContext) {
        mModelContext = modelContext;
        mViewContext = viewContext;
        mSocialContext = socialContext;
        mPersistenceContext = persistenceContext;
        mNetworkContext = networkContext;
    }

    protected void setFactoryReviewView(FactoryReviewView factoryReviewView) {
        mFactoryReviewView = factoryReviewView;
    }

    @Override
    public ModelContext getModelContext() {
        return mModelContext;
    }

    @Override
    public ViewContext getViewContext() {
        return mViewContext;
    }

    @Override
    public SocialContext getSocialContext() {
        return mSocialContext;
    }

    @Override
    public NetworkContext getNetworkContext() {
        return mNetworkContext;
    }

    @Override
    public PersistenceContext getPersistenceContext() {
        return mPersistenceContext;
    }

    @Override
    public FactoryReviewView getReviewViewFactory() {
        return mFactoryReviewView;
    }
}
