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

import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.View.GvImageList;

/**
 * {@link ReviewViewAdapter} for {@link ReviewIdableList} data.
 */
public class ReviewNodeAdapter extends ReviewViewAdapterBasic {
    private ReviewNode mNode;

    public ReviewNodeAdapter(ReviewNode node, GridDataWrapper wrapper, GridDataExpander expander) {
        mNode = node;
        setWrapper(wrapper);
        setExpander(expander);
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
        visitor.visit(mNode);
        return visitor.getRating();
    }

    @Override
    public GvImageList getCovers() {
        return MdGvConverter.convert(mNode.getImages().getCovers());
    }

    public static class DataAdapter extends ReviewNodeAdapter {
        public DataAdapter(Context context, ReviewNode node) {
            super(node, null, null);
            GridDataWrapper wrapper = new NodeDataWrapper(node);
            ReviewViewAdapter adapter = new ReviewDataAdapter(context, this, wrapper);
            setWrapper(wrapper);
            setExpander(new ReviewDataExpander(context, adapter));
        }
    }
}
