/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.startouch.test.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin
        .LocalRelationalDb.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin
        .LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin
        .LocalRelationalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin
        .LocalRelationalDb.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerPersistence;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewStamp;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.RandomPublisher;
import com.chdryra.android.startouch.test.TestUtils.ReviewMocker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbTest extends AndroidTestCase {
    private static final int NUM = 1;

    private ArrayList<Review> mReviews;
    private ReviewerPersistence mDatabase;
    private TagsManager mTagsManager;
    private Map<String, ArrayList<String>> mTagsMap;

    @SmallTest
    public void testGetDatabase() {
        assertNotNull(ReviewerPersistence.getDatabase(getContext(), mTagsManager));
    }

    @SmallTest
    public void testGetTestDatabase() {
        assertNotNull(ReviewerPersistence.getTestDatabase(getContext(), mTagsManager));
    }

    @SmallTest
    public void testGetDatabaseName() {
        assertEquals("Reviewer.db", ReviewerPersistence.getDatabase(getContext(), mTagsManager)
                .getDatabaseName());
        assertEquals("TestReviewer.db", ReviewerPersistence.getTestDatabase(getContext(),
                mTagsManager).getDatabaseName());
    }

    @SmallTest
    public void testGetHelper() {
        assertNotNull(mDatabase.getHelper());
    }

    @SmallTest
    public void testReviewsTable() {
        DbTable table = ReviewerDbContractImpl.REVIEWS_TABLE;

        //Add
        assertEquals(0, getNumberRows(table));
        addReviewsToDatabase();
        int numRows = 0;
        for (Review review : mReviews) {
            numRows += 1 + review.getCriteria().size();
            testReviewInReviewsTable(review, true);
        }
        assertEquals(numRows, getNumberRows(table));

        //Delete
        for (Review review : mReviews) {
            deleteReviewFromDb(review);
            numRows -= 1 + review.getCriteria().size();
            assertEquals(numRows, getNumberRows(table));
            testReviewInReviewsTable(review, false);
        }
        assertEquals(0, getNumberRows(table));
    }

    @SmallTest
    public void testAuthorsTable() {
        DbTable table = ReviewerDbContractImpl.AUTHORS_TABLE;

        //Add
        assertEquals(0, getNumberRows(table));
        addReviewsToDatabase();
        Map<DatumAuthor, MdIdableCollection<Review>> authorMap = new HashMap<>();
        for (Review review : mReviews) {
            DatumAuthor author = review.getAuthor();
            testAuthorsRow(author, true);
            if (authorMap.get(author) == null) {
                authorMap.put(author, new MdIdableCollection<Review>());
            }
            authorMap.get(author).add(review);
        }
        assertEquals(authorMap.size(), getNumberRows(table));

        //Delete
        for (Review review : mReviews) {
            deleteReviewFromDb(review);
            MdIdableCollection<Review> authorReviews = authorMap.get(review.getAuthor());
            authorReviews.remove(review.getMdReviewId());
            testAuthorsRow(review.getAuthor(), authorReviews.size() > 0);
        }
        assertEquals(0, getNumberRows(table));
    }

    @SmallTest
    public void testTagsTable() {
        DbTable table = ReviewerDbContractImpl.TAGS_TABLE;

        //Add
        assertEquals(0, getNumberRows(table));
        addReviewsToDatabase();
        assertEquals(mTagsMap.size(), getNumberRows(table));
        for (String tag : mTagsMap.keySet()) {
            testTag(tag, mTagsMap.get(tag), null);
        }

        //Delete
        for (Review review : mReviews) {
            deleteReviewFromDb(review);
            for (String tag : mTagsMap.keySet()) {
                testTag(tag, mTagsMap.get(tag), review.getMdReviewId().toString());
            }
        }
        assertEquals(0, getNumberRows(table));
    }

    @SmallTest
    public void testCommentsTable() {
        testAddDeleteReviewToTable(ConfigDb.DbData.COMMENTS);
    }

    @SmallTest
    public void testLocationsTable() {
        testAddDeleteReviewToTable(ConfigDb.DbData.LOCATIONS);
    }

    @SmallTest
    public void testImagesTable() {
        testAddDeleteReviewToTable(ConfigDb.DbData.IMAGES);
    }

    @SmallTest
    public void testFactsTable() {
        testAddDeleteReviewToTable(ConfigDb.DbData.FACTS);
    }

    @SmallTest
    public void testLoadReviewsFromDb() {
        MdIdableCollection<Review> fromDb = mDatabase.getReviews();
        assertEquals(0, fromDb.size());

        addReviewsToDatabase();

        fromDb = mDatabase.getReviews();
        assertEquals(mReviews.size(), fromDb.size());
        for (Review review : mReviews) {
            MdReviewId id = review.getMdReviewId();
            Review dbReview = fromDb.get(id);
            assertNotNull(dbReview);
            assertEquals(review, dbReview);
        }
    }

    @SmallTest
    public void testDeleteReviewFromDb() {
        addReviewsToDatabase();
        for (Review review : mReviews) {
            MdIdableCollection<Review> fromDb = mDatabase.getReviews();
            assertNotNull(fromDb.get(review.getMdReviewId()));
            deleteReviewFromDb(review);
            fromDb = mDatabase.getReviews();
            assertNull(fromDb.get(review.getMdReviewId()));
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mTagsManager = new TagsManager();
        mDatabase = ReviewerPersistence.getTestDatabase(getContext(), mTagsManager);
        mReviews = new ArrayList<>();
        ReviewStamp publisher = RandomPublisher.nextPublisher();
        for (int i = 0; i < NUM; ++i) {
            if (i > 1) publisher = RandomPublisher.nextPublisher();
            mReviews.add(ReviewMocker.newReview(publisher));
        }
        mTagsMap = tagReviews();
        deleteDatabaseIfNecessary();
    }

    @Override
    protected void tearDown() throws Exception {
        deleteDatabaseIfNecessary();
    }

    private void testReviewInReviewsTable(Review review, boolean hasData) {
        testReviewsRow(review, null, hasData);
        MdCriterionList criteria = review.getCriteria();
        for (MdCriterion criterion : criteria) {
            testReviewsRow(criterion.getReview(), criterion.getReviewId().toString(), hasData);
        }
    }

    private Map<String, ArrayList<String>> tagReviews() {
        int numTags = 2;
        GvTagList tags = GvDataMocker.newTagList(numTags, false);

        Random r = new Random();
        for (Review review : mReviews) {
            MdReviewId id = review.getMdReviewId();
            for (int i = 0; i < 2; ++i) {
                int index = r.nextInt(numTags);
                String tag = tags.getItem(index).getString();
                mTagsManager.tagItem(id, tag);
            }
        }

        Map<String, ArrayList<String>> tagsMap = new HashMap<>();
        ItemTagCollection tagCollection = mTagsManager.getTags();
        for (ItemTag tag : tagCollection) {
            ArrayList<String> reviewList = new ArrayList<>();
            ArrayList<MdReviewId> tagged = tag.getItemIds();
            for (MdReviewId id : tagged) {
                reviewList.add(id.toString());
            }
            tagsMap.put(tag.getTag(), reviewList);
        }

        return tagsMap;
    }

    private void checkTagList(ItemTagCollection lhs,
                              ItemTagCollection rhs) {
        assertEquals(lhs.size(), rhs.size());
        for (int i = 0; i < rhs.size(); ++i) {
            ItemTag tag = rhs.getItemTag(i);
            assertEquals(lhs.getItemTag(i).getTag(), tag.getTag());
            ArrayList<String> tagged = mTagsMap.get(tag.getTag());
            ArrayList<MdReviewId> taggedIds = tag.getItemIds();
            for (int j = 0; j < taggedIds.size(); ++j) {
                assertEquals(tagged.get(j), taggedIds.get(j).toString());
            }
        }
    }

    private void testTag(String tag, ArrayList<String> tagReviews, String untaggedReview) {
        ContentValues vals = getRowVals(ConfigDb.DbData.TAGS, tag);
        String rowTag = (String) vals.get(RowTag.TAG);
        String csvReviews = (String) vals.get(RowTag.REVIEWS);
        if (csvReviews == null) {
            assertEquals(1, tagReviews.size());
            assertEquals(tagReviews.get(0), untaggedReview);
            assertNull(rowTag);
            return;
        }

        ArrayList<String> rowReviews = new ArrayList<>();
        rowReviews.addAll(Arrays.asList(csvReviews.split(",")));

        assertEquals(tag, rowTag);
        if (untaggedReview == null) {
            assertEquals(tagReviews.size(), rowReviews.size());
            assertEquals(tagReviews, rowReviews);
        } else {
            assertEquals(tagReviews.size() - 1, rowReviews.size());
            for (int i = 0; i < tagReviews.size(); ++i) {
                String tagReview = tagReviews.get(i);
                if (tagReview.equals(untaggedReview)) {
                    assertFalse(rowReviews.contains(tagReview));
                } else {
                    assertTrue(rowReviews.contains(tagReview));
                }
            }
        }
    }

    private void testAddDeleteReviewToTable(ConfigDb.DbData tableType) {
        ConfigDb.Config config = ConfigDb.getConfig(tableType);
        DbTable table = config.getTable();

        //Add
        int numRows = 0;
        assertEquals(numRows, getNumberRows(table));
        addReviewsToDatabase();
        for (Review review : mReviews) {
            numRows += getData(tableType, review).size();
            testRows(tableType, review, true);
        }
        assertEquals(numRows, getNumberRows(table));

        //Delete
        for (Review review : mReviews) {
            deleteReviewFromDb(review);
            assertEquals(numRows - getData(tableType, review).size(), getNumberRows(table));
            testRows(tableType, review, false);
        }
    }

    private void testRows(ConfigDb.DbData dataType, Review review, boolean hasData) {
        MdDataList data = getData(dataType, review);
        for (int i = 0; i < data.size(); ++i) {
            testRow(dataType, (MdData) data.getItem(i), i + 1, hasData);
        }
    }

    private void testRow(ConfigDb.DbData dataType, MdData datum, int index, boolean hasData) {
        switch (dataType) {
            case COMMENTS:
                testCommentsRow((MdComment) datum, index, hasData);
                break;
            case FACTS:
                testFactsRow((MdFact) datum, index, hasData);
                break;
            case IMAGES:
                testImagesRow((MdImage) datum, index, hasData);
                break;
            case LOCATIONS:
                testLocationsRow((MdLocation) datum, index, hasData);
                break;
            default:
                fail();
        }
    }

    private MdDataList getData(ConfigDb.DbData dataType, Review review) {
        switch (dataType) {
            case COMMENTS:
                return review.getComments();
            case FACTS:
                return review.getFacts();
            case IMAGES:
                return review.getImages();
            case LOCATIONS:
                return review.getLocations();
            default:
                fail();
                return new MdDataList(null);
        }
    }

    private void testNullContentValues(ContentValues vals, ConfigDb.DbData dataType) {
        assertNull(vals.get(ConfigDb.getConfig(dataType).getPkColumn()));
    }

    private void testLocationsRow(MdLocation location, int index, boolean hasData) {
        String id = FactoryReviewerDbTableRow.newRow(location, index).getRowId();
        ContentValues vals = getRowVals(ConfigDb.DbData.LOCATIONS, id);
        if (!hasData) {
            testNullContentValues(vals, ConfigDb.DbData.LOCATIONS);
            return;
        }

        assertEquals(id, vals.get(RowLocation.LOCATION_ID));
        assertEquals(location.getReviewId().toString(), vals.get(RowLocation
                .REVIEW_ID));
        assertEquals(location.getLatLng().latitude, vals.get(RowLocation.LAT));
        assertEquals(location.getLatLng().longitude, vals.get(RowLocation.LNG));
        assertEquals(location.getName(), vals.get(RowLocation.NAME));
    }

    private void testImagesRow(MdImage image, int index, boolean hasData) {
        String id = FactoryReviewerDbTableRow.newRow(image, index).getRowId();
        ContentValues vals = getRowVals(ConfigDb.DbData.IMAGES, id);
        if (!hasData) {
            testNullContentValues(vals, ConfigDb.DbData.IMAGES);
            return;
        }

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

    private void testFactsRow(MdFact fact, int index, boolean hasData) {
        String id = FactoryReviewerDbTableRow.newRow(fact, index).getRowId();
        ContentValues vals = getRowVals(ConfigDb.DbData.FACTS, id);
        if (!hasData) {
            testNullContentValues(vals, ConfigDb.DbData.FACTS);
            return;
        }

        assertEquals(id, vals.get(RowFact.FACT_ID));
        assertEquals(fact.getReviewId().toString(), vals.get(RowFact.REVIEW_ID));
        assertEquals(fact.getLabel(), vals.get(RowFact.LABEL));
        assertEquals(fact.getValue(), vals.get(RowFact.VALUE));
        assertEquals(fact.isUrl(), vals.get(RowFact.IS_URL));
    }

    private void testCommentsRow(MdComment comment, int index, boolean hasData) {
        String id = FactoryReviewerDbTableRow.newRow(comment, index).getRowId();
        ContentValues vals = getRowVals(ConfigDb.DbData.COMMENTS, id);
        if (!hasData) {
            testNullContentValues(vals, ConfigDb.DbData.COMMENTS);
            return;
        }

        assertEquals(id, vals.get(RowComment.COMMENT_ID));
        assertEquals(comment.getReviewId().toString(), vals.get(RowComment.REVIEW_ID));
        assertEquals(comment.getComment(), vals.get(RowComment.COMMENT));
        assertEquals(comment.isHeadline(), vals.get(RowComment.IS_HEADLINE));
    }

    private void testReviewsRow(Review review, String parentId, boolean hasData) {
        String id = FactoryReviewerDbTableRow.newRow(review).getRowId();
        ContentValues vals = getRowVals(ConfigDb.DbData.REVIEWS, id);
        if (!hasData) {
            testNullContentValues(vals, ConfigDb.DbData.REVIEWS);
            return;
        }

        assertEquals(id, vals.get(RowReview.REVIEW_ID));
        assertEquals(review.getSubject().getSubject(), vals.get(RowReview.SUBJECT));
        assertEquals(review.getRating().getRating(), vals.get(RowReview.RATING));
        assertEquals(review.getAuthor().getUserId().toString(), vals.get(RowReview.USER_ID));
        assertEquals(review.getPublishDate().getTime(), vals.get(RowReview.PUBLISH_DATE));
        assertEquals(review.isRatingAverageOfCriteria(),
                vals.getAsBoolean(RowReview.IS_AVERAGE).booleanValue());
        assertEquals(parentId, vals.get(RowReview.PARENT_ID));
    }

    private void testAuthorsRow(DatumAuthor author, boolean hasData) {
        String id = FactoryReviewerDbTableRow.newRow(author).getRowId();
        ContentValues vals = getRowVals(ConfigDb.DbData.AUTHORS, id);
        if (!hasData) {
            testNullContentValues(vals, ConfigDb.DbData.AUTHORS);
            return;
        }

        assertEquals(id, vals.get(RowAuthor.USER_ID));
        assertEquals(author.getName(), vals.get(RowAuthor.AUTHOR_NAME));
    }

    private ContentValues getRowVals(ConfigDb.DbData dataType, String id) {
        ConfigDb.Config config = ConfigDb.getConfig(dataType);
        String pkColumn = config.getPkColumn();
        DbTable table = config.getTable();
        DbColumnDefinition idCol = table.getColumn(pkColumn);
        SQLiteOpenHelper helper = mDatabase.getHelper();

        String columnName = idCol.getName();
        String tableName = table.getName();
        String value = id != null ? " = ?" : " IS NULL";
        String whereClause = columnName != null ? " WHERE " + columnName + value : "";

        String query = "SELECT * FROM " + tableName + whereClause;
        String[] args = id != null ? new String[]{id} : null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        DbTableRow row;
        if (cursor.getCount() == 0) {
            row = FactoryDbTableRow.emptyRow(table.getRowClass());
        } else {
            cursor.moveToFirst();
            row = FactoryReviewerDbTableRow.newRow(cursor, table.getRowClass());
            assertTrue(row.hasData());
        }

        cursor.close();
        db.close();

        return row.getContentValues();
    }

    private long getNumberRows(DbTable table) {
        SQLiteOpenHelper helper = mDatabase.getHelper();
        return DatabaseUtils.queryNumEntries(helper.getReadableDatabase(), table.getName());
    }

    private void deleteDatabaseIfNecessary() {
        Context context = getContext();
        File db = context.getDatabasePath(mDatabase.getDatabaseName());
        if (db.exists()) context.deleteDatabase(mDatabase.getDatabaseName());
    }

    private void addReviewsToDatabase() {
        for (Review review : mReviews) {
            mDatabase.addReview(review);
        }
    }

    private void deleteReviewFromDb(Review review) {
        mDatabase.removeReview(review.getMdReviewId().toString());
    }
}
