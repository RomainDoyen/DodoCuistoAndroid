package com.example.dodocuisto.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

public class Files {
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        return RealPathUtil.getRealPathFromURI_API19(context, contentURI);
    }
}
