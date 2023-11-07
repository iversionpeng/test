package com.debug2016.ocr.online;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 * 线上参数
 */
public class CommonIqcConsts {

    public static String getParams() {
        Class<?> constantClass = CommonIqcConsts.class;

        Field[] fields = constantClass.getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
                try {
                    Object value = field.get(null);
                    String fieldName = field.getName();
                    jsonObject.put(fieldName, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        String jsonStr = jsonObject.toJSONString();
        return jsonStr;
    }

    /**
     * 灰暗判定阈值,平均亮度小于该值被判定为灰暗
     */
    public static int DARKNESS_THRESHOLD = 70;

    /**
     * 最大一块反光面积占总面积的比例，大于该值被认为反光
     */
    public static double SPOTLIGHT_AREA_THRESHOLD = 0.006;

    /**
     * 反光面积应该小于总面积的MAX_SPOTLIGHT_AREA_THRESHOLD，否则应当认为是白底
     */
    public static double MAX_SPOTLIGHT_AREA_THRESHOLD = 0.3;

    /**
     * 平均值的倍数默认值，avg*该值 以上算反光
     */
    public static double DEFAULT_LIGHT_THRESHOLD = 1.5;

    public static double ONE_LIGHT_THRESHOLD = 3;
    public static double TWO_LIGHT_THRESHOLD = 3;
    public static double THREE_LIGHT_THRESHOLD = 2.8;
    public static double FOUR_LIGHT_THRESHOLD = 2.42;
    public static double FIVE_LIGHT_THRESHOLD = 2.3;
    public static double SIX_LIGHT_THRESHOLD = 2.2;
    public static double SEVEN_LIGHT_THRESHOLD = 1.91;
    public static double EIGHT_LIGHT_THRESHOLD = 1.91;

    public static double NINE_LIGHT_THRESHOLD = 1.93;
    public static double TEN_LIGHT_THRESHOLD = 1.58;
    public static double ELEVEN_LIGHT_THRESHOLD = 1.55;
    public static double TWELVE_LIGHT_THRESHOLD = 1.55;
    public static double THIRTEEN_LIGHT_THRESHOLD = 1.45;
    public static double FOURTEEN_LIGHT_THRESHOLD;
    public static double FIFTEEN_LIGHT_THRESHOLD;
    public static int GAUSSIAN_BLUR_WIDTH = 9;
    public static int GAUSSIAN_BLUR_HEIGHT = 9;
    public static double GAUSSIAN_BLUR_SIGMA_X = 0;
    public static double GAUSSIAN_BLUR_SIGMA_Y = 0;
    public static double THRESHOLD_MAX_VAL = 255;

    public static int MAT_EXPR_ONES_ROWS = 5;
    public static int MAT_EXPR_ONES_COLS = 5;
    public static int ERODE_POINT_X = -1;
    public static int ERODE_POINT_Y = -1;
    public static int ERODE_POINT_ITERATIONS = 2;
    public static int DILATE_POINT_X = -1;
    public static int DILATE_POINT_Y = -1;
    public static int DILATE_POINT_ITERATIONS = 4;

    public static Double blurryCheckThreshold = 110.0;

//======== id check params
    /**
     * 倾斜角度阈值
     */
    public static double ANGLE_WARNING_THRESHOLD = 20;
    /**
     * 边缘阈值
     */
    public static double WIDTH_EDGE_THRESHOLD = 0.2;
    /**
     * 边缘阈值
     */
    public static double HEIGHT_EDGE_THRESHOLD = 0.2;
    public static double MAX_WIDTH_THRESHOLD = 0.5;
    public static double MAX_HEIGHT_THRESHOLD = 0.5;

    public static int LENGTH_THRESHOLD_VALUE = 5;
    public static int GAP_THRESHOLD_VALUE = 10;

    public static double PROCESSMAT_CANNY_THRESHOLD1 = 5;
    public static double PROCESSMAT_CANNY_THRESHOLD2 = 10;
    public static int DILATE_SIZE_WIDTH = 2;
    public static int DILATE_SIZE_HEIGHT = 2;
    public static double HOUGHLINES_RHO = 1;
    public static int HOUGHLINES_THRESHOLD = 200;
    public static double HOUGHLINES_MIN_LINE_LENGTH = 200;
    public static double HOUGHLINES_MAX_LINE_GAP = 100;

    //========

    public static void modifyIdCheckParam(IdCheckParams idCheckParams) {
        ANGLE_WARNING_THRESHOLD = Optional.ofNullable(idCheckParams.getANGLE_WARNING_THRESHOLD()).orElse(ANGLE_WARNING_THRESHOLD);
        WIDTH_EDGE_THRESHOLD = Optional.ofNullable(idCheckParams.getWIDTH_EDGE_THRESHOLD()).orElse(WIDTH_EDGE_THRESHOLD);
        HEIGHT_EDGE_THRESHOLD = Optional.ofNullable(idCheckParams.getHEIGHT_EDGE_THRESHOLD()).orElse(HEIGHT_EDGE_THRESHOLD);
        PROCESSMAT_CANNY_THRESHOLD1 = Optional.ofNullable(idCheckParams.getPROCESSMAT_CANNY_THRESHOLD1()).orElse(PROCESSMAT_CANNY_THRESHOLD1);
        PROCESSMAT_CANNY_THRESHOLD2 = Optional.ofNullable(idCheckParams.getPROCESSMAT_CANNY_THRESHOLD2()).orElse(PROCESSMAT_CANNY_THRESHOLD2);
        DILATE_SIZE_WIDTH = Optional.ofNullable(idCheckParams.getDILATE_SIZE_WIDTH()).orElse(DILATE_SIZE_WIDTH);
        DILATE_SIZE_HEIGHT = Optional.ofNullable(idCheckParams.getDILATE_SIZE_HEIGHT()).orElse(DILATE_SIZE_HEIGHT);
        HOUGHLINES_RHO = Optional.ofNullable(idCheckParams.getHOUGHLINES_RHO()).orElse(HOUGHLINES_RHO);
        HOUGHLINES_THRESHOLD = Optional.ofNullable(idCheckParams.getHOUGHLINES_THRESHOLD()).orElse(HOUGHLINES_THRESHOLD);
        HOUGHLINES_MIN_LINE_LENGTH = Optional.ofNullable(idCheckParams.getHOUGHLINES_MIN_LINE_LENGTH()).orElse(HOUGHLINES_MIN_LINE_LENGTH);
        HOUGHLINES_MAX_LINE_GAP = Optional.ofNullable(idCheckParams.getHOUGHLINES_MAX_LINE_GAP()).orElse(HOUGHLINES_MAX_LINE_GAP);
    }

    public static void modifyLightParams(LightParams idCheckParams) {
        DARKNESS_THRESHOLD = Optional.ofNullable(idCheckParams.getDARKNESS_THRESHOLD()).orElse(DARKNESS_THRESHOLD);
        DEFAULT_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getDEFAULT_LIGHT_THRESHOLD()).orElse(DEFAULT_LIGHT_THRESHOLD);
        ONE_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getONE_LIGHT_THRESHOLD()).orElse(ONE_LIGHT_THRESHOLD);
        TWO_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getTWO_LIGHT_THRESHOLD()).orElse(TWO_LIGHT_THRESHOLD);
        THREE_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getTHREE_LIGHT_THRESHOLD()).orElse(THREE_LIGHT_THRESHOLD);
        FOUR_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getFOUR_LIGHT_THRESHOLD()).orElse(FOUR_LIGHT_THRESHOLD);
        FIVE_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getFIVE_LIGHT_THRESHOLD()).orElse(FIVE_LIGHT_THRESHOLD);
        SIX_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getSIX_LIGHT_THRESHOLD()).orElse(SIX_LIGHT_THRESHOLD);
        SEVEN_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getSEVEN_LIGHT_THRESHOLD()).orElse(SEVEN_LIGHT_THRESHOLD);
        EIGHT_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getEIGHT_LIGHT_THRESHOLD()).orElse(EIGHT_LIGHT_THRESHOLD);
        NINE_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getNINE_LIGHT_THRESHOLD()).orElse(NINE_LIGHT_THRESHOLD);
        TEN_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getTEN_LIGHT_THRESHOLD()).orElse(TEN_LIGHT_THRESHOLD);
        ELEVEN_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getELEVEN_LIGHT_THRESHOLD()).orElse(ELEVEN_LIGHT_THRESHOLD);
        TWELVE_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getTWELVE_LIGHT_THRESHOLD()).orElse(TWELVE_LIGHT_THRESHOLD);
        THIRTEEN_LIGHT_THRESHOLD = Optional.ofNullable(idCheckParams.getTHIRTEEN_LIGHT_THRESHOLD()).orElse(THIRTEEN_LIGHT_THRESHOLD);
        GAUSSIAN_BLUR_WIDTH = Optional.ofNullable(idCheckParams.getGAUSSIAN_BLUR_WIDTH()).orElse(GAUSSIAN_BLUR_WIDTH);
        GAUSSIAN_BLUR_HEIGHT = Optional.ofNullable(idCheckParams.getGAUSSIAN_BLUR_HEIGHT()).orElse(GAUSSIAN_BLUR_HEIGHT);
        GAUSSIAN_BLUR_SIGMA_X = Optional.ofNullable(idCheckParams.getGAUSSIAN_BLUR_SIGMA_X()).orElse(GAUSSIAN_BLUR_SIGMA_X);
        GAUSSIAN_BLUR_SIGMA_Y = Optional.ofNullable(idCheckParams.getGAUSSIAN_BLUR_SIGMA_Y()).orElse(GAUSSIAN_BLUR_SIGMA_Y);
        THRESHOLD_MAX_VAL = Optional.ofNullable(idCheckParams.getTHRESHOLD_MAX_VAL()).orElse(THRESHOLD_MAX_VAL);
        MAT_EXPR_ONES_ROWS = Optional.ofNullable(idCheckParams.getMAT_EXPR_ONES_ROWS()).orElse(MAT_EXPR_ONES_ROWS);
        MAT_EXPR_ONES_COLS = Optional.ofNullable(idCheckParams.getMAT_EXPR_ONES_COLS()).orElse(MAT_EXPR_ONES_COLS);
        ERODE_POINT_X = Optional.ofNullable(idCheckParams.getERODE_POINT_X()).orElse(ERODE_POINT_X);
        ERODE_POINT_Y = Optional.ofNullable(idCheckParams.getERODE_POINT_Y()).orElse(ERODE_POINT_Y);
        ERODE_POINT_ITERATIONS = Optional.ofNullable(idCheckParams.getERODE_POINT_ITERATIONS()).orElse(ERODE_POINT_ITERATIONS);
        DILATE_POINT_X = Optional.ofNullable(idCheckParams.getDILATE_POINT_X()).orElse(DILATE_POINT_X);
        DILATE_POINT_Y = Optional.ofNullable(idCheckParams.getDILATE_POINT_Y()).orElse(DILATE_POINT_Y);
        DILATE_POINT_ITERATIONS = Optional.ofNullable(idCheckParams.getDILATE_POINT_ITERATIONS()).orElse(DILATE_POINT_ITERATIONS);
    }

    public static void modifyBlurryParam(BlurryParams idCheckParams) {
        blurryCheckThreshold = Optional.ofNullable(idCheckParams.getBlurryCheckThreshold()).orElse(blurryCheckThreshold);
    }
}

