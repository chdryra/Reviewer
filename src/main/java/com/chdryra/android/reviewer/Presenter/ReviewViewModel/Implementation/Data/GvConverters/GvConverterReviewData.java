/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterReviewData<T1 extends HasReviewId,
        T2 extends GvDataParcelable,
        T3 extends GvDataListParcelable<T2>,
        T4 extends GvDataRef<T4, T1, ?>, Reference extends ReviewItemReference<T1>>
        extends GvConverterBasic<T1, T2, T3> {

    private final GvDataType<T4> mReferenceType;

    protected abstract T4 convertReference(Reference reference);

    GvConverterReviewData(Class<T3> listClass, GvDataType<T4> referenceType) {
        super(listClass);
        mReferenceType = referenceType;
    }

    public GvConverterReferences<T1, T4, Reference> getReferencesConverter() {
        return new GvConverterReferences<T1, T4, Reference>(mReferenceType) {
            @Override
            public T4 convert(Reference datum, ReviewId reviewId) {
                return convertReference(datum);
            }
        };
    }

    @Override
    public abstract T2 convert(T1 datum, ReviewId reviewId);

    @Override
    public T2 convert(T1 datum) {
        return convert(datum, datum.getReviewId());
    }

    abstract static class RefDataList<T1 extends HasReviewId,
            T2 extends GvDataParcelable,
            T3 extends GvDataListParcelable<T2>,
            T4 extends GvDataRef<T4, T1, ?>>
            extends GvConverterReviewData<T1, T2, T3, T4, ReviewItemReference<T1>> {

        RefDataList(Class<T3> listClass, GvDataType<T4> referenceType) {
            super(listClass, referenceType);
        }
    }

    abstract static class RefCommentList<T2 extends GvDataParcelable,
            T3 extends GvDataListParcelable<T2>,
            T4 extends GvDataRef<T4, DataComment, ?>>
            extends GvConverterReviewData<DataComment, T2, T3, T4, RefComment> {

        RefCommentList(Class<T3> listClass, GvDataType<T4> referenceType) {
            super(listClass, referenceType);
        }
    }
}
