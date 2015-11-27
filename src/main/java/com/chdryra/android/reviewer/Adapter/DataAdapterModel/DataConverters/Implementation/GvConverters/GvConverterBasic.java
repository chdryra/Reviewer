package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces
        .DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewId;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterBasic<T1, T2 extends GvData, T3 extends GvDataListImpl<T2>>
        implements DataConverter<T1, T2, T3> {
    private static final String NO_CTOR_ERR = "Constructor not found: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private GvDataType<T2> mDataType;
    private Class<T3> mListClass;

    public GvConverterBasic(Class<T3> listClass) {
        mListClass = listClass;
        mDataType = newList(null).getGvDataType();
    }

    protected GvDataType<T2> getDataType() {
        return mDataType;
    }

    protected GvReviewId newId(String reviewId) {
        return new GvReviewId(reviewId);
    }

    @Override
    public abstract T2 convert(T1 datum, String reviewId);

    @Override
    public T2 convert(T1 datum) {
        return convert(datum, null);
    }

    @Override
    public T3 convert(Iterable<? extends T1> data, String reviewId) {
        T3 list = newList(reviewId);
        for(T1 datum : data) {
            list.add(convert(datum));
        }

        return list;
    }

    @Override
    public T3 convert(IdableList<? extends T1> data) {
        T3 list = newList(data.getReviewId());
        for(T1 datum : data) {
            list.add(convert(datum));
        }

        return list;
    }

    private T3 newList(String reviewId) {
        GvReviewId id = new GvReviewId(reviewId);
        try {
            Constructor<T3> ctor = mListClass.getConstructor(GvReviewId.class);
            return ctor.newInstance(id);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(NO_CTOR_ERR + mListClass.getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + mListClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + mListClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOCATION_ERR + mListClass.getName());
        }
    }
}
