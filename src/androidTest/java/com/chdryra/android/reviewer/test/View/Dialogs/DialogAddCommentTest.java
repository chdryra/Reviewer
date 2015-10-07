/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddCommentTest extends
        DialogGvDataAddTest<GvCommentList.GvComment> {

    //Constructors
    public DialogAddCommentTest() {
        super(ConfigGvDataAddEditView.AddComment.class);
    }

    //Overridden
    protected GvData enterDataAndTest() {
        GvCommentList.GvComment data = (GvCommentList.GvComment) super.enterDataAndTest();
        data.setIsHeadline(false);
        return data;
    }
}

