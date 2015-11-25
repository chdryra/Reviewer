/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImage implements CanonicalDatumMaker<GvImage> {
    //Overridden
    @Override
    public GvImage getCanonical(GvDataList<GvImage> data) {
        GvImage nullImage = new GvImage(data.getGvReviewId(), null, null,
                "", false);
        if (data.size() == 0) return nullImage;

        GvImage reference = data.getItem(0);
        ComparitorGvImageBitmap comparitor = new ComparitorGvImageBitmap();
        DifferenceBoolean none = new DifferenceBoolean(false);
        DataDate finalDate = reference.getDate();
        for (GvImage image : data) {
            if (!comparitor.compare(reference, image).lessThanOrEqualTo(none)) return nullImage;
            DataDate imageDate = image.getDate();
            if (imageDate.getTime() > finalDate.getTime()) finalDate = imageDate;
        }

        DatumCounter<GvImage, String> captionCounter =
                new DatumCounter<>(data, new DataGetter<GvImage, String>() {
                    //Overridden
                    @Override
                    public String getData(GvImage datum) {
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
        GvDate finalGvDate = new GvDate(id, finalDate.getTime());
        return new GvImage(id, reference.getBitmap(), finalGvDate, caption, true);
    }
}
