/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorTreeFlattener;
import com.chdryra.android.reviewer.View.GvTagList;

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
        MdDataList<ReviewId> ids = getIds();

        GvTagList tags = new GvTagList();
        for (ReviewId id : ids) {
            GvTagList nodeTags = MdGvConverter.getTags(id.toString());
            for (GvTagList.GvTag tag : nodeTags) {
                if (!tags.contains(tag)) tags.add(tag);
            }
        }

        return tags;
    }

    public MdDataList<ReviewId> getIds() {
        MdDataList<ReviewId> ids = new MdDataList<>(mNode.getId());
        for (ReviewNode node : VisitorTreeFlattener.flatten(mNode)) {
            ids.add(node.getId());
        }

        return ids;
    }
}
