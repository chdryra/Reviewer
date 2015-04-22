/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeAdapter extends ReviewViewAdapterBasic {
    private Context           mContext;
    private ReviewNode        mNode;
    private ReviewDataAdapter mDataAdapter;

    public ReviewNodeAdapter(Context context, ReviewNode node) {
        mContext = context;
        mNode = node;
        mDataAdapter = new ReviewDataAdapter(context, this, new GvReviewNode(mNode));
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
    public GvDataList getGridData() {
        return mDataAdapter.getGridData();
    }

    @Override
    public GvImageList getCovers() {
        return CoversManager.getCovers(mContext, mNode.getId());
    }

    @Override
    public boolean isExpandable(int index) {
        return true;
    }

    @Override
    public ReviewViewAdapter expandItem(int index) {
        return mDataAdapter.expandItem(index);
    }

    private static class GvReviewNode extends GvDataList<GvDataList> {
        public static final GvDataType TYPE = new GvDataType("node", "node");

        public GvReviewNode(ReviewNode node) {
            super(GvDataList.class, TYPE);
            add(MdGvConverter.convertTags(node.getId()));
            add(MdGvConverter.convertChildren(node));
            add(MdGvConverter.convert(node.getImages()));
            add(MdGvConverter.convert(node.getComments()));
            add(MdGvConverter.convert(node.getLocations()));
            add(MdGvConverter.convert(node.getFacts()));
        }

        @Override
        public void sort() {
        }
    }
}
