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
            url: "${path}/album/sybaseAlbum",
            //增删改的数据传输路径
            editurl: "${path}/album/zong",
            //数据返回类型
            datatype: "json",
            //第一列展示行号
            rownumbers: true,
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
            caption: "专辑管理",
            //自动适应父容器
            autowidth: true,
            multiselect: true,// 在表格第一列前面，加入复选框
            //标题
            colNames: [
                "编号", "专辑名", "图片", "分数", "作者", "播音员", "集数", "简介", "上传时间", "状态", "操作"
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
                {name: "score"},
                {name: "author", editable: true},
                {name: "broadcaster", editable: true},
                {name: "count"},
                {name: "brief", editable: true},
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
            ],
            //开启子表格
            subGrid: true,
            //书写添加子表格的函数   subGridId:当前行的id  albumId：数据的id
            subGridRowExpanded: function (subGridId, albumId) {
                //调用添加子表格的函数
                //console.log(subGridId);
                //console.log(albumId);
                addSubGrid(subGridId, albumId)
            }
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
                            url: "${path}/ablum/albumFile",
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
                            url: "${path}/ablum/albumFile",
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
    })

    //添加的函数====subGridId:当前行的id  albumId：数据的id====================================================
    function addSubGrid(subGridId, albumId) {
        //创建动态table的id
        var tableId = subGridId + "table";
        //创建分页div的id
        var divId = subGridId + "div";
        //创建标签 添加到
        $("#" + subGridId).html("<table id='" + tableId + "'></table>" +
            "<div id='" + divId + "' style='height: 50px;'></div>");
        //创建单元格
        $("#" + tableId).jqGrid({
            //加载数据的路径
            url: "${path}/chapter/sybaseChapter?id=" + albumId,
            //增删改的数据传输路径
            editurl: "${path}/chapter/zong?album_Id=" + albumId,
            //数据返回类型
            datatype: "json",
            //分页栏位
            pager: "#" + divId,
            //添加工具栏
            toolbar: [true, "top"],
            //展示的数据
            rowNum: 3,
            //用户可选择的展示数据的条数
            rowList: [3, 8, 10],
            //表格样式
            styleUI: "Bootstrap",
            //第一列展示行号
            rownumbers: true,
            //表格高度
            height: "200px",
            //展示总条数
            viewrecords: true,
            //设置表的名称
            caption: "章节",
            //自动适应父容器
            autowidth: true,
            multiselect: true,// 在表格第一列前面，加入复选框
            //标题
            colNames: [
                "编号", "章节名", "专辑id", "大小", "时间", "路径", "状态"
            ],
            //对应的内容
            colModel: [   //editable:true 的含义是可编译
                {name: "id"},
                {name: "title", editable: true},
                {name: "album_Id"},
                {name: "size", width: 100},
                {name: "duration", width: 100},
                {
                    name: "src",
                    editable: true,
                    width: 400,
                    editable: true,
                    edittype: 'file',
                    formatter: function (value, option, rows) {
                        console.log("option" + option.id);
                        console.log("rows" + rows.id);
                        return "<audio width=\"200px\" controls loop id=\"+rows.id+\" src=\"${path}/albumFile/" + value + "\" type=\"audio/mpeg\">\n" +
                            // "  <source width=\"200px\" src=\"${path}/albumFile/"+value+"\" type=\"audio/mpeg\">\n" +
                            "</audio>";
                    }
                },
                {
                    name: "status", width: 100, editable: true, edittype: "select", editoptions: {
                        value: "显示:显示;不显示:不显示"
                    }
                }
            ]
        }).jqGrid("navGrid", "#" + divId, {search: false, addtext: "添加", edittext: "修改", deltext: "删除"},
            {
                //控制修改
                closeAfterEdit: true,
                //修改后
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${path}/chapter/up",//用于文件上传的服务器端请求地址
                        fileElementId: 'src',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                        //dataType : 'json',       //返回值类型 一般设置为json
                        type: 'POST',
                        //值的传输
                        data: {id: id},
                        success: function () {
                            //添加完刷新数据
                            $("#" + tableId).trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {
                //控制添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.bannerId;
                    //自定义的Ajax文件上传
                    $.ajaxFileUpload({
                        //上传到服务器上的地址
                        url: "${path}/chapter/up?id1=" + albumId,
                        //文件上传空间的id属性 <input type="file" id="file" name="file" />
                        fileElementId: 'src',
                        //返回的值类型 一般为json
                        //dateType:'json',
                        //传输类型
                        type: 'post',
                        //值的传输
                        data: {id: id},
                        //处理值
                        success: function () {
                            $("#" + tableId).trigger("reloadGrid");
                            $("#list").trigger("reloadGrid");
                        }
                    });
                    //随意返回一个字符串
                    return "1000";
                }
            },
            {   //删除控件
                afterComplete: function () {
                    $("#list").trigger("reloadGrid");
                }
            });
        //添加按钮
        $("#t_" + tableId).html("<button class='btn btn-info' onclick=\"play('" + tableId + "')\">播放 <span class='glyphicon glyphicon-play'></span></button>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<button class='btn btn-info'onclick=\"down('" + tableId + "')\">下载 <span class='glyphicon glyphicon-arrow-down'></span></button>"
        )
    }

    //创建播放的事件
    function play(tableId) {
        //获取数据的id
        var id = $("#" + tableId).jqGrid("getGridParam", "selrow");
        //判断id是否存在
        if (id == null) {
            alert("请选者你要播放的音频")
        } else {
            //1.请求后台
            //2.jqgrid 提供的方法 根据id拿到对应的值
            var data = $("#" + tableId).jqGrid('getRowData', id);
            //正则表达式获取值
            /*var pattern = /\/\w{4}_\w{3}\/\w*\/.*\.mp3/ig;
            var match = data.src.match(pattern);     ; */

            var ad = $(data.src)[0];
            var src = $(ad).prop("src");
            alert(src)
            $("#myModal").modal("show")
            $("#ado").prop("src", src);


        }
    }

    //创建下载的事件
    function down(tableId) {
        //获取数据的id
        var id = $("#" + tableId).jqGrid("getGridParam", "selrow");
        //判断id是否存在
        if (id == null) {
            alert("请选者你要下载的音频")
        } else {
            //1.请求后台
            //2.jqgrid 提供的方法 根据id拿到对应的值
            var data = $("#" + tableId).jqGrid('getRowData', id);
            //正则表达式获取路径
            /*var pattern = /\/\w{4}_\w{3}\/\w*\/.*\.mp3/ig;
            var match = data.src.match(pattern);     ; */
            //获取audiojs对象
            var ad = $(data.src)[0];
            var src = $(ad).prop("src");
            location.href = "${path}/chapter/downFile?path=" + src
        }
    }
</script>
<div style="margin-top: -25px;">
    <h2>专辑和章节管理</h2>
</div>
<div class="panel panel-default" style="height: 500px;">
    <div class="panel-heading">专辑和章节列表</div>
    <div class="panel-body">
        <table class="table table-striped" id="list">
        </table>
        <div id="pager" style="height: 50px"></div>
    </div>
</div>
<div id="di" style="color: red;display:none;">上传成功</div>
<div id="di2" style="color: red;display:none;">修改成功</div>


<div class="modal fade" id="myModal" data-backdrop="false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">               <!-- data-dismiss="modal"退出模态框 --->
                <!-- 叉号 -->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">播放音乐</h4>
            </div>
            <div class="modal-body">
                <audio controls loop id="ado" src="" type="audio/mpeg">
                </audio>
            </div>
            <div class="modal-footer">                          <!-- data-dismiss="modal" 关闭模态框 -->
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <!-- btn btn-primary 按钮激活状态 -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"
                        data-dismiss="modal">保存
                </button>
            </div>
        </div>
    </div>
</div>



