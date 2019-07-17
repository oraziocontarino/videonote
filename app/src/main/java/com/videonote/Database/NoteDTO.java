package com.videonote.Database;

public class NoteDTO {
    private int id;
    private int recordId;
    private String type;
    private long startTime;
    private String fileName;

    public NoteDTO(){

    }
    public NoteDTO(int id, String fileName, long startTime, int recordId, String type){
        this.id = id;
        this.fileName = fileName;
        this.startTime = startTime;
        this.recordId = recordId;
        this.type = type;
    }

    public NoteDTO(String fileName, long startTime, int recordId, String type){
        this.fileName = fileName;
        this.startTime = startTime;
        this.recordId = recordId;
        this.type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}