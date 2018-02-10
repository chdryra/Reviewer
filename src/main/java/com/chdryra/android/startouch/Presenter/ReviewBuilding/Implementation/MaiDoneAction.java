/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiDoneAction<T extends GvData> extends MaiDataEditor<T> {
    private static final ActivityResultCode RESULT_DONE = ActivityResultCode.DONE;
    private final boolean mAdjustTagsOnCommit;

    public MaiDoneAction(boolean adjustTagsOnCommit) {
        mAdjustTagsOnCommit = adjustTagsOnCommit;
    }

    @Override
    public void doAction(MenuItem item) {
        getEditor().commitEdits(mAdjustTagsOnCommit);
        getApp().setReturnResult(RESULT_DONE);
    }
}
