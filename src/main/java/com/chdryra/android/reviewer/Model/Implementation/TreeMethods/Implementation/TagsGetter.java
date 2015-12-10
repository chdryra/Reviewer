/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsGetter implements NodeDataGetter<DataTag> {
    private TagsManager mTagsManager;

    public TagsGetter(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    @Override
    public IdableList<DataTag> getData(ReviewNode node) {
        String reviewId = node.getReviewId();
        IdableList<DataTag> reviews = new IdableDataList<>(reviewId);
        for(ItemTag tag : mTagsManager.getTags(reviewId)) {
            reviews.add(new DatumTag(reviewId, tag.getTag()));
        }

        return reviews;
    }
}
