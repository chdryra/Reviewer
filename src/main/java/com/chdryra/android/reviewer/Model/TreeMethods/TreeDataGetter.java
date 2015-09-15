/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 June, 2015
 */

package com.chdryra.android.reviewer.Model.TreeMethods;

import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriteriaList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataGetter {
    private ReviewNode mRoot;

    public TreeDataGetter(ReviewNode root) {
        mRoot = root;
    }

    public MdCriteriaList getCriteria() {
        MdCriteriaList criteria = new MdCriteriaList(mRoot.getId());
        criteria.addList(mRoot.getReview().getCriteria());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            criteria.addList(getter.getCriteria());
        }

        return criteria;
    }

    public MdCommentList getComments() {
        MdCommentList comments = new MdCommentList(mRoot.getId());
        comments.addList(mRoot.getReview().getComments());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            comments.addList(getter.getComments());
        }

        return comments;
    }

    public MdImageList getImages() {
        MdImageList images = new MdImageList(mRoot.getId());
        images.addList(mRoot.getReview().getImages());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            images.addList(getter.getImages());
        }

        return images;
    }

    public MdFactList getFacts() {
        MdFactList facts = new MdFactList(mRoot.getId());
        facts.addList(mRoot.getReview().getFacts());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            facts.addList(getter.getFacts());
        }

        return facts;
    }

    public MdLocationList getLocations() {
        MdLocationList locations = new MdLocationList(mRoot.getId());
        locations.addList(mRoot.getReview().getLocations());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            locations.addList(getter.getLocations());
        }

        return locations;
    }
}

