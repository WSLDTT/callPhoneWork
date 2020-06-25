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
    <style type="text/css">
        .over{
            background: #bcd4ec;
        }     
     </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li role="presentation" ><a href="/comeHome/import">导入文件</a></li>
    <li role="presentation" class="active"><a href="/comeHome/seeTable">表格查看</a></li>
    <li role="presentation"><a href="#">导出表格</a></li>
</ul>
<table id="planMakeTable" class="table table-striped table-bordered" style="width:100%">
    <thead id="theadId" class="simier">
    <tr>
        <th colspan="15" class="tableHead" style="font-size:28px;color: black" id="tableTitle"></th>
    </tr>
    <tr>
        <th>0</th>
        <th>日期</th>
        <th>已拨打ID</th>
        <th>拨打备注</th>
        <th>拨打号码</th>
        <th>拨打号码</th>
        <th>通话分钟</th>
        <th>更新数据按钮</th>
        <th>拍单成功按钮</th>
        <th>成功ld</th>
        <th>姓名</th>
    </tr>
<%--    <tr>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th>未接</th>--%>
<%--        <th>63</th>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th>同意</th>--%>
<%--        <th>67</th>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th>不同意</th>--%>
<%--        <th>53</th>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th></th>--%>
<%--        <th>无效</th>--%>
<%--        <th>163</th>--%>
<%--    </tr>--%>
    </thead>
    <tbody>
    </tbody>
</table>
</body>

<script>
    $(function () {
        $.ajax({
            url: "/queryExeclModelCollection/queryTodayAll",
           // data: {name: 'jenny'},
            type: "POST",
            dataType: "json",
            success: function(data) {
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
                                "<th>"+data[i].aliWWID+"</th>" +
                                "<th id='"+data[i].id+"explainState'>"+selecthtml+"</th>" +
                                "<th id='"+data[i].id+"phone'><input type='text' value='"+ data[i].phone +"'></th>" +
                                "<th id='"+data[i].id+"phone2'><input type='text' value='"+ data[i].phone2 +"'></th>" +
                                "<th>"+data[i].callTime+"</th>" +
                                "<th><button onclick='updateLink("+data[i].id+")'>更新</button></th>" +
                                "<th><button onclick='successLink("+data[i].id+")'>拍单成功</button></th>" +
                         "</tr>";

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
                for (var i = 0;i<data.length;i++){
                    idSelect = i+1+data[i].explainState;
                    idTr = i+1+"tr";
                    // 根据数据来默认选中下拉框的值
                    $("#"+idSelect).attr("selected","selected");
                    //把拍单成功得数据填充上去
                    if (data[i].succeeState =='1'){
                        html2="<th>"+data[i].aliWWID+"</th><th>"+data[i].name+"</th>"
                        $("#"+count2+"tr").append(html2);
                        count2++;
                    }

                    if (data[i].explainState == '未接'){
                        missCallCount+=1;
                    }else if(data[i].explainState == '不同意'){
                        unagreeCount+=1;
                    }else if (data[i].explainState == '无效'){
                        invalidCount+=1;
                    }else if (data[i].explainState == '同意'){
                        agreeCount+=1;
                    }
                    //绑定每行鼠标移入移出事件
                    $("#"+idTr).mouseover(function(){
                        $(this).addClass("over");
                    }).mouseout(function(){
                        $(this).removeClass("over")
                    });
                }
                //已拨打数 = 未接 +不同意+同意
                calledCount =  missCallCount + unagreeCount + agreeCount;
                $("#1tr").append("<th>已拨打数</th>" +"<th>"+ calledCount +"</th>");
                $("#2tr").append("<th>未接</th>" +"<th>"+ missCallCount +"</th>");
                $("#3tr").append("<th>同意</th>" +"<th>"+ agreeCount +"</th>");
                $("#4tr").append("<th>不同意</th>" +"<th>"+ unagreeCount +"</th>");
                $("#5tr").append("<th>无效</th>" +"<th>"+ invalidCount +"</th>");
            }
        });


        //给
    })

    //更新数据
    function updateLink(id) {
        var  explainStateEle= id+"explainState";
        var phoneEle = id+"phone";
        var phone2Ele = id+"phone2";
        var explainStateVal = $("#"+explainStateEle+" select").val();
        var phoneVal = $("#"+phoneEle+" input").val();
        var phone2Val = $("#"+phone2Ele+" input").val();
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
</script>
</html>
