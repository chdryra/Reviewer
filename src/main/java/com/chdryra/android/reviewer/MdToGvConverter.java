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
public class MdToGvConverter {

    //Comments
    public static GvCommentList convert(MdCommentList comments) {
        GvCommentList list = new GvCommentList();
        for (MdCommentList.MdComment comment : comments) {
            list.add(new GvCommentList.GvComment(comment.getComment()));
        }

        return list;
    }

    //Facts
    public static GvFactList convert(MdFactList facts) {
        GvFactList list = new GvFactList();
        for (MdFactList.MdFact fact : facts) {
            list.add(new GvFactList.GvFact(fact.getLabel(), fact.getValue()));
        }

        return list;
    }

    //Images
    public static GvImageList convert(MdImageList images) {
        GvImageList list = new GvImageList();
        for (MdImageList.MdImage image : images) {
            list.add(new GvImageList.GvImage(image.getBitmap(), image.getLatLng(),
                    image.getCaption(),
                    image.isCover()));
        }

        return list;
    }

    //Locations
    public static GvLocationList convert(MdLocationList locations) {
        GvLocationList list = new GvLocationList();
        for (MdLocationList.MdLocation location : locations) {
            list.add(new GvLocationList.GvLocation(location.getLatLng(), location.getName()));
        }

        return list;
    }

    //Urls
    public static GvUrlList convert(MdUrlList urls) {
        GvUrlList list = new GvUrlList();
        for (MdUrlList.MdUrl url : urls) {
            list.add(new GvUrlList.GvUrl(url.getUrl()));
        }

        return list;
    }
}
