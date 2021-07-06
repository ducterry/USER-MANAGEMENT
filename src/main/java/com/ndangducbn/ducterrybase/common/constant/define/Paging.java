package com.ndangducbn.ducterrybase.common.constant.define;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Paging {
    private int page;
    private int size;
    private Long total;
}
