package com.example.hos.service.impl;

import com.example.hos.handle.HosException;
import com.example.hos.mapper.TProductMapper;
import com.example.hos.model.TProduct;
import com.example.hos.model.TProductExample;
import com.example.hos.model.type.ErrorInfo;
import com.example.hos.model.vo.ProductVO;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.service.ProductService;
import com.example.hos.service.ResponseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private TProductMapper productMapper;

    @Resource
    private ResponseService responseService;

    @Override
    public ResultResponse addProduct(ProductVO productVO) {
        ResultResponse resultResponse = new ResultResponse();
        Optional.ofNullable(productMapper.selectByName(productVO.getPName())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        TProduct product = new TProduct();
        product.setpName(productVO.getPName());
        product.setStatus("1");
        productMapper.insertSelective(product);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        return resultResponse;
    }

    @Override
    public ResultResponse delProduct(String pid) {
        ResultResponse resultResponse = new ResultResponse();
        TProduct product = Optional.ofNullable(productMapper.selectByIdAndStatus(pid, "1"))
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        product.setStatus("0");
        TProductExample tUserExample = new TProductExample();
        productMapper.updateByExampleSelective(product, tUserExample);
        return resultResponse.success(responseService.message(ResultResponse.Code.SUCCESS));
    }

    @Override
    public ResultResponse updateProduct(ProductVO productVO) {
        ResultResponse resultResponse = new ResultResponse();
        TProduct product = Optional.ofNullable(productMapper.selectByIdAndStatus(productVO.getPid(), "1"))
                .orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_NOT_FOUND.getMessage()));
        Optional.ofNullable(productMapper.selectByName(productVO.getPName())).orElseThrow(()->new HosException(ErrorInfo.ACCOUNT_IS_EXIST.getMessage()));
        if (StringUtils.isNotBlank(productVO.getPName())){
            product.setpName(productVO.getPName());
        }
        TProductExample tUserExample = new TProductExample();
        productMapper.updateByExampleSelective(product, tUserExample);
        String message = responseService.message(ResultResponse.Code.SUCCESS);
        resultResponse.success(message);
        return resultResponse;
    }

    @Override
    public ResultResponse selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(
                pageNum==null?1:pageNum,
                pageSize==null?2:pageSize);
        List<ProductVO> products = productMapper.selectAllAndStatus().stream().map(product -> {
            ProductVO productVO = new ProductVO();
            productVO.setPName(product.getpName());
            return productVO;
        }).collect(Collectors.toList());
        PageInfo<ProductVO> pageInfo = new PageInfo<>(products);
        ResultResponse response = new ResultResponse();
        response.setReturnData(pageInfo);
        return response;
    }
}
