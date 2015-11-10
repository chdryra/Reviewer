package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataConverter;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.Models.UserModel.UserId;

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
    public ArrayList<Author> convert(Iterable<? extends DataAuthor> data, String reviewId) {
        ArrayList<Author> list = new ArrayList<>();
        for(DataAuthor datum : data) {
            list.add(convert(datum));
        }

        return list;
    }
}
