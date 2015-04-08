/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.DbTableDef;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.ReviewerDbRow;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbTest extends AndroidTestCase {
    ReviewNode mNode;
    ReviewerDb mDatabase;

    @SmallTest
    public void testAddReviewToReviewsTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.REVIEWS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        int numNodes = 1 + 1 + mNode.getChildren().size();
        assertEquals(numNodes, getNumberRows(table));

        testReviewsRow(mNode.getReview());
        testReviewsRow(mNode.getParent().getReview());
        for (ReviewNode child : mNode.getChildren()) {
            testReviewsRow(child.getReview());
        }
    }

    @SmallTest
    public void testAddReviewToReviewTreesTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.TREES_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        int numNodes = 1 + 1 + mNode.getChildren().size();
        assertEquals(numNodes, getNumberRows(table));

        testReviewTreesRow(mNode);
        testReviewTreesRow(mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testReviewTreesRow(child);
        }
    }

    @SmallTest
    public void testAddReviewToCommentsTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.COMMENTS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        int num = mNode.getComments().size();
        num += mNode.getParent().getComments().size();
        for (ReviewNode child : mNode.getChildren()) {
            num += child.getComments().size();
        }
        assertEquals(num, getNumberRows(table));

        testCommentsRows(mNode);
        testCommentsRows(mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testCommentsRows(child);
        }
    }

    @SmallTest
    public void testAddReviewToLocationsTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.LOCATIONS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        int num = mNode.getLocations().size();
        num += mNode.getParent().getLocations().size();
        for (ReviewNode child : mNode.getChildren()) {
            num += child.getLocations().size();
        }
        assertEquals(num, getNumberRows(table));

        testLocationsRows(mNode);
        testLocationsRows(mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testLocationsRows(child);
        }
    }

    @SmallTest
    public void testAddReviewToFactsTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.FACTS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        int num = mNode.getFacts().size();
        num += mNode.getParent().getFacts().size();
        for (ReviewNode child : mNode.getChildren()) {
            num += child.getFacts().size();
        }
        assertEquals(num, getNumberRows(table));

        testFactsRows(mNode);
        testFactsRows(mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testFactsRows(child);
        }
    }

    @Override
    protected void setUp() throws Exception {
        mDatabase = ReviewerDb.getTestDatabase(getContext());
        mNode = ReviewMocker.newReviewNode();
        deleteDatabaseIfNecessary();
    }

    @Override
    protected void tearDown() throws Exception {
        deleteDatabaseIfNecessary();
    }

    private void testLocationsRows(ReviewNode node) {
        MdLocationList locations = node.getLocations();
        for (int i = 0; i < locations.size(); ++i) {
            testLocationsRow(locations.getItem(i), i + 1);
        }
    }

    private void testLocationsRow(MdLocationList.MdLocation location, int index) {
        String id = location.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "l" + String
                .valueOf(index);
        ContentValues vals = getRowVals(ConfigDb.DbData.LOCATIONS, id);

        assertEquals(id, vals.get(ReviewerDbRow.LocationsRow.LOCATION_ID));
        assertEquals(location.getReviewId().toString(), vals.get(ReviewerDbRow.LocationsRow
                .REVIEW_ID));
        assertEquals(location.getLatLng().latitude, vals.get(ReviewerDbRow.LocationsRow.LAT));
        assertEquals(location.getLatLng().longitude, vals.get(ReviewerDbRow.LocationsRow.LNG));
        assertEquals(location.getName(), vals.get(ReviewerDbRow.LocationsRow.NAME));
    }

    private void testFactsRows(ReviewNode node) {
        MdFactList facts = node.getFacts();
        for (int i = 0; i < facts.size(); ++i) {
            testFactsRow(facts.getItem(i), i + 1);
        }
    }

    private void testFactsRow(MdFactList.MdFact fact, int index) {
        String id = fact.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "f" + String
                .valueOf(index);
        ContentValues vals = getRowVals(ConfigDb.DbData.FACTS, id);

        assertEquals(id, vals.get(ReviewerDbRow.FactsRow.FACT_ID));
        assertEquals(fact.getReviewId().toString(), vals.get(ReviewerDbRow.FactsRow.REVIEW_ID));
        assertEquals(fact.getLabel(), vals.get(ReviewerDbRow.FactsRow.LABEL));
        assertEquals(fact.getValue(), vals.get(ReviewerDbRow.FactsRow.VALUE));
        assertEquals(fact.isUrl(), vals.get(ReviewerDbRow.FactsRow.IS_URL));
    }

    private void testCommentsRows(ReviewNode node) {
        MdCommentList comments = node.getComments();
        for (int i = 0; i < comments.size(); ++i) {
            testCommentsRow(comments.getItem(i), i + 1);
        }
    }

    private void testCommentsRow(MdCommentList.MdComment comment, int index) {
        String id = comment.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "c" + String
                .valueOf(index);
        ContentValues vals = getRowVals(ConfigDb.DbData.COMMENTS, id);

        assertEquals(id, vals.get(ReviewerDbRow.CommentsRow.COMMENT_ID));
        assertEquals(comment.getReviewId().toString(), vals.get(ReviewerDbRow.CommentsRow
                .REVIEW_ID));
        assertEquals(comment.getComment(), vals.get(ReviewerDbRow.CommentsRow.COMMENT));
        assertEquals(comment.isHeadline(), vals.get(ReviewerDbRow.CommentsRow.IS_HEADLINE));
    }

    private void testReviewTreesRow(ReviewNode node) {
        String id = node.getId().toString();
        ContentValues vals = getRowVals(ConfigDb.DbData.REVIEW_TREES, id);

        Review review = node.getReview();
        ReviewNode parent = node.getParent();
        assertEquals(id, vals.get(ReviewerDbRow.ReviewTreesRow.NODE_ID));
        assertEquals(review.getId().toString(), vals.get(ReviewerDbRow.ReviewTreesRow.REVIEW_ID));
        assertEquals(mNode.isRatingAverageOfChildren(),
                vals.get(ReviewerDbRow.ReviewTreesRow.IS_AVERAGE));
        if (parent != null) {
            assertEquals(parent.getId().toString(), vals.get(ReviewerDbRow.ReviewTreesRow
                    .PARENT_ID));
        } else {
            assertNull(vals.get(ReviewerDbRow.ReviewTreesRow.PARENT_ID));
        }
    }

    private void testReviewsRow(Review review) {
        String id = review.getId().toString();
        String authorId = review.getAuthor().getUserId().toString();
        long millis = review.getPublishDate().getTime();

        ContentValues vals = getRowVals(ConfigDb.DbData.REVIEWS, id);

        assertEquals(id, vals.get(ReviewerDbRow.ReviewsRow.REVIEW_ID));
        assertEquals(review.getSubject().get(), vals.get(ReviewerDbRow.ReviewsRow.SUBJECT));
        assertEquals(review.getRating().get(), vals.get(ReviewerDbRow.ReviewsRow.RATING));
        assertEquals(authorId, vals.get(ReviewerDbRow.ReviewsRow.AUTHOR_ID));
        assertEquals(millis, vals.get(ReviewerDbRow.ReviewsRow.PUBLISH_DATE));
    }

    private ContentValues getRowVals(ConfigDb.DbData dataType, String id) {
        ConfigDb.Config config = ConfigDb.getConfig(dataType);
        String pkColumn = config.getPkColumn();
        ReviewerDbContract.ReviewerDbTable table = config.getTable();
        DbTableDef.DbColumnDef idCol = table.getColumn(pkColumn);
        Class<? extends ReviewerDbRow.TableRow> resultsClass = config.getRowClass();

        ReviewerDbRow.TableRow row = mDatabase.getRowFor(table, idCol, id, resultsClass);

        assertTrue(row.hasData());
        return row.getContentValues();
    }

    private long getNumberRows(ReviewerDbContract.ReviewerDbTable table) {
        SQLiteOpenHelper helper = mDatabase.getHelper();
        return DatabaseUtils.queryNumEntries(helper.getReadableDatabase(), table.getName());
    }

    private void deleteDatabaseIfNecessary() {
        Context context = getContext();
        File db = context.getDatabasePath(mDatabase.getDatabaseName());
        if (db.exists()) context.deleteDatabase(mDatabase.getDatabaseName());
    }
}
