/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationLaunch;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.Screens.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.View.Screens.FeedScreenGridItem;
import com.chdryra.android.reviewer.View.Screens.FeedScreenMenu;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView
        implements DialogAlertFragment.DialogAlertListener{
    private FeedScreen mScreen;

    //Overridden
    @Override
    protected ReviewView createReviewView() {
        ApplicationContext appContext = ApplicationLaunch.initialiseLaunchState(this);

        Administrator admin = Administrator.getInstance(this);
        ReviewsRepository feed = admin.getReviewsRepository();
        FactoryReview reviewFactory = appContext.getReviewFactory();
        MdGvConverter converter = appContext.getMdGvConverter();
        BuilderChildListScreen childListFactory = appContext.getBuilderChildListScreen();
        FactoryReviewViewAdapter adapterFactory = appContext.getReviewViewAdapterFactory();
        FeedScreenMenu menuAction = new FeedScreenMenu();

        mScreen = new FeedScreen(new FeedScreenGridItem());
        return mScreen.createView(feed, PublishDate.now(), reviewFactory, converter,
                childListFactory, adapterFactory, menuAction);
    }

    //Overridden
    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mScreen.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mScreen.onAlertPositive(requestCode, args);
    }
}
