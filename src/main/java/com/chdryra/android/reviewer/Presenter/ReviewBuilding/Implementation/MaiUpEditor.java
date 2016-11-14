/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUp;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class MaiUpEditor<GC extends GvDataList<? extends GvDataParcelable>> extends MaiUp<GC> {
    @Override
    public void doAction(MenuItem item) {
        getApp().getReviewEditor().discardEditor();
        super.doAction(item);
    }
}
