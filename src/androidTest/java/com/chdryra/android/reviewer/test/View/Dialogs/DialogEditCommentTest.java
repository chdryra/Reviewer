/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditCommentTest extends
        DialogGvDataEditTest<GvCommentList.GvComment> {

    //Constructors
    public DialogEditCommentTest() {
        super(ConfigGvDataAddEditView.EditComment.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        return new GvCommentList.GvComment(mSolo.getEditText(0).getText().toString());
    }

    @Override
    protected GvData getEditDatum() {
        return newDatum();
    }

//Overridden
    @Override
    protected GvData newDatum() {
        GvCommentList.GvComment data = (GvCommentList.GvComment) super.newDatum();
        data.setIsHeadline(false);
        return data;
    }
}

