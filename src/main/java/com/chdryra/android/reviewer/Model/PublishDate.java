/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.Model;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishDate {
    private Date mDate;

    private PublishDate() {
        mDate = new Date();
    }

    private PublishDate(long time) {
        mDate = new Date(time);
    }

    public static PublishDate now() {
        return new PublishDate();
    }

    public static PublishDate then(long time) {
        long now = new Date().getTime();
        if (time > now) {
            throw new IllegalStateException("Publish date must not be in the future!");
        }

        return new PublishDate(time);
    }

    public Date getDate() {
        return mDate;
    }

    public long getTime() {
        return mDate.getTime();
    }
}
