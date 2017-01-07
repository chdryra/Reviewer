/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingEditBuildScreen<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewEditorActionBasic<GC>
        implements RatingBarAction<GC>{
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ReviewEditor<GC> editor = getEditor();
        if(editor != null) editor.setRating(rating, fromUser);
    }

    @Override
    public float getRating() {
        return getEditor().getRating();
    }
}
