package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;

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
    public ReviewViewAdapter expandGridCell(T datum) {
        if (isExpandable(datum)) {
            String title = datum.getStringSummary();
            ReviewsRepository repo = getRepository();
            ReviewNode meta = repo.createMetaReview((GvDataCollection) datum, title);
            if (meta != null) return getTreeDataScreen(meta, repo.getTagsManager());
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        ReviewsRepository repo = getRepository();
        ReviewNode meta = repo.createFlattenedMetaReview(mData, mData.getStringSummary());
        return getTreeDataScreen(meta, repo.getTagsManager());
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }

    private ReviewsRepository getRepository() {
        return Administrator.get(mContext).getReviewsRepository();
    }

    private ReviewViewAdapter<? extends GvData> getTreeDataScreen(ReviewNode node, TagsManager
            tagsManager) {
        return FactoryReviewViewAdapter.newTreeDataAdapter(mContext, node, tagsManager);
    }
}
