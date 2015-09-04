/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTextList<T extends GvText> extends GvDataList<T> {
    public static final GvDataType<GvTextList> TYPE
            = GvTypeMaker.newType(GvTextList.class, GvText.TYPE);

    public GvTextList(GvDataType<T> type) {
        this(type, null);
    }

    public GvTextList(GvDataType<T> type, GvReviewId id) {
        super(GvTypeMaker.newType(GvTextList.class, type), id);
    }

    public GvTextList(GvTextList<T> data) {
        super(data);
    }

    public static GvTextList<GvText> newTextList() {
        return new GvTextList<>(GvText.TYPE);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> strings = new ArrayList<>();
        for (T tag : this) {
            strings.add(tag.get());
        }

        return strings;
    }
}
