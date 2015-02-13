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
public interface GvAdapterReview {
    public String getSubject();

    public float getRating();

    public Author getAuthor();

    public Date getPublishDate();

    public boolean hasData(GvDataList.GvType dataType);

    public GvDataList getData(GvDataList.GvType dataType);
}
