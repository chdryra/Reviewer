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
    public static GVCommentList convert(MdCommentList comments) {
        GVCommentList list = new GVCommentList();
        for (MdCommentList.MdComment comment : comments) {
            list.add(new GVCommentList.GvComment(comment.getComment()));
        }

        return list;
    }

    //Facts
    public static GVFactList convert(MdFactList facts) {
        GVFactList list = new GVFactList();
        for (MdFactList.MdFact fact : facts) {
            list.add(new GVFactList.GvFact(fact.getLabel(), fact.getValue()));
        }

        return list;
    }

    //Images
    public static GVImageList convert(MdImageList images) {
        GVImageList list = new GVImageList();
        for (MdImageList.MdImage image : images) {
            list.add(new GVImageList.GvImage(image.getBitmap(), image.getLatLng(),
                    image.getCaption(),
                    image.isCover()));
        }

        return list;
    }

    //Locations
    public static GVLocationList convert(MdLocationList locations) {
        GVLocationList list = new GVLocationList();
        for (MdLocationList.MdLocation location : locations) {
            list.add(new GVLocationList.GvLocation(location.getLatLng(), location.getName()));
        }

        return list;
    }

    //Urls
    public static GVUrlList convert(MdUrlList urls) {
        GVUrlList list = new GVUrlList();
        for (MdUrlList.MdUrl url : urls) {
            list.add(new GVUrlList.GvUrl(url.getUrl()));
        }

        return list;
    }
}
