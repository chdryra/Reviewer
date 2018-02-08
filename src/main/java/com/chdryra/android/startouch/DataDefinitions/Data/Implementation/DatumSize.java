/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumSize implements DataSize {
    private final ReviewId mId;
    private int mSize = 0;

    public DatumSize(ReviewId id, int size) {
        mId = id;
        mSize = size;
    }

    @Override
    public int getSize() {
        return mSize;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumSize)) return false;

        DatumSize datumSize = (DatumSize) o;

        if (mSize != datumSize.mSize) return false;
        return mId.equals(datumSize.mId);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mSize;
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}