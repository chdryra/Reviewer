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
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEditBuildScreen<T extends GvData> extends ReviewEditorActionBasic<T>
        implements SubjectAction<T>{
    @Override
    public String getSubject() {
        return getEditor().getSubject();
    }

    //Overridden
    @Override
    public void onKeyboardDone(CharSequence s) {
        getEditor().setSubject();
    }
}
