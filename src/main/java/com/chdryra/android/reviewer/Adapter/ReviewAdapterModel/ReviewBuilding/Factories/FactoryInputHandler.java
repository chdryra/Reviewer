/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.InputHandlerImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.InputHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryInputHandler {
    //Static methods
    public static <T extends GvData> InputHandler<T> newHandler
    (GvDataList<T> data) {
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType.equals(GvImageList.GvImage.TYPE)) {
            return new InputHandlerImpl<>(data, new InputHandlerImpl.AddConstraintImpl<T>() {
                //Overridden
                @Override
                public InputHandler.ConstraintResult passes(GvDataList<T> data, T datum) {
                    return imageAdd(data, (GvImageList.GvImage) datum);
                }
            });
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            InputHandler.AddConstraint<T> add = new InputHandler.AddConstraint<T>() {
                //Overridden
                @Override
                public InputHandler.ConstraintResult passes(GvDataList<T> data, T datum) {
                    return childAdd(data, (GvCriterionList.GvCriterion) datum);
                }
            };

            InputHandler.ReplaceConstraint<T> replace = new InputHandler.ReplaceConstraint<T>() {
                //Overridden
                @Override
                public InputHandler.ConstraintResult passes(GvDataList<T> data, T oldDatum, T newDatum) {
                    return childReplace(data,
                            (GvCriterionList.GvCriterion) oldDatum,
                            (GvCriterionList.GvCriterion) newDatum);
                }
            };

            return new InputHandlerImpl<>(data, add, replace);
        } else if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            //TODO make type safe
            return (InputHandler) new GvCommentHandler(data);
        } else {
            return new InputHandlerImpl<>(data);
        }
    }

    private static InputHandler.ConstraintResult imageAdd(GvDataList list, GvImageList.GvImage image) {
        GvImageList images = (GvImageList) list;
        InputHandler.ConstraintResult res;
        if(images == null) {
            res = InputHandler.ConstraintResult.NULL_LIST;
        } else if (!image.isValidForDisplay()) {
            res = InputHandler.ConstraintResult.INVALID_DATUM;
        } else {
            res = !images.contains(image.getBitmap()) ? InputHandler.ConstraintResult.PASSED :
                    InputHandler.ConstraintResult.HAS_DATUM;
        }

        return res;
    }

    private static InputHandler.ConstraintResult childAdd(GvDataList list,
                                                           GvCriterionList.GvCriterion child) {
        GvCriterionList children = (GvCriterionList) list;
        InputHandler.ConstraintResult res;
        if(children == null) {
            res = InputHandler.ConstraintResult.NULL_LIST;
        } else if (child == null || !child.isValidForDisplay()) {
            res = InputHandler.ConstraintResult.INVALID_DATUM;
        } else {
            res = !children.contains(child.getSubject()) ? InputHandler.ConstraintResult.PASSED :
                    InputHandler.ConstraintResult.HAS_DATUM;
        }
        return res;
    }

    private static InputHandler.ConstraintResult childReplace(GvDataList list,
                                        GvCriterionList.GvCriterion oldChild,
                                        GvCriterionList.GvCriterion newChild) {
        GvCriterionList children = (GvCriterionList) list;
        InputHandler.ConstraintResult res;
        if(children == null) {
            res = InputHandler.ConstraintResult.NULL_LIST;
        } else if (oldChild == null || !oldChild.isValidForDisplay() ||
                newChild == null || !newChild.isValidForDisplay()) {
            res = InputHandler.ConstraintResult.INVALID_DATUM;
        } else {
            boolean passed = (oldChild.getSubject().equals(newChild.getSubject())
                    || !children.contains(newChild.getSubject()));
            res = passed ? InputHandler.ConstraintResult.PASSED :
                    InputHandler.ConstraintResult.HAS_DATUM;
        }
        return res;
    }

    private static class GvCommentHandler extends InputHandlerImpl<GvCommentList.GvComment> {
        //Constructors
        public GvCommentHandler(GvDataList data) {
            super((GvCommentList) data);
        }

        //Overridden
        @Override
        public ConstraintResult add(GvCommentList.GvComment newDatum) {
            if (getData().size() == 0) newDatum.setIsHeadline(true);
            return super.add(newDatum);
        }

        @Override
        public ConstraintResult replace(GvCommentList.GvComment oldDatum,
                                        GvCommentList.GvComment newDatum) {
            newDatum.setIsHeadline(oldDatum.isHeadline());
            return super.replace(oldDatum, newDatum);
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
