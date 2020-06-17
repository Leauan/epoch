package com.marshal.epoch.core.rest;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.marshal.epoch.core.base.BaseConstants;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * response工具类
 *
 * @author Marshal
 * @date 2019/8/27
 */
public class Response implements BaseConstants {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private Response() {
    }

    /**
     * 请求成功
     *
     * @return
     */
    public static ResponseEntity success() {
        return success(ResponseMessage.SUCCESS);
    }

    public static ResponseEntity success(String message) {
        return new ResponseEntity(message);
    }

    public static ResponseEntity success(List<?> rows) {
        return new ResponseEntity(new PageableData(rows));
    }

    public static ResponseEntity success(Object data) {
        return new ResponseEntity(data);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static ResponseEntity fail() {
        return fail(ResponseMessage.ERROR);
    }

    public static ResponseEntity fail(String message) {
        return new ResponseEntity(false, message);
    }

    public static ResponseEntity fail(Integer code, String message) {
        return new ResponseEntity(false, message);
    }

    public static void fail(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(fail(message)));
        } catch (IOException e) {
            logger.error("io exception happen ,please check");
        } finally {
            writer.close();
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
            workbook.close();
        }
    }

    /**
     * 文件下载
     *
     * @param bytes
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void download(byte[] bytes, String fileName, HttpServletResponse response) throws IOException {
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
     * 压缩包下载
     *
     * @param files
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void downloadZip(Map<String, byte[]> files, String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        ServletOutputStream servletOutputStream = response.getOutputStream();
        ZipOutputStream zos = new ZipOutputStream(servletOutputStream);
        BufferedOutputStream bos = new BufferedOutputStream(zos);

        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            String name = entry.getKey();
            byte[] content = entry.getValue();

            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(content));
            zos.putNextEntry(new ZipEntry(name));

            int len = 0;
            byte[] buf = new byte[content.length];
            while ((len = bis.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, len);
            }
            bos.flush();
            zos.closeEntry();
            bis.close();
        }
        bos.flush();
        bos.close();
        servletOutputStream.close();
    }

}