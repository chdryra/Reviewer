/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterCommentsAggregate extends AdapterReviewNode<GvCanonical> {
    private GvCanonicalCollection<GvComment> mComments;
    private GvCanonicalCollection<GvComment> mCommentsSplit;
    private FactoryGridDataViewer mViewerFactory;
    private GvDataAggregator mAggregater;

    //Constructors
    public AdapterCommentsAggregate(ReviewNode node,
                                    DataConverter<DataImage, GvImage, GvImageList> converter,
                                    GvCanonicalCollection<GvComment> comments,
                                    FactoryGridDataViewer viewerFactory,
                                    GvDataAggregator aggregater) {
        super(node, converter);
        mComments = comments;
        mViewerFactory = viewerFactory;
        mAggregater = aggregater;
        setSplit(false);
    }
//
//    @Override
//    public GvDataType<? extends GvData> getGvDataType() {
//        return GvComment.TYPE;
//    }

    public void setSplit(boolean split) {
        GvCanonicalCollection<GvComment> current;

        if (split) {
            if (mCommentsSplit == null) splitComments();
            current = mCommentsSplit;
        } else {
            current = mComments;
        }

        setViewer(mViewerFactory.newAggregateToReviewsViewer(current));
    }

    private void splitComments() {
        GvCommentList allComments = new GvCommentList(new GvReviewId(mComments.getReviewId()));
        for (int i = 0; i < mComments.size(); ++i) {
            GvCanonical<GvComment> canonical = mComments.getItem(i);
            allComments.addAll(canonical.toList());
        }
        GvCommentList split = allComments.getSplitComments();
        mCommentsSplit = mAggregater.aggregateComments(split);
    }
}
