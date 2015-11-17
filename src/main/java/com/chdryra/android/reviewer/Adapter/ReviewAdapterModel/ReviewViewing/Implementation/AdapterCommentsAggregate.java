package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterCommentsAggregate extends AdapterReviewNode<GvCanonical> {
    private GvCanonicalCollection<GvCommentList.GvComment> mComments;
    private GvCanonicalCollection<GvCommentList.GvComment> mCommentsSplit;
    private FactoryGridDataViewer mViewerFactory;
    private FactoryReviewViewAdapter mAdapterFactory;
    private GvDataAggregater mAggregater;

    //Constructors
    public AdapterCommentsAggregate(ReviewNode node,
                                    GvImageConverter converter,
                                    GvCanonicalCollection<GvCommentList.GvComment> comments,
                                    FactoryGridDataViewer viewerFactory,
                                    FactoryReviewViewAdapter adapterFactory,
                                    GvDataAggregater aggregater) {
        super(node, converter);
        mComments = comments;
        mViewerFactory = viewerFactory;
        mAdapterFactory = adapterFactory;
        mAggregater = aggregater;
        setSplit(false);
    }

    public void setSplit(boolean split) {
        GvCanonicalCollection<GvCommentList.GvComment> current;

        if (split) {
            if (mCommentsSplit == null) splitComments();
            current = mCommentsSplit;
        } else {
            current = mComments;
        }

        setViewer(mViewerFactory.newDataToReviewsViewer(current, mAdapterFactory));
    }

    private void splitComments() {
        GvCommentList allComments = new GvCommentList(new GvReviewId(mComments.getReviewId()));
        for (int i = 0; i < mComments.size(); ++i) {
            GvCanonical<GvCommentList.GvComment> canonical = mComments.getItem(i);
            allComments.addList(canonical.toList());
        }
        GvCommentList split = allComments.getSplitComments();
        mCommentsSplit = mAggregater.getAggregate(split);
    }
}
