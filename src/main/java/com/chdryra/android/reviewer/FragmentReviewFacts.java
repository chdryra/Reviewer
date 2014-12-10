/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * UI Fragment: facts. Each grid cell shows a fact label and value.
 */
public class FragmentReviewFacts extends FragmentReviewGridAddEdit<VgFactList.VgFact> {
    public FragmentReviewFacts() {
        super(VgDataList.GvType.FACTS);
    }
}
