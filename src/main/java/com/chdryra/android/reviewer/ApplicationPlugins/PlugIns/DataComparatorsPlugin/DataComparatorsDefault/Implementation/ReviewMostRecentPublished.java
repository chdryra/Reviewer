/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewBasicInfo;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMostRecentPublished<T extends DataReviewBasicInfo> implements Comparator<T> {
    private Comparator<DataDate> mDateComparator;

    public ReviewMostRecentPublished(Comparator<DataDate> dateComparator) {
        mDateComparator = dateComparator;
    }

    @Override
    public int compare(DataReviewBasicInfo lhs, DataReviewBasicInfo rhs) {
        return mDateComparator.compare(lhs.getPublishDate(), rhs.getPublishDate());
    }
}
