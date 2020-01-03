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
            url: "${path}/article/sybaseArticle",
            //增删改的数据传输路径
            editurl: "${path}/article/delArticle",
            //数据返回类型
            datatype: "json",
            //分页栏位
            pager: "#pager",
            //展示的数据
            rowNum: 3,
            //工具栏
            toolbar: [true, "top"],
            //用户可选择的展示数据的条数
            rowList: [3, 8, 10],
            //表格样式
            styleUI: "Bootstrap",
            //表格高度
            height: "280px",
            //展示总条数
            viewrecords: true,
            //设置表的名称
            caption: "文章数据表",
            //自动适应父容器
            autowidth: true,
            // 在表格第一列前面，加入复选框
            multiselect: true,
            //标题
            colNames: [
                "编号", "文章名", "作者", "内容", "上师编号", "上传时间", "状态", "操作"
            ],
            //对应的内容
            colModel: [   //editable:true 的含义是可编译
                {name: "id"},
                {name: "title", editable: true},
                {name: "author", editable: true},
                {name: "content", editable: true, height: "60px"},
                {name: "guru_Id", editable: true},
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
                        return "<button class='btn btn-default btn-info' style=\"color:#f60\" onclick=\"zhan('" + rows.id + "')\"><span class='glyphicon glyphicon-th-list'></span></button>" +
                            "&nbsp;&nbsp;&nbsp;" +
                            "<button class='btn btn-default btn-primary' style='color:#f60' onclick=\"upAd('" + rows.id + "')\"><span class='glyphicon glyphicon-pencil'></span></button>"
                    }
                }
            ]
            //这里面可以设在自带按钮的名字   和 自动关闭弹出框
        }).jqGrid("navGrid", "#pager",
            {search: false, add: false, edit: false, addtext: "添加", edittext: "修改", deltext: "删除"},
            //修改

            {   //修改完关闭
                closeAfterEdit: true,
            },
            //添加
            {   //添加完关闭
                closeAfterAdd: true,
            });
        //添加工具栏上的按钮
        $("#t_list").html("&nbsp;&nbsp;&nbsp;" + "<button class='btn btn-default btn-info' onclick='sel()' style=\"color:#f60\"><span class='glyphicon glyphicon-th-list'></span></button>" +
            "&nbsp;&nbsp;&nbsp;" +
            "<button class='btn btn-default btn-primary' onclick='upd()' style='color:#f60'><span class='glyphicon glyphicon-pencil'></span></button>"
        )
        /*$('#myModal').on('hidden.bs.modal', function () {
            // 关闭Dialog前移除编辑器
            KindEditor.remove('#content');
        });*/
    })
    //通过K.create函数的第二个参数，可以对编辑器进行配置
    var eidte = KindEditor.create("#content", {
        //设置宽度
        width: '570px',
        //设置高度
        height: '500px',
        //最小高度不能拉伸
        minHeight: 400,
        //不能拉伸  1是上下 2是都能拉
        resizeType: 1,
        //是否展示图片空间
        allowFileManager: true,
        //上传是后台接收名字
        filePostName: "img",
        //上传后台的路径
        uploadJson: "${path}/article/articleFile",
        //获取全部的图片路径
        fileManagerJson: "${path}/article/getAllImgs",
        //用于传输富文本的数据
        afterBlur: function () {
            this.sync();
        }
    });

    //添加
    function addArticle() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            datatype: "json",
            type: "post",
            data: $("#fd").serialize(),
            success: function (data) {
                $("#myModal").modal("toggle");
                $("#list").trigger("reloadGrid");
            }
        })
    }

    //通过ID查询数据
    function upAd(id) {
        var data = $("#list").getRowData(id);

        $("#id1").val(data.id);
        $("#title1").val(data.title);
        $("#author1").val(data.author);
        $("#guru_Id1").val(data.guru_Id);
        if (data.status == "显示") {
            $("#o11").prop("selected", "selected")
        } else {
            $("#o21").prop("selected", "selected")
        }

        var editor1 = KindEditor.create('#content1', {
            //设置宽度
            width: '570px',
            //指定上传文件参数名称
            filePostName: "img",
            //文件上传路径
            uploadJson: "${pageContext.request.contextPath}/article/articleFile",
            //文件获取路径
            fileManagerJson: "${pageContext.request.contextPath}/article/getAllImgs",
            //展示图片空空间
            allowFileManager: true,
            afterBlur: function () {
                this.sync();
            }
        });
        //清空之前富文本编辑器中保存内容
        KindEditor.html("#content1", data.content);
        //弹出模态框
        $("#myModal1").modal("show")
    }

    //修改
    function upArticle() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/updateArticle",
            datatype: "json",
            type: "post",
            data: $("#fd1").serialize(),
            success: function () {
                //关闭模态框
                $("#myModal1").modal("toggle");
                //刷新数据
                $("#list").trigger("reloadGrid");
            }
        });
    }

    //内容展示
    //通过ID查询数据
    function zhan(id) {
        var data = $("#list").getRowData(id);
        var editor1 = KindEditor.create('#content12', {
            //设置宽度
            width: '570px',
            //指定上传文件参数名称
            filePostName: "img",
            //文件上传路径
            uploadJson: "${pageContext.request.contextPath}/article/articleFile",
            //文件获取路径
            fileManagerJson: "${pageContext.request.contextPath}/article/getAllImgs",
            //展示图片空空间
            allowFileManager: true,
            afterBlur: function () {
                this.sync();
            }
        });
        //清空之前富文本编辑器中保存内容
        KindEditor.html("#content12", null);
        KindEditor.appendHtml("#content12", data.content);
        //弹出模态框
        $("#myModal12").modal("show")
    }
