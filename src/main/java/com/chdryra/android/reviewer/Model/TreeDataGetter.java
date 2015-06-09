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
 * Collects data from ReviewTree. Not very robust at the moment as assumes {@link ReviewTreeNode}
 * underlying all {@link ReviewNode}s. Uses {@link ReviewTreeNode} implementation of getComments
 * () etc. to recursively pull data from the tree. This class just ensures that the data items are
 * unique.
 */
public class TreeDataGetter {
    private ReviewNode                   mRoot;

    public TreeDataGetter(ReviewNode root) {
        mRoot = root;
    }

    public ReviewIdableList<ReviewNode> getNodes() {
        VisitorTreeFlattener flattener = new VisitorTreeFlattener();
        mRoot.acceptVisitor(flattener);
        return flattener.getNodes();
    }

    public MdDataList<ReviewId> getIds(boolean unique) {
        MdDataList<ReviewId> ids = new MdDataList<>(mRoot.getId());
        for (ReviewNode node : getNodes()) {
            addIfRequired(unique, node.getId(), ids);
        }

        return ids;
    }

    public MdDataList<MdSubject> getSubjects(boolean unique) {
        MdDataList<MdSubject> subjects = new MdDataList<>(mRoot.getId());
        for (ReviewNode node : getNodes()) {
            addIfRequired(unique, node.getSubject(), subjects);
        }

        return subjects;
    }

    public MdCommentList getComments(boolean unique) {
        MdCommentList comments = new MdCommentList(mRoot.getId());
        addIfRequired(unique, mRoot.getComments(), comments);

        return comments;
    }

    public MdImageList getImages(boolean unique) {
        MdImageList images = new MdImageList(mRoot.getId());
        addIfRequired(unique, mRoot.getImages(), images);

        return images;
    }

    public MdFactList getFacts(boolean unique) {
        MdFactList facts = new MdFactList(mRoot.getId());
        addIfRequired(unique, mRoot.getFacts(), facts);

        return facts;
    }

    public MdLocationList getLocations(boolean unique) {
        MdLocationList locations = new MdLocationList(mRoot.getId());
        addIfRequired(unique, mRoot.getLocations(), locations);

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
