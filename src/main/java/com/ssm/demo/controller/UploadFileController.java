package com.ssm.demo.controller;

import com.ssm.demo.common.Result;
import com.ssm.demo.common.ResultGenerator;
import com.ssm.demo.controller.enums.UploadFileTypeEnum;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.ssm.demo.common.Constants.FILE_PRE_URL;


@Controller
@RequestMapping("/upload")
public class UploadFileController {

    final static Logger logger = Logger.getLogger(UploadFileController.class);

    /**
     * 通用 文件上传接口 (可以上传图片、视频、excel等文件，具体格式可在UploadFileTypeEnum中进行配置)
     *
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        ServletContext sc = request.getSession().getServletContext();
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        String fileName = null;
        UploadFileTypeEnum uploadFileTypeEnum = UploadFileTypeEnum.getFileEnumByType(type);
        if (uploadFileTypeEnum == UploadFileTypeEnum.ERROR_TYPE) {
            //格式错误则不允许上传，直接返回错误提示
            return ResultGenerator.genFailResult("请检查文件格式！");
        } else {
            //生成文件名称通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random r = new Random();
            StringBuilder tempName = new StringBuilder();
            tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(".").append(type);
            fileName = tempName.toString();
        }
        try {
            String dir = sc.getRealPath("/upload");
            FileUtils.writeByteArrayToFile(new File(dir, fileName), file.getBytes());
        } catch (IOException e) {
            //文件上传异常
            return ResultGenerator.genFailResult("文件上传失败！");
        }
        Result result = ResultGenerator.genSuccessResult();
        //返回文件的全路径
        StringBuilder fileUrl = new StringBuilder();
        fileUrl.append(FILE_PRE_URL).append("/upload/").append(fileName);
        result.setData(fileUrl.toString());
        return result;
    }
}

