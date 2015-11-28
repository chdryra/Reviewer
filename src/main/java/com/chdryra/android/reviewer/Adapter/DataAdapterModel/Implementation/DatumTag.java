package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumTag implements DataTag {
    private final String mReviewId;
    private final String mTag;

    public DatumTag(String reviewId, String tag) {
        mReviewId = reviewId;
        mTag = tag;
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
