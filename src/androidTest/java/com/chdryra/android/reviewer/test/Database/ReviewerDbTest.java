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
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

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
    private static final int NUM = 3;

    ReviewTreeNode mNode;
    ReviewerDb     mDatabase;

    @SmallTest
    public void testAddReviewTreeToReviewTreesTable() {
        ReviewerDbTable table = ReviewerDbContract.TREES_TABLE;

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
        int numNodes = 2 + mNode.getChildren().size();
        assertEquals(numNodes, getNumberRows(table));

        testReviewTreesRow(mNode);
        testReviewTreesRow(mNode.getParent());
        for (ReviewNode child : mNode.getChildren()) {
            testReviewTreesRow(child);
        }
    }

    @SmallTest
    public void testAddMultipleTrees() {
        Review parentReview = mNode.getParent().getReview();
        Review nodeReview = mNode.getReview();
        ReviewIdableList<Review> childReviews = new ReviewIdableList<>();
        for (ReviewNode child : mNode.getChildren()) {
            childReviews.add(child.getReview());
        }

        //First tree
        ReviewIdableList<Review> children = new ReviewIdableList<>();
        children.add(parentReview);
        children.add(nodeReview);

        ReviewNode tree1 = mNode;

        mDatabase.addReviewTreeToDb(tree1);
        int parentNode = mNode.getParent() != null ? 1 : 0;
        int numNodes = 1 + mNode.getChildren().size() + parentNode;
        assertEquals(numNodes, getNumberRows(ReviewerDbContract.TREES_TABLE));

        long reviewRows = getNumberRows(ReviewerDbContract.REVIEWS_TABLE);
        long commentsRows = getNumberRows(ReviewerDbContract.COMMENTS_TABLE);
        long factsRows = getNumberRows(ReviewerDbContract.FACTS_TABLE);
        long locationsRows = getNumberRows(ReviewerDbContract.LOCATIONS_TABLE);
        long imagesRows = getNumberRows(ReviewerDbContract.IMAGES_TABLE);

        //Different tree same reviews
        ReviewTreeNode rootNode = new ReviewTreeNode(childReviews.getItem(0), false,
                ReviewId.generateId());
        for (Review child : children) {
            rootNode.addChild(new ReviewTreeNode(child, false, ReviewId.generateId()));
        }
        ReviewNode tree2 = rootNode.createTree();
        mDatabase.addReviewTreeToDb(tree2);
        assertEquals(numNodes + 3, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(reviewRows, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(commentsRows, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(factsRows, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(locationsRows, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(imagesRows, getNumberRows(ReviewerDbContract.IMAGES_TABLE));

        //Different tree different reviews
        ReviewNode tree3 = ReviewMocker.newReviewNode();
        ReviewNode parent3 = tree3.getParent();
        ReviewIdableList<ReviewNode> children3 = tree3.getChildren();

        mDatabase.addReviewTreeToDb(tree3);

        int numNodes3 = 2 + tree3.getChildren().size();
        assertEquals(numNodes + 3 + numNodes3, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(reviewRows + numNodes3, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));

        long numComments = commentsRows + tree3.getComments().size() + parent3.getComments().size();
        long numFacts = factsRows + tree3.getFacts().size() + parent3.getFacts().size();
        long numLocations = locationsRows + tree3.getLocations().size() + parent3.getLocations()
                .size();
        long numImages = imagesRows + tree3.getImages().size() + parent3.getImages().size();
        for (ReviewNode child : children3) {
            numComments += child.getComments().size();
            numFacts += child.getFacts().size();
            numLocations += child.getLocations().size();
            numImages += child.getImages().size();
        }

        assertEquals(numComments, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages, getNumberRows(ReviewerDbContract.IMAGES_TABLE));
    }

    @SmallTest
    public void testAddReviewToReviewsTable() {
        mDatabase.addReviewTreeToDb(mNode);

        testReviewsRow(mNode.getReview());
        testReviewsRow(mNode.getParent().getReview());
        for (ReviewNode child : mNode.getChildren()) {
            testReviewsRow(child.getReview());
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

        Map<String, ArrayList<String>> tagsMap = tagAndTestNodes();
        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
        assertEquals(tagsMap.size(), getNumberRows(table));

        for (String tag : tagsMap.keySet()) {
            testTag(tag, tagsMap.get(tag));
        }
    }

    @SmallTest
    public void testLoadTags() {
        ReviewerDbTable table = ReviewerDbContract.TAGS_TABLE;
        Map<String, ArrayList<String>> tagsMap = tagAndTestNodes();

        ReviewId parentId = mNode.getParent().getId();
        ReviewId nodeId = mNode.getId();
        ReviewIdableList<ReviewNode> children = mNode.getChildren();

        TagsManager.ReviewTagCollection parentTags = TagsManager.getTags(parentId);
        assertTrue(parentTags.size() > 0);
        TagsManager.ReviewTagCollection nodeTags = TagsManager.getTags(nodeId);
        assertTrue(nodeTags.size() > 0);
        ArrayList<TagsManager.ReviewTagCollection> childrenTags = new ArrayList<>();
        for (ReviewNode child : children) {
            TagsManager.ReviewTagCollection childTags = TagsManager.getTags(child.getId());
            childrenTags.add(childTags);
            assertTrue(childTags.size() > 0);
        }
        assertEquals(children.size(), childrenTags.size());

        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mNode);
        assertEquals(tagsMap.size(), getNumberRows(table));

        for (TagsManager.ReviewTag tag : parentTags) {
            TagsManager.untag(parentId, tag);
        }
        assertEquals(0, TagsManager.getTags(parentId).size());

        for (TagsManager.ReviewTag tag : nodeTags) {
            TagsManager.untag(nodeId, tag);
        }
        assertEquals(0, TagsManager.getTags(nodeId).size());

        for (int i = 0; i < children.size(); ++i) {
            ReviewId childId = children.getItem(i).getId();
            for (TagsManager.ReviewTag tag : childrenTags.get(i)) {
                TagsManager.untag(childId, tag);
            }
            assertEquals(0, TagsManager.getTags(childId).size());
        }

        mDatabase.loadTags();

        checkTagList(parentTags, TagsManager.getTags(parentId), tagsMap);
        checkTagList(nodeTags, TagsManager.getTags(nodeId), tagsMap);
        for (int i = 0; i < children.size(); ++i) {
            ReviewId childId = children.getItem(i).getId();
            checkTagList(childrenTags.get(i), TagsManager.getTags(childId), tagsMap);
        }
    }

    @SmallTest
    public void testAddReviewToCommentsTable() {
        testAddReviewTreeToTable(ConfigDb.DbData.COMMENTS);
    }

    @SmallTest
    public void testAddReviewToLocationsTable() {
        testAddReviewTreeToTable(ConfigDb.DbData.LOCATIONS);
    }

    @SmallTest
    public void testAddReviewToImagesTable() {
        testAddReviewTreeToTable(ConfigDb.DbData.IMAGES);
    }

    @SmallTest
    public void testAddReviewToFactsTable() {
        testAddReviewTreeToTable(ConfigDb.DbData.FACTS);
    }

    @SmallTest
    public void testGetReviewTreesFromDb() {
        ReviewIdableList<ReviewNode> nodes = new ReviewIdableList<>();
        for (int i = 0; i < NUM; ++i) {
            ReviewNode node = ReviewMocker.newReviewNode();
            nodes.add(node);
            mDatabase.addReviewTreeToDb(node);
        }

        ReviewIdableList<ReviewNode> fromDb = mDatabase.getReviewTreesFromDb();

        for (int i = 0; i < NUM; ++i) {
            assertEquals(nodes.getItem(i).getParent(), fromDb.getItem(i));
        }
    }

    @Override
    protected void setUp() throws Exception {
        mDatabase = ReviewerDb.getTestDatabase(getContext());
        mNode = (ReviewTreeNode) ReviewMocker.newReviewNode();
        deleteDatabaseIfNecessary();
    }

    @Override
    protected void tearDown() throws Exception {
        deleteDatabaseIfNecessary();
    }

    private Map<String, ArrayList<String>> newTagsMap(GvTagList tags) {
        Map<String, ArrayList<String>> tagsMap = new HashMap<>();
        for (int i = 0; i < tags.size(); ++i) {
            tagsMap.put(tags.getItem(i).get(), new ArrayList<String>());
        }

        return tagsMap;
    }

    private Map<String, ArrayList<String>> tagAndTestNodes() {
        int numTags = 5;
        GvTagList tags = GvDataMocker.newTagList(numTags);
        Map<String, ArrayList<String>> tagsMap = newTagsMap(tags);

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

        TagsManager.tag(parent.getReview().getId(), parentTags.toStringArray());
        assertEquals(3, TagsManager.getTags(parent.getReview().getId()).size());
        TagsManager.tag(mNode.getReview().getId(), nodeTags.toStringArray());
        assertEquals(3, TagsManager.getTags(mNode.getReview().getId()).size());
        for (ReviewNode child : children) {
            TagsManager.tag(child.getReview().getId(), childrenTags.toStringArray());
            assertEquals(3, TagsManager.getTags(child.getReview().getId()).size());
        }

        return tagsMap;
    }

    private void checkTagList(TagsManager.ReviewTagCollection lhs, TagsManager
            .ReviewTagCollection rhs, Map<String, ArrayList<String>> reviewIds) {
        assertEquals(lhs.size(), rhs.size());
        for (int i = 0; i < rhs.size(); ++i) {
            TagsManager.ReviewTag tag = rhs.getItem(i);
            assertEquals(lhs.getItem(i).get(), tag.get());
            ArrayList<String> tagged = reviewIds.get(tag.get());
            ArrayList<ReviewId> taggedIds = tag.getReviews();
            for (int j = 0; j < taggedIds.size(); ++j) {
                assertEquals(tagged.get(j), taggedIds.get(j).toString());
            }
        }
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

    private void testAddReviewTreeToTable(ConfigDb.DbData tableType) {
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

    private void testDeleteReviewTreeToTable(ConfigDb.DbData tableType) {
        ConfigDb.Config config = ConfigDb.getConfig(tableType);
        ReviewerDbTable table = config.getTable();

        int num = getData(tableType, mNode).size();
        num += getData(tableType, mNode.getParent()).size();
        for (ReviewNode child : mNode.getChildren()) {
            num += getData(tableType, child).size();
        }

        assertEquals(num, getNumberRows(table));
        mDatabase.deleteReviewTreeFromDb(mNode.getId().toString());
        assertEquals(0, getNumberRows(table));
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
