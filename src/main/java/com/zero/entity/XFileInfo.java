package com.zero.entity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class XFileInfo {
    private String _ID;
    private String _Creator;
    private Date _CreateTime;
    private String _LastSavedBy;
    private Date _LastSaveTime;
    private String ownerID;
    private String catalog;//文件分类、文件所在文件夹
    private String category;
    private String tag;
    private String relativePath;
    private String fileName;
    private String fileType;
    private long fileSize;
    private int ordinal;
    private MultipartFile file;
    private boolean canNotRead;
    private boolean verified;
    private String source;

    public XFileInfo(String catalog) {
        this.catalog = catalog;
    }
}
