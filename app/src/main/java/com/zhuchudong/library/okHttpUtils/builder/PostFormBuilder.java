package com.zhuchudong.library.okHttpUtils.builder;

import com.zhuchudong.library.okHttpUtils.request.PostFormRequest;
import com.zhuchudong.library.okHttpUtils.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PostFormBuilder extends BaseOkHttpRequestBuilder implements HasParamsable {

    private List<FileInput> files = new ArrayList<>();

    @Override
    public PostFormBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostFormBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public PostFormBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public PostFormBuilder addHeader(String key, String val) {
        if (headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public PostFormBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFormRequest(url, tag, params, headers, files).build();
    }


    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }


}
