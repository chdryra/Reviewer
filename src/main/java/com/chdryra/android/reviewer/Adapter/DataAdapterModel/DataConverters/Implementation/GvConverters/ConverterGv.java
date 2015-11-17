package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces
        .DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

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

    public ConverterGv(GvConverterComments converterComments, GvConverterFacts converterFacts,
                       GvConverterImages converterImages, GvConverterLocations
                               converterLocations, GvConverterUrls converterUrl,
                       GvConverterCriteria converterCriteria, GvConverterReviews converterReview,
                       GvConverterSubjects converterSubjects, GvConverterAuthors
                               converterAuthors, GvConverterDateReviews converterDates,
                       GvConverterItemTags converterTags) {
        mMap = new ConvertersMap();
        mMap.add(converterComments.getDataType(), converterComments);
        mMap.add(converterFacts.getDataType(), converterFacts);
        mMap.add(converterImages.getDataType(), converterImages);
        mMap.add(converterLocations.getDataType(), converterLocations);
        mMap.add(converterUrl.getDataType(), converterUrl);
        mMap.add(converterCriteria.getDataType(), converterCriteria);
        mMap.add(converterReview.getDataType(), converterReview);
        mMap.add(converterSubjects.getDataType(), converterSubjects);
        mMap.add(converterAuthors.getDataType(), converterAuthors);
        mMap.add(converterDates.getDataType(), converterDates);
        mMap.add(converterTags.getDataType(), converterTags);
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
        return (GvConverterImages) getConverter(GvImageList.GvImage.TYPE);
    }

    public GvConverterReviews getConverterReviews() {
        return (GvConverterReviews) getConverter(GvReviewOverviewList.GvReviewOverview.TYPE);
    }

    public GvConverterComments getConverterComments() {
        return (GvConverterComments) getConverter(GvCommentList.GvComment.TYPE);
    }

    public GvConverterFacts getConverterFacts() {
        return (GvConverterFacts) getConverter(GvFactList.GvFact.TYPE);
    }

    public GvConverterLocations getConverterLocations() {
        return (GvConverterLocations) getConverter(GvLocationList.GvLocation.TYPE);
    }

    public GvConverterUrls getConverterUrls() {
        return (GvConverterUrls) getConverter(GvUrlList.GvUrl.TYPE);
    }

    public GvConverterCriteria getConverterCriteria() {
        return (GvConverterCriteria) getConverter(GvCriterionList.GvCriterion.TYPE);
    }

    public GvConverterSubjects getConverterSubjects() {
        return (GvConverterSubjects) getConverter(GvSubjectList.GvSubject.TYPE);
    }

    public GvConverterAuthors getConverterAuthors() {
        return (GvConverterAuthors) getConverter(GvAuthorList.GvAuthor.TYPE);
    }

    public GvConverterDateReviews getConverterDates() {
        return (GvConverterDateReviews) getConverter(GvDateList.GvDate.TYPE);
    }

    public GvConverterItemTags getConverterTags() {
        return (GvConverterItemTags) getConverter(GvTagList.GvTag.TYPE);
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
    public GvUrlList toGvUrlList(Iterable<? extends DataUrl> urls, String reviewId) {
        return getConverterUrls().convert(urls, reviewId);
    }

    //Criteria
    public GvCriterionList toGvCriterionList(IdableList<? extends DataCriterion> criteria) {
        return getConverterCriteria().convert(criteria);
    }

    public <T extends Review> GvReviewOverviewList toGvReviewOverviewList(IdableList<T> reviews) {
        return getConverterReviews().convert(reviews);
    }

    //Subjects
    public <T extends Review> GvSubjectList toGvSubjectList(Iterable<T> reviews, String reviewId) {
        ArrayList<DataSubject> subjects = new ArrayList<>();
        for(Review review : reviews) {
            subjects.add(review.getSubject());
        }
        return getConverterSubjects().convert(subjects, reviewId);
    }

    //Authors
    public <T extends Review> GvAuthorList toGvAuthorList(Iterable<T> reviews, String reviewId) {
        ArrayList<DataAuthorReview> authors = new ArrayList<>();
        for(Review review : reviews) {
            authors.add(review.getAuthor());
        }
        return getConverterAuthors().convert(authors, reviewId);
    }

    //Dates
    public <T extends Review> GvDateList toGvDateList(Iterable<T> reviews, String reviewId) {
        ArrayList<DataDateReview> dates = new ArrayList<>();
        for(Review review : reviews) {
            dates.add(review.getPublishDate());
        }
        return getConverterDates().convert(dates, reviewId);
    }

    //Tags
    public GvTagList toGvTagList(ItemTagCollection tags, String reviewId) {
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
