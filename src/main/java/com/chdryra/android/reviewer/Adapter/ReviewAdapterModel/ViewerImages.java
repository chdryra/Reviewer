package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerImages extends ViewerToReviews<GvCanonical> {
    public ViewerImages(Context context, GvCanonicalCollection<GvImageList.GvImage> data,
                        ReviewsRepository repository) {
        super(context, data, repository);
    }

    @Override
    public boolean isExpandable(GvCanonical datum) {
        return datum.getGvDataType() == GvImageList.GvImage.TYPE && super.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvCanonical datum) {
        if (!isExpandable(datum)) {
            return null;
        } else if(datum.size() == 1) {
            return super.expandGridCell(datum);
        }

        GvImageList.GvImage canonical = (GvImageList.GvImage) datum.getCanonical();
        return FactoryReviewViewAdapter.newExpandToReviewsAdapter(getContext(), datum.toList(),
                getReviewsRepository(), canonical.getCaption());
    }
}
