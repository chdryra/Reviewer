/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Controller;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.View.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode extends ReviewViewAdapterBasic {
    private ReviewNode mNode;

    public AdapterReviewNode(ReviewNode node, GridDataViewer wrapper, GridDataExpander expander) {
        mNode = node;
        setWrapper(wrapper);
        setExpander(expander);
    }

    public AdapterReviewNode(ReviewNode node, GridDataViewer wrapper) {
        this(node, wrapper, null);
    }

    public ReviewViewAdapter getTreeDataAdapter(Context context) {
        return FactoryReviewViewAdapter.newTreeDataAdapter(context, mNode);
    }

    @Override
    public String getSubject() {
        return mNode.getSubject().get();
    }

    @Override
    public float getRating() {
        return mNode.getRating().get();
    }

    @Override
    public float getAverageRating() {
        if (mNode.isRatingAverageOfChildren()) return getRating();
        VisitorRatingAverageOfChildren visitor = new VisitorRatingAverageOfChildren();
        mNode.acceptVisitor(visitor);
        return visitor.getRating();
    }

    @Override
    public GvImageList getCovers() {
        return MdGvConverter.convert(mNode.getImages().getCovers());
    }
}
