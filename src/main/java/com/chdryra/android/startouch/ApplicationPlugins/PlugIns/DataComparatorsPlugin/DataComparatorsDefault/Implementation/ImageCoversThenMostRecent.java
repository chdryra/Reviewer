/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageCoversThenMostRecent implements Comparator<DataImage> {
    private final Comparator<DateTime> mDateComparator;

    public ImageCoversThenMostRecent(Comparator<DateTime> dateComparator) {
        mDateComparator = dateComparator;
    }

    @Override
    public int compare(DataImage lhs, DataImage rhs) {
        int comp;
        if (lhs.isCover() && !rhs.isCover()) {
            comp = -1;
        } else if (!lhs.isCover() && rhs.isCover()) {
            comp = 1;
        } else {
            return mDateComparator.compare(lhs.getDate(), rhs.getDate());
        }

        return comp;
    }
}
