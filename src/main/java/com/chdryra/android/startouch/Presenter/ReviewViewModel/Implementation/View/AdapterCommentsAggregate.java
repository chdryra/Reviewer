/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGridDataViewer;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterCommentsAggregate extends AdapterReviewNode<GvCanonical> {
    private final GvCanonicalCollection<GvComment> mComments;
    private final FactoryGridDataViewer mViewerFactory;
    private final GvDataAggregator mAggregater;
    private GvCanonicalCollection<GvComment> mCommentsSplit;

    //Constructors
    public AdapterCommentsAggregate(ReviewNode node,
                                    DataReference<ProfileImage> profileImage,
                                    DataConverter<DataImage, GvImage, GvImageList> converter,
                                    GvCanonicalCollection<GvComment> comments,
                                    FactoryGridDataViewer viewerFactory,
                                    GvDataAggregator aggregater) {
        super(node, profileImage, converter, viewerFactory.newAggregateToReviewsViewer(comments));
        mComments = comments;
        mViewerFactory = viewerFactory;
        mAggregater = aggregater;
    }

//    @Override
//    public GvDataType<? extends GvData> getGvDataType() {
//        return GvComment.TYPE;
//    }

    private void setSplit(boolean split) {
        GvCanonicalCollection<GvComment> current;

        if (split) {
            if (mCommentsSplit == null) splitComments();
            current = mCommentsSplit;
        } else {
            current = mComments;
        }

        setWrapper(mViewerFactory.newAggregateToReviewsViewer(current));
    }

    private void splitComments() {
        GvCommentList allComments = new GvCommentList(new GvReviewId(mComments.getReviewId()));
        for (int i = 0; i < mComments.size(); ++i) {
            GvCanonical<GvComment> canonical = mComments.get(i);
            allComments.addAll(canonical.toList());
        }
        GvCommentList split = allComments.getSplitComments();
        mCommentsSplit = mAggregater.aggregateComments(split);
    }
}
