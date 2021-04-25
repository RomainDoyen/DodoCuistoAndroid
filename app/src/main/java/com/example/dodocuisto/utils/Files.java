package com.example.dodocuisto.utils;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

public class Files {
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        //return RealPathUtil.getRealPathFromURI_API19(context, contentURI);
        return RealPathUtil.getRealPath(context, contentURI);
    }
}
