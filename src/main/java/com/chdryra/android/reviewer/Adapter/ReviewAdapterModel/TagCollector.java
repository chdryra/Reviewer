/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagCollector {
    private ReviewNode mNode;
    private TagsManager mTagsManager;

    public TagCollector(ReviewNode node, TagsManager tagsManager) {
        mNode = node;
        mTagsManager = tagsManager;
    }

    public GvTagList collectTags() {
        MdDataList<ReviewId> ids = new MdDataList<>(mNode.getId());
        for (Review review : VisitorReviewsGetter.flatten(mNode)) {
            ids.add(review.getId());
        }

        GvTagList tags = new GvTagList(GvReviewId.getId(mNode.getId().toString()));
        for (ReviewId id : ids) {
            for (GvTagList.GvTag tag : MdGvConverter.getTags(id.toString(), mTagsManager)) {
                tags.add(tag);
            }
        }

        return tags;
    }
}
