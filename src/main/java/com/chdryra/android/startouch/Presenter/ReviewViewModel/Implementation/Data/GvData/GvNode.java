/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;

public class GvNode extends GvDataBasic<GvNode> implements DataReview, ReviewNode.NodeObserver {
    public static final GvDataType<GvNode> TYPE = new GvDataType<>(GvNode.class, TYPE_NAME);

    private ReviewNode mNode;
    private ViewHolderFactory<VhNode> mViewHolderFactory;
    private VhNode mViewHolder;

    public GvNode(ReviewNode node,
                  ViewHolderFactory<VhNode> viewHolderFactory) {
        super(GvNode.TYPE, new GvReviewId(node.getReviewId()));
        mNode = node;
        mNode.registerObserver(this);
        mViewHolderFactory = viewHolderFactory;
    }

    private GvNode() {
        super(GvNode.TYPE);
    }

    public ReviewNode getNode() {
        return mNode;
    }

    public void unbind() {
        if (mViewHolder != null && mViewHolder.isBoundTo(mNode)) mViewHolder.unbind();
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        refresh();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        refresh();
    }

    @Override
    public void onNodeChanged() {
        refresh();
    }

    @Override
    public void onTreeChanged() {
        refresh();
    }

    @Override
    public DataSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mNode.getRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mNode.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public ViewHolder getViewHolder() {
        return mViewHolderFactory.newViewHolder();
    }

    public void setViewHolder(VhNode viewHolder) {
        if (viewHolder.equals(mViewHolder)) return;
        unbind();
        mViewHolder = viewHolder;
    }

    @Override
    public boolean isValidForDisplay() {
        return getGvReviewId() != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return isValidForDisplay();
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    private void refresh() {
        if (mViewHolder != null) mViewHolder.refresh(mNode);
    }
}
