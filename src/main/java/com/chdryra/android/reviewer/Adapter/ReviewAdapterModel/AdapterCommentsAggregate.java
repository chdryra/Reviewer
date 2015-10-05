package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
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

    public AdapterCommentsAggregate(Context context, ReviewNode node,
                                    GvCanonicalCollection<GvCommentList.GvComment> comments) {
        super(node);
        mComments = comments;
        ExpanderToReviews<GvCanonical> expander = new ExpanderToReviews<>(context);
        setWrapper(new ViewerGvDataCollection<>(expander, mComments));
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

        setData(current);
    }

    private void splitComments() {
        GvCommentList allComments = new GvCommentList(mComments.getReviewId());
        for (int i = 0; i < mComments.size(); ++i) {
            GvCanonical<GvCommentList.GvComment> canonical = mComments.getItem(i);
            allComments.addList(canonical.toList());
        }
        GvCommentList split = allComments.getSplitComments();
        mCommentsSplit = Aggregater.aggregate(split);
    }
}
