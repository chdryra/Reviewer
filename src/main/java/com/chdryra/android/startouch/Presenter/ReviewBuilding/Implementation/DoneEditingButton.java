/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ButtonActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class DoneEditingButton<T extends GvDataParcelable> extends ButtonActionNone<T> {
    private final boolean mAdjustTags;

    public DoneEditingButton(boolean adjustTags) {
        super(Strings.Buttons.DONE);
        mAdjustTags = adjustTags;
    }

    @Override
    public void onClick(View v) {
        ReviewDataEditor<T> editor = getEditor();
        editor.commitEdits(mAdjustTags);
        editor.getApp().setReturnResult(ActivityResultCode.DONE);
        editor.getCurrentScreen().close();
    }

    private ReviewDataEditor<T> getEditor() {
        try {
            return (ReviewDataEditor<T>) getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }
    }
}
