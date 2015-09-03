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
    public static final GvDataType<GvList> TYPE =
            GvTypeMaker.newType(GvList.class, GvTypeMaker.newType(GvData.class, "ReviewDatum",
                    "ReviewData"));

    public GvList() {
        super(TYPE, null);
    }

    public GvList(GvReviewId id) {
        super(TYPE, id);
    }

    public GvList(GvList data) {
        super(data);
    }
}
