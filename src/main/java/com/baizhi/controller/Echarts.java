package com.baizhi.controller;

import com.baizhi.vo.MapDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@RequestMapping("echarts")
@Controller
public class Echarts {
    @RequestMapping("ditu")
    @ResponseBody
    public List<Map<String, Object>> echarts() {
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<MapDto> list = new ArrayList<>();
        list.add(new MapDto("北京", new Random().nextInt(1000)));
        list.add(new MapDto("上海", new Random().nextInt(1000)));
        list.add(new MapDto("天津", new Random().nextInt(1000)));
        list.add(new MapDto("山东", new Random().nextInt(1000)));
        list.add(new MapDto("山西", new Random().nextInt(1000)));
        for (MapDto mapDto : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", mapDto.getName());
            map.put("value", mapDto.getValue());
            list1.add(map);
        }
        return list1;
    }

}
