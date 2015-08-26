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
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorTreeFlattener;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagCollector {
    private ReviewNode mNode;

    public TagCollector(ReviewNode node) {
        mNode = node;
    }

    public GvTagList collectTags() {
        MdDataList<ReviewId> ids = new MdDataList<>(mNode.getId());
        for (ReviewNode node : VisitorTreeFlattener.flatten(mNode)) {
            ids.add(node.getId());
        }

        GvTagList tags = new GvTagList(GvReviewId.getId(mNode.getId().toString()));
        for (ReviewId id : ids) {
            for (GvTagList.GvTag tag : MdGvConverter.getTags(id.toString())) {
                tags.add(tag);
            }
        }

        return tags;
    }
}
