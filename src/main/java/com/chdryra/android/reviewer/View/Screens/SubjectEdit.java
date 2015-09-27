/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEdit extends ReviewViewAction.SubjectAction {
    @Override
    public void onEditorDone(CharSequence s) {
        ReviewBuilderAdapter controller = (ReviewBuilderAdapter) getAdapter();
        controller.setSubject(s.toString());
    }
}
