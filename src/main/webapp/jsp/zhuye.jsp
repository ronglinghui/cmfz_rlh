<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>持明法州首页</title>
    <%-- bootstrap的核心css --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.css">
    <%-- jqGrid --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%-- jquery.js --%>
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <%-- bootstrap.js --%>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <%-- 国际化 --%>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%-- jqGrid核心js --%>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%--轮播图的依赖--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
    <%--自定义文件上传所需的依赖--%>
    <script type="text/javascript" src="../boot/js/ajaxfileupload.js"></script>
    <%--富文本所需要的依赖--%>
    <script type="text/javascript" src="../kindeditor/kindeditor-all-min.js"></script>
    <%--富文本所支持的中文--%>
    <script type="text/javascript" src="../kindeditor/lang/zh-CN.js"></script>
    <%--goeasy所需要的js--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/china.js"></script>
    <script type="text/javascript">
        //切换页面
        $(function () {
            //用户列表
            $("#yy").click(function () {
                $("#youce").load("user.jsp")
            })
            //用户列表
            $("#wen").click(function () {
                $("#youce").load("article.jsp")
            })
            //专辑和章节列表
            $("#zji").click(function () {
                $("#youce").load("album.jsp")
            })
            //轮播列表
            $("#lun").click(function () {
                $("#youce").load("banner.jsp")
            })
        })
    </script>
    <%--大图的样式--%>

    <%--goeasy网络js路径--%>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>

    <style type="text/css">
        #datu {
            border: 0px blue solid;
            margin-top: -30px;
            height: 460px;
        }

        #flash {
            width: 1200px;
            height: 535px;
            margin: 50px auto;
            position: relative;
        }

        #flash #play {
            width: 1200px;
            height: 535px;
            list-style: none;
            position: absolute;
            top: 0px;
            left: 0px;
        }

        #flash #play li {
            display: none;
            position: absolute;
            top: 0px;
            left: 0px;
        }

        #flash #play li img {
            float: left;
        }

        #button {
            position: absolute;
            bottom: 140px;
            left: 470px;
            list-style: none;
        }

        #button li {
            margin-left: 10px;
            float: left;
        }

        #button li div {
            width: 12px;
            height: 12px;
            background: #DDDDDD;
            border-radius: 6px;
            cursor: pointer;
        }

        #prev {
            width: 40px;
            height: 63px;
            background: url(images/beijing.png) 0px 0px;
            position: absolute;
            top: 190px;
            left: 10px;
            z-index: 1000;
        }

        #next {
            width: 40px;
            height: 63px;
            background: url(images/beijing.png) -40px 0px;
            position: absolute;
            top: 190px;
            right: 110px;
            z-index: 1000;
        }

        #prev:hover {
            background: url(images/beijing.png) 0px -62px;
        }

        #next:hover {
            background: url(images/beijing.png) -40px -62px;
        }


    </style>
</head>
<body>
<%--
  ====== 导航栏================================================================================================
 --%>
<nav class="navbar navbar-default" style="background-color:tephrosious">
    <!--导航栏铺满-->
    <div class="container-fluid">
        <a href="" class="navbar-brand">
            <span><strong>持明法州管理系统</strong></span>
        </a>
        <%--响应式布局--%>
        <button class="navbar-toggle collapsed" data-toggle="collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <div class=" navbar-right">
            <span class="navbar-brand" style="font-size:14px">欢迎：</span>
            <a href="" class="navbar-brand" style="font-size:14px">
                <span class="glyphicon glyphicon-hand-right"></span>
                退出登录
            </a>
        </div>
    </div>
</nav>
<%--
    ==============左侧面导航 手风琴==================================================================================
 --%>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title text-center">
                            <a role="button" id="hh" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <button class="btn btn-default btn-info col-sm-12" id="yy">
                                用户列表
                            </button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <button class="btn btn-default btn-info col-sm-12" id="wen">
                                文章列表
                            </button>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                专辑和章节管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <button class="btn btn-default btn-info col-sm-12" id="zji">
                                专辑和章节列表
                            </button>
                        </div>
                    </div>
                </div>
                <%--轮播图管理--%>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingfour">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapsefour" aria-expanded="false" aria-controls="collapseThree">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapsefour" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <button class="btn btn-default btn-info col-sm-12" id="lun">
                                轮播图列表
                            </button>
                        </div>
                    </div>
                </div>
                <%--轮播图管理结束--%>
            </div>
        </div>
        <%--
            ==========右侧视图=====================================================================================
            --%>
        <div class="col-sm-9 " id="youce">
            <%--欢迎页面--%>
            <div class="show" id="han">
                <%--视图--%>
                <div class="jumbotron">
                    <h2>欢迎来到持明法州管理系统</h2>
                </div>
                <%--轮播图--%>
                <div>
                    <div id="datu">
                        <div id="flash">
                            <div id="prev"></div>
                            <div id="next"></div>
                            <ul id="play">
                                <li style="display: block;"><img src="images/1.jpg" alt="" width="1105px"
                                                                 height="400px"/></li>
                                <li><img src="images/2.jpg" alt="" width="1105px" height="400px"/></li>
                                <li><img src="images/3.jpg" alt="" width="1105px" height="400px"/></li>
                                <li><img src="images/4.jpg" alt="" width="1105px" height="400px"/></li>
                                <li><img src="images/5.jpg" alt="" width="1105px" height="400px"/></li>
                                <li><img src="images/6.jpg" alt="" width="1105px" height="400px"/></li>
                                <li><img src="images/7.jpg" alt="" width="1105px" height="400px"/></li>
                                <li><img src="images/8.jpg" alt="" width="1105px" height="400px"/></li>
                            </ul>
                            <ul id="button">
                                <li>
                                    <div style="background: #A10000;"></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                                <li>
                                    <div></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <%--轮播图结束--%>
        </div>
    </div>
    <%--右侧结束--%>
</div>
<%--底层--%>
<div id="dd" style="background-color: #EEEEEE;height: 50px;">
    <!--导航栏铺满-->
    <p class="navbar-text" style="margin-left: 700px;">Signed in as <a href="#" class="navbar-link">Mark Otto</a></p>

</div>
<%--底层结束--%>
</body>
</html>