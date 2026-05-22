<%@tag description="Head" pageEncoding="UTF-8" isELIgnored="false" import="javax.servlet.jsp.PageContext,com.base2.kagura.example.javascript.*" %>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />
<!-- Le styles -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/lib/kagura.css" rel="stylesheet" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/2.0.1/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
<!-- HTML5 shim, for IE8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
<![endif]-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/spin.js/1.3.3/spin.min.js"></script>
<script language="JavaScript">
    var server_base = "<%= Utils.serverPath(request) %>";
</script>
