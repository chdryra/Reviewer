/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

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
public class ReviewViewPacker {
    private static final String REVIEWVIEW_ID = TagKeyGenerator.getKey(ReviewViewPacker.class, "ReviewId");
    private final ObjectHolder mViews;

    private static ReviewViewPacker sSingleton;

    private ReviewViewPacker() {
        super();
        mViews = new ObjectHolder();
    }

    private static ReviewViewPacker get() {
        if(sSingleton == null) sSingleton = new ReviewViewPacker();
        return sSingleton;
    }

    private static ObjectHolder getViews() {
        return get().mViews;
    }

    public static void packView(ReviewView view, Intent i) {
        String id = i.getStringExtra(REVIEWVIEW_ID);
        if(id == null) id = UUID.randomUUID().toString();
        getViews().addObject(id, view);
        i.putExtra(REVIEWVIEW_ID, id);
    }

    public static ReviewView unpackView(Intent i) {
        String id = i.getStringExtra(REVIEWVIEW_ID);
        ReviewView view = (ReviewView) getViews().getObject(id);
        getViews().removeObject(id);

        return view;
    }
}
