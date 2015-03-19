/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//TODO how to make this stuff more generic? Type erasure issue for overloading.
public class MdGvConverter {

    //Comments
    public static GvCommentList convert(MdCommentList comments) {
        GvCommentList list = new GvCommentList();
        for (DataComment comment : comments) {
            list.add(new GvCommentList.GvComment(comment.getComment(), comment.isHeadline()));
        }

        return list;
    }

    public static GvCommentList copy(GvCommentList comments) {
        GvCommentList list = new GvCommentList();
        for (DataComment comment : comments) {
            list.add(new GvCommentList.GvComment(comment.getComment(), comment.isHeadline()));
        }

        return list;
    }

    public static MdCommentList toMdCommentList(Iterable<? extends DataComment> comments,
            Review holder) {
        MdCommentList list = new MdCommentList(holder);
        for (DataComment comment : comments) {
            list.add(new MdCommentList.MdComment(comment.getComment(), comment.isHeadline(),
                    holder));
        }

        return list;
    }

    //Facts
    public static GvFactList convert(MdFactList facts) {
        GvFactList list = new GvFactList();
        for (DataFact fact : facts) {
            list.add(new GvFactList.GvFact(fact.getLabel(), fact.getValue()));
        }

        return list;
    }

    public static GvFactList copy(GvFactList facts) {
        GvFactList list = new GvFactList();
        for (DataFact fact : facts) {
            list.add(new GvFactList.GvFact(fact.getLabel(), fact.getValue()));
        }

        return list;
    }

    public static MdFactList toMdFactList(Iterable<? extends DataFact> facts, Review holder) {
        MdFactList list = new MdFactList(holder);
        for (DataFact fact : facts) {
            list.add(new MdFactList.MdFact(fact.getLabel(), fact.getValue(), holder));
        }

        return list;
    }

    //Images
    public static GvImageList convert(MdImageList images) {
        GvImageList list = new GvImageList();
        for (DataImage image : images) {
            list.add(new GvImageList.GvImage(image.getBitmap(), image.getDate(), image.getLatLng(),
                    image.getCaption(), image.isCover()));
        }

        return list;
    }

    public static GvImageList copy(GvImageList images) {
        GvImageList list = new GvImageList();
        for (DataImage image : images) {
            list.add(new GvImageList.GvImage(image.getBitmap(), image.getDate(), image.getLatLng(),
                    image.getCaption(), image.isCover()));
        }

        return list;
    }

    public static MdImageList toMdImageList(Iterable<? extends DataImage> images, Review holder) {
        MdImageList list = new MdImageList(holder);
        for (DataImage image : images) {
            list.add(new MdImageList.MdImage(image.getBitmap(), image.getDate(), image.getLatLng(),
                    image.getCaption(), image.isCover(), holder));
        }

        return list;
    }

    //Locations
    public static GvLocationList convert(MdLocationList locations) {
        GvLocationList list = new GvLocationList();
        for (DataLocation location : locations) {
            list.add(new GvLocationList.GvLocation(location.getLatLng(), location.getName()));
        }

        return list;
    }

    public static GvLocationList copy(GvLocationList locations) {
        GvLocationList list = new GvLocationList();
        for (DataLocation location : locations) {
            list.add(new GvLocationList.GvLocation(location.getLatLng(), location.getName()));
        }

        return list;
    }

    public static MdLocationList toMdLocationList(Iterable<? extends DataLocation> locations,
            Review holder) {
        MdLocationList list = new MdLocationList(holder);
        for (DataLocation location : locations) {
            list.add(new MdLocationList.MdLocation(location.getLatLng(), location.getName(),
                    holder));
        }

        return list;
    }

    //Urls
    public static GvUrlList convert(MdUrlList urls) {
        GvUrlList list = new GvUrlList();
        for (DataUrl url : urls) {
            list.add(new GvUrlList.GvUrl(url.getLabel(), url.getUrl()));
        }

        return list;
    }

    public static GvUrlList copy(GvUrlList urls) {
        GvUrlList list = new GvUrlList();
        for (DataUrl url : urls) {
            list.add(new GvUrlList.GvUrl(url.getLabel(), url.getUrl()));
        }

        return list;
    }

    //Criteria
    public static GvChildList copy(GvChildList children) {
        GvChildList list = new GvChildList();
        for (GvChildList.GvChildReview child : children) {
            list.add(new GvChildList.GvChildReview(child.getSubject(), child.getRating()));
        }

        return list;
    }

    public static MdUrlList toMdUrlList(Iterable<? extends DataUrl> urls, Review holder) {
        MdUrlList list = new MdUrlList(holder);
        for (DataUrl url : urls) {
            list.add(new MdUrlList.MdUrl(url.getLabel(), url.getUrl(), holder));
        }

        return list;
    }

    public static GvTagList copy(GvTagList urls) {
        GvTagList list = new GvTagList();
        for (GvTagList.GvTag url : urls) {
            list.add(new GvTagList.GvTag(url.get()));
        }

        return list;
    }

    public static GvDataList convert(GvDataList.GvDataType dataType, MdDataList data) {
        if (dataType == GvCommentList.TYPE) {
            return MdGvConverter.convert((MdCommentList) data);
        } else if (dataType == GvFactList.TYPE) {
            return MdGvConverter.convert((MdFactList) data);
        } else if (dataType == GvImageList.TYPE) {
            return MdGvConverter.convert((MdImageList) data);
        } else if (dataType == GvLocationList.TYPE) {
            return MdGvConverter.convert((MdLocationList) data);
        } else if (dataType == GvUrlList.TYPE) {
            return MdGvConverter.convert((MdUrlList) data);
        } else {
            return null;
        }
    }

    public static GvDataList copy(GvDataList data) {
        GvDataList.GvDataType dataType = data.getGvDataType();
        if (dataType == GvCommentList.TYPE) {
            return MdGvConverter.copy((GvCommentList) data);
        } else if (dataType == GvFactList.TYPE) {
            return MdGvConverter.copy((GvFactList) data);
        } else if (dataType == GvImageList.TYPE) {
            return MdGvConverter.copy((GvImageList) data);
        } else if (dataType == GvLocationList.TYPE) {
            return MdGvConverter.copy((GvLocationList) data);
        } else if (dataType == GvUrlList.TYPE) {
            return MdGvConverter.copy((GvUrlList) data);
        } else if (dataType == GvTagList.TYPE) {
            return MdGvConverter.copy((GvTagList) data);
        } else if (dataType == GvChildList.TYPE) {
            return MdGvConverter.copy((GvChildList) data);
        } else {
            return null;
        }
    }
}
