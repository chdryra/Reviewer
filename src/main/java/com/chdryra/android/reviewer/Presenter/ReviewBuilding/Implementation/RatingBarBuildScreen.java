package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarBuildScreen<T extends GvData> extends ReviewEditorActionBasic<T>
        implements RatingBarAction<T>{
    //Overridden

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        getEditor().setRating(rating, fromUser);
    }

    @Override
    public float getRating() {
        return getEditor().getRating();
    }
}
