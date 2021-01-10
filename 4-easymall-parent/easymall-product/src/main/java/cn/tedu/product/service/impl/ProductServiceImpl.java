package cn.tedu.product.service.impl;

import cn.tedu.product.mapper.ProductMapper;
import cn.tedu.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.pojo.Product;
import common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired(required = false)
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate template;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public EasyUIResult queryByPage(Integer page, Integer rows) {
        //EasyUIResult
        /* total 查询总数 商品总数量
           rows List<Product> 表示分页查询结果
         */
        EasyUIResult result = new EasyUIResult();
        int total = productMapper.selectCount();
        result.setTotal(total);

        int start = (page - 1) * rows;
        List<Product> pList = productMapper.selectProductByPage(start, rows);
        result.setRows(pList);
        return result;
    }

    @Override
    public Product queryOneProduct(String productId) {
        try {
            //redis缓存逻辑 被动缓存
            String productKey = "product_query_" + productId;
            ValueOperations<String, String> opsForValue = template.opsForValue();
            if (template.hasKey(productKey)) {
                //查到命中商品
                String productJson = opsForValue.get(productKey);
                Product product = mapper.readValue(productJson, Product.class);
                return product;
            } else {
                Product product = productMapper.selectProductById(productId);
                String productJson = mapper.writeValueAsString(product);
                opsForValue.set(productKey, productJson, 12, TimeUnit.HOURS);
                return product;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addProduct(Product product) {
        //product数据不完整
        //Universally Unique Identifier 通用标识唯一码
        //在一个服务器中,生成uuid每次都不一样
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        try {
            //redis缓存逻辑 主动缓存
            ValueOperations<String, String> opsForValue = template.opsForValue();
            String productKey = "product_query_" + productId;
            String productJson = mapper.writeValueAsString(product);
            opsForValue.set(productKey, productJson, 12, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        productMapper.insertProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        try {
            //redis缓存逻辑 主动缓存
            ValueOperations<String, String> opsForValue = template.opsForValue();
            String productKey = "product_query_" + product.getProductId();
            String productJson = mapper.writeValueAsString(product);
            opsForValue.set(productKey, productJson, 12, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        productMapper.updateProduct(product);
    }
}
