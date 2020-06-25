package com.tt.work.starter.impoetfile.collection;

import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.model.ResultModel;
import com.tt.work.starter.impoetfile.service.IImportService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Controller
@RequestMapping("/importCollection")
public class ImportCollection {

    @Resource
    private IImportService importService;

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
                    execlModel.setInsertDate(year+month+day);
                    System.out.println(execlModel.toString());
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
}
