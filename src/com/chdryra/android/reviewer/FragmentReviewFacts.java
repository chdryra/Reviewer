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
import android.widget.Toast;

import com.chdryra.android.reviewer.GVFactList.GVFact;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: facts. Each grid cell shows a fact label and value.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: launches DialogFactAddFragment</li>
 * <li>Grid cell click: launches DialogFactEditFragment</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewFacts
 * @see com.chdryra.android.reviewer.DialogFactAddFragment
 * @see com.chdryra.android.reviewer.DialogFactEditFragment
 */
public class FragmentReviewFacts extends FragmentReviewGridAddEdit<GVFact> {
    public static final String FACT_LABEL = "com.chdryra.android.reviewer.datum_label";
    public static final String FACT_VALUE = "com.chdryra.android.reviewer.datum_value";

    private GVFactList mFacts;

    public FragmentReviewFacts() {
        super(GVType.FACTS);
    }

    @Override
    protected void doAdd(Intent data) {
        String label = (String) data.getSerializableExtra(DialogFactAddFragment.FACT_LABEL);
        String value = (String) data.getSerializableExtra(DialogFactAddFragment.FACT_VALUE);
        if (label != null && label.length() > 0 && value != null && value.length() > 0 &&
                !mFacts.containsLabel(label)) {
            mFacts.add(label, value);
        }
    }

    @Override
    protected void doDelete(Intent data) {
        String deleteLabel = (String) data.getSerializableExtra(DialogFactEditFragment
                .FACT_OLD_LABEL);
        String deleteValue = (String) data.getSerializableExtra(DialogFactEditFragment
                .FACT_OLD_VALUE);
        mFacts.remove(deleteLabel, deleteValue);
    }

    @Override
    protected void doDone(Intent data) {
        String oldLabel = (String) data.getSerializableExtra(DialogFactEditFragment
                .FACT_OLD_LABEL);
        String oldValue = (String) data.getSerializableExtra(DialogFactEditFragment
                .FACT_OLD_VALUE);
        String newLabel = (String) data.getSerializableExtra(FACT_LABEL);
        String newValue = (String) data.getSerializableExtra(FACT_VALUE);
        if (!oldLabel.equalsIgnoreCase(newLabel) && mFacts.containsLabel(newLabel)) {
            Toast.makeText(getActivity(), getResources().getString(R.string
                            .toast_has_fact),
                    Toast.LENGTH_SHORT).show();
        } else {
            mFacts.remove(oldLabel, oldValue);
            mFacts.add(newLabel, newValue);
        }
    }

    @Override
    protected Bundle packGridCellData(GVFact fact, Bundle args) {
        args.putString(FACT_LABEL, fact.getLabel());
        args.putString(FACT_VALUE, fact.getValue());
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFacts = (GVFactList) getGridData();
    }
}
