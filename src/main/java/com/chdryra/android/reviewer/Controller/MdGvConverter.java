/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//TODO how to make this stuff more generic? Type erasure issue for overloading.
public class MdGvConverter {
    private static final String BAD_ARG = "Bad argument: ";

    //Comments
    public static GvCommentList convert(MdCommentList comments) {
        GvCommentList list = new GvCommentList(new GvReviewId(comments.getReviewId()));
        for (MdCommentList.MdComment comment : comments) {
            list.add(new GvCommentList.GvComment(new GvReviewId(comment.getReviewId()), comment
                    .getComment(), comment.isHeadline()));
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
        GvFactList list = new GvFactList(new GvReviewId(facts.getReviewId()));
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
        GvReviewId id = new GvReviewId(fact.getReviewId());
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
        GvImageList list = new GvImageList(new GvReviewId(images.getReviewId()));
        for (MdImageList.MdImage image : images) {
            GvReviewId id = new GvReviewId(image.getReviewId());
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
        GvLocationList list = new GvLocationList(new GvReviewId(locations.getReviewId()));
        for (MdLocationList.MdLocation location : locations) {
            GvReviewId id = new GvReviewId(location.getReviewId());
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
        GvUrlList list = new GvUrlList(new GvReviewId(urls.getReviewId()));
        for (MdUrlList.MdUrl url : urls) {
            GvReviewId id = new GvReviewId(url.getReviewId());
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

    public static GvTagList convertTags(ReviewId id) {
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(id);
        GvTagList tagList = new GvTagList();
        for (TagsManager.ReviewTag tag : tags) {
            tagList.add(new GvTagList.GvTag(tag.get()));
        }

        return tagList;
    }

    public static GvChildList convertChildren(ReviewNode node) {
        GvReviewId id = new GvReviewId(node.getId());
        GvChildList list = new GvChildList(id);
        for (ReviewNode child : node.getChildren()) {
            list.add(new GvChildList.GvChildReview(id, child.getSubject().get(), child.getRating()
                    .get()));
        }

        return list;
    }
}
