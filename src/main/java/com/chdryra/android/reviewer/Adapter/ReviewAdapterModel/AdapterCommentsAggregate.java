package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.FactoryGvDataAggregate;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

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
    private FactoryGvDataAggregate mAggregateFactory;

    //Constructors
    public AdapterCommentsAggregate(ReviewNode node,
                                    MdGvConverter converter,
                                    GvCanonicalCollection<GvCommentList.GvComment> comments,
                                    FactoryGridDataViewer viewerFactory,
                                    FactoryReviewViewAdapter adapterFactory,
                                    FactoryGvDataAggregate aggregateFactory) {
        super(node, converter);
        mComments = comments;
        mViewerFactory = viewerFactory;
        mAdapterFactory = adapterFactory;
        mAggregateFactory = aggregateFactory;
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
        GvCommentList allComments = new GvCommentList(mComments.getReviewId());
        for (int i = 0; i < mComments.size(); ++i) {
            GvCanonical<GvCommentList.GvComment> canonical = mComments.getItem(i);
            allComments.addList(canonical.toList());
        }
        GvCommentList split = allComments.getSplitComments();
        mCommentsSplit = mAggregateFactory.getAggregate(split);
    }
}
