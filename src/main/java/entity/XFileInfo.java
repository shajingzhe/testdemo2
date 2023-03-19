package entity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


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

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String get_Creator() {
        return _Creator;
    }

    public void set_Creator(String _Creator) {
        this._Creator = _Creator;
    }

    public Date get_CreateTime() {
        return _CreateTime;
    }

    public void set_CreateTime(Date _CreateTime) {
        this._CreateTime = _CreateTime;
    }

    public String get_LastSavedBy() {
        return _LastSavedBy;
    }

    public void set_LastSavedBy(String _LastSavedBy) {
        this._LastSavedBy = _LastSavedBy;
    }

    public Date get_LastSaveTime() {
        return _LastSaveTime;
    }

    public void set_LastSaveTime(Date _LastSaveTime) {
        this._LastSaveTime = _LastSaveTime;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public boolean isCanNotRead() {
        return canNotRead;
    }

    public void setCanNotRead(boolean canNotRead) {
        this.canNotRead = canNotRead;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
