package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataReviewSummary {
    DataDate getPublishDate();

    DataAuthor getAuthor();

    String getHeadline();

    ArrayList<String> getTags();

    String getLocationString();

    Bitmap getCoverImage();

    float getRating();

    String getSubject();

    String getId();
}
