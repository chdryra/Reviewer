/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.LauncherModel.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditCommentTest extends
        DialogGvDataEditTest<GvComment> {

    //Constructors
    public DialogEditCommentTest() {
        super(DefaultLaunchables.EditComment.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        return new GvComment(mSolo.getEditText(0).getText().toString());
    }

    @Override
    protected GvData getEditDatum() {
        return newDatum();
    }

//Overridden
    @Override
    protected GvData newDatum() {
        GvComment data = (GvComment) super.newDatum();
        data.setIsHeadline(false);
        return data;
    }
}

