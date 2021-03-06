/*
 * ******************************************************************************
 *  * Copyright 2016 Anteros Tecnologia
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package br.com.anteros.social.facebook.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import br.com.anteros.social.facebook.entities.FacebookIdName;
import br.com.anteros.social.facebook.entities.FacebookUser;

/**
 * Created by edson on 23/03/16.
 */
public class FacebookUtils {

    public static final String EMPTY = "";
    public static final String CHARSET_NAME = "UTF-8";

    public String getFacebookSDKVersion() {
        String sdkVersion = null;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Class<?> cls = classLoader.loadClass("com.facebook.FacebookSdkVersion");
            Field field = cls.getField("BUILD");
            sdkVersion = String.valueOf(field.get(null));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sdkVersion;
    }


    public static void printHashKey(Context context) {
        try {
            String TAG = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(TAG, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d(TAG, "keyHash: " + keyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static String getHashKey(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }


    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? EMPTY : first.toString();
        }
        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static <T> String join(Iterator<T> iterator, String separator, Process<T> process) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        T first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? EMPTY : process.process(first);
        }
        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(process.process(first));
        }
        while (iterator.hasNext()) {
            buf.append(separator);
            T obj = iterator.next();
            if (obj != null) {
                buf.append(process.process(obj));
            }
        }
        return buf.toString();
    }

    public static String join(Map<?, ?> map, char separator, char valueStartChar, char valueEndChar) {

        if (map == null) {
            return null;
        }
        if (map.size() == 0) {
            return EMPTY;
        }
        StringBuilder buf = new StringBuilder(256);
        boolean isFirst = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (isFirst) {
                buf.append(entry.getKey());
                buf.append(valueStartChar);
                buf.append(entry.getValue());
                buf.append(valueEndChar);
                isFirst = false;
            } else {
                buf.append(separator);
                buf.append(entry.getKey());
                buf.append(valueStartChar);
                buf.append(entry.getValue());
                buf.append(valueEndChar);
            }
        }

        return buf.toString();
    }

    public static List<String> extract(List<FacebookIdName> idNames) {
        List<String> names = new ArrayList<>();
        for (FacebookIdName idName : idNames) {
            names.add(idName.getName());
        }
        return names;
    }

    public static List<FacebookUser> extractUsers(List<FacebookIdName> idNames) {
        List<FacebookUser> users = new ArrayList<FacebookUser>();
        for (final FacebookIdName idName : idNames) {
            users.add(new FacebookUser(idName));
        }
        return users;
    }

    public static <T> List<T> typedListFromResponse(GraphResponse response /*, Class<T> cls */) {
        return JsonUtils.fromJson(response.getRawResponse(), new TypeToken<List<T>>() {}.getType());
    }

    public static <T> List<T> typedListFromResponse(String raw) {
        return JsonUtils.fromJson(raw, new TypeToken<List<T>>() {
        }.getType());
    }

    public static class DataResult<T> {
        public List<T> data;
    }

    public static class SingleDataResult<T> {
        public T data;

        @Override
        public String toString() {
            if (data != null) {
                return data.toString();
            }
            return super.toString();
        }
    }

    public static <T> T convert(GraphResponse response, Type type) {
        return JsonUtils.fromJson(response.getRawResponse(), type);
    }

    public static <T> T convert(String json, Type type) {
        return JsonUtils.fromJson(json, type);
    }

    public static FacebookUser convert(final FacebookIdName idName) {
        return new FacebookUser(idName.getId(), idName.getName());
    }

    public interface Process<T> {
        String process(T t);
    }

    public static String encodeUrl(Bundle parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            Object parameter = parameters.get(key);
            if (!(parameter instanceof String)) {
                continue;
            }

            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(key, CHARSET_NAME)).append("=").append(URLEncoder.encode(parameters.getString(key), CHARSET_NAME));
            } catch (UnsupportedEncodingException e) {
                Log.e(FacebookUtils.class.getName(), "Error enconding URL", e);
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("resource")
    public static String encode(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(data.getBytes());
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            Formatter formatter = new Formatter(sb);
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(FacebookUtils.class.getName(), "Failed to create sha256", e);
            return null;
        }
    }

    public static <T> List<T> createSingleItemList(T t) {
        List<T> list = new ArrayList<T>();
        list.add(t);
        return list;
    }

    public static String toHtml(Object object) {
        StringBuilder stringBuilder = new StringBuilder(256);
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Object val = field.get(object);
                stringBuilder.append("<b>");
                stringBuilder.append(field.getName().substring(1, field.getName().length()));
                stringBuilder.append(": ");
                stringBuilder.append("</b>");
                stringBuilder.append(val);
                stringBuilder.append("<br>");
            }
        } catch (Exception e) {

        }
        return stringBuilder.toString();
    }

}
