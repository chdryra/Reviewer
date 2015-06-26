/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTextList<T extends GvText> extends GvDataList<T> {
    private static final GvDataType<GvTextList> TYPE
            = GvTypeMaker.newType(GvTextList.class, GvText.TYPE);

    public GvTextList(Class<T> dataClass, GvDataType type) {
        super(dataClass, type, null);
    }

    public GvTextList(Class<T> dataClass, GvDataType type, GvReviewId id) {
        super(dataClass, type, id);
    }

    public GvTextList(GvTextList<T> data) {
        super(data);
    }

    public static GvTextList<GvText> newTextList() {
        return new GvTextList<>(GvText.class, GvTextList.TYPE);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> strings = new ArrayList<>();
        for (T tag : this) {
            strings.add(tag.get());
        }

        return strings;
    }

    @Override
    protected Comparator<T> getDefaultComparator() {
        return new Comparator<T>() {

            @Override
            public int compare(T lhs, T rhs) {
                return lhs.get().compareToIgnoreCase(rhs.get());
            }
        };
    }
}
