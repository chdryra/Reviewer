/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectDataEdit<T extends GvData> extends ReviewDataEditorActionBasic<T> implements SubjectAction<T> {

    @Override
    public String getSubject() {
        return getAdapter().getSubject();
    }

    @Override
    public void onKeyboardDone(CharSequence s) {
        getEditor().setSubject();
    }
}
