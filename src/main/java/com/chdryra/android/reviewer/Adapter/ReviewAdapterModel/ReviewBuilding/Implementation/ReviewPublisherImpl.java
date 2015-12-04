package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewPublisherImpl implements ReviewPublisher {
    private final DataAuthor mAuthor;
    private final DataDate mDate;
    private int mIndex;

    public ReviewPublisherImpl(DataAuthor author, DataDate date) {
        mAuthor = author;
        mDate = date;
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public DataDate getDate() {
        return mDate;
    }

    @Override
    public int getPublishedIndex() {
        return mIndex++;
    }
}
