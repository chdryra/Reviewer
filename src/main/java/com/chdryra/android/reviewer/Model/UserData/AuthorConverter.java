package com.chdryra.android.reviewer.Model.UserData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverter;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorConverter implements DataConverter<DataAuthor, Author> {
    @Override
    public Author convert(DataAuthor datum) {
        UserId id = UserId.fromString(datum.getUserId());
        return new Author(datum.getName(), id);
    }

    @Override
    public ArrayList<Author> convert(Iterable<DataAuthor> data, String reviewId) {
        ArrayList<Author> list = new ArrayList<>();
        for(DataAuthor datum : data) {
            list.add(convert(datum));
        }

        return list;
    }
}
