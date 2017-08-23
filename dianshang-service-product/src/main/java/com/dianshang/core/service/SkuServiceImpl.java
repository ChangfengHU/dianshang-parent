package com.dianshang.core.service;

import com.dianshang.core.dao.SkuDAO;
import com.dianshang.core.pojo.Sku;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/23 0023 15:51.
 */
@Service("skuService")
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuDAO skuDAO;
    @Override
    public List<Sku> findByProductId(String productId) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("productId", productId);
        List<Sku> skus = skuDAO.selectByExample(example);
        System.err.println("远程调用Sku"+skus.size());
        return skus;
    }

    @Override
    public int update(Sku sku) {
        int i =  skuDAO.updateByPrimaryKeySelective(sku);
        return i;
    }
}
