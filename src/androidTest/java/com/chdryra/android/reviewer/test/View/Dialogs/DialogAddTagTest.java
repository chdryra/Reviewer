/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * More of a black-box behaviour (integration) test than unit test
 */
public class DialogAddTagTest extends DialogAddGvDataTest<GvTagList.GvTag> {

    public DialogAddTagTest() {
        super(ConfigGvDataAddEditView.AddTag.class);
    }
}
