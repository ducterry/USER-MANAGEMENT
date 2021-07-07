package com.ndangducbn.ducterrybase.enums;

public enum ETypeCellFormat {
    TYPE_DATE("Format Cell is Date"),
    TYPE_NUMBER("Format Cell is Number"),
    TYPE_TEXT("Format Cell is Text")
    ;
    private String type;

    ETypeCellFormat(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
