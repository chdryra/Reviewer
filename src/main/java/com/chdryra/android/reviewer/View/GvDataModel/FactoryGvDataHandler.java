/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataHandler {
    public static <T extends GvData> GvDataHandler<T> newHandler
            (GvDataList<T> data) {
        if (data.getGvDataType() == GvImageList.GvImage.TYPE) {
            return new GvDataHandler<>(data, new GvDataHandler.AddConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return imageAdd(data, datum);
                }
            });
        } else if (data.getGvDataType() == GvChildList.GvChildReview.TYPE) {
            GvDataHandler.AddConstraint<T> add = new GvDataHandler.AddConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return childAdd(data, datum);
                }
            };

            GvDataHandler.ReplaceConstraint<T> replace = new GvDataHandler.ReplaceConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T oldDatum, T newDatum) {
                    return childReplace(data, oldDatum, newDatum);
                }
            };

            return new GvDataHandler<>(data, add, replace);
        } else {
            return new GvDataHandler<>(data);
        }
    }

    private static <T extends GvData> boolean imageAdd(GvDataList<T> data, T datum) {
        GvImageList.GvImage image = (GvImageList.GvImage) datum;
        GvImageList list = (GvImageList) data;
        return (image != null && list != null && !list.contains(image.getBitmap()));
    }

    private static <T extends GvData> boolean childAdd(GvDataList<T> data, T datum) {
        GvChildList.GvChildReview child = (GvChildList.GvChildReview) datum;
        GvChildList list = (GvChildList) data;
        return (child != null && list != null && !list.contains(child.getSubject()));
    }

    private static <T extends GvData> boolean childReplace(GvDataList<T> data,
            T oldDatum, T newDatum) {
        GvChildList.GvChildReview oldChild = (GvChildList.GvChildReview) oldDatum;
        GvChildList.GvChildReview newChild = (GvChildList.GvChildReview) newDatum;
        GvChildList list = (GvChildList) data;

        return (oldChild.getSubject().equals(newChild.getSubject()) || !list.contains(newChild
                .getSubject()));
    }
}
