package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewMaker;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderToReviews<T extends GvData> implements GridCellExpander<T> {
    private Context mContext;
    private GvDataCollection<T> mData;

    public ExpanderToReviews(Context context, GvDataCollection<T> data) {
        mContext = context;
        mData = data;
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
    public ReviewViewAdapter<? extends GvData> expandItem(T datum) {
        if (isExpandable(datum)) {
            GvDataCollection data = (GvDataCollection) datum;
            ReviewNode node = ReviewMaker.createMetaReview(mContext, data, data.getStringSummary());
            return ReviewListScreen.newScreen(mContext, node).getAdapter();
        }

        return null;
    }
}
