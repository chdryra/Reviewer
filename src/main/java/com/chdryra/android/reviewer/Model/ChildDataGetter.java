/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Collects data from ReviewTree children.
 */
public class ChildDataGetter {
    private ReviewNode mRoot;

    public ChildDataGetter(ReviewNode root) {
        mRoot = root;
    }

    public MdDataList<MdSubject> getSubjects() {
        MdDataList<MdSubject> subjects = new MdDataList<>(mRoot.getId());
        for (ReviewNode node : mRoot.getChildren()) {
            subjects.add(node.getSubject());
        }

        return subjects;
    }

    public MdCommentList getComments() {
//        MdCommentList comments = new MdCommentList(mRoot.getId());
//        for(ReviewNode node : mRoot.getChildren()) {
//            comments.addList(node.getReview().getComments());
//        }
//
//        return comments;
        return mRoot.getComments();
    }

    public MdImageList getImages() {
//        MdImageList images = new MdImageList(mRoot.getId());
//        for(ReviewNode node : mRoot.getChildren()) {
//            images.addList(node.getReview().getImages());
//        }
//
//        return images;
        return mRoot.getImages();
    }

    public MdFactList getFacts() {
//        MdFactList facts = new MdFactList(mRoot.getId());
//        for(ReviewNode node : mRoot.getChildren()) {
//            facts.addList(node.getReview().getFacts());
//        }
//
//        return facts;
        return mRoot.getFacts();
    }

    public MdLocationList getLocations() {
//        MdLocationList locations = new MdLocationList(mRoot.getId());
//        for(ReviewNode node : mRoot.getChildren()) {
//            locations.addList(node.getReview().getLocations());
//        }
//
//        return locations;
        return mRoot.getLocations();
    }
}
