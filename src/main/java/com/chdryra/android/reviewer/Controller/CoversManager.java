/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CoversManager {
    private static CoversManager sManager;
    private        Context       mContext;

    private CoversManager(Context context) {
        mContext = context;
    }

    private static CoversManager getManager(Context context) {
        if (sManager == null) sManager = new CoversManager(context);
        return sManager;
    }

    public static GvImageList getCovers(Context context, GvReviewId id) {
        return getManager(context).getCovers(id.getId());
    }

    public static GvImageList getCovers(Context context, ReviewId id) {
        return getManager(context).getCovers(id.toString());
    }

    private GvImageList getCovers(String id) {
        return Administrator.get(mContext).getCoversForReview(id);
    }
}
