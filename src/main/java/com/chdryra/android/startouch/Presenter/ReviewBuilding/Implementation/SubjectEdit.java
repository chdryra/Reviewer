/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEdit<T extends GvData> extends ReviewDataEditorActionBasic<T> implements SubjectAction<T> {

    @Override
    public void onKeyboardDone(CharSequence s) {
        getEditor().setSubject();
    }

    @Override
    public void onTextChanged(CharSequence s) {

    }
}
