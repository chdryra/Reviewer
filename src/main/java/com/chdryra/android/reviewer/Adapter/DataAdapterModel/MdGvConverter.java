/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.ChildDataGetter;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//TODO how to make this stuff more generic? Type erasure issue for overloading.
public class MdGvConverter {
    //Comments
    public static GvCommentList convert(MdCommentList comments) {
        GvCommentList list = new GvCommentList(GvReviewId.getId(comments.getReviewId().toString()));
        for (MdCommentList.MdComment comment : comments) {
            list.add(new GvCommentList.GvComment(GvReviewId.getId(comment.getReviewId().toString()),
                    comment.getComment(), comment.isHeadline()));
        }

        return list;
    }

    public static MdCommentList toMdCommentList(Iterable<? extends DataComment> comments,
            ReviewId holder) {
        MdCommentList list = new MdCommentList(holder);
        for (DataComment comment : comments) {
            list.add(new MdCommentList.MdComment(comment.getComment(), comment.isHeadline(),
                    holder));
        }

        return list;
    }

    //Facts
    public static GvFactList convert(MdFactList facts) {
        GvFactList list = new GvFactList(GvReviewId.getId(facts.getReviewId().toString()));
        for (MdFactList.MdFact fact : facts) {
            list.add(getGvFactOrUrl(fact));
        }

        return list;
    }

    public static GvFactList copy(GvFactList facts) {
        GvFactList list = new GvFactList(facts.getReviewId());
        for (GvFactList.GvFact fact : facts) {
            list.add(getGvFactOrUrl(fact));
        }

        return list;
    }

    private static GvFactList.GvFact getGvFactOrUrl(MdFactList.MdFact fact) {
        GvReviewId id = GvReviewId.getId(fact.getReviewId().toString());
        if (fact.isUrl()) {
            MdUrlList.MdUrl url = (MdUrlList.MdUrl) fact;
            return new GvUrlList.GvUrl(id, fact.getLabel(), url.getUrl());
        } else {
            return new GvFactList.GvFact(id, fact.getLabel(), fact.getValue());
        }
    }

    private static GvFactList.GvFact getGvFactOrUrl(GvFactList.GvFact fact) {
        if (fact.isUrl()) {
            return new GvUrlList.GvUrl((GvUrlList.GvUrl) fact);
        } else {
            return new GvFactList.GvFact(fact);
        }
    }

    private static MdFactList.MdFact getMdFactOrUrl(DataFact fact, ReviewId holder) {
        if (fact.isUrl()) {
            DataUrl url = (DataUrl) fact;
            return new MdUrlList.MdUrl(fact.getLabel(), url.getUrl(), holder);
        } else {
            return new MdFactList.MdFact(fact.getLabel(), fact.getValue(), holder);
        }
    }

    public static MdFactList toMdFactList(Iterable<? extends DataFact> facts, ReviewId holder) {
        MdFactList list = new MdFactList(holder);
        for (DataFact fact : facts) {
            list.add(getMdFactOrUrl(fact, holder));
        }

        return list;
    }

    //Images
    public static GvImageList convert(MdImageList images) {
        GvImageList list = new GvImageList(GvReviewId.getId(images.getReviewId().toString()));
        for (MdImageList.MdImage image : images) {
            GvReviewId id = GvReviewId.getId(image.getReviewId().toString());
            GvImageList.GvImage i = new GvImageList.GvImage(id, image.getBitmap
                    (), image.getDate(), image.getCaption(), image.isCover());
            list.add(i);
        }

        return list;
    }

    public static MdImageList toMdImageList(Iterable<? extends DataImage> images, ReviewId holder) {
        MdImageList list = new MdImageList(holder);
        for (DataImage image : images) {
            list.add(new MdImageList.MdImage(image.getBitmap(), image.getDate(),
                    image.getCaption(), image.isCover(), holder));
        }

        return list;
    }

    //Locations
    public static GvLocationList convert(MdLocationList locations) {
        GvLocationList list = new GvLocationList(GvReviewId.getId(locations.getReviewId()
                .toString()));
        for (MdLocationList.MdLocation location : locations) {
            GvReviewId id = GvReviewId.getId(location.getReviewId().toString());
            list.add(new GvLocationList.GvLocation(id, location.getLatLng(), location.getName()));
        }

        return list;
    }

    public static MdLocationList toMdLocationList(Iterable<? extends DataLocation> locations,
            ReviewId holder) {
        MdLocationList list = new MdLocationList(holder);
        for (DataLocation location : locations) {
            list.add(new MdLocationList.MdLocation(location.getLatLng(), location.getName(),
                    holder));
        }

        return list;
    }

