package com.marshal.epoch.core.util;

import com.marshal.epoch.core.constant.BaseConstant;
import com.marshal.epoch.core.dto.PageableData;
import com.marshal.epoch.core.dto.ResponseEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @auth: Marshal
 * @date: 2019/8/27
 * @desc: response工具类
 */
public class ResponseUtil implements BaseConstant {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    private ResponseUtil() {

    }

    /**
     * 请求成功
     *
     * @return
     */
    public static ResponseEntity responseOk() {
        return responseOk(OPERATE_SUCCESS);
    }

    public static ResponseEntity responseOk(String message) {
        return new ResponseEntity(message);
    }

    public static ResponseEntity responseOk(List<?> rows) {
        return new ResponseEntity(new PageableData(rows));
    }

    public static ResponseEntity responseOk(Object data) {
        return new ResponseEntity(data);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static ResponseEntity responseErr() {
        return responseErr(OPERATE_FAIL);
    }

    public static ResponseEntity responseErr(String message) {
        return new ResponseEntity(false, message);
    }

    /**
     * 文件下载
     *
     * @param bytes
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void responseFile(byte[] bytes, String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new IOException("文件下载失败!");
        } finally {
            outputStream.close();
        }
    }

    /**
     * excel导出
     *
     * @param workbook
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void responseExcel(Workbook workbook, String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new IOException("导出失败!");
        } finally {
            outputStream.close();
        }
    }

}