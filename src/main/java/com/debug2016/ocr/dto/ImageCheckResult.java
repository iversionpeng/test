package com.debug2016.ocr.dto;

import lombok.*;

/**
 * @ClassName ImageCheckResult
 * @Description 图片质量检查结果
 * @Author songbo
 * @Date 2023-07-21 08:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageCheckResult {

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 描述
     */
    private String msg;

}

