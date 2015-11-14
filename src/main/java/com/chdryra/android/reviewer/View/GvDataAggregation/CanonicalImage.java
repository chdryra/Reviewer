/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.Interfaces.Data.DataDate;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImage implements CanonicalDatumMaker<GvImageList.GvImage> {
    //Overridden
    @Override
    public GvImageList.GvImage getCanonical(GvDataList<GvImageList.GvImage> data) {
        GvImageList.GvImage nullImage = new GvImageList.GvImage(data.getGvReviewId(), null, null,
                "", false);
        if (data.size() == 0) return nullImage;

        GvImageList.GvImage reference = data.getItem(0);
        ComparitorGvImageBitmap comparitor = new ComparitorGvImageBitmap();
        DifferenceBoolean none = new DifferenceBoolean(false);
        DataDate finalDate = reference.getDate();
        for (GvImageList.GvImage image : data) {
            if (!comparitor.compare(reference, image).lessThanOrEqualTo(none)) return nullImage;
            DataDate imageDate = image.getDate();
            if (imageDate.getTime() > finalDate.getTime()) finalDate = imageDate;
        }

        DatumCounter<GvImageList.GvImage, String> captionCounter =
                new DatumCounter<>(data, new DataGetter<GvImageList.GvImage, String>() {
                    //Overridden
                    @Override
                    public String getData(GvImageList.GvImage datum) {
                        return datum.getCaption();
                    }
                });

        int num = captionCounter.getCount();
        String caption = String.valueOf(num) + " captions";
        if (num == 0) {
            caption = null;
        } else if (num == 1) {
            caption = captionCounter.getMaxItem();
        }

        GvReviewId id = data.getGvReviewId();
        GvDateList.GvDate finalGvDate = new GvDateList.GvDate(id, finalDate.getTime());
        return new GvImageList.GvImage(id, reference.getBitmap(), finalGvDate, caption, true);
    }
}
