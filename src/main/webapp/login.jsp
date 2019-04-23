<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>持明法州后台管理中心</title>
			
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
	<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="css/common.css" type="text/css"></link>
	<link rel="stylesheet" href="css/login.css" type="text/css"></link>
	<script type="text/javascript" src="script/jquery.js"></script>
	<script type="text/javascript" src="script/common.js"></script>
	<script type="text/javascript">
	
		$(function(){
			//点击更换验证码：
			$("#changeImage").click(function(){//点击更换验证码
				$("#changeImage").prop("src","${pageContext.request.contextPath}/user/getImage?time="+new Date().getTime())
			});
			
			//  form 表单提交
			$("#submit").click(function () {
                $.ajax({
                    type:"get",
                    data:"name="+$("#name").val()+"&password="+$("#pwd").val()+"&code="+$("#code").val(),
					url:"${pageContext.request.contextPath}/user/login",
                    dataType:"json",
                    success:function (data) {
                        console.log(data)
                        if(data.isOk){
                            alert(data.info)
                            location.href = "${pageContext.request.contextPath}/main/main1.jsp"
                        }else {
                            alert(data.info)
                        }
                    }
                })
            });
		})


	</script>
</head>
<body>
		<div class="login">
<%--location.href = "${pageContext.request.contextPath}/main/mian1.jsp"--%>
			<form id="loginForm"  method="post" >
				
				<table>
					<tbody>
						<tr>
							<td width="190" rowspan="2" align="center" valign="bottom">
								<img src="img/header_logo.gif" />
							</td>
							<th>
								用户名:
							</th>
							<td>
								<input id="name" type="text"  name="name" class="text" placeholder="输入用户名" maxlength="20"/>
							</td>
					  </tr>
					  <tr>
							<th>
								密&nbsp;&nbsp;&nbsp;码:
							</th>
							<td>
								<input id="pwd" type="password" name="password" class="text" placeholder="输入密码" maxlength="20" autocomplete="off"/>
							</td>
					  </tr>
					
						<tr>
							<td>&nbsp;</td>
							<th>验证码:</th>
							<td>
								<input type="text" id="code" name="code" class="text captcha" maxlength="4" autocomplete="off"/>
								<img id="changeImage" class="captchaImage" style="width: 100px;height: 30px" src="${pageContext.request.contextPath}/user/getImage" title="点击更换验证码"/>
							</td>
						</tr>					
					<tr>
						<td>
							&nbsp;
						</td>
						<th>
							&nbsp;
						</th>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<th>&nbsp;</th>
						<td>
							<input type="button" class="homeButton"><input id="submit" type="button" class="loginButton" value="登录">
						</td>
					</tr>
				</tbody>
				</table>
			</form>
				<div class="powered">COPYRIGHT © 2008-2017.</div>
				<div class="link">
					<a href="http://www.chimingfowang.com/">持名佛网首页</a> |
					<a href="http://www.chimingbbs.com/">交流论坛</a> |
					<a href="">关于我们</a> |
					<a href="">联系我们</a> |
					<a href="">授权查询</a>
				</div>

		</div>
</body>
</html>