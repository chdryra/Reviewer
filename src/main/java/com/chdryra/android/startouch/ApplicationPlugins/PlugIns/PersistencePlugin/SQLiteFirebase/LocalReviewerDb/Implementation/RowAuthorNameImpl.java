/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowValues;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorNameImpl extends RowTableBasic<RowAuthorName> implements RowAuthorName {
    private String mUserId;
    private String mName;

    public RowAuthorNameImpl(AuthorName author) {
        mUserId = author.getAuthorId().toString();
        mName = author.getName();
    }

    //Via reflection
    public RowAuthorNameImpl() {
    }

    public RowAuthorNameImpl(RowValues values) {
        mUserId = values.getValue(AUTHOR_ID.getName(), AUTHOR_ID.getType());
        mName = values.getValue(AUTHOR_NAME.getName(), AUTHOR_NAME.getType());
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public AuthorId getAuthorId() {
        return new AuthorIdParcelable(mUserId);
    }

    @Override
    public String getRowId() {
        return mUserId;
    }

    @Override
    public String getRowIdColumnName() {
        return AUTHOR_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    protected int size() {
        return 2;
    }

    @Override
    protected RowEntry<RowAuthorName, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowAuthorName.class, AUTHOR_ID, mUserId);
        } else if(position == 1){
            return new RowEntryImpl<>(RowAuthorName.class, AUTHOR_NAME, mName);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowAuthorNameImpl)) return false;

        RowAuthorNameImpl that = (RowAuthorNameImpl) o;

        if (mUserId != null ? !mUserId.equals(that.mUserId) : that.mUserId != null) return false;
        return !(mName != null ? !mName.equals(that.mName) : that.mName != null);

    }

    @Override
    public int hashCode() {
        int result = mUserId != null ? mUserId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
