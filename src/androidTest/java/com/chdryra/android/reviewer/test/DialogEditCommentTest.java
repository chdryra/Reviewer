/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditCommentTest extends
        DialogEditGvDataTest<GvCommentList.GvComment> {

    public DialogEditCommentTest() {
        super(ConfigGvDataAddEdit.EditComment.class);
    }

    @Override
    protected GvData getDataShown() {
        return new GvCommentList.GvComment(mSolo.getEditText(0).getText().toString());
    }
}

