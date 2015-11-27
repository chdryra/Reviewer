package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataReviewSummary {
    GvDate getPublishDate();

    GvAuthor getAuthor();

    String getHeadline();

    ArrayList<String> getTags();

    String getLocationString();

    Bitmap getCoverImage();

    float getRating();

    String getSubject();

    String getId();
}
