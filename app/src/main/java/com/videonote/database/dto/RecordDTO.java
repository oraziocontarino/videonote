package com.videonote.database.dto;

public class RecordDTO {
    private long id;
    private String fileName;
    private String type;

    public RecordDTO(){

    }
    public RecordDTO(String fileName, String type){
        this.fileName = fileName;
        this.type = type;
    }

    public RecordDTO(long id, String fileName, String type){
        this.id = id;
        this.fileName = fileName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}