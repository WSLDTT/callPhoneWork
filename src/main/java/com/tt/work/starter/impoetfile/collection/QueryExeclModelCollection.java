package com.tt.work.starter.impoetfile.collection;


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

    @RequestMapping("/queryTodayAll")
    @ResponseBody
    public PageQuery queryTodayAll(){
        PageQuery pageQuery = execlModelService.queryTodayAll();
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
            resultModel.setResult(true);
            return  resultModel;
        }else {
            resultModel.setResult(false);
            resultModel.setErrMsg("未知失败");
            return resultModel;
        }
    }
}
