/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditTagTest extends DialogGvDataEditTest<GvTagList.GvTag> {

    //Constructors
    public DialogEditTagTest() {
        super(ConfigGvDataAddEditView.EditTag.class);
    }

    //protected methods
    @Override
    protected GvTagList.GvTag getDataShown() {
        return new GvTagList.GvTag(mSolo.getEditText(0).getText().toString());
    }
}
