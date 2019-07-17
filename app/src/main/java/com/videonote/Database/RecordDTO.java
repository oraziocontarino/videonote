package com.videonote.Database;

public class RecordDTO {
    private int id;
    private String fileName;
    private String type;

    public RecordDTO(){

    }
    public RecordDTO(String fileName, String type){
        this.fileName = fileName;
        this.type = type;
    }

    public RecordDTO(int id, String fileName, String type){
        this.id = id;
        this.fileName = fileName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}