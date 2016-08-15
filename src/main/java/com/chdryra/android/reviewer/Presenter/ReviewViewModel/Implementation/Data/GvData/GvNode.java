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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ReviewSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.SelectorMostRecent;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewSelected;

public class GvNode extends GvDataBasic<GvNode> implements DataReviewInfo {
    public static final GvDataType<GvNode> TYPE =
            new GvDataType<>(GvNode.class, "review");

    private ReviewNode mNode;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private VhReviewSelected mViewHolder;

    public GvNode() {
        super(GvNode.TYPE);
    }

    public GvNode(ReviewNode node,
                  GvConverterComments converterComments,
                  GvConverterLocations converterLocations) {
        super(GvNode.TYPE, new GvReviewId(node.getReviewId()));
        mNode = node;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    public void unbind() {
        if(mViewHolder != null && mViewHolder.isBoundTo(mNode)) {
            mViewHolder.unbindFromReview();
        }
    }

    public void setViewHolder(VhReviewSelected viewHolder) {
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
        return new VhReviewSelected(new ReviewSelector(new SelectorMostRecent()),
                mConverterComments, mConverterLocations);
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
    public String getStringSummary() {
        return getGvReviewId().getStringSummary();
    }
}
