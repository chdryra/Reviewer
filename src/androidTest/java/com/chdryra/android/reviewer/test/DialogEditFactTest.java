/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditFactTest extends DialogEditGvDataTest<GvFactList.GvFact> {

    public DialogEditFactTest() {
        super(ConfigGvDataAddEdit.EditFact.class);
    }

    @Override
    protected GvDataList.GvData getDataShown() {
        return new GvFactList.GvFact(mSolo.getEditText(0).getText().toString(),
                mSolo.getEditText(1).getText().toString());
    }
}

