package com.debug2016.ocr.ofline;

import com.debug2016.ocr.dto.ImageCheckResult;
import com.debug2016.ocr.online.CommonIqcConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.opencv.opencv_core.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;

import java.util.Arrays;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.opencv_core.Mat.ones;


/**
 * NON-NLS
 *
 * @ClassName LightCheck
 * @Description 图片质量检测-光线灰暗与,反光检测,防伪反光检测
 * @Author songbo
 * @Date 2023-07-21 07:52
 **/
@Slf4j
public class OriginLightCheck {

    private static final String IMAGE_TOO_DARK = "图片过暗";
    private static final String IMAGE_REFLECT_LIGHT = "图片存在反光";
    //防伪反光提示
    private static final String IMAGE_SECURITY_REFLECT_LIGHT = "图片存在反光,可能为防伪区域";


    public static void main(String[] strings) throws InterruptedException {

        String imagePath = strings[0];
        Integer count = Integer.valueOf(strings[1]);
        Mat test = imread(imagePath);
        if (test == null || test.cols() <= 1) {
            log.warn("testSdk mat downloadMat error");
            return;
        }
        log.info("testSdk begin testAddress={}", test.address());
        for (int j = 0; j < count; j++) {
            try {
                List<Object> objects = Lists.newArrayList();
                Mat clone = test.clone();
                log.info("testSdk {} testAddress={}", j, clone.address());
                ImageCheckResult testSdk = securityCheck(clone, j + "",objects);
                log.info("testSdk-" + Thread.currentThread().getName() + "-" + j + "-" + testSdk);
            } catch (Exception e) {
                log.warn("testSdk mat processing error", e);
            }
        }
        System.out.println("end ");
    }

