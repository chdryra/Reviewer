package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumImage implements DataImage {
    private final Bitmap mBitmap;
    private final DataDate mDate;
    private final String mCaption;
    private final ReviewId mReviewId;
    private boolean mIsCover = false;

    public DatumImage(ReviewId reviewId, Bitmap bitmap, DataDate date, String caption,
                      boolean isCover) {
        mReviewId = reviewId;
        mBitmap = bitmap;
        mDate = date;
        mCaption = caption;
        mIsCover = isCover;
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public DataDate getDate() {
        return mDate;
    }

    @Override
    public String getCaption() {
        return mCaption;
    }

    @Override
    public boolean isCover() {
        return mIsCover;
    }

    @Override
    public boolean hasData(DataValidator va) {
        return va.validate(this);
    }

    @Override
    public ReviewId getReviewId() {
        return null;
    }
}
