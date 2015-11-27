/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.DataBuilderImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterionList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataBuilder {
    private ConverterGv mConverter;

    public FactoryDataBuilder(ConverterGv converter) {
        mConverter = converter;
    }

    public <T extends GvData> DataBuilder<T> newDataBuilder
    (GvDataList<T> data) {
        GvDataType<T> dataType = data.getGvDataType();
        GvDataList<T> copied = mConverter.copy(data);
        if (dataType.equals(GvImage.TYPE)) {
            return new DataBuilderImpl<>(copied,
                    new DataBuilderImpl.AddConstraintImpl<T>() {
                //Overridden
                @Override
                public DataBuilder.ConstraintResult passes(GvDataList<T> data, T datum) {
                    return imageAdd(data, (GvImage) datum);
                }
            });
        } else if (dataType.equals(GvCriterion.TYPE)) {
            DataBuilder.AddConstraint<T> add = new DataBuilder.AddConstraint<T>() {
                //Overridden
                @Override
                public DataBuilder.ConstraintResult passes(GvDataList<T> data, T datum) {
                    return childAdd(data, (GvCriterion) datum);
                }
            };

            DataBuilder.ReplaceConstraint<T> replace = new DataBuilder.ReplaceConstraint<T>() {
                //Overridden
                @Override
                public DataBuilder.ConstraintResult passes(GvDataList<T> data, T oldDatum, T newDatum) {
                    return childReplace(data,
                            (GvCriterion) oldDatum,
                            (GvCriterion) newDatum);
                }
            };

            return new DataBuilderImpl<>(data, add, replace);
        } else if (dataType.equals(GvComment.TYPE)) {
            //TODO make type safe
            return (DataBuilder) new GvCommentHandler((GvDataList<GvComment>) copied);
        } else {
            return new DataBuilderImpl<>(copied);
        }
    }

    private static DataBuilder.ConstraintResult imageAdd(GvDataList list, GvImage image) {
        GvImageList images = (GvImageList) list;
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

    private static DataBuilder.ConstraintResult childAdd(GvDataList list,
                                                           GvCriterion child) {
        GvCriterionList children = (GvCriterionList) list;
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

    private static DataBuilder.ConstraintResult childReplace(GvDataList list,
                                        GvCriterion oldChild,
                                        GvCriterion newChild) {
        GvCriterionList children = (GvCriterionList) list;
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

    public static class GvCommentHandler extends DataBuilderImpl<GvComment> {
        //Constructors
        public GvCommentHandler(GvDataList<GvComment> data) {
            super(data);
        }

        //Overridden
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
