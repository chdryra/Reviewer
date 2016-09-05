/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.os.AsyncTask;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeTraverserIterated implements TreeTraverser {
    private final Iterator<ReviewNode> mIterator;
    private final Map<String, VisitorReviewNode> mVisitors;

    public TreeTraverserIterated(Iterator<ReviewNode> iterator) {
        mIterator = iterator;
        mVisitors = new HashMap<>();
    }

    @Override
    public void addVisitor(String id, VisitorReviewNode visitor) {
        mVisitors.put(id, visitor);
    }

    @Override
    public void traverse(TraversalCallback callback) {
        new TraversalTask(callback).execute();
    }

    private class TraversalTask extends AsyncTask<Void, Void, Void> {
        private final TraversalCallback mCallback;

        public TraversalTask(TraversalCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while(mIterator.hasNext()) {
                ReviewNode node = mIterator.next();
                for(VisitorReviewNode visitor : mVisitors.values()) {
                    node.acceptVisitor(visitor);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mCallback.onTraversed(mVisitors);
        }
    }
}
