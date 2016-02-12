/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryShareScreenView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView {
    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);

        String title = getResources().getString(R.string.button_social);
        SocialPlatformList socialPlatforms = app.getSocialPlatformList();

        ReviewBuilderAdapter reviewInProgress = app.getReviewBuilderAdapter();
        if(reviewInProgress == null) throw new RuntimeException("Builder is null!");

        FactoryShareScreenView factory = new FactoryShareScreenView();

        return factory.buildView(title, socialPlatforms, reviewInProgress, ActivityFeed.class);
    }
}
