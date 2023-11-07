package com.debug2016.ocr.online;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class IdCheckParams {
    /**
     * 倾斜角度阈值
     */
    public Double ANGLE_WARNING_THRESHOLD = 20.0;
    /**
     * 边缘阈值
     */
    public Double WIDTH_EDGE_THRESHOLD = 0.2;
    /**
     * 边缘阈值
     */
    public Double HEIGHT_EDGE_THRESHOLD = 0.2;

    public Double PROCESSMAT_CANNY_THRESHOLD1 = 5.0;
    public Double PROCESSMAT_CANNY_THRESHOLD2 = 10.0;
    public Integer DILATE_SIZE_WIDTH = 2;
    public Integer DILATE_SIZE_HEIGHT = 2;
    public Double HOUGHLINES_RHO = 1.0;
    public Integer HOUGHLINES_THRESHOLD = 200;
    public Double HOUGHLINES_MIN_LINE_LENGTH = 200.0;
    public Double HOUGHLINES_MAX_LINE_GAP = 100.0;

}

