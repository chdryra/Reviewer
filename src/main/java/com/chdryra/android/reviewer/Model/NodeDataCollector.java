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
public class NodeDataCollector {
    private ReviewNode                   mRoot;

    public NodeDataCollector(ReviewNode root) {
        mRoot = root;
    }

    public ReviewIdableList<ReviewNode> collectNodes() {
        VisitorTreeFlattener flattener = new VisitorTreeFlattener();
        mRoot.acceptVisitor(flattener);
        return flattener.getNodes();
    }

    public MdDataList<ReviewId> collectIds(boolean unique) {
        MdDataList<ReviewId> ids = new MdDataList<>(mRoot.getId());
        for (ReviewNode node : collectNodes()) {
            addIfRequired(unique, node.getId(), ids);
        }

        return ids;
    }

    public MdDataList<MdSubject> collectSubjects(boolean unique) {
        MdDataList<MdSubject> subjects = new MdDataList<>(mRoot.getId());
        for (ReviewNode node : collectNodes()) {
            addIfRequired(unique, node.getSubject(), subjects);
        }

        return subjects;
    }

    public MdCommentList collectComments(boolean unique) {
        MdCommentList comments = new MdCommentList(mRoot.getId());
        for (ReviewNode node : collectNodes()) {
            addIfRequired(unique, node.getComments(), comments);
        }

        return comments;
    }

    public MdImageList collectImages(boolean unique) {
        MdImageList images = new MdImageList(mRoot.getId());
        for (ReviewNode node : collectNodes()) {
            addIfRequired(unique, node.getImages(), images);
        }

        return images;
    }

    public MdFactList collectFacts(boolean unique) {
        MdFactList facts = new MdFactList(mRoot.getId());
        for (ReviewNode node : collectNodes()) {
            addIfRequired(unique, node.getFacts(), facts);
        }

        return facts;
    }

    public MdLocationList collectLocations(boolean unique) {
        MdLocationList locations = new MdLocationList(mRoot.getId());
        for (ReviewNode node : collectNodes()) {
            addIfRequired(unique, node.getLocations(), locations);
        }

        return locations;
    }

    private <T extends MdData> void addIfRequired(boolean unique, T datum, MdDataList<T> list) {
        if (unique && !list.contains(datum) || !unique) list.add(datum);
    }

    private <T extends MdData> void addIfRequired(boolean unique, MdDataList<T> toAdd,
            MdDataList<T> list) {
        for (T datum : toAdd) {
            addIfRequired(unique, datum, list);
        }
    }
}
