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
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
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
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdData;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.ReviewTreeComparer;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
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

    Review mReview;
    ReviewerDb     mDatabase;

    @SmallTest
    public void testGetDatabase() {
        assertNotNull(ReviewerDb.getDatabase(getContext()));
    }

    @SmallTest
    public void testGetTestDatabase() {
        assertNotNull(ReviewerDb.getTestDatabase(getContext()));
    }

    @SmallTest
    public void testGetDatabaseName() {
        assertEquals("Reviewer.db", ReviewerDb.getDatabase(getContext()).getDatabaseName());
        assertEquals("TestReviewer.db", ReviewerDb.getTestDatabase(getContext()).getDatabaseName());
    }

    @SmallTest
    public void testGetHelper() {
        assertNotNull(mDatabase.getHelper());
    }

    @SmallTest
    public void testAddReviewToReviewsTable() {
        mDatabase.addReviewToDb(mReview);

        testReviewsRow(mReview);
        testReviewsRow(mReview.getParent().getReview());
        for (ReviewNode child : mReview.getChildren()) {
            testReviewsRow(child.getReview());
        }
    }

    @SmallTest
    public void testAddReviewToAuthorsTable() {
        ReviewerDbTable table = ReviewerDbContract.AUTHORS_TABLE;

        assertEquals(0, getNumberRows(table));

        mDatabase.addReviewTreeToDb(mReview);

        Set<Author> authors = new HashSet<>();
        authors.add(mReview.getAuthor());
        authors.add(mReview.getParent().getAuthor());
        for (ReviewNode child : mReview.getChildren()) {
            authors.add(child.getAuthor());
        }
        assertEquals(authors.size(), getNumberRows(table));

        testAuthorsRow(mReview);
        testAuthorsRow(mReview.getParent());
        for (ReviewNode child : mReview.getChildren()) {
            testAuthorsRow(child);
        }
    }

    @SmallTest
    public void testAddReviewToTagsTable() {
        ReviewerDbTable table = ReviewerDbContract.TAGS_TABLE;

        Map<String, ArrayList<String>> tagsMap = tagAndTestNodes();
        assertEquals(0, getNumberRows(table));
        mDatabase.addReviewTreeToDb(mReview);
        assertEquals(tagsMap.size(), getNumberRows(table));

        for (String tag : tagsMap.keySet()) {
            testTag(tag, tagsMap.get(tag));
        }
    }

    @SmallTest
    public void testLoadTags() {
        ReviewerDbTable table = ReviewerDbContract.TAGS_TABLE;
        Map<String, ArrayList<String>> tagsMap = tagAndTestNodes();

        ReviewId parentId = mReview.getParent().getId();
        ReviewId nodeId = mReview.getId();
        IdableList<ReviewNode> children = mReview.getChildren();

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
        mDatabase.addReviewTreeToDb(mReview);
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
        IdableList<ReviewNode> nodes = new IdableList<>();
        for (int i = 0; i < NUM; ++i) {
            ReviewNode node = ReviewMocker.newReviewNode(false);
            nodes.add(node);
            mDatabase.addReviewTreeToDb(node);
        }

        IdableList<ReviewNode> fromDb = mDatabase.getReviewTreesFromDb();

        for (int i = 0; i < NUM; ++i) {
            assertEquals(nodes.getItem(i).getParent(), fromDb.getItem(i));
        }
    }

    @SmallTest
    public void testAddMultipleTrees() {
        int numTags = 5;
        GvTagList tags = GvDataMocker.newTagList(numTags, false);
        GvTagList tags1 = new GvTagList();
        GvTagList tags2 = new GvTagList();
        GvTagList tags3 = new GvTagList();
        for (int i = 0; i < 3; ++i) {
            tags1.add(tags.getItem(i));
        }
        for (int i = 1; i < 4; ++i) {
            tags2.add(tags.getItem(i));
        }
        for (int i = 2; i < 5; ++i) {
            tags3.add(tags.getItem(i));
        }

        //First tree
        ReviewNode tree1 = mReview;
        TagsManager.tag(tree1.getId(), tags1.toStringArray());

        mDatabase.addReviewTreeToDb(tree1);

        IdableList<ReviewNode> fromDb = mDatabase.getReviewTreesFromDb();
        assertEquals(1, fromDb.size());
        assertTrue(ReviewTreeComparer.compareTrees(tree1, fromDb.getItem(0)));

        ReviewNode parent1 = mReview.getParent();
        int numComments = getNumDataInSubTree(parent1, ConfigDb.DbData.COMMENTS);
        int numFacts = getNumDataInSubTree(parent1, ConfigDb.DbData.FACTS);
        int numLocations = getNumDataInSubTree(parent1, ConfigDb.DbData.LOCATIONS);
        int numImages = getNumDataInSubTree(parent1, ConfigDb.DbData.IMAGES);
        int numNodes1 = 1 + mReview.getChildren().size() + 1;
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(3, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(numComments, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages, getNumberRows(ReviewerDbContract.IMAGES_TABLE));
        assertEquals(numImages, getNumberRows(ReviewerDbContract.IMAGES_TABLE));

        //Different tree same reviews
        Review parentReview = mReview.getParent().getReview();
        Review nodeReview = mReview.getReview();
        IdableList<Review> childReviews = new IdableList<>();
        for (ReviewNode child : mReview.getChildren()) {
            childReviews.add(child.getReview());
        }
        ReviewTreeNode node2 = new ReviewTreeNode(childReviews.getItem(0), false, RandomReviewId
                .nextId());
        IdableList<Review> children = new IdableList<>();
        children.add(parentReview);
        children.add(nodeReview);
        for (Review child : children) {
            node2.addChild(new ReviewTreeNode(child, false, RandomReviewId.nextId()));
        }
        ReviewNode tree2 = node2.createTree();
        TagsManager.tag(tree2.getId(), tags2.toStringArray());

        mDatabase.addReviewTreeToDb(tree2);

        fromDb = mDatabase.getReviewTreesFromDb();
        assertEquals(2, fromDb.size());
        assertTrue(ReviewTreeComparer.compareTrees(tree1, fromDb.getItem(0)));
        assertTrue(ReviewTreeComparer.compareTrees(tree2, fromDb.getItem(1)));

        int numNodes2 = 1 + tree2.getChildren().size();
        assertEquals(numNodes1 + numNodes2, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(4, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(numComments, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages, getNumberRows(ReviewerDbContract.IMAGES_TABLE));

        //Different tree different reviews
        ReviewNode tree3 = ReviewMocker.newReviewNode(false);
        TagsManager.tag(tree3.getId(), tags3.toStringArray());

        mDatabase.addReviewTreeToDb(tree3);

        fromDb = mDatabase.getReviewTreesFromDb();
        assertEquals(3, fromDb.size());
        assertTrue(ReviewTreeComparer.compareTrees(tree1, fromDb.getItem(0)));
        assertTrue(ReviewTreeComparer.compareTrees(tree2, fromDb.getItem(1)));
        assertTrue(ReviewTreeComparer.compareTrees(tree3, fromDb.getItem(2)));

        ReviewNode parent3 = tree3.getParent();
        numComments += getNumDataInSubTree(parent3, ConfigDb.DbData.COMMENTS);
        numFacts += getNumDataInSubTree(parent3, ConfigDb.DbData.FACTS);
        numLocations += getNumDataInSubTree(parent3, ConfigDb.DbData.LOCATIONS);
        numImages += getNumDataInSubTree(parent3, ConfigDb.DbData.IMAGES);
        int numNodes3 = 2 + tree3.getChildren().size();
        int numNodes = numNodes1 + numNodes2 + numNodes3;
        assertEquals(numNodes, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(numNodes1 + numNodes3, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(numNodes1 + numNodes3, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(5, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(numComments, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages, getNumberRows(ReviewerDbContract.IMAGES_TABLE));
    }

    @SmallTest
    public void testDeleteReviewTreeFromDb() {
        int numTags = 5;
        GvTagList tags = GvDataMocker.newTagList(numTags, false);
        GvTagList tags1 = new GvTagList();
        GvTagList tags2 = new GvTagList();
        GvTagList tags3 = new GvTagList();
        for (int i = 0; i < 3; ++i) {
            tags1.add(tags.getItem(i));
        }
        for (int i = 1; i < 4; ++i) {
            tags2.add(tags.getItem(i));
        }
        for (int i = 2; i < 5; ++i) {
            tags3.add(tags.getItem(i));
        }

        Review parentReview = mReview.getParent().getReview();
        Review nodeReview = mReview.getReview();
        IdableList<Review> childReviews = new IdableList<>();
        for (ReviewNode child : mReview.getChildren()) {
            childReviews.add(child.getReview());
        }

        ReviewTreeNode rootNode = new ReviewTreeNode(childReviews.getItem(0), false,
                RandomReviewId.nextId());
        IdableList<Review> children = new IdableList<>();
        children.add(parentReview);
        children.add(nodeReview);
        for (Review child : children) {
            rootNode.addChild(new ReviewTreeNode(child, false, RandomReviewId.nextId()));
        }
        ReviewNode tree1 = mReview;
        ReviewNode tree2 = rootNode.createTree();
        ReviewNode tree3 = ReviewMocker.newReviewNode(false);

        TagsManager.tag(tree1.getId(), tags1.toStringArray());
        TagsManager.tag(tree2.getId(), tags2.toStringArray());
        TagsManager.tag(tree3.getId(), tags3.toStringArray());

        mDatabase.addReviewTreeToDb(tree1);
        mDatabase.addReviewTreeToDb(tree2);
        mDatabase.addReviewTreeToDb(tree3);

        IdableList<ReviewNode> fromDb = mDatabase.getReviewTreesFromDb();
        assertEquals(3, fromDb.size());
        assertTrue(ReviewTreeComparer.compareTrees(tree1, fromDb.getItem(0)));
        assertTrue(ReviewTreeComparer.compareTrees(tree2, fromDb.getItem(1)));
        assertTrue(ReviewTreeComparer.compareTrees(tree3, fromDb.getItem(2)));

        ReviewNode parent1 = tree1.getParent();
        ReviewNode parent3 = tree3.getParent();
        long numComments1 = getNumDataInSubTree(parent1, ConfigDb.DbData.COMMENTS);
        long numFacts1 = getNumDataInSubTree(parent1, ConfigDb.DbData.FACTS);
        long numLocations1 = getNumDataInSubTree(parent1, ConfigDb.DbData.LOCATIONS);
        long numImages1 = getNumDataInSubTree(parent1, ConfigDb.DbData.IMAGES);
        long numComments3 = getNumDataInSubTree(parent3, ConfigDb.DbData.COMMENTS);
        long numFacts3 = getNumDataInSubTree(parent3, ConfigDb.DbData.FACTS);
        long numLocations3 = getNumDataInSubTree(parent3, ConfigDb.DbData.LOCATIONS);
        long numImages3 = getNumDataInSubTree(parent3, ConfigDb.DbData.IMAGES);
        long numComments = numComments1 + numComments3;
        long numFacts = numFacts1 + numFacts3;
        long numLocations = numLocations1 + numLocations3;
        long numImages = numImages1 + numImages3;

        int numNodes1 = 2 + tree1.getChildren().size();
        int numNodes2 = 1 + tree2.getChildren().size();
        int numNodes3 = 2 + tree3.getChildren().size();
        int numNodes = numNodes1 + numNodes2 + numNodes3;

        assertEquals(numNodes, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(numNodes1 + numNodes3, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(numNodes1 + numNodes3, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(5, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(numComments, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages, getNumberRows(ReviewerDbContract.IMAGES_TABLE));

        mDatabase.deleteReviewTreeFromDb(tree3.getId().toString());

        fromDb = mDatabase.getReviewTreesFromDb();
        assertEquals(2, fromDb.size());
        assertTrue(ReviewTreeComparer.compareTrees(tree1, fromDb.getItem(0)));
        assertTrue(ReviewTreeComparer.compareTrees(tree2, fromDb.getItem(1)));

        assertEquals(numNodes - numNodes3, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(4, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(numComments1, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts1, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations1, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages1, getNumberRows(ReviewerDbContract.IMAGES_TABLE));

        mDatabase.deleteReviewTreeFromDb(tree2.getId().toString());

        fromDb = mDatabase.getReviewTreesFromDb();
        assertEquals(1, fromDb.size());
        assertTrue(ReviewTreeComparer.compareTrees(tree1, fromDb.getItem(0)));

        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(numNodes1, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(3, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(numComments1, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(numFacts1, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(numLocations1, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(numImages1, getNumberRows(ReviewerDbContract.IMAGES_TABLE));

        mDatabase.deleteReviewTreeFromDb(tree1.getId().toString());

        assertEquals(0, getNumberRows(ReviewerDbContract.TREES_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.REVIEWS_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.AUTHORS_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.TAGS_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.COMMENTS_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.FACTS_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.LOCATIONS_TABLE));
        assertEquals(0, getNumberRows(ReviewerDbContract.IMAGES_TABLE));
    }

    @Override
    protected void setUp() throws Exception {
        mDatabase = ReviewerDb.getTestDatabase(getContext());
        mReview = (ReviewTreeNode) ReviewMocker.newReviewNode(false);
        deleteDatabaseIfNecessary();
    }

    @Override
    protected void tearDown() throws Exception {
        deleteDatabaseIfNecessary();
    }

    private int getNumDataInSubTree(ReviewNode node, ConfigDb.DbData dataType) {
        int n = 0;
        switch (dataType) {
            case COMMENTS:
                n = node.getComments().size();
                break;
            case FACTS:
                n = node.getFacts().size();
                break;
            case IMAGES:
                n = node.getImages().size();
                break;
            case LOCATIONS:
                n = node.getLocations().size();
                break;
            default:
                fail();
        }

        return n;
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
        GvTagList tags = GvDataMocker.newTagList(numTags, false);
        Map<String, ArrayList<String>> tagsMap = newTagsMap(tags);

        ReviewNode parent = mReview.getParent();
        IdableList<ReviewNode> children = mReview.getChildren();

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
            tagsMap.get(tag.get()).add(mReview.getReview().getId().toString());
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
        TagsManager.tag(mReview.getReview().getId(), nodeTags.toStringArray());
        assertEquals(3, TagsManager.getTags(mReview.getReview().getId()).size());
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
        mDatabase.addReviewTreeToDb(mReview);
        int num = getData(tableType, mReview.getRoot()).size();

        assertEquals(num, getNumberRows(table));

        testRows(tableType, mReview.getReview().getTreeRepresentation());
        testRows(tableType, mReview.getParent().getReview().getTreeRepresentation());
        for (ReviewNode child : mReview.getChildren()) {
            testRows(tableType, child.getReview().getTreeRepresentation());
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
        assertEquals(mReview.isRatingAverageOfChildren(),
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
        SQLiteOpenHelper helper = mDatabase.getHelper();

        String columnName = idCol.getName();
        String tableName = table.getName();
        String value = id != null ? " = ?" : " IS NULL";
        String whereClause = columnName != null ? " WHERE " + columnName + value : "";

        String query = "SELECT * FROM " + tableName + whereClause;
        String[] args = id != null ? new String[]{id} : null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        ReviewerDbRow.TableRow row;
        if (cursor.getCount() == 0) {
            row = ReviewerDbRow.emptyRow(table.getRowClass());
        } else {
            cursor.moveToFirst();
            row = ReviewerDbRow.newRow(cursor, table.getRowClass());
            assertTrue(row.hasData());
        }

        cursor.close();
        db.close();

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
