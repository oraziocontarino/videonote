package com.videonote.database.dto;

public class NoteDTO {
    private long id;
    private long recordId;
    private String type;
    private long startTime;
    private String fileName;

    public NoteDTO(){

    }
    public NoteDTO(long id, String fileName, long startTime, long recordId, String type){
        this.id = id;
        this.fileName = fileName;
        this.startTime = startTime;
        this.recordId = recordId;
        this.type = type;
    }

    public NoteDTO(String fileName, long startTime, long recordId, String type){
        this.fileName = fileName;
        this.startTime = startTime;
        this.recordId = recordId;
        this.type = type;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}