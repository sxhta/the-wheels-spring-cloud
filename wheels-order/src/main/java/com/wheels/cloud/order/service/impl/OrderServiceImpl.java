package com.wheels.cloud.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.constant.SecurityConstants;
import com.sxhta.cloud.common.exception.CommonNullException;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.remote.domain.SysFile;
import com.sxhta.cloud.security.service.TokenService;
import com.sxhta.cloud.wheels.remote.domain.order.Order;
import com.sxhta.cloud.wheels.remote.domain.user.WheelsFrontUser;
import com.sxhta.cloud.wheels.remote.openfeign.user.FrontUserOpenFeign;
import com.sxhta.cloud.wheels.remote.response.order.*;
import com.sxhta.cloud.wheels.remote.vo.FrontUserCacheVo;
import com.sxhta.cloud.wheels.remote.vo.excel.PublicExportData;
import com.wheels.cloud.order.mapper.OrderMapper;
import com.wheels.cloud.order.request.OrderRequest;
import com.sxhta.cloud.wheels.remote.request.order.OrderSearchRequest;
import com.wheels.cloud.order.service.OrderInfoService;
import com.wheels.cloud.order.service.OrderService;
import jakarta.inject.Inject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 订单管理 服务实现类
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Value("${file.path}")
    private String localFilePath;
    //文件地址
    @Value("${file.domain}")
    private String storagePath;

    @Value("${file.prefix}")
    private String prefixPath;

    @Inject
    private TokenService<FrontUserCacheVo, WheelsFrontUser> tokenService;

    @Inject
    private OrderInfoService orderInfoService;

    @Inject
    private FrontUserOpenFeign frontUserOpenFeign;

    @Override
    public Boolean create(OrderRequest request) {
        final var entity = new Order();
        BeanUtils.copyProperties(request, entity);
        final var loginUser = tokenService.getLoginUser();
        final var createBy = loginUser.getUsername();
        entity.setCreateBy(createBy);
        return save(entity);
    }

    @Override
    public OrderResponse getInfoByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        final var response = new OrderResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public Boolean softDeleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        entity.setDeleteTime(LocalDateTime.now());
        return updateById(entity);
    }

    @Override
    public Boolean deleteByHash(String hash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        return removeById(entity);
    }

    @Override
    public Boolean updateEntity(OrderRequest request) {
        final var hash = request.getHash();
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.eq(Order::getHash, hash);
        final var entity = getOne(lqw);
        if (ObjectUtil.isNull(entity)) {
            throw new CommonNullException("");
        }
        BeanUtils.copyProperties(request, entity);
        return updateById(entity);
    }

    @Override
    public List<OrderResponse> getAdminList(OrderSearchRequest request) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.isNull(Order::getDeleteTime);

        final var entityList = list(lqw);
        final var responseList = new ArrayList<OrderResponse>();
        entityList.forEach(entity -> {
            final var response = new OrderResponse();
            BeanUtils.copyProperties(entity, response);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public List<OrderResponse> getFrontList(String userHash, Integer type) {//1已完成，2已取消
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.isNull(Order::getDeleteTime))
                .and(i -> i.eq(Order::getUserHash, userHash));
        if (ObjectUtil.isNotNull(type)) {
            if (type == 1) {
                lqw.and(i -> i.eq(Order::getOrderStatus, 4));
            }
            if (type == 2) {
                lqw.and(i -> i.eq(Order::getOrderStatus, 5));
            }
        }
        lqw.orderByDesc(Order::getCreateTime);
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order -> {
            final var response = new OrderResponse();
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            BeanUtils.copyProperties(orderInfo, response);
            response.setId(order.getId());
            response.setHash(order.getHash());
            response.setOrderStatus(order.getOrderStatus());
            response.setOrderType(order.getOrderType());
            response.setIsUrgent(order.getIsUrgent());
            response.setOrderAmount(order.getOrderAmount());
            //TODO: 车辆类型
            response.setCarType("车辆类型未写，请注意提醒！");
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public OrderInfoResponse getFrontInfo(String orderHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.isNull(Order::getDeleteTime))
                .and(i -> i.eq(Order::getHash, orderHash));
        final var order = getOne(lqw);
        if (ObjectUtil.isNull(order)) {
            throw new CommonNullException("该订单不存在！");
        }
        final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
        final var response = new OrderInfoResponse();
        BeanUtils.copyProperties(order, response);
        BeanUtils.copyProperties(orderInfo, response);
        response.setId(order.getId());
        response.setHash(order.getHash());

        //计算其他费用
        final var allOtherAmountRef = new AtomicReference<BigDecimal>();//总金额
        allOtherAmountRef.set(BigDecimal.ZERO);//设置总金额
        if (ObjectUtil.isNotNull(order.getUrgentAmount())) {
            allOtherAmountRef.set(allOtherAmountRef.get().add(order.getUrgentAmount()));
        }
        if (ObjectUtil.isNotNull(order.getOtherFee())) {
            allOtherAmountRef.set(allOtherAmountRef.get().add(order.getOtherFee()));
        }
        response.setOtherFee(allOtherAmountRef.get());
        response.setOrderCreateTime(order.getCreateTime());
        //TODO:优惠卷金额
        response.setCouponAmount(new BigDecimal(BigInteger.ZERO));
        //TODO:车辆类型
        response.setCarType("车辆类型");
        if (orderInfo.getIsHelpCall() == 1) {
            response.setUserName(orderInfo.getHelpName());
            response.setUserPhone(orderInfo.getHelpPhone());
            response.setSex(orderInfo.getHelpSex());
        }
        return response;
    }

    @Override
    public List<OrderExpectationResponse> getFrontExpectationList(String userHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.isNull(Order::getDeleteTime))
                .and(i -> i.eq(Order::getUserHash, userHash))
                .and(i -> i.in(Order::getOrderStatus, 2, 3));
        lqw.orderByDesc(Order::getOrderStatus);
        lqw.orderByDesc(Order::getAppointmentTime);
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderExpectationResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order -> {
            final var response = new OrderExpectationResponse();
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            BeanUtils.copyProperties(order, response);
            BeanUtils.copyProperties(orderInfo, response);
            response.setId(order.getId());
            response.setHash(order.getHash());
            response.setAppointmentTime(order.getAppointmentTime());
            if (orderInfo.getIsHelpCall() == 1) {
                response.setUserName(orderInfo.getHelpName());
                response.setUserPhone(orderInfo.getHelpPhone());
                response.setSex(orderInfo.getHelpSex());
            }
            //TODO:车辆管理
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public Double getFrontTotalMileage(String userHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and(i -> i.eq(Order::getUserHash, userHash));
        lqw.and(i -> i.in(Order::getOrderStatus, 4));
        final var orderList = list(lqw);
        if (CollUtil.isEmpty(orderList)) {
            return 0.00;
        }
        final var totalMileage = new AtomicReference<>(0.00);
        orderList.forEach(order -> {
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            totalMileage.updateAndGet(v -> v + Double.parseDouble(orderInfo.getMileage()));
        });
        return totalMileage.get();
    }

    @Override
    public List<OrderAdminResponse> getBackstageList(OrderSearchRequest request) throws ParseException {
        final var lqw = new LambdaQueryWrapper<Order>();
        if (StrUtil.isNotBlank(request.getOrderNo())) {
            lqw.and(i -> i.like(Order::getOrderNo, request.getOrderNo()));
        }
        if (ObjectUtil.isNotNull(request.getOrderStatus())) {
            lqw.and(i -> i.in(Order::getOrderStatus, request.getOrderStatus()));
        }
        if (ObjectUtil.isNotNull(request.getIsUrgent())) {
            lqw.and(i -> i.in(Order::getIsUrgent, request.getIsUrgent()));
        }
        if (ObjectUtil.isNotNull(request.getOrderType())) {
            lqw.and(i -> i.in(Order::getOrderType, request.getOrderType()));
        }
        if (StrUtil.isNotBlank(request.getPlaceOrderUserName())) {
            final var resData = frontUserOpenFeign.getHashListByUserName(request.getPlaceOrderUserName(), SecurityConstants.INNER);
            if (CollUtil.isNotEmpty(resData.getData())) {
                lqw.and(i -> i.in(Order::getUserHash, resData.getData()));
            }
        }
        if (StrUtil.isNotBlank(request.getPlaceOrderUserPhone())) {
            final var resData = frontUserOpenFeign.getHashListByUserPhone(request.getPlaceOrderUserPhone(), SecurityConstants.INNER);
            if (CollUtil.isNotEmpty(resData.getData())) {
                lqw.and(i -> i.in(Order::getUserHash, resData.getData()));
            }
        }
        if (StrUtil.isNotBlank(request.getReservationStartTime()) && StrUtil.isNotBlank(request.getReservationEndTime())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = sdf.parse(request.getReservationStartTime());
            Date dateEnd = sdf.parse(request.getReservationEndTime());
            lqw.and(consumer -> consumer.between(Order::getAppointmentTime, dateStart, dateEnd));
        }
        if (StrUtil.isNotBlank(request.getCreateStartTime()) && StrUtil.isNotBlank(request.getCreateEndTime())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = sdf.parse(request.getCreateStartTime());
            Date dateEnd = sdf.parse(request.getCreateEndTime());
            lqw.and(consumer -> consumer.between(Order::getCreateTime, dateStart, dateEnd));
        }
        lqw.orderByDesc(Order::getCreateTime);
        final var orderList = list(lqw);
        final var responseList = new ArrayList<OrderAdminResponse>();
        if (CollUtil.isEmpty(orderList)) {
            return responseList;
        }
        orderList.forEach(order -> {
            final var response = new OrderAdminResponse();
            BeanUtils.copyProperties(order,response);
            final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
            response.setDeparture(orderInfo.getDeparture());
            response.setDestination(orderInfo.getDestination());
            final var resData = frontUserOpenFeign.getInfoByHash(order.getUserHash(), SecurityConstants.INNER);
            response.setPlaceOrderUserName(resData.getData().getUserName());
            response.setPlaceOrderUserPhone(resData.getData().getAccount());
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public OrderAdminInfoResponse getBackstageInfo(String orderHash) {
        final var lqw = new LambdaQueryWrapper<Order>();
        lqw.and((i->i.eq(Order::getHash,orderHash)));
        final var order = getOne(lqw);
        if (ObjectUtil.isNull(order)) {
            throw new CommonNullException("该订单不存在！");
        }
        final var response = new OrderAdminInfoResponse();
        final var orderInfo = orderInfoService.getInfoByOrderHash(order.getHash());
        response.setDestination(orderInfo.getDestination());
        response.setDeparture(orderInfo.getDeparture());
        response.setMileage(orderInfo.getMileage());
        final var resData = frontUserOpenFeign.getInfoByHash(order.getUserHash(), SecurityConstants.INNER);
        response.setPlaceOrderUserName(resData.getData().getUserName());
        response.setPlaceOrderUserPhone(resData.getData().getAccount());
        if (orderInfo.getIsHelpCall() == 1) {
            response.setTravelersUserName(orderInfo.getHelpName());
            response.setTravelersUserPhone(orderInfo.getHelpPhone());
            response.setTravelersUserSex(orderInfo.getHelpSex());
        }
        response.setIsHelpCall(orderInfo.getIsHelpCall());
        if (orderInfo.getIsHelpCall() == 2) {
            response.setTravelersUserName(resData.getData().getUserName());
            response.setTravelersUserPhone(resData.getData().getAccount());
            response.setTravelersUserSex(resData.getData().getGender());
        }
        //TODO 优惠卷
        if (order.getIsUseCoupon()) {
            response.setCouponAmount(new BigDecimal("100"));
        }else {
            response.setCouponAmount(BigDecimal.ZERO);
        }
        //TODO 车主信息
        response.setOwnerUserName("车主姓名");
        response.setOwnerUserPhone("车主联系电话");
        response.setOwnerUserAvatar("车主头像");
        //TODO 车辆信息
        response.setCarType("车辆类型");
        BeanUtils.copyProperties(order,response);
        return response;
    }

    @Override
    public SysFile getBackstageExport(OrderSearchRequest request) {
//        String exportFileName = "出库单";
//        int[] mergeColumnIndex = {};
//        final var publicExportData = this.getOutboundOrderData(codeList.getCodeList());
//        final var myMergeStrategy = new OutboundOrderStrategy(publicExportData.getDataList(), publicExportData.getGroupDataList());
//        return this.getFileInfo(publicExportData, exportFileName, mergeColumnIndex, OutboundOrderExVo.class, myMergeStrategy);
        return null;
    }



//    private PublicExportData getOutboundOrderData(List<String> codeList) {
//        final var exportResponseList = new ArrayList<OutboundOrderExVo>();//数据
//        final var groupDataList = new ArrayList<Integer>();//组别
//
//        final var outStashLqw = new LambdaQueryWrapper<OutStash>();
//        if (CollUtil.isNotEmpty(codeList)) {
//            outStashLqw.in(OutStash::getOutStashCode, codeList);
//        }
//        outStashLqw.isNull(OutStash::getDeleteTime)
//                .orderByDesc(OutStash::getCreateName);
//        final var outStashList = outStashService.list(outStashLqw);
//        if (CollUtil.isEmpty(outStashList)) {
//            throw new ServiceException(ExceptionConstants.EXCEL_EXPORT);
//        }
//        outStashList.forEach(i -> {
//            var groupSize = 0;
//            final var user = remoteUserService.getUserInfoByUserId(Long.valueOf(i.getOutStashId()), SecurityConstants.INNER).getData();
//            final var outStashComponentLqw = new LambdaQueryWrapper<OutStashComponent>();
//            outStashComponentLqw.eq(OutStashComponent::getOutStashCode, i.getOutStashCode())
//                    .orderByDesc(OutStashComponent::getCreateTime);
//            final var outstashComponentList = outstashComponentService.list(outStashComponentLqw);
//            int sum = outstashComponentList.stream()
//                    .mapToInt(OutStashComponent::getOutStashNumber)
//                    .sum();
//            outstashComponentList.forEach(j -> {
//                final var exportResponse = new OutboundOrderExVo();
//                exportResponse.setDocumentId(i.getOutStashCode());
//                exportResponse.setOutboundPersonnel(user.getSysUser().getNickName().concat("(").concat(user.getSysUser().getJobNumber()).concat(")"));
//                exportResponse.setOutboundTime(i.getOutStashTime());
//                exportResponse.setActualOutboundTime(i.getActualTime());
//                switch (i.getStatus()) {//0:审核中、1:已出库、2:未提交出库申请、3:出库申请被驳回
//                    case 0 -> exportResponse.setStatus("审核中");
//                    case 1 -> exportResponse.setStatus("已出库");
//                    case 2 -> exportResponse.setStatus("未提交出库申请");
//                    case 3 -> exportResponse.setStatus("出库申请被驳回");
//                }
////                exportResponse.setOutboundAmount(BigDecimal.valueOf(i.getAmount()));
//                exportResponse.setOutboundQuantity(sum);
//                exportResponse.setRelatedMaterialRequestForm(i.getMaterielCode());
//                exportResponse.setMaterialNo(j.getPartsCode());
//                exportResponse.setName(plmFeignService.erpInfo(j.getPartsCode()));
//                exportResponse.setNumber(j.getOutStashNumber());
//                final var warehouseRelationInfo = warehouseRelationInfoService.getById(j.getArticleId());
//                exportResponse.setOutboundWarehouse(warehouseInfoService.getNameByCode(warehouseRelationInfo.getWarehouseCode()));
//                exportResponse.setOutboundItemNumber(warehouseRelationInfo.getLevelName());
//                exportResponseList.add(exportResponse);
//            });
//            groupSize = outstashComponentList.size();
//            groupDataList.add(groupSize);
//        });
//        final var publicExportData = new PublicExportData();
//        publicExportData.setDataList(exportResponseList);
//        publicExportData.setGroupDataList(groupDataList);
//        return publicExportData;
//    }
//
//    private <T> SysFile getFileInfo(
//            PublicExportData publicExportData, String exportFileName, int[] mergeColumnIndex, Class<T> dataTypeClass, AbstractMergeStrategy abstractMergeStrategy) {
//        final var sysFile = new SysFile();
//        int mergeRowIndex = 1;
//        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//        String directoryPath = localFilePath.concat("/excel/export/").concat(currentDate);
//        try {
//            Path directory = Paths.get(directoryPath);
//            if (!Files.exists(directory)) {
//                Files.createDirectories(directory);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String fileName = exportFileName.concat("导出-").concat(TimesUtils.getCurrentTimeString()).concat(".xlsx");
//        String rootPath = directoryPath.concat("/").concat(fileName);
//        EasyExcel.write(rootPath, dataTypeClass)
//                .autoCloseStream(Boolean.TRUE)
//                .registerWriteHandler(EasyExcelStyleUtils.getStyle())
//                .registerWriteHandler(abstractMergeStrategy)
//                .registerWriteHandler(new ExcelFillCellMergeStrategy(mergeRowIndex, mergeColumnIndex))
//                .sheet(exportFileName).doWrite(publicExportData.getDataList());
//        String url = storagePath.concat(prefixPath).concat("/excel/export/").concat(currentDate).concat("/").concat(fileName);
//        sysFile.setName(exportFileName);
//        sysFile.setUrl(url);
//        return sysFile;
//    }
}
