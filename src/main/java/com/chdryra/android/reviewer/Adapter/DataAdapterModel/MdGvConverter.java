/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
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
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//TODO how to make this stuff more generic? Type erasure issue for overloading.
public class MdGvConverter {
    //Comments
    public GvCommentList convert(MdCommentList comments) {
        GvCommentList list = new GvCommentList(GvReviewId.getId(comments.getReviewId().toString()));
        for (MdCommentList.MdComment comment : comments) {
            list.add(new GvCommentList.GvComment(GvReviewId.getId(comment.getReviewId().toString()),
                    comment.getComment(), comment.isHeadline()));
        }

        return list;
    }

    public MdCommentList toMdCommentList(Iterable<? extends DataComment> comments,
                                                ReviewId holder) {
        MdCommentList list = new MdCommentList(holder);
        for (DataComment comment : comments) {
            list.add(new MdCommentList.MdComment(comment.getComment(), comment.isHeadline(),
                    holder));
        }

        return list;
    }

    //Facts
    public GvFactList convert(MdFactList facts) {
        GvFactList list = new GvFactList(GvReviewId.getId(facts.getReviewId().toString()));
        for (MdFactList.MdFact fact : facts) {
            list.add(getGvFactOrUrl(fact));
        }

        return list;
    }

    public MdFactList toMdFactList(Iterable<? extends DataFact> facts, ReviewId holder) {
        MdFactList list = new MdFactList(holder);
        for (DataFact fact : facts) {
            list.add(getMdFactOrUrl(fact, holder));
        }

        return list;
    }

    //Images
    public GvImageList convert(MdImageList images) {
        GvImageList list = new GvImageList(GvReviewId.getId(images.getReviewId().toString()));
        for (MdImageList.MdImage image : images) {
            GvReviewId id = GvReviewId.getId(image.getReviewId().toString());
            GvImageList.GvImage i = new GvImageList.GvImage(id, image.getBitmap
                    (), image.getDate(), image.getCaption(), image.isCover());
            list.add(i);
        }

        return list;
    }

    public MdImageList toMdImageList(Iterable<? extends DataImage> images, ReviewId holder) {
        MdImageList list = new MdImageList(holder);
        for (DataImage image : images) {
            list.add(new MdImageList.MdImage(image.getBitmap(), image.getDate(),
                    image.getCaption(), image.isCover(), holder));
        }

        return list;
    }

    //Locations
    public GvLocationList convert(MdLocationList locations) {
        GvLocationList list = new GvLocationList(GvReviewId.getId(locations.getReviewId()
                .toString()));
        for (MdLocationList.MdLocation location : locations) {
            GvReviewId id = GvReviewId.getId(location.getReviewId().toString());
            list.add(new GvLocationList.GvLocation(id, location.getLatLng(), location.getName()));
        }

        return list;
    }

    public MdLocationList toMdLocationList(Iterable<? extends DataLocation> locations,
                                                  ReviewId holder) {
        MdLocationList list = new MdLocationList(holder);
        for (DataLocation location : locations) {
            list.add(new MdLocationList.MdLocation(location.getLatLng(), location.getName(),
                    holder));
        }

        return list;
    }

    //Urls
    public GvUrlList convert(MdUrlList urls) {
        GvUrlList list = new GvUrlList(GvReviewId.getId(urls.getReviewId().toString()));
        for (MdUrlList.MdUrl url : urls) {
            GvReviewId id = GvReviewId.getId(url.getReviewId().toString());
            list.add(new GvUrlList.GvUrl(id, url.getLabel(), url.getUrl()));
        }

        return list;
    }

    public MdUrlList toMdUrlList(Iterable<? extends DataUrl> urls, ReviewId holder) {
        MdUrlList list = new MdUrlList(holder);
        for (DataUrl url : urls) {
            list.add(new MdUrlList.MdUrl(url.getLabel(), url.getUrl(), holder));
        }

        return list;
    }

