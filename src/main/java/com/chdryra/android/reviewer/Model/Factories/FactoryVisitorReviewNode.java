/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.ReviewGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.TagsGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorReviewDataGetterImpl;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorReviewDataGetter<Review> newReviewsCollector() {
        return new VisitorReviewDataGetterImpl<>(new ReviewGetter());
    }

    public VisitorReviewDataGetter<DataTag> newTagsCollector(TagsManager tagsManager) {
        return new VisitorReviewDataGetterImpl<>(new TagsGetter(tagsManager));
    }
}
