package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Adapter.DataConverters.GvConverterComments;
import com.chdryra.android.reviewer.Adapter.DataConverters.GvConverterCriteria;
import com.chdryra.android.reviewer.Adapter.DataConverters.GvConverterFacts;
import com.chdryra.android.reviewer.Adapter.DataConverters.GvConverterImages;
import com.chdryra.android.reviewer.Adapter.DataConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Adapter.DataConverters.GvConverterUrls;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataUrl;
import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDataList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdSubject;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdUrlList;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
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
    private GvConverterCriteria mGvConverterCriteria;

    public ConverterGv(GvConverterComments converterComments, GvConverterFacts converterFacts,
                       GvConverterImages converterImages, GvConverterLocations
                               converterLocations, GvConverterUrls converterUrl) {
        mConverterComments = converterComments;
        mConverterFacts = converterFacts;
        mConverterImages = converterImages;
        mConverterLocations = converterLocations;
        mConverterUrl = converterUrl;
    }

    //Comments
    public GvCommentList toGvCommentList(Iterable<? extends DataComment> comments,
                                         String reviewId) {
        return mConverterComments.convert(comments, reviewId);
    }

    //Facts
    public GvFactList toGvFactList(Iterable<? extends DataFact> facts, String reviewId) {
        return mConverterFacts.convert(facts, reviewId);
    }

    //Images
    public GvImageList toGvImageList(Iterable<? extends DataImage> images, String reviewId) {
        return mConverterImages.convert(images, reviewId);
    }

    //Locations
    public GvLocationList toGvLocationList(Iterable<? extends DataLocation> locations,
                                           String reviewId) {

        return mConverterLocations.convert(locations, reviewId);
    }

    //Urls
    public GvUrlList toGvUrlList(Iterable<? extends DataUrl> urls, String reviewId) {
        return mConverterUrl.convert(urls, reviewId);
    }

    //Criteria
    public GvCriterionList toGvCriterionList(IdableList<? extends DataCriterion> criteria, String reviewId) {
        return mGvConverterCriteria.convert(criteria, reviewId);
    }

    public <T extends Review> GvReviewOverviewList toGvReviewOverviewList(IdableCollection<T> reviews,
                                                                String reviewId,
                                                                TagsManager tagsManager) {
        GvReviewOverviewList data = new GvReviewOverviewList(new GvReviewId(holder.toString()));
        for (Review review : reviews) {
            data.add(toGvDataList(review, holder, tagsManager));
        }

        return data;
    }

    public <T extends Review> GvReviewOverviewList.GvReviewOverview toGvDataList(T review,
                                                                                 MdReviewId holder,
                                                                                 TagsManager
                                                                                         tagsManager) {
        GvReviewId id = new GvReviewId(holder.toString());
        GvImageList images = toGvDataList(review.getImages());
        GvCommentList headlines = toGvDataList(review.getComments()).getHeadlines();
        GvLocationList locations = toGvDataList(review.getLocations());

        Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                null;

        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocationList.GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }

        ArrayList<String> tags = tagsManager.getTags(review.getMdReviewId()).toStringArray();

        return new GvReviewOverviewList.GvReviewOverview(id, review.getMdReviewId().toString(),
                review.getAuthor(), review.getPublishDate().getDate(), review.getSubject().getSubject(),
                review.getRating().getRating(), cover, headline, locationNames, tags);
    }

    public GvSubjectList convertSubjects(MdIdableCollection<ReviewNode> nodes, MdReviewId holder) {
        MdDataList<MdSubject> mdsubjects = new MdDataList<>(holder);
        for (ReviewNode node : nodes) {
            mdsubjects.add(node.getSubject());
        }

        GvReviewId id = new GvReviewId(holder.toString());
        GvSubjectList subjects = new GvSubjectList(id);
        for (MdSubject mdSubject : mdsubjects) {
            GvReviewId subjectId = new GvReviewId(mdSubject.getReviewId().toString());
            String subject = mdSubject.getSubject();
            subjects.add(new GvSubjectList.GvSubject(subjectId, subject));
        }

        return subjects;
    }

    public GvAuthorList convertAuthors(MdIdableCollection<ReviewNode> nodes, MdReviewId holder) {
        GvReviewId id = new GvReviewId(holder.toString());
        GvAuthorList authors = new GvAuthorList(id);
        for (ReviewNode node : nodes) {
            GvReviewId childId = new GvReviewId(node.getMdReviewId().toString());
            Author author = node.getAuthor();
            authors.add(new GvAuthorList.GvAuthor(childId, author.getName(),
                    author.getUserId()));

        }

        return authors;
    }

    public GvDateList convertPublishDates(MdIdableCollection<ReviewNode> nodes, MdReviewId holder) {
        GvReviewId id = new GvReviewId(holder.toString());
        GvDateList list = new GvDateList(id);
        for (ReviewNode node : nodes) {
            GvReviewId childId = new GvReviewId(node.getMdReviewId().toString());
            PublishDate date = node.getPublishDate();
            list.add(new GvDateList.GvDate(childId, date.getDate()));
        }

        return list;
    }
//
//    public GvDataList copy(GvDataList data) {
//        GvDataType dataType = data.getGvDataType();
//        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
//            return new GvCommentList((GvCommentList) data);
//        } else if (dataType.equals(GvFactList.GvFact.TYPE)) {
//            return copyFactsOrUrls((GvFactList) data);
//        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
//            return new GvImageList((GvImageList) data);
//        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
//            return new GvLocationList((GvLocationList) data);
//        } else if (dataType.equals(GvUrlList.GvUrl.TYPE)) {
//            return new GvUrlList((GvUrlList) data);
//        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
//            return new GvTagList((GvTagList) data);
//        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
//            return new GvCriterionList((GvCriterionList) data);
//        } else {
//            return null;
//        }
//    }

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

    private GvFactList.GvFact getGvFactOrUrl(MdFactList.MdFact fact) {
        GvReviewId id = new GvReviewId(fact.getReviewId().toString());
        if (fact.isUrl()) {
            MdUrlList.MdUrl url = (MdUrlList.MdUrl) fact;
            return new GvUrlList.GvUrl(id, fact.getLabel(), url.getUrl());
        } else {
            return new GvFactList.GvFact(id, fact.getLabel(), fact.getValue());
        }
    }

    private MdFactList.MdFact getMdFactOrUrl(DataFact fact, MdReviewId holder) {
        if (fact.isUrl()) {
            DataUrl url = (DataUrl) fact;
            return new MdUrlList.MdUrl(holder, fact.getLabel(), url.getUrl());
        } else {
            return new MdFactList.MdFact(holder, fact.getLabel(), fact.getValue());
        }
    }
}
