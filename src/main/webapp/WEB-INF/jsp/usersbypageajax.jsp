<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 用于格式化时间 -->
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <script type="text/javascript">
        /*进入添加商品的页面*/
        function adduser(){
            window.location.href="${pageContext.request.contextPath}/adduser";//get
        }
    </script>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/reset.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/base.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/list.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/bright.css" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/addBook.css" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/kkpage.css" />
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/kkpage.js"></script>
    <title></title>
    <style type="text/css">
        #batchdelpro {
            background-color: orangered;
            width: 150px;
            height: 48px;
            margin-top: 15px;
            margin-left: 25px;
            font-size: 18px;
        }
    </style>

    <script type="text/javascript">
        $(function(){
            $("#checkAll").click(function(){
                if($(this).prop("checked")){
                    $("input[type='checkbox']").not(this).prop("checked",true);
                }else{
                    $("input[type='checkbox']").not(this).prop("checked",false);
                }
            });

            $("#batchdelusers").click(function(){
                var ids = new Array(); //定义一个数组存储id
                $("input[name='id']:checked").each(function() {
                    ids.push($(this).val()); // 把值push进入数组里面
                });
                if(ids.length == 0){
                    alert('请选择至少一条记录删除');
                    return;
                }
                location.href="${pageContext.request.contextPath}/batchdelusers?ids="+ids;//get
            });
        });
    </script>
</head>

<body>

<!-- 判断否登录  登录成功会将users保存到session ,如果没有登录 session中的users为空 -->
<!--UsersController.java中的登录中的登录成功保存用户信息到session的代码
session.setAttribute("users", users); -->
<c:if test="${sessionScope.users == null }">
    <!--  没有登录，重新去登录页面登录  对应UsersController.java中的
    @RequestMapping(value="/login",method=RequestMethod.GET)-->
    <c:redirect url="login"></c:redirect>
</c:if>
<div id="brall">
    <div id="nav">
        <p>用户管理>用户列表</p>
    </div>
    <div id="condition" style="text-align: center">
        <form id="myform">
            用户名称：<input name="uname" id="uname" >
            角色类型:<select name="roleid" id="roleid">
            <option value="-1">请选择</option>
            <c:forEach items="${sessionScope.rolelist}" var="role">
                <option value="${role.id}"
                        <c:if test="${role.id==roleid}">
                            selected="selected"
                        </c:if>>
                        ${role.rolename}
                </option>
            </c:forEach>
        </select>
            <input id="search" type="button" value="查询">
        </form>
    </div>
    <br>
    <div id="table">
        <div id="top">
            <input type="button" class="btn btn-warning" id="batchdelusers" value="批量删除">
            <input type="button" class="btn btn-warning" id="btn1" value="新增用户" onclick="adduser()">
        </div>
        <!--显示没有分页的用户信息-->
        <div id="middle">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th style="width: 50px;text-align: center;">
                        <input type="checkbox" style="width: 20px;height: 20px;" id="checkAll">
                    </th>
                    <th>账号</th>
                    <th>部门</th>
                    <th>真实姓名</th>
                    <th>角色</th>
                    <th>图片</th>
                    <th>操作</th>
                </tr>
                </thead>
                <!-- 动态生成数据，做数据的显示的局部刷新 -->
                <tbody id="userslist"></tbody>
            </table>
            <!--分页栏 动态生成-->
            <div id="kkpager"></div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //修改函数
    function modify(id) {
        location.href = "${pageContext.request.contextPath}/getuserbyid?id="+id;//get
    }
    //删除函数
    $(document).ready(function(){
        $(document).on("click",".del",function(){
            var id = $(this).attr("name");
            if(confirm("确定要删除吗？")){
                location.href="${pageContext.request.contextPath}/deluser?id="+id;//get
            }
        });
    });

    //分页的js代码
    //第一次进入页面默认显示第一页的数据
    var currentPage = 1;
    //查询
    function loadData(page) {
        currentPage = page;
        //获取查询的参数
        //商品类型的ID值
        //var roleid = $("#roleid").val();
        var roleid = $('#roleid option:selected').val();
        if(roleid == ''){
            roleid = -1;
        }
        //商品类型的名称值
        var uname = $("#uname").val();
        //异步加载数据 参数的格式为json格式的参数{currentPage:page} 参数的名称currentPage 参数的值page
        //在springmvc中使用Map集合接受参数，还需要注解@RequestParam
        //数据返回的对象名称为data,名称可以自定义，返回的格式有时json
        $.post('${pageContext.request.contextPath}/userslistajax', {currentPage:page,roleid:roleid,uname:uname}, function(data){
            //先清除前一步的数据tbody
            $("#userslist").html("");
            //先清除前一步的分页div
            $("#kkpager").html("");
            //遍历数据 生成动态的数据 附加到tbody里面去 ，data就是我们的分页的实体类PageBean转换后的Map集合,list键就是数据
            if (data.list != null && data.list.length > 0) {
                for (var i = 0; i < data.list.length; i++) {
                    //将数据动态的附加到<tbody id="producttypelist"></tbody>
                    $("#userslist").append("<tr>"+
                        "<td style='width: 50px;text-align: center;'> <input type='checkbox' name='id' value='"+data.list[i].uid+"' style='width: 20px;height: 20px;'></td>"+
                        "<td>"+data.list[i].uname+"</td>"+
                        "<td>"+data.list[i].udepartment+"</td>"+
                        "<td>"+data.list[i].realname+"</td>"+
                        "<td>"+data.list[i].rolename+"</td>"+
                        "<td><img width='55px' height='45px' src='${pageContext.request.contextPath}/resources/image_user/"+data.list[i].uimage+"'></td>"+
                        "<td><button type='button' class='btn btn-info myupdate' onclick=location.href='${pageContext.request.contextPath}/getuserbyid?id="+data.list[i].uid+"'>修改</button>"+
                        "<button type='button' class='btn btn-warning del' name="+data.list[i].uid+">删除 </a>"+
                        "</td></tr>");
                }
            }
            //分页脚标 ：data.pageSize每页显示数， data.pageCount总的页数， data.rowCount总的行数
            createPageInfo(page,data.pageSize,data.pageCount,data.rowCount,goToPage);
        },"json" );
    }

    function goToPage(n){
        loadData(n);
    }

    //载入 (默认加载全部) 默认第一次为currentPage为 1
    loadData(currentPage);

    //init
    function createPageInfo(currentPage,pageSize,pageCount,recordCount,callbackFunction){

        var totalPage = pageCount;
        var totalRecords = recordCount;
        var pageNo = currentPage;
        if(!pageNo){
            pageNo = 1;
        }

        $("#kkpager").html("");


        //生成分页
        //有些参数是可选的，比如lang，若不传有默认值
        kkpager.inited = false;
        kkpager.generPageHtml({
            pno : pageNo,
            //总页码
            total : totalPage,
            mode : 'click',
            //总数据条数
            totalRecords : totalRecords,
            click : function(n){
                //这里可以做自已的处理
                //处理完后可以手动条用selectPage进行页码选中切换
                callbackFunction(n);
                kkpager.selectPage(n)
            }
        });
    }

    //查询按钮的提交
    $("#search").bind("click",function(){
        loadData(currentPage);
    });
</script>
</html>