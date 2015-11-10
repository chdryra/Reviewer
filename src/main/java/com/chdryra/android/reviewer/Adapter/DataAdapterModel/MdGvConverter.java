/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataUrl;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDataList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdSubject;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdUrlList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
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
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//TODO how to make this stuff more generic? Type erasure issue for overloading.
public class MdGvConverter {
    //Comments
    public GvCommentList toGvDataList(MdCommentList comments) {
        GvReviewId idList = new GvReviewId(comments.getReviewId());
        GvCommentList list = new GvCommentList(idList);
        for (MdCommentList.MdComment comment : comments) {
            GvReviewId  id = new GvReviewId(comment.getReviewId());
            list.add(new GvCommentList.GvComment(id, comment.getComment(), comment.isHeadline()));
        }

        return list;
    }

    public MdCommentList toMdCommentList(Iterable<? extends DataComment> comments,
                                                MdReviewId holder) {
        MdCommentList list = new MdCommentList(holder);
        for (DataComment comment : comments) {
            list.add(new MdCommentList.MdComment(holder, comment.getComment(), comment.isHeadline()
            ));
        }

        return list;
    }

    //Facts
    public GvFactList toGvDataList(MdFactList facts) {
        GvReviewId idList = new GvReviewId(facts.getReviewId().toString());
        GvFactList list = new GvFactList(idList);
        for (MdFactList.MdFact fact : facts) {
            list.add(getGvFactOrUrl(fact));
        }

        return list;
    }

    public MdFactList toMdFactList(Iterable<? extends DataFact> facts, MdReviewId holder) {
        MdFactList list = new MdFactList(holder);
        for (DataFact fact : facts) {
            list.add(getMdFactOrUrl(fact, holder));
        }

        return list;
    }

    //Images
    public GvImageList toGvDataList(MdImageList images) {
        GvReviewId idList = new GvReviewId(images.getReviewId().toString());
        GvImageList list = new GvImageList(idList);
        for (MdImageList.MdImage image : images) {
            GvReviewId id = new GvReviewId(image.getReviewId().toString());
            GvImageList.GvImage i = new GvImageList.GvImage(id, image.getBitmap
                    (), image.getDate(), image.getCaption(), image.isCover());
            list.add(i);
        }

        return list;
    }

    public MdImageList toMdImageList(Iterable<? extends DataImage> images, MdReviewId holder) {
        MdImageList list = new MdImageList(holder);
        for (DataImage image : images) {
            list.add(new MdImageList.MdImage(holder, image.getBitmap(), image.getDate(),
                    image.getCaption(), image.isCover()));
        }

        return list;
    }

    //Locations
    public GvLocationList toGvDataList(MdLocationList locations) {
        GvReviewId idList = new GvReviewId(locations.getReviewId().toString());
        GvLocationList list = new GvLocationList(idList);
        for (MdLocationList.MdLocation location : locations) {
            GvReviewId id = new GvReviewId(location.getReviewId().toString());
            list.add(new GvLocationList.GvLocation(id, location.getLatLng(), location.getName()));
        }

        return list;
    }

    public MdLocationList toMdLocationList(Iterable<? extends DataLocation> locations,
                                                  MdReviewId holder) {
        MdLocationList list = new MdLocationList(holder);
        for (DataLocation location : locations) {
            list.add(new MdLocationList.MdLocation(holder, location.getLatLng(), location.getName()
            ));
        }

        return list;
    }

    //Urls
    public GvUrlList toGvDataList(MdUrlList urls) {
        GvReviewId idList = new GvReviewId(urls.getReviewId().toString());
        GvUrlList list = new GvUrlList(idList);
        for (MdUrlList.MdUrl url : urls) {
            GvReviewId id = new GvReviewId(url.getReviewId().toString());
            list.add(new GvUrlList.GvUrl(id, url.getLabel(), url.getUrl()));
        }

        return list;
    }

    public MdUrlList toMdUrlList(Iterable<? extends DataUrl> urls, MdReviewId holder) {
        MdUrlList list = new MdUrlList(holder);
        for (DataUrl url : urls) {
            list.add(new MdUrlList.MdUrl(holder, url.getLabel(), url.getUrl()));
        }

        return list;
    }

    //Criteria
    public GvCriterionList toGvDataList(MdCriterionList criteria) {
        GvReviewId idList = new GvReviewId(criteria.getReviewId().toString());
        GvCriterionList list = new GvCriterionList(idList);
        for (MdCriterionList.MdCriterion criterion : criteria) {
            GvReviewId id = new GvReviewId(criterion.getReviewId().toString());
            list.add(new GvCriterionList.GvCriterion(id, criterion.getSubject(), criterion
                    .getRating()));
        }

        return list;
    }

    public <T extends Review> GvReviewOverviewList toGvDataList(MdIdableCollection<T> reviews,
                                                                MdReviewId holder,
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
