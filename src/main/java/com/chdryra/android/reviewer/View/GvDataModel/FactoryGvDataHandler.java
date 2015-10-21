/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataHandler {
    //Static methods
    public static <T extends GvData> GvDataHandler<T> newHandler
    (GvDataList<T> data) {
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType.equals(GvImageList.GvImage.TYPE)) {
            return new GvDataHandler<>(data, new GvDataHandler.AddConstraint<T>() {
                //Overridden
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return imageAdd(data, (GvImageList.GvImage) datum);
                }
            });
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            GvDataHandler.AddConstraint<T> add = new GvDataHandler.AddConstraint<T>() {
//Overridden
                @Override
                public boolean passes(GvDataList<T> data, T datum) {
                    return childAdd(data, (GvCriterionList.GvCriterion) datum);
                }
            };

            GvDataHandler.ReplaceConstraint<T> replace = new GvDataHandler.ReplaceConstraint<T>() {
                //Overridden
                @Override
                public boolean passes(GvDataList<T> data, T oldDatum, T newDatum) {
                    return childReplace(data,
                            (GvCriterionList.GvCriterion) oldDatum,
                            (GvCriterionList.GvCriterion) newDatum);
                }
            };

            return new GvDataHandler<>(data, add, replace);
        } else if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            //TODO make type safe
            return (GvDataHandler) new GvCommentHandler(data);
        } else {
            return new GvDataHandler<>(data);
        }
    }

    private static boolean imageAdd(GvDataList list, GvImageList.GvImage image) {
        GvImageList images = (GvImageList) list;
        return (image != null && list != null && !images.contains(image.getBitmap()));
    }

    private static boolean childAdd(GvDataList list, GvCriterionList.GvCriterion child) {
        GvCriterionList children = (GvCriterionList) list;
        return (child != null && list != null && !children.contains(child.getSubject()));
    }

    private static boolean childReplace(GvDataList list,
                                        GvCriterionList.GvCriterion oldChild,
                                        GvCriterionList.GvCriterion newChild) {
        GvCriterionList children = (GvCriterionList) list;
        return (oldChild.getSubject().equals(newChild.getSubject()) || !children.contains(newChild
                .getSubject()));
    }

    private static class GvCommentHandler extends GvDataHandler<GvCommentList.GvComment> {
        //Constructors
        public GvCommentHandler(GvDataList data) {
            super((GvCommentList) data);
        }

        //Overridden
        @Override
        public boolean add(GvCommentList.GvComment newDatum, Context context) {
            if (getData().size() == 0) newDatum.setIsHeadline(true);
            return super.add(newDatum, context);
        }

        @Override
        public void replace(GvCommentList.GvComment oldDatum, GvCommentList.GvComment newDatum,
                            Context context) {
            newDatum.setIsHeadline(oldDatum.isHeadline());
            super.replace(oldDatum, newDatum, context);
        }

        @Override
        public void delete(GvCommentList.GvComment data) {
            super.delete(data);
            if (data.isHeadline()) {
                data.setIsHeadline(false);
                GvCommentList comments = (GvCommentList) getData();
                if (comments.getHeadlines().size() == 0 && comments.size() > 0) {
                    comments.getItem(0).setIsHeadline(true);
                }
            }
        }
    }
}
