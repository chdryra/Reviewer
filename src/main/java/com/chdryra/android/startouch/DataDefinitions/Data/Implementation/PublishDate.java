/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishDate implements DateTime {
    private long mTime;

    public PublishDate(PublishDate date) {
        this(date.getTime());
    }

    public PublishDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        long timeNow = calendar.getTime().getTime();
        if (time > timeNow) {
            throw new IllegalStateException("Publish date must not be in the future!");
        }
        mTime = time;
    }

    @Override
    public long getTime() {
        return mTime;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return mTime > 0;
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

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    //public methods
    private Date getDate() {
        return new Date(mTime);
    }
}
