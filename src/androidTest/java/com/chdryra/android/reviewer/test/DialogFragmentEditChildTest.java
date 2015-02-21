/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentEditChildTest extends
        DialogEditGvDataTest<GvChildrenList.GvChildReview> {

    public DialogFragmentEditChildTest() {
        super(ConfigGvDataAddEdit.EditChild.class);
    }

    @Override
    protected GvDataList.GvData getDataShown() {
        EditText et = mSolo.getEditText(0);
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);
        return new GvChildrenList.GvChildReview(et.getText().toString(), rb.getRating());
    }
}

