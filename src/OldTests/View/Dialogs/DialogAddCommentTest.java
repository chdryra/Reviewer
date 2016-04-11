/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddCommentTest extends
        DialogGvDataAddTest<GvComment> {

    //Constructors
    public DialogAddCommentTest() {
        super(UiAndroid.DefaultLaunchables.AddComment.class);
    }

    //Overridden
    protected GvData enterDataAndTest() {
        GvComment data = (GvComment) super.enterDataAndTest();
        data.setIsHeadline(false);
        return data;
    }
}

