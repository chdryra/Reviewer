/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class SizeImpl implements Size {
    private final int mSize;

    public SizeImpl(int size) {
        mSize = size;
    }

    @Override
    public int getSize() {
        return mSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SizeImpl)) return false;

        SizeImpl size = (SizeImpl) o;

        return mSize == size.mSize;
    }

    @Override
    public int hashCode() {
        return mSize;
    }
}
