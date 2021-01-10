package cn.tedu.service.impl;

import cn.tedu.mapper.DemoMapper;
import cn.tedu.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    public DemoMapper demoMapper;

    public String getName(int id) {
        return demoMapper.getNameById(id);
    }
}
