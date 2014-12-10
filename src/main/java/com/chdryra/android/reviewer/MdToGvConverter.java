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
    public static VgCommentList convert(MdCommentList comments) {
        VgCommentList list = new VgCommentList();
        for (MdCommentList.MdComment comment : comments) {
            list.add(new VgCommentList.GvComment(comment.getComment()));
        }

        return list;
    }

    //Facts
    public static VgFactList convert(MdFactList facts) {
        VgFactList list = new VgFactList();
        for (MdFactList.MdFact fact : facts) {
            list.add(new VgFactList.VgFact(fact.getLabel(), fact.getValue()));
        }

        return list;
    }

    //Images
    public static VgImageList convert(MdImageList images) {
        VgImageList list = new VgImageList();
        for (MdImageList.MdImage image : images) {
            list.add(new VgImageList.GvImage(image.getBitmap(), image.getLatLng(),
                    image.getCaption(),
                    image.isCover()));
        }

        return list;
    }

    //Locations
    public static VgLocationList convert(MdLocationList locations) {
        VgLocationList list = new VgLocationList();
        for (MdLocationList.MdLocation location : locations) {
            list.add(new VgLocationList.GvLocation(location.getLatLng(), location.getName()));
        }

        return list;
    }

    //Urls
    public static VgUrlList convert(MdUrlList urls) {
        VgUrlList list = new VgUrlList();
        for (MdUrlList.MdUrl url : urls) {
            list.add(new VgUrlList.GvUrl(url.getUrl()));
        }

        return list;
    }
}
