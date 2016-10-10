/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface ReviewBuilderSuite {
    String QUICK_ADD = TagKeyGenerator.getKey(ReviewBuilderSuite.class, "QuickAdd");
    String QUICK_REVIEW = TagKeyGenerator.getKey(ReviewBuilderSuite.class, "QuickReview");

    ReviewEditor<?> newReviewEditor(ReviewEditor.EditMode editMode, LocationClient client, @Nullable Review template);

    ReviewEditor<?> getReviewEditor();

    void discardReviewEditor();
}
