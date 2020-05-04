<%--
  Created by IntelliJ IDEA.
  User: mariu
  Date: 4/4/2020
  Time: 7:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<form name="caesarForm" method="post" action="caesarServlet">
        Plain text: <input type="text" name="plainText"/> <br/>
        K: <input type="number" min="0" max="26" name="K"/> <br/>
        Key: <input type="text" name="key">
        <input type="submit" value="Submit"/>
</form>
</body>
</html>
