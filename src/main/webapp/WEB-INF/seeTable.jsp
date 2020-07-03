<%--
  Created by IntelliJ IDEA.
  User: hspcadmin
  Date: 2020/6/23
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TT</title>

    <!-- 引入bootstrap -->
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/webjars/bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css" />
    <link rel="stylesheet" href="/webjars/bootstrap-table/1.15.5/bootstrap-table.min.css" />
    <script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="/webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="/webjars/bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
    <script src="/webjars/bootstrap-table/1.15.5/bootstrap-table.min.js"></script>
    <script src="/webjars/bootstrap-select/1.7.3/js/bootstrap-select.js"></script>
    <style type="text/css">
        .over{
            background: #bcd4ec;
        }     

         #tableId{
            border:1px solid black;
          }
     </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li role="presentation" ><a href="/comeHome/import">导入文件</a></li>
    <li role="presentation" class="active"><a href="/comeHome/seeTable">表格查看</a></li>
    <li role="presentation"><a href="#">导出表格</a></li>
</ul>
<div>
    <form action="">
        <input type="button" value="报表导出" onclick="download()"/>
    </form>

    拍单时间超过<input type="text" style="width: 30px" id="updateExplainStateId" oninput = "value=value.replace(/[^\d]/g,'')"/>天客户置为无效<button onclick="updateExplainState()">确定</button>
    <br/><br/>
    <table id="tableId" class="table table-striped table-bordered" style="width:100%;font-size: 12px;display:inline">
        <tr>
            <th id="radio1">同意(不包括已拍单了的)：<input type="radio" name="state"  onclick="queryExeclModelByState('同意')"></th>
            <th id="radio2">不同意：<input type="radio" name="state"  onclick="queryExeclModelByState('不同意')"></th>
            <th id="radio3">无效：<input type="radio" name="state"  onclick="queryExeclModelByState('无效')"></th>
            <th id="radio4">未接：<input type="radio" name="state"  onclick="queryExeclModelByState('未接')"></th>
            <th id="radio5">拍单成功：<input type="radio" name="state"  onclick="queryExeclModelByState('成功')"></th>
            <th id="radio6">待处理：<input type="radio" name="state"  onclick="queryExeclModelByState('待处理')"></th>
        </tr>
    </table>
    <br/><br/>
    <table id="censusWorkId" class="table table-striped table-bordered" style="width:100%;font-size: 12px;display:inline">
        <tr>
            <th>今日拍单成功：</th>
            <th id="sucessNumId"></th>
        </tr>
    </table>
    <br/><br/>
    <div class="row form-group">
        <div class="col-lg-2"></div>
        <div class="form-group col-lg-8" >
            <label class="input-group">
                <span class="input-group-addon">已拨打ID：</span>
                <select class="col-lg-6 form-control"  id="userName" name="name"
                        data-live-search="true">
                </select>
            </label>
        </div>
    </div>
</div>
<div style="width: 100%">
    <table id="planMakeTable" class="table table-striped table-bordered" style="width:100%;font-size: 12px;display:inline">
        <thead id="theadId" class="simier">
        <tr id="0tr">
            <th>序号</th>
            <th>日期</th>
            <th >已拨打ID</th>
            <th>拨打备注</th>
            <th>拨打号码</th>
            <th>拨打号码</th>
            <th>通话分钟</th>
            <th>更新数据按钮</th>
            <th>拍单成功按钮</th>
        </tr>
        </thead>
    </table>
    <table id="planMakeTable2" class="table table-striped table-bordered" style="width:100%;font-size: 12px;display:inline">
        <thead id="theadId2" class="simier" >
        <tr id="0tr2">
            <th>成功ld</th>
            <th>姓名</th>
        </tr>
        </thead>
    </table>
    <table id="planMakeTable3" class="table table-striped table-bordered" style="width:100%;font-size: 12px;display:inline">
        <thead id="theadId3" class="simier" >

        </thead>
    </table>
</div>

</body>

