package com.videonote.Database;

public class Common {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "videonote_db";
    public static enum RECORD_TYPE {
        AUDIO,
        VIDEO
    }
    public static enum NOTE_TYPE {
        TEXT,
        IMAGE
    }
}
