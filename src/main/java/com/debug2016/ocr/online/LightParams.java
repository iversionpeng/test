package com.debug2016.ocr.online;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LightParams {
    /**
     * 灰暗判定阈值,平均亮度小于该值被判定为灰暗
     */
    public int DARKNESS_THRESHOLD = 70;
    /**
     * 平均值的倍数默认值，avg*该值 以上算反光
     */
    public double DEFAULT_LIGHT_THRESHOLD = 1.5;

    public double ONE_LIGHT_THRESHOLD = 3;
    public double TWO_LIGHT_THRESHOLD = 3;
    public double THREE_LIGHT_THRESHOLD = 2.8;
    public double FOUR_LIGHT_THRESHOLD = 2.42;
    public double FIVE_LIGHT_THRESHOLD = 2.3;
    public double SIX_LIGHT_THRESHOLD = 2.2;
    public double SEVEN_LIGHT_THRESHOLD = 1.91;
    public double EIGHT_LIGHT_THRESHOLD = 1.91;
    public double NINE_LIGHT_THRESHOLD = 1.60;
    public double TEN_LIGHT_THRESHOLD = 1.58;
    public double ELEVEN_LIGHT_THRESHOLD = 1.55;
    public double TWELVE_LIGHT_THRESHOLD = 1.4;
    public double THIRTEEN_LIGHT_THRESHOLD = 1.3;


    public int GAUSSIAN_BLUR_WIDTH = 9;
    public int GAUSSIAN_BLUR_HEIGHT = 9;
    public double GAUSSIAN_BLUR_SIGMA_X = 0;
    public double GAUSSIAN_BLUR_SIGMA_Y = 0;
    public double THRESHOLD_MAX_VAL = 255;

    public int MAT_EXPR_ONES_ROWS = 5;
    public int MAT_EXPR_ONES_COLS = 5;

    public int ERODE_POINT_X = -1;
    public int ERODE_POINT_Y = -1;

    public int ERODE_POINT_ITERATIONS = 2;
    public int DILATE_POINT_X = -1;
    public int DILATE_POINT_Y = -1;
    public int DILATE_POINT_ITERATIONS = 4;

}

