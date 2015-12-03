/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumTag;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.IdableDataList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;

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
        IdableList<DataTag> reviews = new IdableDataList<>(node.getReviewId());
        String reviewId = node.getReviewId();
        for(ItemTag tag : mTagsManager.getTags(reviewId)) {
            reviews.add(new DatumTag(tag.getTag(), reviewId));
        }

        return reviews;
    }
}
