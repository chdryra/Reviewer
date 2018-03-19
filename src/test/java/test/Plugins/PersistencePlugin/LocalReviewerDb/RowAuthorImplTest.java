/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowAuthorNameImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorImplTest extends RowTableBasicTest<RowAuthorName, RowAuthorNameImpl> {

    public RowAuthorImplTest() {
        super(RowAuthorName.AUTHOR_ID.getName(), 2);
    }

    @Test
    public void constructionWithDataAuthorAndGetters() {
        AuthorId id = AuthorIdGenerator.newId();
        String name = RandomString.nextWord();
        AuthorName author = new AuthorNameDefault(name, id);

        RowAuthorNameImpl row = new RowAuthorNameImpl(author);
        assertThat(row.getAuthorId().toString(), is(id.toString()));
        assertThat(row.getName(), is(name));
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowAuthorName reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowAuthorName.AUTHOR_ID, reference.getAuthorId().toString());
        values.put(RowAuthorName.AUTHOR_NAME, reference.getName());

        RowAuthorNameImpl row = new RowAuthorNameImpl(values);
        assertThat(row.hasData(new DataValidator()), is(true));

        assertThat(row.getAuthorId().toString(), is(reference.getAuthorId().toString()));
        assertThat(row.getName(), is(reference.getName()));
    }

    @Test
    public void constructionWithDataAuthorWithInvalidUserIdMakesRowAuthorInvalid() {
        AuthorName author = new AuthorNameDefault(RandomString.nextWord(), new AuthorIdParcelable
                (""));
        RowAuthorNameImpl row = new RowAuthorNameImpl(author);
        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithDataAuthorWithInvalidNameMakesRowAuthorInvalid() {
        AuthorName author = new AuthorNameDefault("", AuthorIdGenerator.newId());
        RowAuthorNameImpl row = new RowAuthorNameImpl(author);
        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithValidDataAuthorMakesRowAuthorValid() {
        AuthorName author = new AuthorNameDefault(RandomString.nextWord(), AuthorIdGenerator
                .newId());
        RowAuthorNameImpl row = new RowAuthorNameImpl(author);
        assertThat(row.hasData(new DataValidator()), is(true));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowAuthorNameImpl row = newRow();

        ArrayList<RowEntry<RowAuthorName, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(2));

        checkEntry(entries.get(0), RowAuthorName.AUTHOR_ID, row.getAuthorId().toString());
        checkEntry(entries.get(1), RowAuthorName.AUTHOR_NAME, row.getName());
    }

    @NonNull
    @Override
    protected RowAuthorNameImpl newRow() {
        AuthorId id = AuthorIdGenerator.newId();
        String name = RandomString.nextWord();
        AuthorName author = new AuthorNameDefault(name, id);

        return new RowAuthorNameImpl(author);
    }

    @Override
    protected String getRowId(RowAuthorNameImpl row) {
        return row.getAuthorId().toString();
    }
}