    //Urls
    public static GvUrlList convert(MdUrlList urls) {
        GvUrlList list = new GvUrlList(GvReviewId.getId(urls.getReviewId().toString()));
        for (MdUrlList.MdUrl url : urls) {
            GvReviewId id = GvReviewId.getId(url.getReviewId().toString());
            list.add(new GvUrlList.GvUrl(id, url.getLabel(), url.getUrl()));
        }

        return list;
    }

    public static MdUrlList toMdUrlList(Iterable<? extends DataUrl> urls, ReviewId holder) {
        MdUrlList list = new MdUrlList(holder);
        for (DataUrl url : urls) {
            list.add(new MdUrlList.MdUrl(url.getLabel(), url.getUrl(), holder));
        }

        return list;
    }

    public static GvDataList copy(GvDataList data) {
        GvDataType dataType = data.getGvDataType();
        if (dataType == GvCommentList.TYPE) {
            return new GvCommentList((GvCommentList) data);
        } else if (dataType == GvFactList.TYPE) {
            return copy((GvFactList) data);
        } else if (dataType == GvImageList.TYPE) {
            return new GvImageList((GvImageList) data);
        } else if (dataType == GvLocationList.TYPE) {
            return new GvLocationList((GvLocationList) data);
        } else if (dataType == GvUrlList.TYPE) {
            return new GvUrlList((GvUrlList) data);
        } else if (dataType == GvTagList.TYPE) {
            return new GvTagList((GvTagList) data);
        } else if (dataType == GvChildList.TYPE) {
            return new GvChildList((GvChildList) data);
        } else {
            return null;
        }
    }

    public static GvTagList getTags(String reviewId) {
        ReviewId id = ReviewId.fromString(reviewId);
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(id);
        GvReviewId gvid = GvReviewId.getId(reviewId);
        GvTagList tagList = new GvTagList(gvid);
        for (TagsManager.ReviewTag tag : tags) {
            tagList.add(new GvTagList.GvTag(gvid, tag.get()));
        }

        return tagList;
    }

    public static GvChildList convertChildren(ReviewNode node) {
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvChildList list = new GvChildList(id);
        for (ReviewNode child : node.getChildren()) {
            list.add(new GvChildList.GvChildReview(id, child.getSubject().get(), child.getRating()
                    .get()));
        }

        return list;
    }

    public static GvList convertChildSubjects(ReviewNode node) {
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        Map<String, GvSubjectList> subjectMap = new LinkedHashMap<>();
        ChildDataGetter getter = new ChildDataGetter(node);
        for (MdSubject mdSubject : getter.getSubjects()) {
            String subject = mdSubject.get();
            GvReviewId subjectId = GvReviewId.getId(mdSubject.getReviewId().toString());
            GvSubjectList.GvSubject gvSubject = new GvSubjectList.GvSubject(subjectId, subject);
            if (!subjectMap.containsKey(subject)) {
                subjectMap.put(subject, new GvSubjectList(id));
            }

            subjectMap.get(subject).add(gvSubject);
        }

        GvList collection = new GvList(id, GvSubjectList.TYPE);
        for (Map.Entry<String, GvSubjectList> entry : subjectMap.entrySet()) {
            collection.add(entry.getValue());
        }

        return collection;
    }

    public static GvDataList convertChildAuthors(ReviewNode node) {
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        Map<Author, GvAuthorList> authorMap = new LinkedHashMap<>();
        for (ReviewNode child : node.getChildren()) {
            GvReviewId childId = GvReviewId.getId(child.getId().toString());
            Author author = child.getAuthor();
            GvAuthorList.GvAuthor gvAuthor = new GvAuthorList.GvAuthor(childId, author.getName(),
                    author.getUserId().toString());
            if (!authorMap.containsKey(author)) {
                authorMap.put(author, new GvAuthorList(id));
            }

            authorMap.get(author).add(gvAuthor);
        }

        GvList collection = new GvList(id, GvAuthorList.TYPE);
        for (Map.Entry<Author, GvAuthorList> entry : authorMap.entrySet()) {
            collection.add(entry.getValue());
        }

        return collection;
    }

    public static GvDateList convertChildPublishDates(ReviewNode node) {
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvDateList list = new GvDateList(id);
        for (ReviewNode child : node.getChildren()) {
            GvReviewId childId = GvReviewId.getId(child.getId().toString());
            PublishDate date = child.getPublishDate();
            list.add(new GvDateList.GvDate(childId, date.getDate()));
        }

        return list;
    }
}
