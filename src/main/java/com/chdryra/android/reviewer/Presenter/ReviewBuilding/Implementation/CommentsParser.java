/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 11/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class CommentsParser {
    private ReviewBuilder mBuilder;

    public CommentsParser(ReviewBuilder builder) {
        mBuilder = builder;
    }

    public void add(GvComment newDatum) {
        DataBuilder<GvTag> dataBuilder = mBuilder.getDataBuilder(GvTag.TYPE);
        for(String tag : TextUtils.getHashTags(newDatum.getComment())) {
            dataBuilder.add(new GvTag(tag));
        }
    }

    public void delete(GvComment datum) {
        DataBuilder<GvTag> dataBuilder = mBuilder.getDataBuilder(GvTag.TYPE);
        for(String tag : TextUtils.getHashTags(datum.getComment())) {
            dataBuilder.delete(new GvTag(tag));
        }
    }

    public void commit() {
        mBuilder.getDataBuilder(GvTag.TYPE).buildData();
    }
}