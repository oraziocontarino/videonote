package com.videonote.database.dto;

public class RecordDTO {
    private long id;
    private String fileName;
    private String type;
    private double latitude;
    private double longitude;
    private String locality;

    public RecordDTO(){

    }

    public RecordDTO(long id, String fileName, String type, double latitude, double longitude, String locality){
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locality = locality;
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
    public String getFileName(boolean last){
        if(last){
            String[] pieces = getFileName().split("/");
            return pieces[pieces.length-1];
        }
        return getFileName();
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}