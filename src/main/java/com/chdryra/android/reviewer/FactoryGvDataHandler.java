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
public class FactoryGvDataHandler {
    public static <T extends GvDataList.GvData> GvDataHandler<T> newHandler
            (GvDataList<T> data) {
        if (data.getGvType() == GvDataList.GvType.IMAGES) {
            return new GvDataHandler<>(data, new GvDataHandler.AddConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return imagePasses(data, datum);
                }
            });
        } else if (data.getGvType() == GvDataList.GvType.CHILDREN) {
            GvDataHandler.AddConstraint<T> add = new GvDataHandler.AddConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return childPasses(data, datum);
                }
            };

            GvDataHandler.ReplaceConstraint<T> replace = new GvDataHandler.ReplaceConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T oldDatum, T newDatum) {
                    return childPasses(data, newDatum);
                }
            };

            return new GvDataHandler<>(data, add, replace);
        } else {
            return new GvDataHandler<>(data);
        }
    }

    private static <T extends GvDataList.GvData> boolean imagePasses(GvDataList<T> data, T datum) {
        GvImageList.GvImage image = (GvImageList.GvImage) datum;
        GvImageList list = (GvImageList) data;
        return (image != null && list != null && !list.contains(image.getBitmap()));
    }

    private static <T extends GvDataList.GvData> boolean childPasses(GvDataList<T> data, T datum) {
        GvChildrenList.GvChildReview child = (GvChildrenList.GvChildReview) datum;
        GvChildrenList list = (GvChildrenList) data;
        return (child != null && list != null && !list.contains(child.getSubject()));
    }
}
