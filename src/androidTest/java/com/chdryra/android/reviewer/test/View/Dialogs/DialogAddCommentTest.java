/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Implementation.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddCommentTest extends
        DialogGvDataAddTest<GvComment> {

    //Constructors
    public DialogAddCommentTest() {
        super(DefaultLaunchables.AddComment.class);
    }

    //Overridden
    protected GvData enterDataAndTest() {
        GvComment data = (GvComment) super.enterDataAndTest();
        data.setIsHeadline(false);
        return data;
    }
}

