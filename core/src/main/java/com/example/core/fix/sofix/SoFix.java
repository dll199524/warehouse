package com.example.core.fix.sofix;

import android.content.Context;
import java.lang.reflect.Field;

/**
 * System.loadLibrary->loadLibrary0->
 * 方案是往 nativeLibraryPathElements 的最前面插入一个 NativeLibraryElement
 */

public abstract class SoFix {
    protected Object mPathList;
    protected Field mNativeLibraryElementsField;
    public SoFix(Context context) {
        ClassLoader classLoader = context.getClassLoader();
    }
    protected abstract void reflectNativeLibraryElements();
    public abstract Object createNativeLibraryElement(String soPath) throws Exception;

    public void hotFix(String soPath) throws Exception {
        reflectNativeLibraryElements();
        Object nativeLibraryPathElements = mNativeLibraryElementsField.get(mPathList);
        Object firstElement = createNativeLibraryElement(soPath);
        Object newLibraryPathElements = CommonUtils.insertElementAtFirst(firstElement, nativeLibraryPathElements);
        mNativeLibraryElementsField.set(mPathList, newLibraryPathElements);
    }

}
