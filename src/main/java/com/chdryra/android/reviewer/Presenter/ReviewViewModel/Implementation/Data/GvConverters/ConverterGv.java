/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDateList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSubjectList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrlList;

import java.util.ArrayList;
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
    private GvConverterCriteriaSubjects mCriteriaSubjectsConverter;

    public ConverterGv(GvConverterComments converterComments,
                       GvConverterFacts converterFacts,
                       GvConverterImages converterImages,
                       GvConverterLocations converterLocations,
                       GvConverterUrls converterUrl,
                       GvConverterCriteria converterCriteria,
                       GvConverterCriteriaSubjects converterCriteriaSubjects,
                       GvConverterReviews converterReview,
                       GvConverterReferences converterReferences,
                       GvConverterSubjects converterSubjects,
                       GvConverterAuthors converterAuthors,
                       GvConverterAuthorIds converterAuthorIds,
                       GvConverterDateReviews converterDates,
                       GvConverterDataTags converterTags,
                       GvConverterItemTags converterItemTags) {
        mMap = new ConvertersMap();
        mMap.add(converterComments.getDataType(), converterComments);
        mMap.add(converterFacts.getDataType(), converterFacts);
        mMap.add(converterImages.getDataType(), converterImages);
        mMap.add(converterLocations.getDataType(), converterLocations);
        mMap.add(converterUrl.getDataType(), converterUrl);
        mMap.add(converterCriteria.getDataType(), converterCriteria);
        mMap.add(converterReview.getDataType(), converterReview);
        mMap.add(converterReferences.getDataType(), converterReferences);
        mMap.add(converterSubjects.getDataType(), converterSubjects);
        mMap.add(converterAuthors.getDataType(), converterAuthors);
        mMap.add(converterAuthorIds.getDataType(), converterAuthorIds);
        mMap.add(converterDates.getDataType(), converterDates);
        mMap.add(converterTags.getDataType(), converterTags);
        mItemTagsConverter = converterItemTags;
        mCriteriaSubjectsConverter = converterCriteriaSubjects;
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

    public GvConverterReferences getConverterReferences() {
        return (GvConverterReferences) getConverter(GvReference.TYPE);
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

    public GvConverterCriteriaSubjects getConverterCriteriaSubjects() {
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

    //Comments
    public GvCommentList toGvCommentList(IdableList<? extends DataComment> comments) {
        return getConverterComments().convert(comments);
    }

    //Facts
    public GvFactList toGvFactList(IdableList<? extends DataFact> facts) {
        return getConverterFacts().convert(facts);
    }

    //Images
    public GvImageList toGvImageList(IdableList<? extends DataImage> images) {
        return getConverterImages().convert(images);
    }

    //Locations
    public GvLocationList toGvLocationList(IdableList<? extends DataLocation> locations) {
        return getConverterLocations().convert(locations);
    }

    //Urls
    public GvUrlList toGvUrlList(Iterable<? extends DataUrl> urls, ReviewId reviewId) {
        return getConverterUrls().convert(urls, reviewId);
    }

    //Criteria
    public GvCriterionList toGvCriterionList(IdableList<? extends DataCriterion> criteria) {
        return getConverterCriteria().convert(criteria);
    }

    public <T extends Review> GvReviewList toGvReviewList(IdableList<T> reviews) {
        return getConverterReviews().convert(reviews);
    }

    //Subjects
    public <T extends ReviewReference> GvSubjectList toGvSubjectList(Iterable<T> reviews, ReviewId reviewId) {
        ArrayList<DataSubject> subjects = new ArrayList<>();
        for(ReviewReference review : reviews) {
            subjects.add(review.getSubject());
        }
        return getConverterSubjects().convert(subjects, reviewId);
    }

    //Authors
    public <T extends ReviewReference> GvAuthorList toGvAuthorList(Iterable<T> reviews, ReviewId reviewId) {
        ArrayList<DataAuthor> authors = new ArrayList<>();
//        for(ReviewReference review : reviews) {
//            authors.add(review.getAuthorId());
//        }
        return getConverterAuthors().convert(authors, reviewId);
    }

    //Dates
    public <T extends ReviewReference> GvDateList toGvDateList(Iterable<T> reviews, ReviewId reviewId) {
        ArrayList<DataDate> dates = new ArrayList<>();
        for(ReviewReference review : reviews) {
            dates.add(review.getPublishDate());
        }
        return getConverterDates().convert(dates, reviewId);
    }

    //Tags
    public GvTagList toGvTagList(ItemTagCollection tags, ReviewId reviewId) {
        return getConverterItemTags().convert(tags, reviewId);
    }

    public GvTagList toGvTagList(IdableCollection<? extends DataTag> tags, ReviewId reviewId) {
        return getConverterTags().convert(tags, reviewId);
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
