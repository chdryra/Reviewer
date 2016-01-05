/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImage implements CanonicalDatumMaker<DataImage> {
    //Overridden
    @Override
    public DataImage getCanonical(IdableList<? extends DataImage> data) {
        ReviewId id = data.getReviewId();
        DatumImage nullImage = new DatumImage(id);
        if (data.size() == 0) return nullImage;

        String caption = getCaption(data);
        DataImage lastEquivalentBitmap = getLastImage(data, nullImage);
        if(lastEquivalentBitmap == nullImage) return nullImage;
        DataDate finalDate = new DatumDateReview(id, lastEquivalentBitmap.getDate().getTime());

        return new DatumImage(id, lastEquivalentBitmap.getBitmap(), finalDate, caption, true);
    }

    private String getCaption(IdableList<? extends DataImage> data) {
        ItemCounter<DataImage, String> captionCounter = getCaptionCounter();
        captionCounter.performCount(data);
        int num = captionCounter.getCount();

        String caption = String.valueOf(num) + " captions";
        if (num == 0) {
            caption = "";
        } else if (num == 1) {
            caption = captionCounter.getModeItem();
        }
        return caption;
    }

    private DataImage getLastImage(IdableList<? extends DataImage> data, DatumImage nullImage) {
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

    @NonNull
    private ItemCounter<DataImage, String> getCaptionCounter() {
        return new ItemCounter<>(new ItemGetter<DataImage, String>() {
            @Override
            public String getData(DataImage datum) {
                return datum.getCaption();
            }
        });
    }

}
