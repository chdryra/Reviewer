package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterCommentsAggregate extends AdapterReviewNode<GvCanonical> {
    private GvCanonicalCollection<GvComment> mComments;
    private GvCanonicalCollection<GvComment> mCommentsSplit;
    private FactoryGridDataViewer mViewerFactory;
    private GvDataAggregater mAggregater;

    //Constructors
    public AdapterCommentsAggregate(ReviewNode node,
                                    GvImageConverter converter,
                                    GvCanonicalCollection<GvComment> comments,
                                    FactoryGridDataViewer viewerFactory,
                                    GvDataAggregater aggregater) {
        super(node, converter);
        mComments = comments;
        mViewerFactory = viewerFactory;
        mAggregater = aggregater;
        setSplit(false);
    }

    public void setSplit(boolean split) {
        GvCanonicalCollection<GvComment> current;

        if (split) {
            if (mCommentsSplit == null) splitComments();
            current = mCommentsSplit;
        } else {
            current = mComments;
        }

        setViewer(mViewerFactory.newDataToReviewsViewer(current));
    }

    private void splitComments() {
        GvCommentList allComments = new GvCommentList(new GvReviewId(mComments.getReviewId()));
        for (int i = 0; i < mComments.size(); ++i) {
            GvCanonical<GvComment> canonical = mComments.getItem(i);
            allComments.addList(canonical.toList());
        }
        GvCommentList split = allComments.getSplitComments();
        mCommentsSplit = mAggregater.getAggregate(split);
    }
}
