package com.example.hos.service.impl;

import com.example.hos.dao.repository.ProductRepository;
import com.example.hos.dao.repository.SupplierRespository;
import com.example.hos.handle.HosException;
import com.example.hos.model.entity.Product;
import com.example.hos.model.entity.Supplier;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.model.vo.SupplierVO;
import com.example.hos.service.SupplierService;
import com.example.hos.until.Constant;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: 吃面龙
 * @Description:
 * @Date: 2021/4/5
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierRespository supplierRespository;

    @Resource
    private ProductRepository productRepository;

    @Override
    public ResultResponse addSupplier(SupplierVO supplierVO) {
        Optional<Supplier> bySname = supplierRespository.findBySname(supplierVO.getSname());
        if (bySname.isPresent()){
            throw new HosException(ErrorInfo.SUPPLIER_IS_EXIST.getMessage());
        }
        Supplier supplier = new Supplier();
        supplier.setAddress(supplierVO.getAddress());
        supplier.setTel(supplierVO.getTel());
        supplier.setStatus(Constant.STATUS);
        supplier.setSname(supplierVO.getSname());
        productRepository.findByPname(supplierVO.getPname()).orElseThrow(() -> new HosException(ErrorInfo.PRODUCT_NOT_FOUND.getMessage()));
        supplier.setPname(supplierVO.getPname());
        supplierRespository.saveAndFlush(supplier);
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
        Map<String, Supplier> productMap = supplierRespository.findAllByStatus(Constant.STATUS).stream().collect(Collectors.toMap(Supplier::getPname, Function.identity()));
        List<Product> tProducts = productRepository.findAllByStatus(Constant.STATUS);
        List<SupplierVO> supplierVOList = Lists.newArrayList();
        tProducts.forEach(product -> {
            Supplier supplier = productMap.get(product.getPname());
            if (ObjectUtils.allNotNull(supplier)){
                SupplierVO supplierVO = new SupplierVO();
                ProductVO productVO = new ProductVO();
                productVO.setPname(product.getPname());
                productVO.setRemark(product.getRemark());
                supplierVO.setProductVO(productVO);
                supplierVO.setAddress(supplier.getAddress());
                supplierVO.setSname(supplier.getSname());
                supplierVO.setTel(supplier.getTel());
                supplierVOList.add(supplierVO);
            }
        });
        PageInfo<SupplierVO> pageInfo = new PageInfo<>(supplierVOList);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }

    @Override
    public ResultResponse delSupplier(String sid) {
        Supplier supplier = supplierRespository.findBySidAndStatus(sid, Constant.STATUS)
                .orElseThrow(() -> new HosException(ErrorInfo.SUPPLIER_NOT_FOUND.getMessage()));
        supplier.setStatus(Constant.DEL_STATUS);
        supplierRespository.saveAndFlush(supplier);
        return null;
    }
}
