/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.widget.RatingBar;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddChildTest extends
        DialogGvDataAddTest<GvChildReviewList.GvChildReview> {

    public DialogAddChildTest() {
        super(ConfigGvDataAddEditView.AddChild.class);
    }

    @Override
    protected boolean isDataNulled() {
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);

        return super.isDataNulled() && rb.getRating() == 0;
    }
}
