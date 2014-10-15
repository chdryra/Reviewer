/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: tags. Each grid cell shows a tag.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: launches DialogTagAddFragment</li>
 * <li>Grid cell click: launches DialogTagEditFragment</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewTags
 * @see com.chdryra.android.reviewer.DialogTagAddFragment
 * @see com.chdryra.android.reviewer.DialogTagEditFragment
 */
public class FragmentReviewTags extends FragmentReviewGridAddEdit<GVTagList.GVTag> {
    private IHTags mHandler;

    public FragmentReviewTags() {
        super(GVType.TAGS);
    }

    @Override
    protected void doDatumAdd(Intent data) {
        mHandler.add(data, getActivity());
    }

    @Override
    protected void doDatumDelete(Intent data) {
        mHandler.delete(data);
    }

    @Override
    protected void doDatumEdit(Intent data) {
        mHandler.replace(data, getActivity());
    }

    @Override
    protected Bundle packGridCellData(GVTagList.GVTag tag, Bundle args) {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, tag, args);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new IHTags((GVTagList) getGridData());
    }
}
