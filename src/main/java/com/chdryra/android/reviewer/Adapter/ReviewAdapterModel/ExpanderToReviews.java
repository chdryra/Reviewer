package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewMaker;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewsManager;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderToReviews<T extends GvData> implements GridDataExpander<T> {
    private Context mContext;
    private GvDataCollection<T> mData;

    public ExpanderToReviews(Context context) {
        mContext = context;
    }

    @Override
    public boolean isExpandable(T datum) {
        boolean isExpandable = false;
        if (datum.hasElements()) {
            for (int i = 0; i < mData.size(); ++i) {
                T item = mData.getItem(i);
                isExpandable = item.equals(datum);
                if (isExpandable) break;
            }
        }

        return isExpandable;
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridCell(T datum) {
        if (isExpandable(datum)) {
            ReviewNode node = getReviewNode(datum);
            if (node != null) return getReviewListScreen(node);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridData() {
        IdableList<Review> nodes = new IdableList<>();
        for (int i = 0; i < mData.size(); ++i) {
            ReviewNode node = getReviewNode(mData.getItem(i));
            if (node != null) nodes.add(node);
        }

        ReviewNode meta = ReviewMaker.createMetaReview(mContext, nodes, mData.getStringSummary());
        return getReviewListScreen(meta);
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }

    private ReviewNode getReviewNode(T item) {
        if (item.isCollection() && !item.hasElements()) return null;
        return ReviewsManager.getReview(mContext, item);
    }

    private ReviewViewAdapter<? extends GvData> getReviewListScreen(ReviewNode node) {
        return ReviewListScreen.newScreen(mContext, node).getAdapter();
    }
}
