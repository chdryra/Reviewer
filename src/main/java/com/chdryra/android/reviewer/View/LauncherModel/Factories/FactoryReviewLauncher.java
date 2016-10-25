/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewLauncherImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewLauncher {
    private final FactoryReviewView mFactoryReviewView;
    private final ReviewsSource mMasterRepo;

    public FactoryReviewLauncher(FactoryReviewView factoryReviewView,
                                 ReviewsSource masterRepo) {
        mFactoryReviewView = factoryReviewView;
        mMasterRepo = masterRepo;
    }

    public ReviewLauncher newReviewLauncher(UiLauncher launcher, ReviewUiLauncher formattedLauncher) {
        return new ReviewLauncherImpl(mMasterRepo, launcher, formattedLauncher, mFactoryReviewView);
    }

}
