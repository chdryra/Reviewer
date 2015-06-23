/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataCollection extends GvDataList<GvDataList> {
    public static final GvDataType<GvDataList> TYPE = new GvDataType<>(GvDataList
            .class, "ReviewDataCollection");

    public GvDataCollection() {
        super(null, TYPE);
    }

    public GvDataCollection(GvReviewId id) {
        super(id, TYPE);
    }

    public GvDataCollection(GvDataCollection data) {
        super(data);
    }
}
