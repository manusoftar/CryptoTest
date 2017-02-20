<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="ar.com.manusoftar.CryptoTest.Security" %><%--
  Created by IntelliJ IDEA.
  User: Manusoftar
  Date: 18/02/2017
  Time: 11:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String dato = request.getParameter("entrada");
    if (null != dato){
        session.setAttribute("entrada", dato);
        session.setAttribute("clave", request.getParameter("clave"));
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    Enumeration<String> params = session.getAttributeNames();
    Map<String,String> mapaValores = new HashMap<String,String>();
    mapaValores.clear();
    while (params.hasMoreElements()) {
        String param = params.nextElement();
        System.out.println(param + " => " + session.getAttribute(param));
        if (null != session.getAttribute(param)) {
            mapaValores.put(param, (String)session.getAttribute(param));
        }
    }
    String desenc = "";
    if (!mapaValores.isEmpty()){
        desenc = Security.decrypt(mapaValores.get("entrada"), mapaValores.get("clave"));
    }
%>

<html>
  <head>
      <meta http-equiv="Expires"       content="Sat, 01 Dec 2017 00:00:00 GMT" />
      <meta http-equiv="pragma"        content="no-cache" />
      <meta http-equiv="Cache-Control" content="no-cache" />
    <title>CryptoTest</title>
  </head>
  <body>

      <%="Entrada => " + mapaValores.get("entrada")

      %>
      <form method="post" action="<%=request.getContextPath()%>/index.jsp">
          Texto encriptado:<input type="text" id="entrada" name="entrada" style="width:50%;"/><br>
          Clave: <input type="password" id="clave" name="clave" /><br>
          <button value="Desencriptar" id="desenc" name="desenc" onclick="">Desencriptar</button><br><br>
          Texto desencriptado:<textarea id="salida" name="salida" style="width:50%;"><%= desenc %></textarea>
      </form>
  </body>
</html>
