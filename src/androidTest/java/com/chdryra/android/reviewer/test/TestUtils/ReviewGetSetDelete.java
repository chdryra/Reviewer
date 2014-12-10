/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.MdDataList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewEditable;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewGetSetDelete {
    public static MdDataList getData(GvDataList.GvType dataType, Review review) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return review.getComments();
        } else if (dataType == GvDataList.GvType.FACTS) {
            return review.getFacts();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return review.getImages();
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            return review.getLocations();
        } else if (dataType == GvDataList.GvType.URLS) {
            return review.getUrls();
        } else {
            return null;
        }
    }

    public static void setData(GvDataList.GvType dataType, ReviewEditable editable,
            MdDataList data) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            editable.setComments(data);
        } else if (dataType == GvDataList.GvType.FACTS) {
            editable.setFacts(data);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            editable.setImages(data);
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            editable.setLocations(data);
        } else if (dataType == GvDataList.GvType.URLS) {
            editable.setUrls(data);
        }
    }

    public static void setData(GvDataList.GvType dataType, ReviewEditable editable,
            GvDataList data) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            editable.setComments(data);
        } else if (dataType == GvDataList.GvType.FACTS) {
            editable.setFacts(data);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            editable.setImages(data);
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            editable.setLocations(data);
        } else if (dataType == GvDataList.GvType.URLS) {
            editable.setUrls(data);
        }
    }

    public static void deleteData(GvDataList.GvType dataType, ReviewEditable editable) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            editable.deleteComments();
        } else if (dataType == GvDataList.GvType.FACTS) {
            editable.deleteFacts();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            editable.deleteImages();
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            editable.deleteLocations();
        } else if (dataType == GvDataList.GvType.URLS) {
            editable.deleteUrls();
        }
    }
}
