/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 May, 2015
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationSingleton {
    private static final String NO_CTOR_ERR        = "Constructor not found: ";
    private static final String INSTANTIATION_ERR  = "Constructor not found: ";
    private static final String INVOCATION_ERR     = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private Context mContext;
    private String  mName;

    protected ApplicationSingleton(Context context, String name) {
        mContext = context;
        mName = name;
    }

    public static <T extends ApplicationSingleton> T getSingleton(T staticSingleton,
                                                                  Class<T> singletonClass,
                                                                  Context context) {
        if (staticSingleton == null) {
            staticSingleton = construct(singletonClass, context);
        } else {
            staticSingleton.checkContextOrThrow(context);
        }

        return staticSingleton;

    }

    private static <T extends ApplicationSingleton> T construct(Class<T> singletonClass,
                                                                Context context) {
        try {
            Constructor<T> ctor = singletonClass.getDeclaredConstructor(Context.class);
            ctor.setAccessible(true);
            return ctor.newInstance(context);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(NO_CTOR_ERR + singletonClass.getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + singletonClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + singletonClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOCATION_ERR + singletonClass.getName());
        }
    }

    protected void checkContextOrThrow(Context context) {
        if (context.getApplicationContext() != mContext.getApplicationContext()) {
            throw new RuntimeException("Can only have 1 " + mName + " per application!");
        }
    }

    protected Context getContext() {
        return mContext;
    }
}
