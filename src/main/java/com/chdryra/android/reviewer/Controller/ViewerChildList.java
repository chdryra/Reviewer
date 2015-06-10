/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.View.GvReviewList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is {@link GvReviewList}.
 */
public class ViewerChildList implements GridDataViewer {
    private ReviewNode mNode;

    public ViewerChildList(ReviewNode node) {
        mNode = node;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    @Override
    public GvDataList getGridData() {
        GvReviewList data = new GvReviewList(GvReviewId.getId(mNode.getId().toString()));
        for (Review review : mNode.getChildren()) {
            GvImageList images = MdGvConverter.convert(review.getImages());
            GvCommentList headlines = MdGvConverter.convert(review.getComments()).getHeadlines();
            GvLocationList locations = MdGvConverter.convert(review.getLocations());

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                    null;

            ArrayList<String> locationNames = new ArrayList<>();
            for (GvLocationList.GvLocation location : locations) {
                locationNames.add(location.getShortenedName());
            }

            data.add(review.getId().toString(), review.getAuthor(), review.getPublishDate()
                            .getDate(),
                    review.getSubject().get(), review.getRating().get(), cover, headline,
                    locationNames);
        }

        return data;
    }
}
