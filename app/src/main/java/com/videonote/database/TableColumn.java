package com.videonote.database;

public class TableColumn {
    private String name;
    private String attrs;

    public TableColumn(String name, String attrs){
        this.name = name;
        this.attrs = attrs;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }
}
