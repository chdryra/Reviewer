/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
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
        T4 extends GvDataRef<T4, T1, ?>>
        extends GvConverterBasic<T1, T2, T3> {

    private GvDataType<T4> mReferenceType;

    protected abstract T4 convertReference(ReviewItemReference<T1> reference);

    public GvConverterReviewData(Class<T3> listClass, GvDataType<T4> referenceType) {
        super(listClass);
        mReferenceType = referenceType;
    }

    public GvConverterReferences<T1, T4> getReferencesConverter() {
        return new GvConverterReferences<T1, T4>(mReferenceType) {
            @Override
            public T4 convert(ReviewItemReference<T1> datum, ReviewId reviewId) {
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
}
