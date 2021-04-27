package com.example.dodocuisto.utils;

import android.content.Context;
import android.net.Uri;

public class Files {
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        return RealPathUtil.getRealPath(context, contentURI);
    }
}
