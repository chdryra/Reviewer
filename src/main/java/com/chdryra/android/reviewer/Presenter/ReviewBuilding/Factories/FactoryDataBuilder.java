/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.AddConstraintDefault;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.DataBuilderImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataBuilder {
    private final FactoryGvData mDataFactory;

    public FactoryDataBuilder(FactoryGvData dataFactory) {
        mDataFactory = dataFactory;
    }

    public <T extends GvData> DataBuilder<T> newDataBuilder
    (final GvDataType<T> dataType) {
        GvDataList<T> data = mDataFactory.newDataList(dataType);
        if (dataType.equals(GvImage.TYPE)) {
            return newImageDataBuilder(data);
        } else if (dataType.equals(GvCriterion.TYPE)) {
            return newCriterionDataBuilder(data);
        } else if (dataType.equals(GvComment.TYPE)) {
            //TODO make type safe
            return (DataBuilder<T>) new GvCommentHandler((GvDataList<GvComment>) data);
        } else {
            return new DataBuilderImpl<>(data, mDataFactory);
        }
    }

    @NonNull
    private <T extends GvData> DataBuilder<T> newCriterionDataBuilder(GvDataList<T> data) {
        DataBuilder.AddConstraint<T> add = newCriterionAddConstraint();
        DataBuilder.ReplaceConstraint<T> replace = newCriterionReplaceConstraint();

        return new DataBuilderImpl<>(data, mDataFactory, add, replace);
    }

    @NonNull
    private <T extends GvData> DataBuilder.ReplaceConstraint<T> newCriterionReplaceConstraint() {
        return new DataBuilder.ReplaceConstraint<T>() {
                    @Override
                    public DataBuilder.ConstraintResult passes(GvDataList<T> data, T oldDatum, T newDatum) {
                        return childReplace((GvCriterionList) data,
                                (GvCriterion) oldDatum,
                                (GvCriterion) newDatum);
                    }
                };
    }

    @NonNull
    private <T extends GvData> DataBuilder.AddConstraint<T> newCriterionAddConstraint() {
        return new DataBuilder.AddConstraint<T>() {
                    @Override
                    public DataBuilder.ConstraintResult passes(GvDataList<T> data, T datum) {
                        return childAdd((GvCriterionList) data, (GvCriterion) datum);
                    }
                };
    }

    @NonNull
    private <T extends GvData> DataBuilder<T> newImageDataBuilder(GvDataList<T> data) {
        return new DataBuilderImpl<>(data, mDataFactory,
                new AddConstraintDefault<T>() {
            //Overridden
            @Override
            public DataBuilder.ConstraintResult passes(GvDataList<T> data, T datum) {
                return imageAdd((GvImageList) data, (GvImage) datum);
            }
        });
    }

    private static DataBuilder.ConstraintResult imageAdd(GvImageList images, GvImage image) {
        DataBuilder.ConstraintResult res;
        if(images == null) {
            res = DataBuilder.ConstraintResult.NULL_LIST;
        } else if (!image.isValidForDisplay()) {
            res = DataBuilder.ConstraintResult.INVALID_DATUM;
        } else {
            res = !images.contains(image.getBitmap()) ? DataBuilder.ConstraintResult.PASSED :
                    DataBuilder.ConstraintResult.HAS_DATUM;
        }

        return res;
    }

    private static DataBuilder.ConstraintResult childAdd(GvCriterionList children,
                                                           GvCriterion child) {
        DataBuilder.ConstraintResult res;
        if(children == null) {
            res = DataBuilder.ConstraintResult.NULL_LIST;
        } else if (child == null || !child.isValidForDisplay()) {
            res = DataBuilder.ConstraintResult.INVALID_DATUM;
        } else {
            res = !children.contains(child.getSubject()) ? DataBuilder.ConstraintResult.PASSED :
                    DataBuilder.ConstraintResult.HAS_DATUM;
        }
        return res;
    }

    private static DataBuilder.ConstraintResult childReplace(GvCriterionList children,
                                        GvCriterion oldChild,
                                        GvCriterion newChild) {
        DataBuilder.ConstraintResult res;
        if(children == null) {
            res = DataBuilder.ConstraintResult.NULL_LIST;
        } else if (oldChild == null || !oldChild.isValidForDisplay() ||
                newChild == null || !newChild.isValidForDisplay()) {
            res = DataBuilder.ConstraintResult.INVALID_DATUM;
        } else {
            boolean passed = (oldChild.getSubject().equals(newChild.getSubject())
                    || !children.contains(newChild.getSubject()));
            res = passed ? DataBuilder.ConstraintResult.PASSED :
                    DataBuilder.ConstraintResult.HAS_DATUM;
        }
        return res;
    }

    private class GvCommentHandler extends DataBuilderImpl<GvComment> {
        private GvCommentHandler(GvDataList<GvComment> data) {
            super(data, mDataFactory);
        }

        @Override
        public ConstraintResult add(GvComment newDatum) {
            if (getData().size() == 0) newDatum.setIsHeadline(true);
            return super.add(newDatum);
        }

        @Override
        public ConstraintResult replace(GvComment oldDatum,
                                        GvComment newDatum) {
            newDatum.setIsHeadline(oldDatum.isHeadline());
            return super.replace(oldDatum, newDatum);
        }

        @Override
        public void delete(GvComment data) {
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
