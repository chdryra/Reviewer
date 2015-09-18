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
import com.chdryra.android.reviewer.Database.ReviewerDbTable;
import com.chdryra.android.reviewer.Database.RowAuthor;
import com.chdryra.android.reviewer.Database.RowComment;
import com.chdryra.android.reviewer.Database.RowFact;
import com.chdryra.android.reviewer.Database.RowImage;
import com.chdryra.android.reviewer.Database.RowLocation;
import com.chdryra.android.reviewer.Database.RowReview;
import com.chdryra.android.reviewer.Database.RowTag;

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

    private ConfigDb() {
        mConfigsMap = new HashMap<>();
        mConfigsMap.put(DbData.REVIEWS,
                new Config(ReviewerDbContract.REVIEWS_TABLE,
                        ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID,
                        RowReview.class));
        mConfigsMap.put(DbData.COMMENTS,
                new Config(ReviewerDbContract.COMMENTS_TABLE,
                        ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT_ID,
                        RowComment.class));
        mConfigsMap.put(DbData.FACTS,
                new Config(ReviewerDbContract.FACTS_TABLE,
                        ReviewerDbContract.TableFacts.COLUMN_NAME_FACT_ID,
                        RowFact.class));
        mConfigsMap.put(DbData.LOCATIONS,
                new Config(ReviewerDbContract.LOCATIONS_TABLE,
                        ReviewerDbContract.TableLocations.COLUMN_NAME_LOCATION_ID,
                        RowLocation.class));
        mConfigsMap.put(DbData.IMAGES,
                new Config(ReviewerDbContract.IMAGES_TABLE,
                        ReviewerDbContract.TableImages.COLUMN_NAME_IMAGE_ID,
                        RowImage.class));
        mConfigsMap.put(DbData.AUTHORS,
                new Config(ReviewerDbContract.AUTHORS_TABLE,
                        ReviewerDbContract.TableAuthors.COLUMN_NAME_USER_ID,
                        RowAuthor.class));
        mConfigsMap.put(DbData.TAGS,
                new Config(ReviewerDbContract.TAGS_TABLE,
                        ReviewerDbContract.TableTags.COLUMN_NAME_TAG,
                        RowTag.class));
    }

    private static ConfigDb getConfig() {
        if (sConfig == null) sConfig = new ConfigDb();
        return sConfig;
    }

    public static Config getConfig(DbData dataType) {
        return getConfig().mConfigsMap.get(dataType);
    }

    public enum DbData {REVIEWS, REVIEW_TREES, COMMENTS, FACTS, LOCATIONS, IMAGES, AUTHORS, TAGS}

    public class Config {
        private ReviewerDbTable mTable;
        private String                                  mPkColumn;
        private Class<? extends ReviewerDbRow.TableRow> mRowClass;

        private Config(ReviewerDbTable table, String pkColumn,
                Class<? extends ReviewerDbRow.TableRow> rowClass) {
            mTable = table;
            mPkColumn = pkColumn;
            mRowClass = rowClass;
        }

        public ReviewerDbTable getTable() {
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
