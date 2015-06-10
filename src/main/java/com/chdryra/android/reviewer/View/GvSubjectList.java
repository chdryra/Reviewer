/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.View;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSubjectList extends GvTextList {
    public static final GvDataType TYPE = new GvDataType("subject");

    public GvSubjectList() {
        super(TYPE);
    }

    public GvSubjectList(GvReviewId id) {
        super(id, TYPE);
    }

    public GvSubjectList(GvSubjectList data) {
        super(data);
    }
}
