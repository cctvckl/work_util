package com.learn.controller;

import com.learn.util.A07ShopExportExcel4;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ExportController {



	@RequestMapping("/GetVehicleTrail1.do")
	public void getVehicleTrail(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	}
}
