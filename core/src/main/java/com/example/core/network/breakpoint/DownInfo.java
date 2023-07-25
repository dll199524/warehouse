package com.example.core.network.breakpoint;

public class DownInfo {
    public static final long TOTAL_ERROR = -1;
    private String url;
    private long total;
    private long progress;
    private String fileName;

    public DownInfo(String url) {this.url = url;}

    public String getUrl() {
        return url;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
