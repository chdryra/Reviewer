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
public class InputHandlerFactory {
    public static <T extends GvDataList.GvData> InputHandlerGvData<T> newInputHandler
            (GvDataList<T> data) {
        if (data.getGvType() == GvDataList.GvType.IMAGES) {
            return new InputHandlerGvData<>(data, new InputHandlerGvData.AddConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return imagePasses(data, datum);
                }
            });
        } else if (data.getGvType() == GvDataList.GvType.CHILDREN) {
            return new InputHandlerGvData<>(data, new InputHandlerGvData.AddConstraint<T>() {
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return childPasses(data, datum);
                }
            });
        } else {
            return new InputHandlerGvData<>(data);
        }
    }

    public static <T extends GvDataList.GvData> InputHandlerGvData<T> newInputHandler
            (Class<? extends GvDataList<T>> dataClass) {
        try {
            return newInputHandler(dataClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <T extends GvDataList.GvData> boolean imagePasses(GvDataList<T> data, T datum) {
        GvImageList.GvImage image = (GvImageList.GvImage) datum;
        GvImageList list = (GvImageList) data;
        return (image != null && list != null && !list.contains(image.getBitmap()));
    }

    private static <T extends GvDataList.GvData> boolean childPasses(GvDataList<T> data, T datum) {
        GvSubjectRatingList.GvSubjectRating child = (GvSubjectRatingList.GvSubjectRating) datum;
        GvSubjectRatingList list = (GvSubjectRatingList) data;
        return (child != null && list != null && !list.contains(child.getSubject()));
    }
}
