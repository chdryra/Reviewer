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
public class GvList extends GvDataList<GvData> {
    public static final GvDataType<GvData> TYPE =
            new GvDataType<>(GvData.class, "Review Data", "Review Data");

    //Constructors
    public GvList() {
        super(TYPE, null);
    }

    public GvList(GvReviewId id) {
        super(TYPE, id);
    }

    public GvList(GvList data) {
        super(data);
    }

    //Overridden
    @Override
    public boolean contains(GvData datum) {
        return mData.contains(datum);
    }
}