<script>
    $(function () {

        //初始化select
        $("#userName").selectpicker({
            'selectedText': 'cat'
        });

        for (var i=1;i<6;i++){
            var id = '#radio'+i
            //绑定每行鼠标移入移出事件
            $(id).mouseover(function(){
                $(this).addClass("over");
            }).mouseout(function(){
                $(this).removeClass("over")
            });
        }



        $.ajax({
            url: "/queryExeclModelCollection/queryTodayAll",
           // data: {name: 'jenny'},
            type: "POST",
            dataType: "json",
            success: function(res) {
               var data=  res.todayAll;

                var html="";
                var count="";
                var selecthtml="";
                for (var i = 0;i<data.length;i++){
                    count =i+1;
                    selecthtml="<select onselect='未接'>" +
                                    "<option value='' id='"+count+"'></option>" +
                                    "<option value='未接' id='"+count+"未接'>未接</option>" +
                                    "<option value='同意' id='"+count+"同意'>同意</option>" +
                                    "<option value='不同意' id='"+count+"不同意'>不同意</option>" +
                                    "<option value='无效' id='"+count+"无效'>无效</option>" +
                                "</select>";

                    html+="<tr id='"+count+"tr'>" +
                                "<th>"+count+"</th>" +
                                "<th>"+data[i].date+"</th>" +
                                "<th style='max-width: 10%'>"+data[i].aliWWID+"</th>" +
                                "<th id='"+data[i].id+"explainState'>"+selecthtml+"</th>" +
                                "<th id='"+data[i].id+"phone'><input type='text' style='width: 100%' value='"+ data[i].phone +"'></th>" +
                                "<th id='"+data[i].id+"phone2'><input type='text' style='width: 100%' value='"+ data[i].phone2 +"'></th>" +
                                "<th>"+data[i].callTime+"</th>" +
                                "<th><button onclick='updateLink("+data[i].id+")'>更新</button></th>";
                                if (data[i].succeeState=='1'){
                                    html+="<th><button disabled='disabled'>拍单成功</button></th></tr>";
                                }else if (data[i].succeeState=='0'){
                                    html+="<th><button onclick='successLink("+data[i].id+")'>设为拍单成功</button></th></tr>";
                                }
                }
                $("#theadId").append(html);
                var idSelect = '';
                var idTr ='';
                // 统计拍单成功得行数
                var count2 = 1;
                // 追加每行排挡成功得数据填充
                var html2 ="";

                // 统计未接数量
                var missCallCount = 0;
                // 统计不不同意得数量
                var unagreeCount = 0;
                // 统计统计无效得数量
                var invalidCount = 0;
                // 统计已拨打数量
                var calledCount = 0;
                //统计同意数量
                var agreeCount = 0;
                //统计已拍单数量
                var sucessCount = 0;
                for (var i = 0;i<data.length;i++){
                    idSelect = i+1+data[i].explainState;
                    idTr = i+1+"tr";
                    // 根据数据来默认选中下拉框的值
                    $("#"+idSelect).attr("selected","selected");
                    //把拍单成功得数据填充上去
                    if (data[i].succeeState =='1'){
                        var tr2Id = i+i+'tr2';
                        html2+="<tr id="+tr2Id+"><th>"+data[i].aliWWID+"</th><th>"+data[i].name+"</th></tr>"
                    }

                    if (data[i].explainState == '未接'){
                        missCallCount+=1;
                    }else if(data[i].explainState == '不同意'){
                        unagreeCount+=1;
                    }else if (data[i].explainState == '无效'){
                        invalidCount+=1;
                    }else if (data[i].explainState == '同意' ){
                        agreeCount+=1;
                    }

                   if (data[i].succeeState =='1'){
                        sucessCount+=1;
                    }
                    //绑定每行鼠标移入移出事件
                    $("#"+idTr).mouseover(function(){
                        $(this).addClass("over");
                    }).mouseout(function(){
                        $(this).removeClass("over")
                    });
                }
                $("#sucessNumId").html("<font style='color: brown'>"+sucessCount+"</font>");
                $("#theadId2").append(html2);
                //已拨打数 = 未接 +不同意+同意
                calledCount =  missCallCount + unagreeCount + agreeCount;
                $("#theadId3").append("<tr><th>已拨打数</th>" +"<th>"+ calledCount +"</th></tr>");
                $("#theadId3").append("<tr><th>未接</th>" +"<th>"+ missCallCount +"</th></tr>");
                $("#theadId3").append("<tr><th>同意</th>" +"<th>"+ agreeCount +"</th></tr>");
                $("#theadId3").append("<tr><th>不同意</th>" +"<th>"+ unagreeCount +"</th></tr>");
                $("#theadId3").append("<tr><th>无效</th>" +"<th>"+ invalidCount +"</th></tr>");

                //电话号码追加
                var phoneData = res.mapList;
                var phoneTr ="";
                // var phoneHtml='<th rowspan= '+phoneData.length+' >拨打号码</th>';
                var phoneHtml='';
                for (var i =0;i<phoneData.length;i++){
                    var phoneVal = phoneData[i].phone;
                    var numVal = phoneData[i].num;
                    phoneTr = i+5+"tr";
                    // if (i==0){
                    //     $("#"+phoneTr).append(phoneHtml);
                    // }
                    if (i==0){
                        phoneHtml+='<tr><th rowspan= '+phoneData.length+' >拨打号码</th><th>'+phoneVal+'</th>' +'<th>'+ numVal +'</th></tr>';
                    }else {
                        phoneHtml+="<tr><th>"+phoneVal+"</th>" +"<th>"+ numVal +"</th></tr>";
                    }
                   // $("#"+phoneTr).append("<th>"+phoneVal+"</th>" +"<th>"+ numVal +"</th>");
                }
                $("#theadId3").append(phoneHtml);
            }
        });

    })

    //更新数据
    function updateLink(id) {
        var  explainStateEle= id+"explainState";
        var phoneEle = id+"phone";
        var phone2Ele = id+"phone2";
        var explainStateVal = $("#"+explainStateEle+" select").val();
        var phoneVal = $("#"+phoneEle+" input").val();
        var phone2Val = $("#"+phone2Ele+" input").val();
        //正则校验手机号格式
        if ('无效' != explainStateVal && explainStateVal!=''){
            var rex = /^1[34578]\d{9}$/;
            if (!rex.test(phoneVal) && !rex.test(phone2Val)) {
                if (phoneVal!='' && phone2Val!=''){
                    alert('手机号无效：'+phoneVal +"--" + '手机号无效：'+phone2Val );
                }else if (phoneVal!=''){
                    alert('手机号无效：'+phoneVal);
                }else {
                    alert('手机号无效：'+phone2Val);
                }
                return ;
            }
        }

        var dateJSON = JSON.stringify({
            "id": id,
            "explainState": explainStateVal,
            "phone": phoneVal,
            "phone2": phone2Val,
        });

        $.ajax({
            url: "/queryExeclModelCollection/updateExeclModel",
            data: dateJSON,
            type: "POST",
            dataType: "json",
            contentType:"application/json",
            success: function (data) {
                if(data.result==false){
                    alert("更新失败，失败原因："+data.errMsg);
                }else {
                    alert("更新成功");
                    window.location.reload();
                }
            }
        });
    }

    function successLink(id){
        if(confirm("确定将此客户置为拍单成功?")){
            //点击确定后操作
            var dateJSON = JSON.stringify({
                "id": id,
            });
            $.ajax({
                url: "/queryExeclModelCollection/updatesuccess",
                data: dateJSON,
                type: "POST",
                dataType: "json",
                contentType:"application/json",
                success: function (data) {
                    if(data.result==false){
                        alert("拍单失败，失败原因："+data.errMsg);
                    }else {
                        window.location.reload();
                    }
                }
            });
        }

    }

    /**
     * 下载execl
     * */
    function download(){
        var url="/importCollection/downloadExcel";
        window.open(url);
    }

    /**
     * 把距离上一次拍单时间超过xxx天的客户的状态置为无效
     */
    function updateExplainState() {
       var day =  $("#updateExplainStateId").val().trim();
       if(day ==''){
           alert("请输入天数");
           return;
       }
        if(confirm("确定将拍单时间超过"+ day +"天客户置为无效?")){
            //点击确定后操作
            var dateJSON = JSON.stringify({
                "wxDay": day,
            });

            var url="/queryExeclModelCollection/updateExplainState";
            $.ajax({
                url: url,
                data: dateJSON,
                type: "POST",
                dataType: "json",
                contentType:"application/json",
                success: function (res) {
                    if(res.result==false){
                        alert("拍单失败，失败原因："+res.errMsg);
                    }else {
                        window.location.reload();
                    }
                }
            });
        }

    }

    function queryExeclModelByState(state) {
        var url="/queryExeclModelCollection/queryExeclModelByState";
        var dateJSON = JSON.stringify({
            "state": state,
        });
        $.ajax({
            url: url,
            data: dateJSON,
            type: "POST",
            dataType: "json",
            contentType:"application/json",
            success: function (data) {
                $("#theadId").html("<tr id=\"0tr\">\n" +
                                        "<th>序号</th>\n" +
                                        "<th>日期</th>\n" +
                                        "<th >已拨打ID</th>\n" +
                                        "<th>拨打备注</th>\n" +
                                        "<th>拨打号码</th>\n" +
                                        "<th>拨打号码</th>\n" +
                                        "<th>通话分钟</th>\n" +
                                        "<th>更新数据按钮</th>\n" +
                                        "<th>拍单成功按钮</th>\n" +
                                    "</tr>")
                alert("111");
                var html="";
                var count="";
                var selecthtml="";
                for (var i = 0;i<data.length;i++){
                    count =i+1;
                    selecthtml="<select onselect='未接'>" +
                        "<option value='' id='"+count+"'></option>" +
                        "<option value='未接' id='"+count+"未接'>未接</option>" +
                        "<option value='同意' id='"+count+"同意'>同意</option>" +
                        "<option value='不同意' id='"+count+"不同意'>不同意</option>" +
                        "<option value='无效' id='"+count+"无效'>无效</option>" +
                        "</select>";

                    html+="<tr id='"+count+"tr'>" +
                        "<th>"+count+"</th>" +
                        "<th>"+data[i].date+"</th>" +
                        "<th style='max-width: 10%'>"+data[i].aliWWID+"</th>" +
                        "<th id='"+data[i].id+"explainState'>"+selecthtml+"</th>" +
                        "<th id='"+data[i].id+"phone'><input type='text' style='width: 100%' value='"+ data[i].phone +"'></th>" +
                        "<th id='"+data[i].id+"phone2'><input type='text' style='width: 100%' value='"+ data[i].phone2 +"'></th>" +
                        "<th>"+data[i].callTime+"</th>" +
                        "<th><button onclick='updateLink("+data[i].id+")'>更新</button></th>";
                    if (data[i].succeeState=='1'){
                        html+="<th><button disabled='disabled'>拍单成功</button></th></tr>";
                    }else if (data[i].succeeState=='0'){
                        html+="<th><button onclick='successLink("+data[i].id+")'>设为拍单成功</button></th></tr>";
                    }
                }
                $("#theadId").append(html);

                var idSelect='';
                var idTr='';
                for (var i = 0;i<data.length;i++){
                    idSelect = i+1+data[i].explainState;
                    // 根据数据来默认选中下拉框的值
                    $("#"+idSelect).attr("selected","selected");

                    idTr = i+1+"tr";
                    //绑定每行鼠标移入移出事件
                    $("#"+idTr).mouseover(function(){
                        $(this).addClass("over");
                    }).mouseout(function(){
                        $(this).removeClass("over")
                    });

                }
            }
        });
    }
</script>
</html>
