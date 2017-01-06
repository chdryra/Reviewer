/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EditUiLauncher extends PackingLauncherImpl<Review> {
    private final ReviewEditorSuite mBuilder;
    private final RepositorySuite mRepo;

    public EditUiLauncher(LaunchableConfig ui, ReviewEditorSuite builder, RepositorySuite repo) {
        super(ui);
        mBuilder = builder;
        mRepo = repo;
    }

    public void launch(@Nullable ReviewId template) {
        if (template == null) {
            super.launch(null);
        } else {
            fetchAndLaunch(template);
        }
    }

    private void fetchAndLaunch(ReviewId template) {
        mRepo.getReview(template, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                EditUiLauncher.super.launch(result.getReview());
            }
        });
    }

    @Override
    protected void onPrelaunch() {
        mBuilder.discardEditor(false, null);
    }
}
