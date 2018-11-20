<%@page import="com.learn.util.A07ShopExportExcel4"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" import="java.util.HashMap" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
	response.setCharacterEncoding("UTF-8");
    List<Map<String,String>> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
        HashMap<String, String> map = new HashMap<>();
        map.put("xh","12");
        map.put("skrmc","15");
        map.put("khyh","15");
        map.put("yhzh","15");
        map.put("bxje","15");
        map.put("bxje","15");

        list.add(map);
    }
    response.reset();
    A07ShopExportExcel4 excel4 = new A07ShopExportExcel4("test",list);
    excel4.export(response);
	
%>

