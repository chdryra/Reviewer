/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.EditorSuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EditUiLauncher extends PackingLauncherImpl<Review> {
    private static final String TEMPLATE_OR_EDIT = TagKeyGenerator.getKey(EditUiLauncher.class, "TemplateOrEdit");
    private final EditorSuite mBuilder;
    private final ReviewsNodeRepo mRepo;

    public EditUiLauncher(LaunchableConfig ui, EditorSuite builder, ReviewsNodeRepo repo) {
        super(ui);
        mBuilder = builder;
        mRepo = repo;
    }

    public void launchCreate(@Nullable ReviewId template) {
        if(template == null ) {
            super.launch(null);
        } else {
            launch(template, ReviewPack.TemplateOrEdit.TEMPLATE);
        }
    }

    public ReviewPack unpackReview(Bundle args) {
        Review review = super.unpack(args);
        ReviewPack.TemplateOrEdit templateOrEdit
                = (ReviewPack.TemplateOrEdit) args.getSerializable(TEMPLATE_OR_EDIT);
        if(templateOrEdit == null) templateOrEdit = ReviewPack.TemplateOrEdit.TEMPLATE;
        return review != null ? new ReviewPack(review, templateOrEdit) : new ReviewPack();
    }

    public void launchEdit(ReviewId review) {
        launch(review, ReviewPack.TemplateOrEdit.EDIT);
    }

    private void launch(ReviewId review, final ReviewPack.TemplateOrEdit templateOrEdit) {
        mRepo.getReview(review, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                Bundle args = new Bundle();
                args.putSerializable(TEMPLATE_OR_EDIT, templateOrEdit);
                EditUiLauncher.super.launch(result.getReview(), args);
            }
        });
    }

    @Override
    protected void onPrelaunch() {
        mBuilder.discardEditor(false, null);
    }
}
