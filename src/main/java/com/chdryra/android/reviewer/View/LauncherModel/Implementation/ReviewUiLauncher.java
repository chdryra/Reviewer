/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUiLauncher {
    public static final String REVIEW_ID = "ReviewId";

    private final LaunchableConfig mUi;
    private final RepositorySuite mRepository;
    private final ItemPacker<Review> mPacker;

    public ReviewUiLauncher(LaunchableConfig ui,
                            RepositorySuite repository,
                            ItemPacker<Review> packer) {
        mUi = ui;
        mRepository = repository;
        mPacker = packer;
    }

    public void setUiLauncher(UiLauncher uiLauncher) {
        mUi.setLauncher(uiLauncher);
    }

    public void launch(@Nullable ReviewId reviewId) {
        onPrelaunch();
        if (reviewId == null) {
            launchUi(new Bundle());
        } else {
            mRepository.getReview(reviewId, new RepositoryCallback() {
                @Override
                public void onRepositoryCallback(RepositoryResult result) {
                    launchUi(toBundle(result));
                }
            });
        }
    }

    void onPrelaunch() {

    }

    @Nullable
    Review unpackReview(Bundle args) {
        return mPacker.unpack(args);
    }

    @NonNull
    private Bundle toBundle(RepositoryResult result) {
        Bundle args = new Bundle();
        Review review = result.getReview();
        if (!result.isError() && review != null) mPacker.pack(review, args);
        return args;
    }

    private void launchUi(Bundle args) {
        mUi.launch(new UiLauncherArgs(mUi.getDefaultRequestCode()).setBundle(args));
    }
}
