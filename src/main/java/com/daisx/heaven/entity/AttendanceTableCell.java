package com.daisx.heaven.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AttendanceTableCell implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private LocalDate recordDate;

    /**
     * 点工-加班时间
     */
    private BigDecimal overWorkTimeHour = BigDecimal.ZERO;

    /**
     * 包工按天计-加班时间
     */
    private BigDecimal overWorkTimeContract = BigDecimal.ZERO;

    /**
     * 点工-工
     */
    private BigDecimal workNumberHour = BigDecimal.ZERO;

    /**
     * 包工按天计-工
     */
    private BigDecimal workNumberContract = BigDecimal.ZERO;

    /**
     * 借支-金额
     */
    private BigDecimal borrowing = BigDecimal.ZERO;

    /**
     * 结算-金额
     */
//    private BigDecimal settlement = BigDecimal.ZERO;

    /**
     * 包工按量计-金额
     */
    private BigDecimal contractQuantity = BigDecimal.ZERO;
}
