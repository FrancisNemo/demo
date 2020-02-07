package com.example.demo.service;

import com.example.demo.exception.DemoException;
import com.example.demo.model.DirMgtPO;
import com.example.demo.model.DirMgtPOExample;
import com.example.demo.repository.DirMgtPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OSSService {

    @Autowired
    public DirMgtPOMapper dirMgtPOMapper;

    public void save(String name) throws DemoException {
        DirMgtPO dirMgtPO = new DirMgtPO();
        dirMgtPO.setName(name);
        int ret = dirMgtPOMapper.insert(dirMgtPO);
        if(ret <= 0){
            throw new DemoException("save failed");
        }
    }

    public DirMgtPO get(String name) {
        DirMgtPOExample  example = new DirMgtPOExample();
        example.createCriteria().andNameEqualTo(name);
        List<DirMgtPO> dirMgtPOList = dirMgtPOMapper.selectByExample(example);
        if(dirMgtPOList == null || dirMgtPOList.size() == 0){
            return null;
        }
        return dirMgtPOList.get(0);
    }
}
