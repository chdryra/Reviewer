/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImage implements CanonicalDatumMaker<DataImage> {
    //Overridden
    @Override
    public DataImage getCanonical(IdableList<DataImage> data) {
        String id = data.getReviewId();
        DatumImage nullImage = new DatumImage(id, null, null, "", false);
        if (data.size() == 0) return nullImage;

        DatumCounter<DataImage, String> captionCounter = getCaptionCounter(data);
        String caption = getCaption(captionCounter);
        DataImage lastEquivalentBitmap = getLastImage(data, nullImage);
        if(lastEquivalentBitmap == nullImage) return nullImage;

        DataDate finalDate = new DatumDateReview(id, lastEquivalentBitmap.getDate().getTime());

        return new DatumImage(id, lastEquivalentBitmap.getBitmap(), finalDate, caption, true);
    }

    private DataImage getLastImage(IdableList<DataImage> data, DatumImage nullImage) {
        DataImage reference = data.getItem(0);
        ComparitorImageBitmap comparitor = new ComparitorImageBitmap();
        DifferenceBoolean none = new DifferenceBoolean(false);
        DataImage lastImage = data.getItem(0);
        for (DataImage image : data) {
            if (!comparitor.compare(reference, image).lessThanOrEqualTo(none)) {
                lastImage = nullImage;
                break;
            }
            DataDate imageDate = image.getDate();
            if (imageDate.getTime() > lastImage.getDate().getTime()) lastImage = image;
        }
        return lastImage;
    }

    @Nullable
    private String getCaption(DatumCounter<DataImage, String> captionCounter) {
        int num = captionCounter.getCount();
        String caption = String.valueOf(num) + " captions";
        if (num == 0) {
            caption = null;
        } else if (num == 1) {
            caption = captionCounter.getMaxItem();
        }
        return caption;
    }

    @NonNull
    private DatumCounter<DataImage, String> getCaptionCounter(IdableList<DataImage> data) {
        return new DatumCounter<>(data, new DataGetter<DataImage, String>() {
            @Override
            public String getData(DataImage datum) {
                return datum.getCaption();
            }
        });
    }

}
