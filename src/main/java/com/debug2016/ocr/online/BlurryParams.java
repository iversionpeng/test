package com.debug2016.ocr.online;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class BlurryParams {
    /**
     * 清晰阈值
     */
    public Double blurryCheckThreshold = 110.0;

}

