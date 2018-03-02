/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.View;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

import android.graphics.Bitmap;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Adapter for {@link Review} model data to be presented in a {@link ReviewView} View layer using
 * {@link GvData}
 */
public interface ReviewViewAdapter<T extends GvData> extends GridDataViewer<T> {
    interface Filterable<T extends GvData> extends ReviewViewAdapter<T> {
        interface Callback {
            void onFiltered();
        }

        void filterGrid(String query, Callback callback);
    }

    void attachReviewView(ReviewView<T> view);

    void detachReviewView();

    ReviewView<T> getReviewView();

    DataReference<String> getSubjectReference();

    DataReference<Float> getRatingReference();

    DataReference<Bitmap> getCoverReference();
}
