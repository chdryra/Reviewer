/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * More of a black-box behaviour (integration) test than unit test
 */
public class DialogAddTagTest extends DialogGvDataAddTest<GvTag> {

    //Constructors
    public DialogAddTagTest() {
        super(ClassesAddEditViewDefault.AddTag.class);
    }
}
