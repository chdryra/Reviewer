/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterGv {
    private ConvertersMap mMap;
    private GvConverterItemTags mItemTagsConverter;
    private GvConverterCriteria.SubjectOnly mCriteriaSubjectsConverter;

    public ConverterGv(GvConverterComments converterComments,
                       GvConverterFacts converterFacts,
                       GvConverterImages converterImages,
                       GvConverterLocations converterLocations,
                       GvConverterUrls converterUrl,
                       GvConverterCriteria converterCriteria,
                       GvConverterReviews converterReview,
                       GvConverterReviewReferences converterReferences,
                       GvConverterSubjects converterSubjects,
                       GvConverterAuthors converterAuthors,
                       GvConverterAuthorIds converterAuthorIds,
                       GvConverterDateReviews converterDates,
                       GvConverterDataTags converterTags,
                       GvConverterItemTags converterItemTags) {
        mMap = new ConvertersMap();
        mMap.add(converterComments.getOutputType(), converterComments);
        mMap.add(converterFacts.getOutputType(), converterFacts);
        mMap.add(converterImages.getOutputType(), converterImages);
        mMap.add(converterLocations.getOutputType(), converterLocations);
        mMap.add(converterUrl.getOutputType(), converterUrl);
        mMap.add(converterCriteria.getOutputType(), converterCriteria);
        mMap.add(converterReview.getOutputType(), converterReview);
        mMap.add(converterReferences.getOutputType(), converterReferences);
        mMap.add(converterSubjects.getOutputType(), converterSubjects);
        mMap.add(converterAuthors.getOutputType(), converterAuthors);
        mMap.add(converterAuthorIds.getOutputType(), converterAuthorIds);
        mMap.add(converterDates.getOutputType(), converterDates);
        mMap.add(converterTags.getOutputType(), converterTags);
        mItemTagsConverter = converterItemTags;
        mCriteriaSubjectsConverter = new GvConverterCriteria.SubjectOnly();
    }

    public <T extends GvData> DataConverter<?, T, ? extends GvDataList<T>>
    getConverter(GvDataType<T> dataType) {
        return mMap.get(dataType);
    }

    public <T extends GvData> DataConverter<? super T, T, ? extends GvDataList<T>>
    getCopier(GvDataType<T> dataType) {
        //TODO not sure how to enforce this is DataConverter spec...
        return (DataConverter<? super T, T, ? extends GvDataList<T>>) mMap.get(dataType);
    }

    public GvConverterImages getConverterImages() {
        return (GvConverterImages) getConverter(GvImage.TYPE);
    }

    public GvConverterReviews getConverterReviews() {
        return (GvConverterReviews) getConverter(GvReview.TYPE);
    }

    public GvConverterReviewReferences getConverterReferences() {
        return (GvConverterReviewReferences) getConverter(GvReviewRef.TYPE);
    }

    public GvConverterComments getConverterComments() {
        return (GvConverterComments) getConverter(GvComment.TYPE);
    }

    public GvConverterFacts getConverterFacts() {
        return (GvConverterFacts) getConverter(GvFact.TYPE);
    }

    public GvConverterLocations getConverterLocations() {
        return (GvConverterLocations) getConverter(GvLocation.TYPE);
    }

    public GvConverterUrls getConverterUrls() {
        return (GvConverterUrls) getConverter(GvUrl.TYPE);
    }

    public GvConverterCriteria getConverterCriteria() {
        return (GvConverterCriteria) getConverter(GvCriterion.TYPE);
    }

    public GvConverterCriteria.SubjectOnly getConverterCriteriaSubjects() {
        return mCriteriaSubjectsConverter;
    }

    public GvConverterSubjects getConverterSubjects() {
        return (GvConverterSubjects) getConverter(GvSubject.TYPE);
    }

    public GvConverterAuthors getConverterAuthors() {
        return (GvConverterAuthors) getConverter(GvAuthor.TYPE);
    }

    public GvConverterAuthorIds getConverterAuthorsIds() {
        return (GvConverterAuthorIds) getConverter(GvAuthorId.TYPE);
    }

    public GvConverterDateReviews getConverterDates() {
        return (GvConverterDateReviews) getConverter(GvDate.TYPE);
    }

    public GvConverterItemTags getConverterItemTags() {
        return mItemTagsConverter;
    }

    public GvConverterDataTags getConverterTags() {
        return (GvConverterDataTags) getConverter(GvTag.TYPE);
    }

    //Copy
    public <T extends GvData> GvDataList<T> copy(GvDataList<T> data) {
        DataConverter<? super T, T, ? extends GvDataList<T>> copier;
        copier = getCopier(data.getGvDataType());
        return copier.convert(data);
    }

    //To ensure type safety
    private class ConvertersMap {
        private final Map<GvDataType<? extends GvData>, DataConverter<?, ? extends GvData, ? extends GvDataList>>

                mConverters;

        private ConvertersMap() {
            mConverters = new HashMap<>();
        }

        private <T extends GvData> void add(GvDataType<T> type, DataConverter<?, T, ? extends GvDataList<T>> converter) {
            mConverters.put(type, converter);
        }

        //TODO make type safe although it is really....
        private <T extends GvData> DataConverter<?, T, ? extends GvDataList<T>> get(GvDataType<T> type) {
            return (DataConverter<?, T, ? extends GvDataList<T>>) mConverters.get(type);
        }
    }
}
