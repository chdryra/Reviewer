/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RdGvConverter {

    //Comments
    public static GVCommentList.GVComment convert(RDCommentList.RDComment comment) {
        return new GVCommentList.GVComment(comment.get());
    }

    public static RDCommentList.RDComment convert(GVCommentList.GVComment comment, Review review) {
        return new RDCommentList.RDComment(comment.getComment(), review);
    }

    public static GVCommentList convert(RDCommentList comments) {
        GVCommentList list = new GVCommentList();
        for (RDCommentList.RDComment comment : comments) {
            list.add(convert(comment));
        }

        return list;
    }

    public static RDCommentList convert(GVCommentList comments, Review review) {
        RDCommentList list = new RDCommentList();
        for (GVCommentList.GVComment comment : comments) {
            list.add(convert(comment, review));
        }

        return list;
    }

    //Facts
    public static GVFactList.GVFact convert(RDFactList.RDFact fact) {
        return new GVFactList.GVFact(fact.getLabel(), fact.getValue());
    }

    public static RDFactList.RDFact convert(GVFactList.GVFact fact, Review review) {
        return new RDFactList.RDFact(fact.getLabel(), fact.getValue(), review);
    }

    public static GVFactList convert(RDFactList facts) {
        GVFactList list = new GVFactList();
        for (RDFactList.RDFact fact : facts) {
            list.add(convert(fact));
        }

        return list;
    }

    public static RDFactList convert(GVFactList facts, Review review) {
        RDFactList list = new RDFactList();
        for (GVFactList.GVFact fact : facts) {
            list.add(convert(fact, review));
        }

        return list;
    }

    //Images
    public static GVImageList.GVImage convert(RDImageList.RDImage image) {
        return new GVImageList.GVImage(image.getBitmap(), image.getLatLng(), image.getCaption(),
                image.isCover());
    }

    public static RDImageList.RDImage convert(GVImageList.GVImage image, Review review) {
        return new RDImageList.RDImage(image.getBitmap(), image.getLatLng(), image.getCaption(),
                image.isCover(), review);
    }

    public static GVImageList convert(RDImageList images) {
        GVImageList list = new GVImageList();
        for (RDImageList.RDImage image : images) {
            list.add(convert(image));
        }

        return list;
    }

    public static RDImageList convert(GVImageList images, Review review) {
        RDImageList list = new RDImageList();
        for (GVImageList.GVImage image : images) {
            list.add(convert(image, review));
        }

        return list;
    }

    //Locations
    public static GVLocationList.GVLocation convert(RDLocationList.RDLocation location) {
        return new GVLocationList.GVLocation(location.getLatLng(), location.getName());
    }

    public static RDLocationList.RDLocation convert(GVLocationList.GVLocation location,
            Review review) {
        return new RDLocationList.RDLocation(location.getLatLng(), location.getName(), review);
    }

    public static GVLocationList convert(RDLocationList locations) {
        GVLocationList list = new GVLocationList();
        for (RDLocationList.RDLocation location : locations) {
            list.add(convert(location));
        }

        return list;
    }

    public static RDLocationList convert(GVLocationList locations, Review review) {
        RDLocationList list = new RDLocationList();
        for (GVLocationList.GVLocation location : locations) {
            list.add(convert(location, review));
        }

        return list;
    }

    //Urls
    public static GVUrlList.GVUrl convert(RDUrlList.RDUrl url) {
        try {
            return new GVUrlList.GVUrl(url.toString());
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RDUrlList.RDUrl convert(GVUrlList.GVUrl url, Review review) {
        return new RDUrlList.RDUrl(url.getUrl(), review);
    }

    public static GVUrlList convert(RDUrlList urls) {
        GVUrlList list = new GVUrlList();
        for (RDUrlList.RDUrl url : urls) {
            list.add(convert(url));
        }

        return list;
    }

    public static RDUrlList convert(GVUrlList urls, Review review) {
        RDUrlList list = new RDUrlList();
        for (GVUrlList.GVUrl url : urls) {
            list.add(convert(url, review));
        }

        return list;
    }
}
