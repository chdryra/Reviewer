package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthorReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.Interfaces.Data.DataDateReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.Interfaces.Data.DataUrl;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterGv {
    private GvConverterComments mConverterComments;
    private GvConverterFacts mConverterFacts;
    private GvConverterImages mConverterImages;
    private GvConverterLocations mConverterLocations;
    private GvConverterUrls mConverterUrl;
    private GvConverterCriteria mConverterCriteria;
    private GvConverterReviews mConverterReview;
    private GvConverterSubjects mConverterSubjects;
    private GvConverterAuthors mConverterAuthors;
    private GvConverterDateReviews mConverterDates;
    private GvConverterItemTags mConverterTags;

    public ConverterGv(GvConverterComments converterComments, GvConverterFacts converterFacts,
                       GvConverterImages converterImages, GvConverterLocations
                               converterLocations, GvConverterUrls converterUrl,
                       GvConverterCriteria converterCriteria, GvConverterReviews converterReview,
                       GvConverterSubjects converterSubjects, GvConverterAuthors
                               converterAuthors, GvConverterDateReviews converterDates,
                       GvConverterItemTags converterTags) {
        mConverterComments = converterComments;
        mConverterFacts = converterFacts;
        mConverterImages = converterImages;
        mConverterLocations = converterLocations;
        mConverterUrl = converterUrl;
        mConverterCriteria = converterCriteria;
        mConverterReview = converterReview;
        mConverterSubjects = converterSubjects;
        mConverterAuthors = converterAuthors;
        mConverterDates = converterDates;
        mConverterTags = converterTags;
    }

    public GvConverterComments getConverterComments() {
        return mConverterComments;
    }

    public GvConverterFacts getConverterFacts() {
        return mConverterFacts;
    }

    public GvConverterImages getConverterImages() {
        return mConverterImages;
    }

    public GvConverterLocations getConverterLocations() {
        return mConverterLocations;
    }

    public GvConverterUrls getConverterUrl() {
        return mConverterUrl;
    }

    public GvConverterCriteria getConverterCriteria() {
        return mConverterCriteria;
    }

    public GvConverterReviews getConverterReview() {
        return mConverterReview;
    }

    public GvConverterSubjects getConverterSubjects() {
        return mConverterSubjects;
    }

    public GvConverterAuthors getConverterAuthors() {
        return mConverterAuthors;
    }

    public GvConverterDateReviews getConverterDates() {
        return mConverterDates;
    }

    public GvConverterItemTags getConverterTags() {
        return mConverterTags;
    }

    //Comments
    public GvCommentList toGvCommentList(IdableList<? extends DataComment> comments) {
        return mConverterComments.convert(comments);
    }

    //Facts
    public GvFactList toGvFactList(IdableList<? extends DataFact> facts) {
        return mConverterFacts.convert(facts);
    }

    //Images
    public GvImageList toGvImageList(Iterable<? extends DataImage> images, String reviewId) {
        return mConverterImages.convert(images, reviewId);
    }

    public GvImageList toGvImageList(IdableList<? extends DataImage> images) {
        return mConverterImages.convert(images);
    }

    //Locations
    public GvLocationList toGvLocationList(IdableList<? extends DataLocation> locations) {

        return mConverterLocations.convert(locations);
    }

    //Urls
    public GvUrlList toGvUrlList(Iterable<? extends DataUrl> urls, String reviewId) {
        return mConverterUrl.convert(urls, reviewId);
    }

    //Criteria
    public GvCriterionList toGvCriterionList(IdableList<? extends DataCriterion> criteria) {
        return mConverterCriteria.convert(criteria);
    }

    public <T extends Review> GvReviewOverviewList toGvReviewOverviewList(IdableList<T> reviews) {
        return mConverterReview.convert(reviews);
    }

    //Subjects
    public <T extends Review> GvSubjectList toGvSubjectList(Iterable<T> reviews, String reviewId) {
        ArrayList<DataSubject> subjects = new ArrayList<>();
        for(Review review : reviews) {
            subjects.add(review.getSubject());
        }
        return mConverterSubjects.convert(subjects, reviewId);
    }

    //Authors
    public <T extends Review> GvAuthorList toGvAuthorList(Iterable<T> reviews, String reviewId) {
        ArrayList<DataAuthorReview> authors = new ArrayList<>();
        for(Review review : reviews) {
            authors.add(review.getAuthor());
        }
        return mConverterAuthors.convert(authors, reviewId);
    }

    //Dates
    public <T extends Review> GvDateList toGvDateList(Iterable<T> reviews, String reviewId) {
        ArrayList<DataDateReview> dates = new ArrayList<>();
        for(Review review : reviews) {
            dates.add(review.getPublishDate());
        }
        return mConverterDates.convert(dates, reviewId);
    }

    //Tags
    public GvTagList toGvTagList(ItemTagCollection tags, String reviewId) {
        return mConverterTags.convert(tags, reviewId);
    }

    //Copy
    public <T extends GvData> GvDataList<T> copy(GvDataList<T> data) {
        Class<?> dataClass = data.getClass();
        Constructor<?> con;
        try {
            con = dataClass.getConstructor(dataClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            return (GvDataList<T>) con.newInstance(data);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
