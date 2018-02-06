/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FinishEditsButton<GC extends GvDataList<? extends GvDataParcelable>> extends UploadButton<GC> {
    public FinishEditsButton(PublishAction publishAction) {
        super(Strings.Buttons.FINISH_EDITING, publishAction);
    }

    @Override
    protected Review getReview() {
        try {
            ReviewEditor<GC> editor = (ReviewEditor<GC>)getReviewView();
            return editor.buildReview();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }
    }
}
