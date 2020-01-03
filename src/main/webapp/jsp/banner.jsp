<%@page contentType="text/html;charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<style type="text/css">
    #yong {
        border: 0px solid red;
        margin-top: -22px;
    }

    #fo {
        border: 0px solid red;
        margin-left: 120px;
    }

    #ff {
        border: 0px solid red;
        margin-top: 5px;
    }

    #list tr td {
        text-align: center;
    }
</style>

<%--书写jqGrit脚本代码--%>
<script type="text/javascript">
    //删除函数
    function de(id) {
        jQuery("#list").jqGrid('delGridRow', id, {
            reloadAfterSubmit: true//这里设为true执行完自动刷新数据
        })
    }

    //删除函数
    function up(id) {
        jQuery("#list").jqGrid('editGridRow', id, {
            height: 300,
            reloadAfterSubmit: true
        });
    }

    //页面加载完执行
    $(function () {
        //表格的样式
        $("#list").jqGrid({
            //加载数据的路径
            url: "${path}/banner/sybaseBanner",
            //增删改的数据传输路径
            editurl: "${path}/banner/zong",
            //数据返回类型
            datatype: "json",
            //分页栏位
            pager: "#pager",
            //展示的数据
            rowNum: 3,
            //用户可选择的展示数据的条数
            rowList: [3, 8, 10],
            //表格样式
            styleUI: "Bootstrap",
            //表格高度
            height: "280px",
            //展示总条数
            viewrecords: true,
            //设置表的名称
            caption: "我的表",
            //自动适应父容器
            autowidth: true,
            multiselect: true,// 在表格第一列前面，加入复选框
            //标题
            colNames: [
                "编号", "图片名", "img", "上传时间", "状态", "操作"
            ],
            //对应的内容
            colModel: [   //editable:true 的含义是可编译
                {name: "id"},
                {name: "title", editable: true},
                {
                    name: "img", editable: true, edittype: 'file', editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, options, row) {
                        return "<image style='width: 100%;height:70px;' src=${path}/upload/" + value + "></image>";
                    }
                },
                //edittype:'date'  的含义是时间类型的样式
                {name: "create_Date", edittype: 'date'},//
                //edittype:"select"  下拉框
                //editoptions:  下拉框的选择值
                {
                    name: "status", editable: true, edittype: "select", editoptions: {
                        value: "显示:显示;不显示:不显示"
                    }
                },
                //使用formatter创建两个按钮
                {
                    name: "", formatter: function (value, grid, rows, state) {
                        return "<button class='btn btn-default btn-info' style=\"color:#f60\" onclick=\"de('" + rows.id + "')\">delete</button>" +
                            "<button class='btn btn-default btn-primary' style='color:#f60' onclick=\"up('" + rows.id + "')\">update</button>"
                    }
                }

            ]
            //这里面可以设在自带按钮的名字   和 自动关闭弹出框
        }).jqGrid("navGrid", "#pager",
            {search: false, addtext: "添加", edittext: "修改", deltext: "删除"},
            //修改
            {
                closeAfterEdit: true,//修改完关闭
                //添加后提交
                afterSubmit: function (response) {
                    //后台传来的map中的data属性,存放了对象的id
                    var id = response.responseJSON.bannerId;
                    console.log("id名" + id);
                    //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                    var code = response.responseJSON.status;
                    console.log("状态" + code);
                    //判断是否返回的是成功的标识
                    if (code == '200') {
                        //自定义的Ajax文件上传
                        $.ajaxFileUpload({
                            //上传到服务器上的地址
                            url: "${path}/banner/upload",
                            //文件上传空间的id属性 <input type="file" id="file" name="file" />
                            fileElementId: 'img',
                            //返回的值类型 一般为json
                            //dateType:'json',
                            //传输类型
                            //type:'post',
                            //值的传输
                            data: {id: id},
                            //处理值
                            success: function () {
                                $("#list").trigger("reloadGrid");
                                $("#di2").show();
                                setTimeout(function () {
                                    $("#di2").hide();
                                }, 3000)
                            }
                        });
                        //随意返回一个字符串
                        return response;
                    }
                }
            },
            //添加
            {
                closeAfterAdd: true,//添加完关闭
                //添加后提交
                afterSubmit: function (response) {
                    //后台传来的map中的data属性,存放了对象的id
                    var id = response.responseJSON.bannerId;
                    console.log("id名" + id);
                    //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                    var code = response.responseJSON.status;
                    console.log("状态" + code);
                    //判断是否返回的是成功的标识
                    if (code == '200') {
                        //自定义的Ajax文件上传
                        $.ajaxFileUpload({
                            //上传到服务器上的地址
                            url: "${path}/banner/upload1",
                            //文件上传空间的id属性 <input type="file" id="file" name="file" />
                            fileElementId: 'img',
                            //返回的值类型 一般为json
                            //dateType:'json',
                            //传输类型
                            //type:'post',
                            //值的传输
                            data: {id: id},
                            //处理值
                            success: function () {
                                $("#list").trigger("reloadGrid");
                                $("#di").show();
                                setTimeout(function () {
                                    $("#di").hide();
                                }, 3000)
                            }
                        });
                        //随意返回一个字符串
                        return "1000";
                    }
                }
                //自定义上传结束
            });

        //导出轮播图excle表格======================
        $("#dao").click(function () {
            location.href = "${path}/banner/excle"
        });

        //导入excle表格
        $("#tj").click(function () {
            var jQuery = $("#file").val();
            alert(jQuery)
            $.ajaxFileUpload({
                //上传到服务器上的地址
                url: "${path}/banner/inExcle",
                //文件上传空间的id属性 <input type="file" id="file" name="file" />
                fileElementId: 'file',
                //返回的值类型 一般为json
                //dateType:'json',
                //传输类型
                //type:'post',
                //值的传输
                //data:{id:id},
                //处理值
                success: function () {
                    $("#file").prop("value", "")
                }
            });

        })
    })
</script>
<div style="margin-top: -25px;">
    <h2>轮播图管理</h2>
</div>
<div class="panel panel-default" style="height: 500px;">
    <div class="panel-heading">

        <span></span>
        <form class="form-inline" id="ff" enctype="multipart/form-data">
            <label for="file" class="">轮播图列表</label>&nbsp;&nbsp;&nbsp;
            <label for="file" class=""><a href="javascript:void(0)" id="dao">导出轮播图excle表格</a></label>&nbsp;&nbsp;&nbsp;
            <label for="file" class="">导入轮播图excle表格：</label>
            <div class="form-group">
                <input type="file" class="form-horizontal" name="file" id="file">
            </div>
            <button type="button" id="tj" class="btn btn-default btn-info">提交</button>
        </form>
        <span></span>
    </div>
    <div class="panel-body">
        <table class="table table-striped" id="list">
        </table>
        <div id="pager" style="height: 50px"></div>
    </div>
</div>
<div id="di" style="color: red;display:none;">上传成功</div>
<div id="di2" style="color: red;display:none;">修改成功</div>
