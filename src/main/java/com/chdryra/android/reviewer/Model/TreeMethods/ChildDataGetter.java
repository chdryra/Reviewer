/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.TreeMethods;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Collects data from ReviewTree children.
 */
public class ChildDataGetter {
    private ReviewNode mRoot;

    //Constructors
    public ChildDataGetter(ReviewNode root) {
        mRoot = root;
    }

    //public methods
    public MdDataList<MdSubject> getSubjects() {
        MdDataList<MdSubject> subjects = new MdDataList<>(mRoot.getId());
        for (ReviewNode node : mRoot.getChildren()) {
            subjects.add(node.getSubject());
        }

        return subjects;
    }

    public MdCommentList getComments() {
        MdCommentList comments = new MdCommentList(mRoot.getId());
        for (ReviewNode node : mRoot.getChildren()) {
            comments.addList(node.getReview().getComments());
        }

        return comments;
    }

    public MdImageList getImages() {
        MdImageList images = new MdImageList(mRoot.getId());
        for (ReviewNode node : mRoot.getChildren()) {
            images.addList(node.getReview().getImages());
        }

        return images;
    }

    public MdFactList getFacts() {
        MdFactList facts = new MdFactList(mRoot.getId());
        for (ReviewNode node : mRoot.getChildren()) {
            facts.addList(node.getReview().getFacts());
        }

        return facts;
    }

    public MdLocationList getLocations() {
        MdLocationList locations = new MdLocationList(mRoot.getId());
        for (ReviewNode node : mRoot.getChildren()) {
            locations.addList(node.getReview().getLocations());
        }

        return locations;
    }
}
