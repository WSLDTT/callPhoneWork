package com.tt.work.starter.impoetfile.collection;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tt.work.starter.DateUtils;
import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.model.ResultModel;
import com.tt.work.starter.impoetfile.service.IExeclModelService;
import com.tt.work.starter.impoetfile.service.IImportService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Controller
@RequestMapping("/importCollection")
public class ImportCollection {

    @Resource
    private IImportService importService;

    @Autowired
    private IExeclModelService execlModelService;

    @RequestMapping("/importExecl")
    @ResponseBody
    public ResultModel importExecl(@RequestParam MultipartFile file) {
        ResultModel resultMode = new ResultModel();
        try {
            List<ExeclModel> execlModels = new ArrayList<ExeclModel>();
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowNum = sheet.getLastRowNum() +1;
            System.out.println("总行数："+rowNum);
            for (int i=1;i<rowNum;i++) {
                ExeclModel execlModel = new ExeclModel();
                XSSFRow currRow = sheet.getRow(i);
                // currRow.getCell(0).setCellType(CellType.STRING);
                XSSFCell cell = currRow.getCell(0);
                String dateStr = "";
                if (cell.getCellType() == CellType.NUMERIC) {
                    DateFormat dft = new SimpleDateFormat("yyyy/MM/dd");
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        dateStr = dft.format(date);
                    }
                }else {
                    cell.setCellType(CellType.STRING);
                    dateStr = cell.getStringCellValue();
                }
                if (dateStr.contains("/")){
                    dateStr =dateStr.replaceAll("/","-");
                }

                    execlModel.setDate(dateStr);
                    currRow.getCell(1).setCellType(CellType.STRING);
                    String aliWWID = currRow.getCell(1).getStringCellValue();
                    execlModel.setAliWWID(aliWWID);

                    execlModel.setSucceeState("0");
                    execlModel.setName("邓彤彤");
                    execlModel.setCallTime("1");
                    execlModel.setExplainState("");
                    execlModel.setPhone("");
                    execlModel.setPhone2("");
                    execlModels.add(execlModel);
                    Calendar calendar = Calendar.getInstance();
                    // 获取当前年
                    String year = calendar.get(Calendar.YEAR)+"";
                     // 获取当前月
                    String month = calendar.get(Calendar.MONTH) + 1+"";
                    // 获取当前日
                    String day = calendar.get(Calendar.DATE)+"";
                    // 客户与上次拍单时间相差小于10天  自动设为无效
                    if (DateUtils.daysBetween(dateStr,year + "-" + month + "-" +day)<10){
                        execlModel.setExplainState("无效");
                    }
                    execlModel.setInsertDate(year+month+day);
                    importService.insert(execlModel);
            }
            resultMode.setResult(true);
            return resultMode;
        } catch (Exception e){
            e.printStackTrace();
            resultMode.setResult(false);
            return resultMode;
        }

    }

    @RequestMapping("/downloadExcel")
    @ResponseBody
    public void  downloadExcel(HttpServletResponse response) throws IOException {
        List<ExeclModel> execlModels = execlModelService.queryTodayAll().getTodayAll();
        List<Map<String, String>> maps = execlModelService.queryTodayCallPhoneNum();
        ServletOutputStream out=null;
        try {
            out=response.getOutputStream();
            //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.pdf)
            Calendar calendar = Calendar.getInstance();
            // 获取当前月
            String month = calendar.get(Calendar.MONTH) + 1+"";
            // 获取当前日
            String day = calendar.get(Calendar.DATE)+"";
            XSSFWorkbook workbook = new XSSFWorkbook();
            if (CollectionUtils.isEmpty(execlModels)){
                workbook.createSheet("Sheet1");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("无今日数据.xlsx", "UTF-8"));
                workbook.write(out);
                return;
            }
            String fileName = execlModels.get(0).getName()+month+"-"+day;

            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName+".xlsx", "UTF-8"));
            execl(workbook,execlModels,maps);
            workbook.write(out);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (out!=null){
                    out.flush();
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void execl( XSSFWorkbook workbook, List<ExeclModel> execlModels,List<Map<String, String>> maps){
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        // 居中样式
        XSSFCellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        //实线样式
        XSSFCellStyle soildStyle = workbook.createCellStyle();
        //设置边框bai样式
        soildStyle.setAlignment(HorizontalAlignment.CENTER);
        soildStyle.setBorderTop(BorderStyle.THIN);
        soildStyle.setBorderBottom(BorderStyle.THIN);
        soildStyle.setBorderLeft(BorderStyle.THIN);
        soildStyle.setBorderRight(BorderStyle.THIN);
        //设置边框颜色du
        XSSFCellStyle bgColorStyle = workbook.createCellStyle();
        bgColorStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        bgColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        bgColorStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFRow firstRow = sheet.createRow(0);
        XSSFCell dateCell = firstRow.createCell(0);
        dateCell.setCellStyle(centerStyle);
        XSSFCell aliWWIDCell = firstRow.createCell(1);
        aliWWIDCell.setCellStyle(centerStyle);
        XSSFCell explainStateCell = firstRow.createCell(2);
        explainStateCell.setCellStyle(centerStyle);
        XSSFCell phoneCell = firstRow.createCell(3);
        phoneCell.setCellStyle(centerStyle);
        XSSFCell phone2Cell = firstRow.createCell(4);
        phone2Cell.setCellStyle(centerStyle);
        XSSFCell callTimeCell = firstRow.createCell(5);
        callTimeCell.setCellStyle(centerStyle);
        XSSFCell successCell = firstRow.createCell(6);
        successCell.setCellStyle(centerStyle);
        XSSFCell nameCell = firstRow.createCell(7);
        nameCell.setCellStyle(centerStyle);


        dateCell.setCellValue("日期");
        aliWWIDCell.setCellValue("已拨打ID");
        explainStateCell.setCellValue("拨打备注");
        phoneCell.setCellValue("拨打号码");
        phone2Cell.setCellValue("拨打号码");
        callTimeCell.setCellValue("通话分钟");
        successCell.setCellValue("成功ld");
        successCell.setCellValue("姓名");
        XSSFCell cell = null;
        XSSFRow row = null;
        XSSFCellStyle cellStyle=null;
        // 统计未接数量
        int missCallCount = 0;
        // 统计不不同意得数量
        int unagreeCount = 0;
        // 统计统计无效得数量
        int invalidCount = 0;
        // 统计已拨打数量
        int calledCount = 0;
        //统计同意数量
        int agreeCount = 0;

        List<ExeclModel> successList = new ArrayList<ExeclModel>();
        for (int i= 0; i<execlModels.size();i++){
             row = sheet.createRow(i+1);
            ExeclModel execlModel = execlModels.get(i);
            cell = row.createCell(0);
            if ("1".equals(execlModel.getSucceeState())){
                cell.setCellStyle(bgColorStyle);
            }else {
                cell.setCellStyle(centerStyle);
            }
            // cell.setCellStyle(centerStyle);
            cell.setCellValue(execlModel.getDate());
            cell = row.createCell(1);
            if ("1".equals(execlModel.getSucceeState())){
                cell.setCellStyle(bgColorStyle);
            }else {
                cell.setCellStyle(centerStyle);
            }
            cell.setCellValue(execlModel.getAliWWID());
            cell = row.createCell(2);
            if ("1".equals(execlModel.getSucceeState())){
                cell.setCellStyle(bgColorStyle);
            }else {
                cell.setCellStyle(centerStyle);
            }
            cell.setCellValue(execlModel.getExplainState());
            cell = row.createCell(3);
            if ("1".equals(execlModel.getSucceeState())){
                cell.setCellStyle(bgColorStyle);
            }else {
                cell.setCellStyle(centerStyle);
            }
            cell.setCellValue(execlModel.getPhone());

            cell = row.createCell(4);
            if ("1".equals(execlModel.getSucceeState()) && !StringUtils.isEmpty(execlModel.getPhone2())){
                cell.setCellStyle(bgColorStyle);
            }else {
                cell.setCellStyle(centerStyle);
            }
            cell.setCellValue(execlModel.getPhone2());

            cell = row.createCell(5);
            cell.setCellValue(execlModel.getCallTime());

            String explainState = execlModel.getExplainState();
            if ("未接".equals(explainState)){
                missCallCount+=1;
            }else if ("不同意".equals(explainState)){
                unagreeCount+=1;
            }else if ("无效".equals(explainState)){
                invalidCount+=1;
            }else if ("同意".equals(explainState)){
                agreeCount+=1;
            }

            if ("1".equals(execlModel.getSucceeState())){
                successList.add(execlModel);
            }
        }

        for (int i= 1; i<successList.size();i++){
            row = sheet.getRow(i);
            cell = row.createCell(6);
            cell.setCellStyle(bgColorStyle);
            cell.setCellValue(successList.get(i).getAliWWID());
            cell = row.createCell(7);
            cell.setCellStyle(centerStyle);
            cell.setCellValue(successList.get(i).getName());
        }

        //已拨打数 = 未接 +不同意+同意
        calledCount =  missCallCount + unagreeCount + agreeCount;
        row = sheet.getRow(0);
        // 列名
        cell= row.createCell(8);
        cell.setCellStyle(soildStyle);
        cell.setCellValue("已拨打数");
        // 列值
        cell= row.createCell(9);
        cell.setCellStyle(soildStyle);
        cell.setCellValue(calledCount);

        row = sheet.getRow(1);
        // 列名
        cell= row.createCell(8);
        cell.setCellStyle(soildStyle);
        cell.setCellValue("未接");
        // 列值
        cell= row.createCell(9);
        cell.setCellStyle(soildStyle);
        cell.setCellValue(missCallCount);

        row = sheet.getRow(2);
        // 列名
        cell= row.createCell(8);
        cell.setCellStyle(soildStyle);
        cell.setCellValue("同意");
        // 列值
        cell= row.createCell(9);
        cell.setCellStyle(soildStyle);
        cell.setCellValue(agreeCount);

        row = sheet.getRow(3);
        // 列名
        cell= row.createCell(8);
        cell.setCellStyle(soildStyle);
        cell.setCellValue("不同意");
        // 列值
        cell= row.createCell(9);
        cell.setCellStyle(soildStyle);
        cell.setCellValue(unagreeCount);

        row = sheet.getRow(4);
        // 列名
        cell= row.createCell(8);
        cell.setCellStyle(soildStyle);
        cell.setCellValue("无效");
        // 列值
        cell= row.createCell(9);
        cell.setCellStyle(soildStyle);
        cell.setCellValue(invalidCount);

        sheet.setColumnWidth(0,4200);
        sheet.setColumnWidth(1,4200);
        sheet.setColumnWidth(2,4200);
        sheet.setColumnWidth(3,4200);
        sheet.setColumnWidth(4,4200);
        sheet.setColumnWidth(5,4200);
        sheet.setColumnWidth(6,4200);
        sheet.setColumnWidth(7,2144);
        sheet.setColumnWidth(8,4200);
        sheet.setColumnWidth(9,4200);


        row = sheet.getRow(5);
        cell  = row.createCell(8);
        cell.setCellValue("拨打号码");

        int count =5;
        for (Map<String,String> map:maps){
            if (count !=5){
                row = sheet.getRow(count);
            }
                cell  = row.createCell(9);
                cell.setCellStyle(soildStyle);
                cell.setCellValue(map.get("phone"));

                cell  = row.createCell(10);
                cell.setCellStyle(soildStyle);
                cell.setCellValue(map.get("num"));
            count+=1;

        }

    }

}
