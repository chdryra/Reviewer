/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class InputHandlerAddConstraintImage {
    public static <T extends GvDataList.GvData> boolean passes(GvDataList<T> data, T datum) {
        GvImageList.GvImage image = (GvImageList.GvImage) datum;
        GvImageList list = (GvImageList) data;
        return (image != null && list != null && !list.contains(image.getBitmap()));
    }
}
