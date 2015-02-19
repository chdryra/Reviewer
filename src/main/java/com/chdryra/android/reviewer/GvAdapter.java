/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Adapter for {@link com.chdryra.android.reviewer.Review} for passing {@link com.chdryra.android
 * .reviewer.MdData} to View layer as {@link com.chdryra.android.reviewer.GvDataList.GvData}
 */
public interface GvAdapter {
    public String getId();

    public String getSubject();

    public float getRating();

    public float getAverageRating();

    public Author getAuthor();

    public Date getPublishDate();

    public GvDataList getData(GvDataList.GvType dataType);
}
