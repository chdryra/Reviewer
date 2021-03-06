/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenSubjectEdit<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewEditorActionBasic<GC>
        implements SubjectAction<GC> {

    @Override
    public void onKeyboardDone(CharSequence s) {
        getEditor().setSubject();
    }

    @Override
    public void onTextChanged(CharSequence s) {
        if (!getEditor().getSubject().equals(s.toString())) getEditor().setSubject();
    }
}
