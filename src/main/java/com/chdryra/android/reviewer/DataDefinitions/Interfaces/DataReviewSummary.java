/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataReviewSummary extends DataReviewInfo {
    @Override
    DataSubject getSubject();

    @Override
    DataRating getRating();

    @Override
    DataDate getPublishDate();

    @Override
    DataAuthor getAuthor();

    String getHeadline();

    ArrayList<String> getTags();

    String getLocationString();

    Bitmap getCoverImage();


    ReviewId getParentId();
}
