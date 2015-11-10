package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataConverter;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdData;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDataList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterBasic<T1, T2 extends MdData, T3 extends MdDataList<T2>>
        implements DataConverter<T1, T2>{
    private static final String NO_CTOR_ERR = "Constructor not found: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private Class<T3> mListClass;

    protected MdConverterBasic(Class<T3> listClass) {
        mListClass = listClass;
    }

    @Override
    public abstract T2 convert(T1 datum);

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
        MdReviewId id = new MdReviewId(reviewId);
        try {
            Constructor<T3> ctor = mListClass.getConstructor(MdReviewId.class);
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
