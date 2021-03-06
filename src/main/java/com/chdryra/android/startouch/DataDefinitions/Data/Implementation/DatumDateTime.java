/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumDateTime implements DateTime {
    private long mTime;

    public DatumDateTime() {
        mTime = 0;
    }

    public DatumDateTime(long time) {
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
        if (!(o instanceof DatumDateTime)) return false;

        DatumDateTime datumDate = (DatumDateTime) o;

        return mTime == datumDate.mTime;

    }

    @Override
    public int hashCode() {
        return (int) (mTime ^ (mTime >>> 32));
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
