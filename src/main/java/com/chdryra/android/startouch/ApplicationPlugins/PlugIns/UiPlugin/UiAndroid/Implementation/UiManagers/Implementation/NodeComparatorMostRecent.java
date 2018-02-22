/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeComparatorMostRecent implements Comparator<ReviewNode> {
    @Override
    public int compare(ReviewNode lhs, ReviewNode rhs) {
        long lTime = lhs.getPublishDate().getTime();
        long rTime = rhs.getPublishDate().getTime();
        if (lTime > rTime) {
            return -1;
        } else if (lTime < rTime) {
            return 1;
        } else {
            return 0;
        }
    }
}
