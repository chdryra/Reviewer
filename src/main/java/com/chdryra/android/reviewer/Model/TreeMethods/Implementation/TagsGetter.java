/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

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
    public IdableList<DataTag> getData(@NonNull ReviewNode node) {
        ReviewId reviewId = node.getReviewId();
        IdableList<DataTag> reviews = new IdableDataList<>(reviewId);
        for(ItemTag tag : mTagsManager.getTags(reviewId.toString())) {
            reviews.add(new DatumTag(reviewId, tag.getTag()));
        }

        return reviews;
    }
}
