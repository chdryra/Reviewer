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
import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.DbTableDef;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.ReviewerDbRow;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdData;
import com.chdryra.android.reviewer.Model.MdDataList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.RCollectionReview;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public void testAddReviewToAuthorsTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.AUTHORS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        Set<Author> authors = new HashSet<>();
        authors.add(mNode.getAuthor());
        authors.add(mNode.getParent().getAuthor());
        for (ReviewNode child : mNode.getChildren()) {
            authors.add(child.getAuthor());
        }

        assertEquals(authors.size(), getNumberRows(table));

        testAuthorsRow(mNode);
        testAuthorsRow(mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testAuthorsRow(child);
        }
    }

    @SmallTest
    public void testAddReviewToTagsTable() {
        ReviewerDbContract.ReviewerDbTable table = ReviewerDbContract.TAGS_TABLE;

        int numTags = 5;
        Map<String, ArrayList<String>> tagsMap = new HashMap<>();
        GvTagList tags = new GvTagList();
        for (int i = 0; i < numTags; ++i) {
            String tag = RandomString.nextWord();
            tags.add(new GvTagList.GvTag(tag));
            tagsMap.put(tag, new ArrayList<String>());
        }

        ReviewNode parent = mNode.getParent();
        RCollectionReview<ReviewNode> children = mNode.getChildren();
        GvTagList parentTags = new GvTagList();
        for (int i = 0; i < 3; ++i) {
            GvTagList.GvTag tag = tags.getItem(i);
            parentTags.add(tag);
            tagsMap.get(tag.get()).add(parent.getReview().getId().toString());
        }
        GvTagList nodeTags = new GvTagList();
        for (int i = 1; i < 4; ++i) {
            GvTagList.GvTag tag = tags.getItem(i);
            nodeTags.add(tag);
            tagsMap.get(tag.get()).add(mNode.getReview().getId().toString());
        }
        GvTagList childrenTags = new GvTagList();
        for (int i = 2; i < 5; ++i) {
            GvTagList.GvTag tag = tags.getItem(i);
            childrenTags.add(tag);
            for (ReviewNode child : children) {
                tagsMap.get(tag.get()).add(child.getReview().getId().toString());
            }
        }

        TagsManager.tag(parent.getReview(), parentTags);
        assertEquals(3, TagsManager.getTags(parent.getReview()).size());
        TagsManager.tag(mNode.getReview(), nodeTags);
        assertEquals(3, TagsManager.getTags(mNode.getReview()).size());
        for (ReviewNode child : children) {
            TagsManager.tag(child.getReview(), childrenTags);
            assertEquals(3, TagsManager.getTags(child.getReview()).size());
        }

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        assertEquals(tags.size(), getNumberRows(table));

        for (GvTagList.GvTag tag : tags) {
            testTag(tag.get(), tagsMap.get(tag.get()));
        }
    }

    @SmallTest
    public void testAddReviewToCommentsTable() {
        testAddReviewToTable(ConfigDb.DbData.COMMENTS);
    }

    @SmallTest
    public void testAddReviewToLocationsTable() {
        testAddReviewToTable(ConfigDb.DbData.LOCATIONS);
    }

    @SmallTest
    public void testAddReviewToImagesTable() {
        testAddReviewToTable(ConfigDb.DbData.IMAGES);
    }

    @SmallTest
    public void testAddReviewToFactsTable() {
        testAddReviewToTable(ConfigDb.DbData.FACTS);
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

    private void testTag(String tag, ArrayList<String> tagReviews) {
        ContentValues vals = getRowVals(ConfigDb.DbData.TAGS, tag);
        String rowTag = (String) vals.get(ReviewerDbRow.TagsRow.TAG);
        String csvReviews = (String) vals.get(ReviewerDbRow.TagsRow.REVIEWS);
        ArrayList<String> rowReviews = new ArrayList<>();
        rowReviews.addAll(Arrays.asList(csvReviews.split(",")));

        assertEquals(tag, rowTag);
        assertEquals(tagReviews.size(), rowReviews.size());
        assertEquals(tagReviews, rowReviews);
    }

    private void testAddReviewToTable(ConfigDb.DbData tableType) {
        ConfigDb.Config config = ConfigDb.getConfig(tableType);
        ReviewerDbContract.ReviewerDbTable table = config.getTable();

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewNodeToDb(mNode);
        int num = getData(tableType, mNode).size();
        num += getData(tableType, mNode.getParent()).size();
        for (ReviewNode child : mNode.getChildren()) {
            num += getData(tableType, child).size();
        }
        assertEquals(num, getNumberRows(table));

        testRows(tableType, mNode);
        testRows(tableType, mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testRows(tableType, child);
        }
    }

    private void testRows(ConfigDb.DbData dataType, ReviewNode node) {
        MdDataList comments = getData(dataType, node);
        for (int i = 0; i < comments.size(); ++i) {
            testRow(dataType, (MdData) comments.getItem(i), i + 1);
        }
    }

    private void testRow(ConfigDb.DbData dataType, MdData datum, int index) {
        switch (dataType) {
            case COMMENTS:
                testCommentsRow((MdCommentList.MdComment) datum, index);
                break;
            case FACTS:
                testFactsRow((MdFactList.MdFact) datum, index);
                break;
            case IMAGES:
                testImagesRow((MdImageList.MdImage) datum, index);
                break;
            case LOCATIONS:
                testLocationsRow((MdLocationList.MdLocation) datum, index);
                break;
            default:
                fail();
        }
    }

    private MdDataList getData(ConfigDb.DbData dataType, ReviewNode node) {
        switch (dataType) {
            case COMMENTS:
                return node.getComments();
            case FACTS:
                return node.getFacts();
            case IMAGES:
                return node.getImages();
            case LOCATIONS:
                return node.getLocations();
            default:
                fail();
                return new MdDataList(null);
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

    private void testImagesRow(MdImageList.MdImage image, int index) {
        String id = image.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "i" + String
                .valueOf(index);

        ContentValues vals = getRowVals(ConfigDb.DbData.IMAGES, id);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] array = bos.toByteArray();

        assertTrue(Arrays.equals(array, (byte[]) vals.get(ReviewerDbRow.ImagesRow.BITMAP)));
        assertEquals(id, vals.get(ReviewerDbRow.ImagesRow.IMAGE_ID));
        assertEquals(image.getReviewId().toString(), vals.get(ReviewerDbRow.ImagesRow.REVIEW_ID));
        assertEquals(image.getCaption(), vals.get(ReviewerDbRow.ImagesRow.CAPTION));
        assertEquals(image.isCover(), vals.get(ReviewerDbRow.ImagesRow.IS_COVER));
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

    private void testAuthorsRow(ReviewNode node) {
        Author author = node.getAuthor();
        String id = author.getUserId().toString();

        ContentValues vals = getRowVals(ConfigDb.DbData.AUTHORS, id);

        assertEquals(id, vals.get(ReviewerDbRow.AuthorsRow.USER_ID));
        assertEquals(author.getName(), vals.get(ReviewerDbRow.AuthorsRow.AUTHOR_NAME));
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