    //Criteria
    public GvCriterionList convert(MdCriterionList criteria) {
        GvCriterionList list = new GvCriterionList(GvReviewId.getId(criteria.getReviewId()
                .toString()));
        for (MdCriterionList.MdCriterion criterion : criteria) {
            GvReviewId id = GvReviewId.getId(criterion.getReviewId().toString());
            list.add(new GvCriterionList.GvCriterion(id, criterion.getSubject(), criterion
                    .getRating()));
        }

        return list;
    }

    public <T extends Review> GvReviewOverviewList convert(IdableList<T> reviews,
                                                                  ReviewId holder,
                                                                  TagsManager tagsManager) {
        GvReviewOverviewList data = new GvReviewOverviewList(GvReviewId.getId(holder.toString()));
        for (Review review : reviews) {
            data.add(convert(review, holder, tagsManager));
        }

        return data;
    }

    public <T extends Review> GvReviewOverviewList.GvReviewOverview convert(T review,
                                                                                   ReviewId holder,
                                                                                   TagsManager
                                                                                           tagsManager) {
        GvReviewId id = GvReviewId.getId(holder.toString());
        GvImageList images = convert(review.getImages());
        GvCommentList headlines = convert(review.getComments()).getHeadlines();
        GvLocationList locations = convert(review.getLocations());

        Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                null;

        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocationList.GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }

        ArrayList<String> tags = tagsManager.getTags(review.getId()).toStringArray();

        return new GvReviewOverviewList.GvReviewOverview(id, review.getId().toString(),
                review.getAuthor(), review.getPublishDate().getDate(), review.getSubject().get(),
                review.getRating().getValue(), cover, headline, locationNames, tags);
    }

    public GvSubjectList convertSubjects(IdableList<ReviewNode> nodes, ReviewId holder) {
        MdDataList<MdSubject> mdsubjects = new MdDataList<>(holder);
        for (ReviewNode node : nodes) {
            mdsubjects.add(node.getSubject());
        }

        GvReviewId id = GvReviewId.getId(holder.toString());
        GvSubjectList subjects = new GvSubjectList(id);
        for (MdSubject mdSubject : mdsubjects) {
            GvReviewId subjectId = GvReviewId.getId(mdSubject.getReviewId().toString());
            String subject = mdSubject.get();
            subjects.add(new GvSubjectList.GvSubject(subjectId, subject));
        }

        return subjects;
    }

    public GvAuthorList convertAuthors(IdableList<ReviewNode> nodes, ReviewId holder) {
        GvReviewId id = GvReviewId.getId(holder.toString());
        GvAuthorList authors = new GvAuthorList(id);
        for (ReviewNode node : nodes) {
            GvReviewId childId = GvReviewId.getId(node.getId().toString());
            Author author = node.getAuthor();
            authors.add(new GvAuthorList.GvAuthor(childId, author.getName(),
                    author.getUserId().toString()));

        }

        return authors;
    }

    public GvDateList convertPublishDates(IdableList<ReviewNode> nodes, ReviewId holder) {
        GvReviewId id = GvReviewId.getId(holder.toString());
        GvDateList list = new GvDateList(id);
        for (ReviewNode node : nodes) {
            GvReviewId childId = GvReviewId.getId(node.getId().toString());
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
        GvReviewId id = GvReviewId.getId(fact.getReviewId().toString());
        if (fact.isUrl()) {
            MdUrlList.MdUrl url = (MdUrlList.MdUrl) fact;
            return new GvUrlList.GvUrl(id, fact.getLabel(), url.getUrl());
        } else {
            return new GvFactList.GvFact(id, fact.getLabel(), fact.getValue());
        }
    }

    private MdFactList.MdFact getMdFactOrUrl(DataFact fact, ReviewId holder) {
        if (fact.isUrl()) {
            DataUrl url = (DataUrl) fact;
            return new MdUrlList.MdUrl(fact.getLabel(), url.getUrl(), holder);
        } else {
            return new MdFactList.MdFact(fact.getLabel(), fact.getValue(), holder);
        }
    }
}
