/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishDate implements DataDate {
    private long mTime;

    public PublishDate(PublishDate date) {
        this(date.getTime());
    }

    public PublishDate(long time) {
        if (time > new Date().getTime()) {
            throw new IllegalStateException("Publish date must not be in the future!");
        }
        mTime = time;
    }

    //public methods
    public Date getDate() {
        return new Date(mTime);
    }

    public long getTime() {
        return mTime;
    }

    //Overridden
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublishDate)) return false;

        PublishDate that = (PublishDate) o;

        return mTime == that.mTime;

    }

    @Override
    public int hashCode() {
        return getDate().hashCode();
    }
}
