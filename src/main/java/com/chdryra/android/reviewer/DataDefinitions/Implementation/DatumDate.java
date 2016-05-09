/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumDate implements DataDate {
    private long mTime;

    public DatumDate() {
    }

    public DatumDate(long time) {
        mTime = time;
    }

    @Override
    public long getTime() {
        return mTime;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumDate)) return false;

        DatumDate datumDate = (DatumDate) o;

        return mTime == datumDate.mTime;

    }

    @Override
    public int hashCode() {
        return (int) (mTime ^ (mTime >>> 32));
    }
}
