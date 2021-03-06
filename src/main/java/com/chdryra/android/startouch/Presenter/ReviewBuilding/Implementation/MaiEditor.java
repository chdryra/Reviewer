/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MenuActionItemBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MaiEditor<GC extends GvDataList<? extends GvDataParcelable>>
        extends MenuActionItemBasic<GC> {
    public ReviewEditor<GC> getEditor() {
        return (ReviewEditor<GC>) getReviewView();
    }
}
