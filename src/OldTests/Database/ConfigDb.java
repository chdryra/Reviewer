/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowTag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConfigDb {
    private static ConfigDb sConfig;
    private Map<DbData, Config> mConfigsMap;

    public enum DbData {REVIEWS, COMMENTS, FACTS, LOCATIONS, IMAGES, AUTHORS, TAGS}

    private ConfigDb() {
        mConfigsMap = new HashMap<>();
        mConfigsMap.put(DbData.REVIEWS,
                new Config(ReviewerDbContractImpl.REVIEWS_TABLE,
                        TableReviews.COLUMN_NAME_REVIEW_ID,
                        RowReview.class));
        mConfigsMap.put(DbData.COMMENTS,
                new Config(ReviewerDbContractImpl.COMMENTS_TABLE,
                        TableComments.COLUMN_NAME_COMMENT_ID,
                        RowComment.class));
        mConfigsMap.put(DbData.FACTS,
                new Config(ReviewerDbContractImpl.FACTS_TABLE,
                        TableFacts.COLUMN_NAME_FACT_ID,
                        RowFact.class));
        mConfigsMap.put(DbData.LOCATIONS,
                new Config(ReviewerDbContractImpl.LOCATIONS_TABLE,
                        TableLocations.COLUMN_NAME_LOCATION_ID,
                        RowLocation.class));
        mConfigsMap.put(DbData.IMAGES,
                new Config(ReviewerDbContractImpl.IMAGES_TABLE,
                        TableImages.COLUMN_NAME_IMAGE_ID,
                        RowImage.class));
        mConfigsMap.put(DbData.AUTHORS,
                new Config(ReviewerDbContractImpl.AUTHORS_TABLE,
                        TableAuthors.COLUMN_NAME_USER_ID,
                        RowAuthor.class));
        mConfigsMap.put(DbData.TAGS,
                new Config(ReviewerDbContractImpl.TAGS_TABLE,
                        TableTags.COLUMN_NAME_TAG,
                        RowTag.class));
    }

    //Static methods
    public static Config getConfig(DbData dataType) {
        return getConfig().mConfigsMap.get(dataType);
    }

    //private methods
    private static ConfigDb getConfig() {
        if (sConfig == null) sConfig = new ConfigDb();
        return sConfig;
    }

    public class Config {
        private DbTable mTable;
        private String mPkColumn;
        private Class<? extends DbTableRow> mRowClass;

        private Config(DbTable table, String pkColumn,
                       Class<? extends DbTableRow> rowClass) {
            mTable = table;
            mPkColumn = pkColumn;
            mRowClass = rowClass;
        }

        //public methods
        public DbTable getTable() {
            return mTable;
        }

        public String getPkColumn() {
            return mPkColumn;
        }

        public Class<? extends DbTableRow> getRowClass() {
            return mRowClass;
        }
    }
}
