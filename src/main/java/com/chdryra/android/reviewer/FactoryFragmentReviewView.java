/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFragmentReviewView {
    private static FactoryFragmentReviewView                      sEditors;
    private        HashMap<GvDataList.GvType, FragmentReviewView> mFragments;

    private FactoryFragmentReviewView() {
        mFragments = new HashMap<>();
        mFragments.put(GvDataList.GvType.TAGS, new FragmentEditTags());
        mFragments.put(GvDataList.GvType.FACTS, new FragmentEditFacts());
        mFragments.put(GvDataList.GvType.COMMENTS, new FragmentEditComments());
        mFragments.put(GvDataList.GvType.CHILDREN, new FragmentEditChildren());
        mFragments.put(GvDataList.GvType.LOCATIONS, new FragmentEditLocations());
        mFragments.put(GvDataList.GvType.IMAGES, new FragmentEditImages());
    }

    private static FactoryFragmentReviewView get() {
        if (sEditors == null) sEditors = new FactoryFragmentReviewView();

        return sEditors;
    }

    public static Fragment newView(GvDataList.GvType dataType, boolean isEdit) {
        return newEditor(dataType);
    }

    public static Fragment newEditor(GvDataList.GvType dataType) {
        return get().mFragments.get(dataType);
    }

    public static class FragmentEditFacts extends FragmentReviewView {
        public FragmentEditFacts() {
            super(GvDataList.GvType.FACTS, true);
        }
    }

    public static class FragmentEditComments extends FragmentReviewView {
        public FragmentEditComments() {
            super(GvDataList.GvType.COMMENTS, true);
        }
    }

    public static class FragmentEditTags extends FragmentReviewView {
        public FragmentEditTags() {
            super(GvDataList.GvType.TAGS, true);
        }
    }

    public static class FragmentEditChildren extends FragmentReviewView {
        public FragmentEditChildren() {
            super(GvDataList.GvType.CHILDREN, true);
        }
    }

    public static class FragmentEditLocations extends FragmentReviewView {
        public FragmentEditLocations() {
            super(GvDataList.GvType.LOCATIONS, true);
        }
    }

    public static class FragmentEditImages extends FragmentReviewView {
        public FragmentEditImages() {
            super(GvDataList.GvType.IMAGES, true);
        }
    }
}
