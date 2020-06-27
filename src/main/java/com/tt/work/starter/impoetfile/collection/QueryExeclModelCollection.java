package com.tt.work.starter.impoetfile.collection;


import com.tt.work.starter.DateUtils;
import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.model.PageQuery;
import com.tt.work.starter.impoetfile.model.ResultModel;
import com.tt.work.starter.impoetfile.service.IExeclModelService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Controller
@RequestMapping("/queryExeclModelCollection")
public class QueryExeclModelCollection {

    @Autowired
    private IExeclModelService execlModelService;

    private List<ExeclModel> sucessMap = new ArrayList<ExeclModel>();

    private List<ExeclModel> wXMap = new ArrayList<ExeclModel>();

    private List<ExeclModel> tyMap = new ArrayList<ExeclModel>();

    private List<ExeclModel> unTyMap = new ArrayList<ExeclModel>();
    private List<ExeclModel> wjMap = new ArrayList<ExeclModel>();


    @RequestMapping("/queryTodayAll")
    @ResponseBody
    public PageQuery queryTodayAll(){
        PageQuery pageQuery = execlModelService.queryTodayAll();
        initMap(pageQuery.getTodayAll());
        return  pageQuery;
    }

    @RequestMapping("/updateExeclModel")
    @ResponseBody
    public ResultModel updateExeclModel(@RequestBody Map<String,Object> param){

        String id = param.get("id").toString();
        String explainState = param.get("explainState").toString();
        String phone = param.get("phone").toString();
        String phone2 = param.get("phone2").toString();
        ResultModel resultModel = new ResultModel();

        if (StringUtils.isEmpty(explainState) || StringUtils.isEmpty(id)){
            resultModel.setResult(false);
            resultModel.setErrMsg("拨打备注或者id不能不填写");
            return resultModel;
        }
        if (!"无效".equals(explainState) && (StringUtils.isEmpty(phone) && StringUtils.isEmpty(phone2))){
            resultModel.setResult(false);
            resultModel.setErrMsg("非无效的客户需要填写号码");
            return resultModel;
        }

        if ("无效".equals(explainState) && (!StringUtils.isEmpty(phone) || !StringUtils.isEmpty(phone2))){
            resultModel.setResult(false);
            resultModel.setErrMsg("无效的客户不准填写拨打号码");
            return resultModel;
        }
        ExeclModel execlModel = new ExeclModel();
        execlModel.setId(id);
        execlModel.setExplainState(explainState);
        execlModel.setPhone(phone);
        execlModel.setPhone2(phone2);
        Integer count = execlModelService.updateExeclModel(execlModel);

        if (count >0){
            resultModel.setResult(true);
            sucessMap = null;
            wXMap = null;
            tyMap = null;
            unTyMap = null;
            return  resultModel;
        }
        resultModel.setResult(false);
        resultModel.setErrMsg("未知失败");
        return resultModel;
    }

    @RequestMapping("/updatesuccess")
    @ResponseBody
    public ResultModel updatesuccess(@RequestBody Map<String,Object> param){
        ExeclModel execlModel = execlModelService.queryByid(param.get("id").toString());
        ResultModel resultModel = new ResultModel();
        if (null == execlModel){
            resultModel.setResult(false);
            resultModel.setErrMsg("只可以将同意拍单的设置为拍单成功");
            return resultModel;
        }
        Integer count = execlModelService.updatesuccess(param.get("id").toString());
        if (count >0){
            sucessMap = null;
            wXMap = null;
            tyMap = null;
            unTyMap = null;
            resultModel.setResult(true);
            return  resultModel;
        }else {
            resultModel.setResult(false);
            resultModel.setErrMsg("未知失败");
            return resultModel;
        }
    }

    @RequestMapping("/updateExplainState")
    @ResponseBody
    public ResultModel updateExplainState() {
        PageQuery pageQuery = execlModelService.queryTodayAll();
        List<ExeclModel> todayAll = pageQuery.getTodayAll();
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        String year = calendar.get(Calendar.YEAR)+"";
        // 获取当前月
        String month = calendar.get(Calendar.MONTH) + 1+"";
        // 获取当前日
        String day = calendar.get(Calendar.DATE) +"";
        String today = year + "-" + month + "-" +day;
        ResultModel resultModel = new ResultModel();
        try {
            for (ExeclModel execlModel:todayAll){
                String date = execlModel.getDate();
                if (date.contains("/")){
                    date =date.replaceAll("/","-");
                }
                if (DateUtils.daysBetween(date,today)>30){
                    Integer integer = execlModelService.updateExplainStateToWX(execlModel.getId());
                }
            }
        }catch (ParseException e){
            e.printStackTrace();

        }
        resultModel.setResult(true);
        sucessMap = null;
        wXMap = null;
        tyMap = null;
        unTyMap = null;
        return resultModel;
    }

    @RequestMapping("/queryExeclModelByState")
    @ResponseBody
    public List<ExeclModel> queryExeclModelByState(@RequestBody Map<String,Object> param){
        String state = param.get("state").toString();
        if (sucessMap==null || wXMap==null || tyMap==null || unTyMap==null){
            PageQuery pageQuery = execlModelService.queryTodayAll();
            initMap(pageQuery.getTodayAll());
        }

        if ("无效".equals(state)){
            return wXMap;
        }else if ("成功".equals(state)){
            return sucessMap;
        }else if ("同意".equals(state)){
            return tyMap;
        }else if ("不同意".equals(state)){
            return unTyMap;
        }else if ("未接".equals(state)){
            return wjMap;
        }
        return null;
    }

    private void initMap(List<ExeclModel> todayList){
            sucessMap = new ArrayList<ExeclModel>();
            wXMap = new ArrayList<ExeclModel>();
            tyMap = new ArrayList<ExeclModel>();
            unTyMap = new ArrayList<ExeclModel>();
            wjMap = new ArrayList<ExeclModel>();

        for (ExeclModel execlModel:todayList){
            String explainState = execlModel.getExplainState();
            if ("无效".equals(explainState)){
                wXMap.add(execlModel);
            }else if ("同意".equals(explainState) && "0".equals(execlModel.getSucceeState())){
                tyMap.add(execlModel);
            }else if ("不同意".equals(explainState)){
                unTyMap.add(execlModel);
            }else if ("未接".equals(explainState)){
                wjMap.add(execlModel);
            }else if ("1".equals(execlModel.getSucceeState())){
                sucessMap.add(execlModel);
            }

        }
    }
}
