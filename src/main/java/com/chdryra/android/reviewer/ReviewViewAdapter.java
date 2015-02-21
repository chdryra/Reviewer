/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

import java.util.Date;

/**
 * Adapter for {@link com.chdryra.android.reviewer.Review} for passing {@link com.chdryra.android
 * .reviewer.MdData} to View layer as {@link com.chdryra.android.reviewer.GvDataList.GvData}
 */
public interface ReviewViewAdapter {
    public String getSubject();

    public float getRating();

    public float getAverageRating();

    public GvDataList getGridData();

    public Author getAuthor();

    public Date getPublishDate();

    public GvImageList getImages();
}
