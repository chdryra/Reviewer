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
class GVTagList extends GVReviewDataList<GVTagList.GVTag> {

    GVTagList() {
        super(GVType.TAGS);
    }

    void add(String string) {
        if (string != null && string.length() > 0) {
            add(new GVTag(string));
        }
    }

    boolean contains(String string) {
        return contains(new GVTag(string));
    }

    void remove(String string) {
        remove(new GVTag(string));
    }

    @Override
    protected Comparator<GVTag> getDefaultComparator() {
        return new Comparator<GVTag>() {

            @Override
            public int compare(GVTag lhs, GVTag rhs) {
                return lhs.get().compareTo(rhs.get());
            }
        };
    }

    static class GVTag extends GVString {
        GVTag(String tag) {
            super(tag);
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHTagView();
        }
    }
}
