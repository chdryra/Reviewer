/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeImpl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumSize extends SizeImpl implements DataSize {
    private final ReviewId mId;

    public DatumSize(ReviewId id, int size) {
        super(size);
        mId = id;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumSize)) return false;
        if (!super.equals(o)) return false;

        DatumSize datumSize = (DatumSize) o;

        return mId != null ? mId.equals(datumSize.mId) : datumSize.mId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mId != null ? mId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
