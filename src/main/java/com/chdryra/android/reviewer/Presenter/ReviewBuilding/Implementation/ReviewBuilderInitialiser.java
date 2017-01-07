/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewBuilderInitialiser {
    private final ConverterGv mConverterGv;

    public ReviewBuilderInitialiser(ConverterGv converterGv) {
        mConverterGv = converterGv;
    }

    public void useTemplate(ReviewBuilder builder, Review template) {
        builder.setSubject(template.getSubject().getSubject());
        setCover(template, builder);
        setData(builder, GvTag.TYPE, mConverterGv.newConverterTags(), template.getTags());
        setData(builder, GvCriterion.TYPE, mConverterGv.newConverterCriteriaSubjects(),
                template.getCriteria());
        setData(builder, GvLocation.TYPE, mConverterGv.newConverterLocations(), template
                .getLocations());
        setData(builder, GvFact.TYPE, mConverterGv.newConverterFacts(), template.getFacts());
    }

    private <T1 extends HasReviewId, T2 extends GvData> void setData(ReviewBuilder builder,
                                                                     GvDataType<T2> dataType,
                                                                     DataConverter<T1, T2, ?
                                                                             extends
                                                                             GvDataList<T2>>
                                                                             converter,
                                                                     IdableList<? extends T1>
                                                                             data) {
        DataBuilder<T2> dataBuilder = builder.getDataBuilder(dataType);
        for (T1 datum : data) {
            dataBuilder.add(converter.convert(datum, null));
        }

        dataBuilder.commitData();
    }

    private void setCover(Review template, ReviewBuilder builder) {
        DataBuilder<GvImage> dataBuilder = builder.getDataBuilder(GvImage.TYPE);
        dataBuilder.add(mConverterGv.newConverterImages().convert(template.getCover(), null));
        dataBuilder.commitData();
    }
}
