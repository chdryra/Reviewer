/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentEditTagTest extends DialogEditGvDataTest<GvTagList.GvTag> {

    public DialogFragmentEditTagTest() {
        super(ConfigGvDataAddEdit.EditTag.class);
    }

    @Override
    protected GvTagList.GvTag getDataShown() {
        return new GvTagList.GvTag(mSolo.getEditText(0).getText().toString());
    }
}