</script>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#" aria-controls="home" role="tab" data-toggle="tab">文章信息</a>
        </li>
        <li role="presentation"><a href="#" aria-controls="profile" data-toggle="modal" data-target="#myModal">添加文章</a>
        </li>
    </ul>
</div>
<table class="table table-striped" id="list" name="home">
</table>
<div id="pager" style="height: 50px"></div>
<div class="modal fade" id="myModal" data-backdrop="false">
    <div class="modal-dialog" style="width: 680px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章添加</h4>
            </div>
            <div class="modal-body">
                <%--form添加--%>
                <form class="form-horizontal" id="fd"><!-- 水平放置 -->
                    <div class="form-group">
                        <input type="hidden" id="id" name="title">
                        <label for="title" class="col-sm-2 control-label">文章名:</label>
                        <div class="col-sm-6">
                            <input type="text" id="title" name="title" class="form-control " placeholder="请输入文章名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="author" class="col-sm-2 control-label">作者:</label>
                        <div class="col-sm-6">
                            <input type="text" id="author" name="author" class="form-control " placeholder="请输入作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="guru_Id" class="col-sm-2 control-label">上师编号:</label>
                        <div class="col-sm-6">
                            <input type="text" id="guru_Id" name="guru_Id" class="form-control " placeholder="请输入上师编号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status" class="col-sm-2 control-label">状态:</label>
                        <div class="col-sm-3">
                            <select type="text" id="status" name="status" class="form-control ">
                                <option id="o1" value="显示">显示</option>
                                <option id="o2" value="不显示">不显示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="content" name="content" style="width:570px;height:300px;"></textarea>
                        </div>
                    </div>
                </form>
                <%--form添加结束--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addArticle();">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<%--用户修改===========--%>
<div class="modal fade" id="myModal1" data-backdrop="false">
    <div class="modal-dialog" style="width: 680px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章修改</h4>
            </div>
            <div class="modal-body">
                <%--form添加--%>
                <form class="form-horizontal" id="fd1"><!-- 水平放置 -->
                    <div class="form-group">
                        <input type="hidden" id="id1" name="id">
                        <label for="title" class="col-sm-2 control-label">文章名:</label>
                        <div class="col-sm-6">
                            <input type="text" id="title1" name="title" class="form-control " placeholder="请输入文章名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="author" class="col-sm-2 control-label">作者:</label>
                        <div class="col-sm-6">
                            <input type="text" id="author1" name="author" class="form-control " placeholder="请输入作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="guru_Id" class="col-sm-2 control-label">上师编号:</label>
                        <div class="col-sm-6">
                            <input type="text" id="guru_Id1" name="guru_Id" class="form-control " placeholder="请输入上师编号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status" class="col-sm-2 control-label">状态:</label>
                        <div class="col-sm-3">
                            <select type="text" id="status1" name="status" class="form-control ">
                                <option id="o11" value="显示">显示</option>
                                <option id="o21" value="不显示">不显示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="content1" name="content" style="width:570px;height:300px;"></textarea>
                        </div>
                    </div>
                </form>
                <%--form添加结束--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="upArticle();">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<%--用户修改结束========--%>

<%--信息展示===========--%>
<div class="modal fade" id="myModal12" data-backdrop="false">
    <div class="modal-dialog" style="width: 710px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">内容展示</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="col-sm-12">
                        <textarea id="content12" name="content" style="width:570px;height:300px;"></textarea>
                    </div>
                </div>
                <%--form添加结束--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<%--文本展示修改结束========--%>