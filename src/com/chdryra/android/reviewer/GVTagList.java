/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;

/**
 * GVReviewDataList: GVString
 * <p>
 * ViewHolder: VHTagView
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewTags`
 * @see com.chdryra.android.reviewer.VHTagView
 */
class GVTagList extends GVReviewDataList<GVString> {

    GVTagList() {
        super(GVType.TAGS);
    }

    void add(String string) {
        if (string != null && string.length() > 0) {
            add(new GVString(string));
        }
    }

    boolean contains(String string) {
        return contains(new GVString(string));
    }

    void remove(String string) {
        remove(new GVString(string));
    }

    @Override
    protected Comparator<GVString> getDefaultComparator() {
        return new Comparator<GVString>() {

            @Override
            public int compare(GVString lhs, GVString rhs) {
                return lhs.get().compareTo(rhs.get());
            }
        };
    }

    @Override
    public ViewHolder getViewHolder(int position) {
        return new VHTagView();
    }

}
