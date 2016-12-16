/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.chdryra.android.reviewer.Algorithms.DataSorting.Bucket;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class VhRatingBucket extends VhBucket<Float, DataRating> {
    private static final int LAYOUT = R.layout.grid_cell_rating_bucket;
    private static final int RATING = R.id.rating_bucket;
    private static final int BAR = R.id.progress_bar;
    private static final int NUMBER = R.id.number_reviews;

    private TextView mRatingBucket;
    private ProgressBar mBar;
    private TextView mNumberReviews;

    public VhRatingBucket() {
        super(LAYOUT, new int[]{RATING, BAR, NUMBER});
    }

    @Override
    public void updateView(GvBucket<Float, DataRating> data) {
        setViewsIfNecessary();
        Bucket<Float, DataRating> bucket = data.getBucket();
        int percentage = (int) Math.round(data.getPercentageOfTotal() * 100);
        mRatingBucket.setText(bucket.getRange().toString());
        mBar.setProgress(percentage);
        mNumberReviews.setText(percentage + "%" + " (" + bucket.size() + ")");
    }

    private void setViewsIfNecessary() {
        if (mRatingBucket == null) mRatingBucket = (TextView) getView(RATING);
        if (mBar == null) mBar = (ProgressBar) getView(BAR);
        if (mNumberReviews == null) mNumberReviews = (TextView) getView(NUMBER);
    }
}