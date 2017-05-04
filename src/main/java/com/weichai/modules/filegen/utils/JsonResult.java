package com.weichai.modules.filegen.utils;

import com.weichai.modules.filegen.entity.InOutDetail;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tepusoft on 2017/3/14.
 */
public class JsonResult implements Serializable {
    private boolean flag;
    private File file;
    private String content;
    private String encodes;
    private List<InOutDetail> inOutDetailList;
    private String path;
    private String convertPath;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEncodes() {
        return encodes;
    }

    public void setEncodes(String encodes) {
        this.encodes = encodes;
    }

    public List<InOutDetail> getInOutDetailList() {
        return inOutDetailList;
    }

    public void setInOutDetailList(List<InOutDetail> inOutDetailList) {
        this.inOutDetailList = inOutDetailList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConvertPath() {
        return convertPath;
    }

    public void setConvertPath(String convertPath) {
        this.convertPath = convertPath;
    }
}
