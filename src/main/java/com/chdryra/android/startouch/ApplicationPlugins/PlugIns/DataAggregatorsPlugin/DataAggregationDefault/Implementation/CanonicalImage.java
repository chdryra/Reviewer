/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.corelibrary.Aggregation.ItemCounter;
import com.chdryra.android.corelibrary.Aggregation.ItemGetter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImage implements CanonicalDatumMaker<DataImage> {
    @Override
    public DataImage getCanonical(IdableList<? extends DataImage> data) {
        ReviewId id = data.getReviewId();
        DataImage nullImage = FactoryNullData.nullImage(id);
        if (data.size() == 0) return nullImage;

        String caption = getCaption(data);
        DataImage lastEquivalentBitmap = getLastImage(data, nullImage);
        if (lastEquivalentBitmap == nullImage) return nullImage;
        DateTime date = lastEquivalentBitmap.getDate();
        DateTime finalDate = new DatumDate(id, date.getTime());

        return new DatumImage(id, lastEquivalentBitmap.getBitmap(), finalDate, caption,
                lastEquivalentBitmap.getLatLng(), true);
    }

    @NonNull
    private ItemCounter<DataImage, String> getCaptionCounter() {
        return new ItemCounter<>(new ItemGetter<DataImage, String>() {
            @Override
            public String getItem(DataImage datum) {
                return datum.getCaption();
            }
        });
    }

    private String getCaption(IdableList<? extends DataImage> data) {
        ItemCounter<DataImage, String> captionCounter = getCaptionCounter();
        captionCounter.performCount(data);
        int num = captionCounter.getCountOfItemTypes();

        String caption = String.valueOf(num) + " captions";
        if (num == 0) {
            caption = "";
        } else if (num == 1) {
            caption = captionCounter.getModeItem();
        }
        return caption;
    }

    private DataImage getLastImage(IdableList<? extends DataImage> data, DataImage nullImage) {
        DataImage reference = data.get(0);
        ComparatorImageBitmap comparitor = new ComparatorImageBitmap();
        DifferenceBoolean none = new DifferenceBoolean(false);

        DataImage lastImage = null;
        DateTime lastDate = null;
        int i = 0;
        while (lastDate == null && i < data.size()) {
            lastImage = data.get(i++);
            lastDate = lastImage.getDate();
        }

        if (lastDate == null) return reference;

        for (int j = i; j < data.size(); ++j) {
            DataImage image = data.get(j);
            DateTime imageDate = image.getDate();

            if (!comparitor.compare(reference, image).lessThanOrEqualTo(none)) {
                lastImage = nullImage;
                break;
            }

            if (imageDate == null) continue;

            if (imageDate.getTime() > lastDate.getTime()) {
                lastImage = image;
                lastDate = imageDate;
            }
        }

        return lastImage;
    }

}
