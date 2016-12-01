/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

public class GvNode extends GvDataBasic<GvNode> implements DataReviewInfo {
    public static final GvDataType<GvNode> TYPE = new GvDataType<>(GvNode.class, TYPE_NAME);

    private ReviewNode mNode;
    private ViewHolderFactory<VhNode> mViewHolderFactory;
    private VhNode mViewHolder;

    private GvNode() {
        super(GvNode.TYPE);
    }

    public GvNode(ReviewNode node,
                  ViewHolderFactory<VhNode> viewHolderFactory) {
        super(GvNode.TYPE, new GvReviewId(node.getReviewId()));
        mNode = node;
        mViewHolderFactory = viewHolderFactory;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    public void unbind() {
        if(mViewHolder != null && mViewHolder.isBoundTo(mNode)) {
            mViewHolder.unbindFromNode();
        }
    }

    public void setViewHolder(VhNode viewHolder) {
        mViewHolder = viewHolder;
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

    @Override
    public boolean isValidForDisplay() {
        return getGvReviewId() != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return isValidForDisplay();
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return null;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
