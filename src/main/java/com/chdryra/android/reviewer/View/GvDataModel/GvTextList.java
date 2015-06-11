/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTextList extends GvDataList<GvText> {
    private static final Class<GvText> DATA_CLASS = GvText.class;

    public GvTextList(GvDataType type) {
        super(null, DATA_CLASS, type);
    }

    public GvTextList(GvReviewId id, GvDataType type) {
        super(id, GvText.class, type);
    }

    public GvTextList(GvTextList data) {
        super(data);
    }
}
