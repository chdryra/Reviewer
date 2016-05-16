/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import android.content.Context;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.CacheUtils.ObjectHolder;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

import java.util.UUID;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewPacker extends ApplicationSingleton {
    private static final String NAME = "ReviewViewPacker";
    private static final String REVIEWVIEW_ID = TagKeyGenerator.getKey(ReviewViewPacker.class, "ReviewId");
    private final ObjectHolder mViews;

    private static ReviewViewPacker sSingleton;

    private ReviewViewPacker(Context context) {
        super(context, NAME);
        mViews = new ObjectHolder();
    }

    public static ReviewViewPacker get(Context c) {
        sSingleton = getSingleton(sSingleton, ReviewViewPacker.class, c);
        return sSingleton;
    }

    private static ObjectHolder getViews(Context context) {
        return get(context).mViews;
    }

    public static void packView(Context context, ReviewView view, Intent i) {
        String id = i.getStringExtra(REVIEWVIEW_ID);
        if(id == null) id = UUID.randomUUID().toString();
        getViews(context).addObject(id, view);
        i.putExtra(REVIEWVIEW_ID, id);
    }

    public static ReviewView unpackView(Context context, Intent i) {
        String id = i.getStringExtra(REVIEWVIEW_ID);
        ReviewView view = (ReviewView) getViews(context).getObject(id);
        getViews(context).removeObject(id);

        return view;
    }
}
