<%@tag description="Head" pageEncoding="UTF-8" isELIgnored="false" import="javax.servlet.jsp.PageContext,com.base2.kagura.example.javascript.*" %>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />
<!-- Le styles -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-MY3lqAyZWEjvYefTFTBYUN85pnmwAxdSjsX+8IqCP1N5VBzOIX6GNo1rUt9gz74C" crossorigin="anonymous" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-responsive.min.css" rel="stylesheet" integrity="sha384-F6SkVapMeW8omBv9cyqV+15oQ6VSZ+g9B3dXePPOKKVGEdFOfLo50I4anpVkG3kQ" crossorigin="anonymous" />
<link href="${pageContext.request.contextPath}/lib/kagura.css" rel="stylesheet" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/2.0.1/css/bootstrap-datetimepicker.min.css" rel="stylesheet" integrity="sha384-Hp9oUQcw85WfhhrGK4It01YI3bvWXwYEtX/OW7fpLzirr6Q28cfRwq8F/K712yW2" crossorigin="anonymous" />
<!-- HTML5 shim, for IE8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" integrity="sha384-qFIkRsVO/J5orlMvxK1sgAt2FXT67og+NyFTITYzvbIP1IJavVEKZM7YWczXkwpB" crossorigin="anonymous"></script>
<![endif]-->
<script language="JavaScript">
    var server_base = "<%= Utils.serverPath(request) %>";
</script>
