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
import com.chdryra.android.reviewer.Database.ReviewerDbTable;
import com.chdryra.android.reviewer.Database.RowAuthor;
import com.chdryra.android.reviewer.Database.RowComment;
import com.chdryra.android.reviewer.Database.RowFact;
import com.chdryra.android.reviewer.Database.RowImage;
import com.chdryra.android.reviewer.Database.RowLocation;
import com.chdryra.android.reviewer.Database.RowReview;
import com.chdryra.android.reviewer.Database.RowReviewNode;
import com.chdryra.android.reviewer.Database.RowTag;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdData;
import com.chdryra.android.reviewer.Model.MdDataList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
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
        ReviewerDbTable table = ReviewerDbContract.REVIEWS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
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
        ReviewerDbTable table = ReviewerDbContract.TREES_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
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
        ReviewerDbTable table = ReviewerDbContract.AUTHORS_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
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
        ReviewerDbTable table = ReviewerDbContract.TAGS_TABLE;

        int numTags = 5;
        Map<String, ArrayList<String>> tagsMap = new HashMap<>();
        GvTagList tags = new GvTagList();
        for (int i = 0; i < numTags; ++i) {
            String tag = RandomString.nextWord();
            tags.add(new GvTagList.GvTag(tag));
            tagsMap.put(tag, new ArrayList<String>());
        }

        ReviewNode parent = mNode.getParent();
        ReviewIdableList<ReviewNode> children = mNode.getChildren();
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
        mDatabase.addReviewTreeToDb(mNode);
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

    @SmallTest
    public void testGetReviewFromDb() {
        mDatabase.addReviewTreeToDb(mNode);
        Review review = mNode.getReview();
        Review reviewDb = mDatabase.getReviewFromDb(review.getId().toString());
        assertEquals(review, reviewDb);
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
        String rowTag = (String) vals.get(RowTag.TAG);
        String csvReviews = (String) vals.get(RowTag.REVIEWS);
        ArrayList<String> rowReviews = new ArrayList<>();
        rowReviews.addAll(Arrays.asList(csvReviews.split(",")));

        assertEquals(tag, rowTag);
        assertEquals(tagReviews.size(), rowReviews.size());
        assertEquals(tagReviews, rowReviews);
    }

    private void testAddReviewToTable(ConfigDb.DbData tableType) {
        ConfigDb.Config config = ConfigDb.getConfig(tableType);
        ReviewerDbTable table = config.getTable();

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
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

        assertEquals(id, vals.get(RowLocation.LOCATION_ID));
        assertEquals(location.getReviewId().toString(), vals.get(RowLocation
                .REVIEW_ID));
        assertEquals(location.getLatLng().latitude, vals.get(RowLocation.LAT));
        assertEquals(location.getLatLng().longitude, vals.get(RowLocation.LNG));
        assertEquals(location.getName(), vals.get(RowLocation.NAME));
    }

    private void testImagesRow(MdImageList.MdImage image, int index) {
        String id = image.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "i" + String
                .valueOf(index);

        ContentValues vals = getRowVals(ConfigDb.DbData.IMAGES, id);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] array = bos.toByteArray();

        assertTrue(Arrays.equals(array, (byte[]) vals.get(RowImage.BITMAP)));
        assertEquals(id, vals.get(RowImage.IMAGE_ID));
        assertEquals(image.getReviewId().toString(), vals.get(RowImage.REVIEW_ID));
        assertEquals(image.getDate().getTime(), vals.get(RowImage.DATE));
        assertEquals(image.getCaption(), vals.get(RowImage.CAPTION));
        assertEquals(image.isCover(), vals.get(RowImage.IS_COVER));
    }

    private void testFactsRow(MdFactList.MdFact fact, int index) {
        String id = fact.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "f" + String
                .valueOf(index);
        ContentValues vals = getRowVals(ConfigDb.DbData.FACTS, id);

        assertEquals(id, vals.get(RowFact.FACT_ID));
        assertEquals(fact.getReviewId().toString(), vals.get(RowFact.REVIEW_ID));
        assertEquals(fact.getLabel(), vals.get(RowFact.LABEL));
        assertEquals(fact.getValue(), vals.get(RowFact.VALUE));
        assertEquals(fact.isUrl(), vals.get(RowFact.IS_URL));
    }

    private void testCommentsRow(MdCommentList.MdComment comment, int index) {
        String id = comment.getReviewId().toString() + ReviewerDbRow.SEPARATOR + "c" + String
                .valueOf(index);
        ContentValues vals = getRowVals(ConfigDb.DbData.COMMENTS, id);

        assertEquals(id, vals.get(RowComment.COMMENT_ID));
        assertEquals(comment.getReviewId().toString(), vals.get(RowComment
                .REVIEW_ID));
        assertEquals(comment.getComment(), vals.get(RowComment.COMMENT));
        assertEquals(comment.isHeadline(), vals.get(RowComment.IS_HEADLINE));
    }

    private void testReviewTreesRow(ReviewNode node) {
        String id = node.getId().toString();
        ContentValues vals = getRowVals(ConfigDb.DbData.REVIEW_TREES, id);

        Review review = node.getReview();
        ReviewNode parent = node.getParent();
        assertEquals(id, vals.get(RowReviewNode.NODE_ID));
        assertEquals(review.getId().toString(), vals.get(RowReviewNode.REVIEW_ID));
        assertEquals(mNode.isRatingAverageOfChildren(),
                vals.get(RowReviewNode.IS_AVERAGE));
        if (parent != null) {
            assertEquals(parent.getId().toString(), vals.get(RowReviewNode
                    .PARENT_ID));
        } else {
            assertNull(vals.get(RowReviewNode.PARENT_ID));
        }
    }

    private void testReviewsRow(Review review) {
        String id = review.getId().toString();
        String authorId = review.getAuthor().getUserId().toString();
        long millis = review.getPublishDate().getTime();

        ContentValues vals = getRowVals(ConfigDb.DbData.REVIEWS, id);

        assertEquals(id, vals.get(RowReview.REVIEW_ID));
        assertEquals(review.getSubject().get(), vals.get(RowReview.SUBJECT));
        assertEquals(review.getRating().get(), vals.get(RowReview.RATING));
        assertEquals(authorId, vals.get(RowReview.AUTHOR_ID));
        assertEquals(millis, vals.get(RowReview.PUBLISH_DATE));
    }

    private void testAuthorsRow(ReviewNode node) {
        Author author = node.getAuthor();
        String id = author.getUserId().toString();

        ContentValues vals = getRowVals(ConfigDb.DbData.AUTHORS, id);

        assertEquals(id, vals.get(RowAuthor.USER_ID));
        assertEquals(author.getName(), vals.get(RowAuthor.AUTHOR_NAME));
    }

    private ContentValues getRowVals(ConfigDb.DbData dataType, String id) {
        ConfigDb.Config config = ConfigDb.getConfig(dataType);
        String pkColumn = config.getPkColumn();
        ReviewerDbTable table = config.getTable();
        DbTableDef.DbColumnDef idCol = table.getColumn(pkColumn);

        ReviewerDbRow.TableRow row = mDatabase.getRowWhere(table, idCol, id);

        assertTrue(row.hasData());
        return row.getContentValues();
    }

    private long getNumberRows(ReviewerDbTable table) {
        SQLiteOpenHelper helper = mDatabase.getHelper();
        return DatabaseUtils.queryNumEntries(helper.getReadableDatabase(), table.getName());
    }

    private void deleteDatabaseIfNecessary() {
        Context context = getContext();
        File db = context.getDatabasePath(mDatabase.getDatabaseName());
        if (db.exists()) context.deleteDatabase(mDatabase.getDatabaseName());
    }
}