    public static ImageCheckResult securityCheck(Mat originMat, String sessionId, List<Object> objects) {
        log.info("securityCheck {} a originMat={},hash={},reference={}", sessionId, originMat, Long.toHexString(originMat.hashCode()), originMat.referenceCount());
        //1.转hsv
        Mat hsvMat = new Mat();
        cvtColor(originMat, hsvMat, COLOR_BGR2HSV);


        Scalar lowerBound1 = new Scalar(0, 80, 250, 0); // HSV的下限值
        Scalar upperBound1 = new Scalar(180, 255, 255, 0); // HSV的上限值
        Mat securityMat = new Mat();
        Mat low1 = new Mat(lowerBound1);
        Mat upper1 = new Mat(upperBound1);
        inRange(hsvMat, low1, upper1, securityMat);

        //7.提取的区域膨胀，使分散的区域连通起来，方便后续处理
        MatExpr securityKernel = ones(5, 5, CV_8UC1);
        Mat securityDilateMat = new Mat();
        dilate(securityMat, securityDilateMat, securityKernel.asMat(), new Point(CommonIqcConsts.DILATE_POINT_X, CommonIqcConsts.DILATE_POINT_Y), 3, BORDER_CONSTANT, morphologyDefaultBorderValue());

        //8.腐蚀，缩小区域，让边框达到防伪内部，方便统计颜色数量
        MatExpr kernel = ones(3, 3, CV_8UC1);
        Mat securityErodeMat = new Mat();
        erode(securityDilateMat, securityErodeMat, kernel.asMat(), new Point(CommonIqcConsts.ERODE_POINT_X, CommonIqcConsts.ERODE_POINT_Y), 4, BORDER_CONSTANT, morphologyDefaultBorderValue());
        //9.找到防伪区域的轮廓,该对象被回收
        MatVector contours = new MatVector();
        objects.add(new MyReference(contours, sessionId, "contours"));
        Mat hierarchy = new Mat();
        findContours(
                securityErodeMat,
                contours,
                hierarchy,
                RETR_CCOMP,
                CHAIN_APPROX_SIMPLE
        );
        log.info("securityCheck {} contours hash={},size={}", sessionId, Long.toHexString(contours.address()), contours.size());
        //10.画出防伪区域的轮廓，并按轮廓大小倒排
        Mat[] mats = contours.get();
        AtomicInteger num = new AtomicInteger(0);
        List<Mat> collect = Arrays.stream(mats).peek(a -> {
            num.getAndIncrement();
            log.info("securityCheck {} newMat count={},Mat={},hash={}", sessionId, num.get(), a, Long.toHexString(a.address()));
        }).sorted((o1, o2) -> {
            double area1 = contourArea(o1, false);
            double area2 = contourArea(o2, false);
            return Double.compare(area2, area1);
        }).collect(Collectors.toList());
        //11.计算防伪区域的颜色数量
        boolean haveLight = false;
        //取面积最大轮廓的循环次数
        int loopCount = Math.min(collect.size(), 4);
        for (int i = 0; i < loopCount; ++i) {
            Mat mat = collect.get(i);
            log.info("securityCheck {} preCheck count={},Mat={},hash={},reference={}", sessionId, i, mat, Long.toHexString(mat.address()), mat.referenceCount());
        }
        Boolean havaError = false;
        for (int i = 0; i < loopCount; ++i) {
            Mat myMat = collect.get(i);
            try {
                log.info("securityCheck {} start count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                if (havaError) {
                    continue;
                }
//            System.gc();
//            log.info("securityCheck {} gc count={},myMat={},hash={},reference={}", sessionId, i, myMat, myMat.address(), myMat.referenceCount());
                Mat mask = new Mat(originMat.size(), CV_8UC1, Scalar.all(0));
                log.info("securityCheck {} mask count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                drawContours(mask, new MatVector(myMat), 0, new Scalar(255, 255, 255, 0));
                log.info("securityCheck {} drawContours count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                // 使用掩码图像提取轮廓区域
                Mat extractedRegion = new Mat();
                log.info("securityCheck {} extractedRegion count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                hsvMat.copyTo(extractedRegion, mask);
                log.info("securityCheck {} copyTo count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                List<MyReference> myReferences = Lists.newArrayList();
                Map<Scalar, Integer> scalarIntegerMap = checkLightNum(extractedRegion, myMat, sessionId, myReferences,havaError);
                log.info("securityCheck {} checkLightNum count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                double area = contourArea(myMat, false);
                log.info("securityCheck {} end", sessionId);
            } catch (Exception e) {
                log.info("securityCheck {} error1 count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount(), e);
                break;
            }
            log.info("securityCheck {} end count={},myMat={},hash={},reference={}", sessionId, i, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
        }
        boolean b = contours == null;
        return ImageCheckResult.builder()
                .errorCode(haveLight ? "ID_CARD_CHECK_FAIL_SECURITY_REFLECTIVE" : null)
                .msg(haveLight ? IMAGE_SECURITY_REFLECT_LIGHT : null)
                .build();
    }

    private static Map<Scalar, Integer> checkLightNum(Mat extractedRegion, Mat myMat, String sessionId, List<MyReference> myReferences, Boolean isLog) {
// 创建一个空列表来存储检测到的颜色
        log.info("checkLightNum 01 myMat={},hash={},myMatreference={}", myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
        List<Scalar> detectedColors = new ArrayList<>();
        Map<Scalar, Integer> colorMap = new HashMap<>();
        Indexer indexer = extractedRegion.createIndexer();
        log.info("checkLightNum 02 myMat={},hash={},myMatreference={}", myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
        // 遍历图像的每个像素
        for (int y = 0; y < extractedRegion.rows(); y++) {
            for (int x = 0; x < extractedRegion.cols(); x++) {
                // 获取当前像素的HSV值
                double h = indexer.getDouble(y, x, 0);
                double s = indexer.getDouble(y, x, 1);
                double v = indexer.getDouble(y, x, 2);
                if ((myMat.cols()) == 0 && !isLog) {
                    isLog = Boolean.TRUE;
                    log.info("checkLightNum findChange sessionId {} myMat={},hash={},myMatreference={}", sessionId, myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
                }
                if (h <= 0) {
                    continue;
                }
                if (s <= 50) {
                    continue;
                }
                if (v <= 200) {
                    continue;
                }
                // 创建一个Scalar表示HSV值
                Scalar currentColor = new Scalar(h, s, v, 0);
                myReferences.add(new MyReference(currentColor, sessionId, "currentColor"));
                // 检查当前颜色是否已经存在于列表中
                boolean colorExists = false;
                for (Scalar existingColor : detectedColors) {
                    if (compareColors(currentColor, existingColor)) {
                        colorExists = true;
                        colorMap.put(existingColor, colorMap.getOrDefault(existingColor, 0) + 1);
                        break;
                    }
                }

//                 如果当前颜色不在列表中，则添加它
                if (!colorExists) {
                    detectedColors.add(currentColor);
                    colorMap.put(currentColor, 1);
                }
            }
        }
//        detectedColors.forEach(Pointer::deallocate);
//        colorMap.keySet().forEach(Pointer::close);
        log.info("checkLightNum end myMat={},hash={},myMatreference={}", myMat, Long.toHexString(myMat.address()), myMat.referenceCount());
        return colorMap;
    }

    // 比较两个Scalar对象是否表示相同的颜色
    private static boolean compareColors(Scalar color1, Scalar color2) {
//        double hueDifference = Math.abs(color1.get(0) - color2.get(0));
//        double saturationDifference = Math.abs(color1.get(1) - color2.get(1));
//        double valueDifference = Math.abs(color1.get(2) - color2.get(2));

        return getColorLevel(color1) == getColorLevel(color2);
    }

    private static double getColorLevel(Scalar color1) {
        double h = color1.get(0);
        double s = color1.get(1);
        double sLevel = 0;
        //饱和度等级
        if (s <= 80) {
            sLevel = 0.1;
        }
        if (s <= 110) {
            sLevel = 0.2;
        }
        if (s <= 140) {
            sLevel = 0.3;
        }
        if (s <= 170) {
            sLevel = 0.4;
        }
        if (s <= 200) {
            sLevel = 0.5;
        }
        if (s <= 230) {
            sLevel = 0.6;
        }
        if (s <= 260) {
            sLevel = 0.7;
        }
        //颜色等级
        if (h <= 30) {
            return 1 + sLevel;
        }
        if (h <= 60) {
            return 2 + sLevel;
        }
        if (h <= 90) {
            return 3 + sLevel;
        }
        if (h <= 120) {
            return 4 + sLevel;
        }
        if (h <= 150) {
            return 5 + sLevel;
        }
        if (h <= 180) {
            return 6 + sLevel;
        }
        return 7;
    }

}
