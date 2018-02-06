/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

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
        setCover(builder, template);
        setData(builder, GvTag.TYPE, mConverterGv.newConverterTags(), template.getTags());
        setData(builder, GvLocation.TYPE, mConverterGv.newConverterLocations(), template
                .getLocations());
        setData(builder, GvCriterion.TYPE, mConverterGv.newConverterCriteriaSubjects(),
                template.getCriteria());
        setData(builder, GvFact.TYPE, mConverterGv.newConverterFacts(), template.getFacts());
    }

    public void copy(ReviewBuilder builder, Review toCopy) {
        builder.setSubject(toCopy.getSubject().getSubject());
        builder.setRating(toCopy.getRating().getRating());
        setData(builder, GvTag.TYPE, mConverterGv.newConverterTags(), toCopy.getTags());
        setData(builder, GvComment.TYPE, mConverterGv.newConverterComments(), toCopy.getComments());
        setData(builder, GvImage.TYPE, mConverterGv.newConverterImages(), toCopy.getImages());
        setData(builder, GvLocation.TYPE, mConverterGv.newConverterLocations(), toCopy.getLocations());
        setData(builder, GvCriterion.TYPE, mConverterGv.newConverterCriteria(), toCopy.getCriteria());
        setData(builder, GvFact.TYPE, mConverterGv.newConverterFacts(), toCopy.getFacts());
    }

    private <T1 extends HasReviewId, T2 extends GvData> void
    setData(ReviewBuilder builder,
            GvDataType<T2> dataType,
            DataConverter<T1, T2, ? extends GvDataList<T2>> converter,
            IdableList<? extends T1> data) {
        DataBuilder<T2> dataBuilder = builder.getDataBuilder(dataType);
        dataBuilder.deleteAll();
        for (T1 datum : data) {
            dataBuilder.add(converter.convert(datum, null));
        }

        dataBuilder.commitData();
    }

    private void setCover(ReviewBuilder builder, Review template) {
        DataBuilder<GvImage> dataBuilder = builder.getDataBuilder(GvImage.TYPE);
        dataBuilder.add(mConverterGv.newConverterImages().convert(template.getCover(), null));
        dataBuilder.commitData();
    }
}
