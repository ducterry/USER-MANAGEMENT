package com.ndangducbn.ducterrybase.entity;

import lombok.Data;

import javax.persistence.Column;
import java.time.ZonedDateTime;

@Data
public class BaseTable {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;
}
