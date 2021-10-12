package com.daisx.heaven.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author daisx
 * @date 2021/7/27 17:27
 */
@Data
public class AttendanceTableVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String workName;

    /**
     * 本月总计-点工-加班时间
     */
    private BigDecimal totalOverWorkTimeHour = BigDecimal.ZERO;

    /**
     * 本月总计-包工按天计-加班时间
     */
    private BigDecimal totalOverWorkTimeContract = BigDecimal.ZERO;

    /**
     * 本月总计-点工-工
     */
    private BigDecimal totalWorkNumberHour = BigDecimal.ZERO;

    /**
     * 本月总计-包工按天计-工
     */
    private BigDecimal totalWorkNumberContract = BigDecimal.ZERO;

    /**
     * 本月总计-借支-笔
     */
    private BigDecimal borrowingTotal = BigDecimal.ZERO;

    /**
     * 本月总计-结算-金额
     */
//    private BigDecimal settlementTotal = BigDecimal.ZERO;

    /**
     * 本月总计-包工按量计-金额
     */
    private BigDecimal contractQuantityTotal = BigDecimal.ZERO;


    /**
     * 本月每日
     */
    private List<AttendanceTableCell> cells;
}
