package com.ndangducbn.ducterrybase.enums;

public enum ETypeDateFormat {
    DATE_FORMAT_FULL("DATE_FORMAT_FULL"),
    DATE_FORMAT_SHORT("DATE_FORMAT_SHORT");
    private String type;

    ETypeDateFormat(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
