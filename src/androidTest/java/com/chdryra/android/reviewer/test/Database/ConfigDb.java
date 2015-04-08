/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.ReviewerDbRow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConfigDb {
    private static ConfigDb            sConfig;
    private        Map<DbData, Config> mConfigsMap;

    public enum DbData {REVIEWS, REVIEW_TREES, COMMENTS, FACTS, LOCATIONS, IMAGES, AUTHORS, TAGS}

    private ConfigDb() {
        mConfigsMap = new HashMap<>();
        mConfigsMap.put(DbData.REVIEWS,
                new Config(ReviewerDbContract.REVIEWS_TABLE,
                        ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID,
                        ReviewerDbRow.ReviewsRow.class));
        mConfigsMap.put(DbData.REVIEW_TREES,
                new Config(ReviewerDbContract.TREES_TABLE,
                        ReviewerDbContract.TableReviewTrees.COLUMN_NAME_REVIEW_NODE_ID,
                        ReviewerDbRow.ReviewTreesRow.class));
        mConfigsMap.put(DbData.COMMENTS,
                new Config(ReviewerDbContract.COMMENTS_TABLE,
                        ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT_ID,
                        ReviewerDbRow.CommentsRow.class));
        mConfigsMap.put(DbData.FACTS,
                new Config(ReviewerDbContract.FACTS_TABLE,
                        ReviewerDbContract.TableFacts.COLUMN_NAME_FACT_ID,
                        ReviewerDbRow.FactsRow.class));
        mConfigsMap.put(DbData.LOCATIONS,
                new Config(ReviewerDbContract.LOCATIONS_TABLE,
                        ReviewerDbContract.TableLocations.COLUMN_NAME_LOCATION_ID,
                        ReviewerDbRow.LocationsRow.class));
        mConfigsMap.put(DbData.IMAGES,
                new Config(ReviewerDbContract.IMAGES_TABLE,
                        ReviewerDbContract.TableImages.COLUMN_NAME_IMAGE_ID,
                        ReviewerDbRow.ImagesRow.class));
        mConfigsMap.put(DbData.AUTHORS,
                new Config(ReviewerDbContract.AUTHORS_TABLE,
                        ReviewerDbContract.TableAuthors.COLUMN_NAME_USER_ID,
                        ReviewerDbRow.AuthorsRow.class));
        mConfigsMap.put(DbData.TAGS,
                new Config(ReviewerDbContract.TAGS_TABLE,
                        ReviewerDbContract.TableTags.COLUMN_NAME_TAG,
                        ReviewerDbRow.TagsRow.class));
    }

    private static ConfigDb getConfig() {
        if (sConfig == null) sConfig = new ConfigDb();
        return sConfig;
    }

    public static Config getConfig(DbData dataType) {
        return getConfig().mConfigsMap.get(dataType);
    }

    public class Config {
        private ReviewerDbContract.ReviewerDbTable      mTable;
        private String                                  mPkColumn;
        private Class<? extends ReviewerDbRow.TableRow> mRowClass;

        private Config(ReviewerDbContract.ReviewerDbTable table, String pkColumn,
                Class<? extends ReviewerDbRow.TableRow> rowClass) {
            mTable = table;
            mPkColumn = pkColumn;
            mRowClass = rowClass;
        }

        public ReviewerDbContract.ReviewerDbTable getTable() {
            return mTable;
        }

        public String getPkColumn() {
            return mPkColumn;
        }

        public Class<? extends ReviewerDbRow.TableRow> getRowClass() {
            return mRowClass;
        }
    }
}
