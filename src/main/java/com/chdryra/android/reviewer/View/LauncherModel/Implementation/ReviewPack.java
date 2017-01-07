/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewPack {
    public enum TemplateOrEdit {TEMPLATE, EDIT}

    private final Review mReview;
    private final TemplateOrEdit mTemplateOrEdit;

    public  ReviewPack() {
        mReview = null;
        mTemplateOrEdit = TemplateOrEdit.TEMPLATE;
    }

    public ReviewPack(Review review, TemplateOrEdit templateOrEdit) {
        mReview = review;
        mTemplateOrEdit = templateOrEdit;
    }

    @Nullable
    public Review getReview() {
        return mReview;
    }

    public TemplateOrEdit getTemplateOrEdit() {
        return mTemplateOrEdit;
    }
}
