package com.daisx.heaven.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daisx.heaven.entity.AttendanceTableCell;
import com.daisx.heaven.entity.AttendanceTableVo;
import com.daisx.heaven.entity.CellStyleModel;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @Author: daisx
 * @Date: 2021/10/11 15:16
 */
public class EasyExcelUtil {

    /**
     * excel首列序号列样式
     * @param workbook
     * @return
     */
    public static CellStyle firstCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //文字
        Font font = workbook.createFont();
        font.setBold(Boolean.TRUE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 用于设置excel导出时的样式
     * easyexcel 导出样式
     * @return
     */
    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
//        headWriteCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)13);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setWrapped(true);
        //文字
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    public static void write(String name,List dataList,HttpServletResponse response, Class<T> tClass){
        ExcelWriter excelWriter = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和EasyExcel没有关系
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), tClass)
                    .registerWriteHandler(EasyExcelUtil.getHorizontalCellStyleStrategy())
                    .sheet(name)
                    .doWrite(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (excelWriter!=null){
                excelWriter.finish();
            }
        }
    }

    private static List<List<String>> getHeader(LocalDate date) {
        List<List<String>> header=new ArrayList<>();
        List<String> header0=new ArrayList<>();
        header0.add("工人");
        header.add(header0);
        List<String> header1=new ArrayList<>();
        header1.add("记工类型");
        header.add(header1);
        List<String> header2=new ArrayList<>();
        header2.add("本月总计");
        header.add(header2);
        LocalDate lastDay =date.with(TemporalAdjusters.lastDayOfMonth());
        for (int i = 1; i <= lastDay.getDayOfMonth(); i++) {
            List<String> headerN=new ArrayList<>();
            headerN.add(date.getMonthValue()+"月");
            headerN.add(i+"日");
            header.add(headerN);
        }
        return header;
    }

    public static void main(String[] args) throws Exception {
        String attendance="{\"workName\": \"先生\",\"totalOverWorkTimeHour\": 112,\"totalOverWorkTimeContract\": 0,\"totalWorkNumberHour\": 10.45,\"totalWorkNumberContract\": 0,\"borrowingTotal\": 350,\"contractQuantityTotal\": 0," +
                "\"cells\": [{\"recordDate\": \"2021-10-18\",\"overWorkTimeHour\": 0,\"overWorkTimeContract\": 20,\"workNumberHour\": 0,\"workNumberContract\": 0,\"borrowing\": 100,\"contractQuantity\": 0}," +
                            "{\"recordDate\": \"2021-10-08\",\"overWorkTimeHour\": 106,\"overWorkTimeContract\": 0,\"workNumberHour\": 10.26,\"workNumberContract\": 20,\"borrowing\": 0,\"contractQuantity\": 0}," +
                            "{\"recordDate\": \"2021-10-06\",\"overWorkTimeHour\": 0,\"overWorkTimeContract\": 20,\"workNumberHour\": 0,\"workNumberContract\": 0,\"borrowing\": 330,\"contractQuantity\": 0}," +
                            "{\"recordDate\": \"2021-10-11\",\"overWorkTimeHour\": 6,\"overWorkTimeContract\": 0,\"workNumberHour\": 0.19,\"workNumberContract\": 0,\"borrowing\": 220,\"contractQuantity\": 330}," +
                            "{\"recordDate\": \"2021-10-13\",\"overWorkTimeHour\": 0,\"overWorkTimeContract\": 0,\"workNumberHour\": 0,\"workNumberContract\": 0,\"borrowing\": 0,\"contractQuantity\": 0}," +
                            "{\"recordDate\": \"2021-10-19\",\"overWorkTimeHour\": 0,\"overWorkTimeContract\": 0,\"workNumberHour\": 0,\"workNumberContract\": 0,\"borrowing\": 250,\"contractQuantity\": 0}]}";
        String attendance1="{\"workName\": \"女士\",\"totalOverWorkTimeHour\": 312,\"totalOverWorkTimeContract\": 136,\"totalWorkNumberHour\": 1250.45,\"totalWorkNumberContract\": 560,\"borrowingTotal\": 2531350,\"contractQuantityTotal\": 0," +
                "\"cells\": [{\"recordDate\": \"2021-10-10\",\"overWorkTimeHour\": 0,\"overWorkTimeContract\": 0,\"workNumberHour\": 0,\"workNumberContract\": 0,\"borrowing\": 100,\"contractQuantity\": 0}," +
                "{\"recordDate\": \"2021-10-05\",\"overWorkTimeHour\": 106,\"overWorkTimeContract\": 0,\"workNumberHour\": 10.26,\"workNumberContract\": 0,\"borrowing\": 0,\"contractQuantity\": 0}," +
                "{\"recordDate\": \"2021-10-11\",\"overWorkTimeHour\": 0,\"overWorkTimeContract\": 20,\"workNumberHour\": 220,\"workNumberContract\": 0,\"borrowing\": 0,\"contractQuantity\": 230}," +
                "{\"recordDate\": \"2021-10-15\",\"overWorkTimeHour\": 6,\"overWorkTimeContract\": 40,\"workNumberHour\": 0.19,\"workNumberContract\": 0,\"borrowing\": 110,\"contractQuantity\":560}," +
                "{\"recordDate\": \"2021-10-13\",\"overWorkTimeHour\": 40,\"overWorkTimeContract\": 50,\"workNumberHour\": 0,\"workNumberContract\": 0,\"borrowing\": 110,\"contractQuantity\": 340}," +
                "{\"recordDate\": \"2021-10-07\",\"overWorkTimeHour\": 20,\"overWorkTimeContract\": 0,\"workNumberHour\": 30,\"workNumberContract\": 0,\"borrowing\": 250,\"contractQuantity\": 670}]}";


        AttendanceTableVo attendanceTableVo = JSONObject.parseObject(attendance, AttendanceTableVo.class);
        AttendanceTableVo attendanceTableVo1 = JSONObject.parseObject(attendance1, AttendanceTableVo.class);
        List<AttendanceTableVo> dataList=new ArrayList<>();
        dataList.add(attendanceTableVo);
        dataList.add(attendanceTableVo1);
        List<List<String>> header = getHeader(LocalDate.now());
        System.out.println(JSON.toJSON(header));
        List<List<String>> data=new ArrayList<>();

        int dayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        for (AttendanceTableVo tableVo : dataList) {
            List<AttendanceTableCell> cells = tableVo.getCells();

            //点工
            List<String> detail1= Arrays.asList(new String[dayOfMonth+3]);
            //工人名称
            detail1.set(0,tableVo.getWorkName());
            detail1.set(1,"点工");
            detail1.set(2,tableVo.getTotalWorkNumberHour()+"个工\n"+tableVo.getTotalOverWorkTimeHour()+"小时");
            for (AttendanceTableCell cell : cells) {
                if (BigDecimal.ZERO.compareTo(cell.getWorkNumberHour())==-1||BigDecimal.ZERO.compareTo(cell.getOverWorkTimeHour())==-1) {
                    int day = cell.getRecordDate().getDayOfMonth();
                    detail1.set(day + 2, cell.getWorkNumberHour() + "个工\n" + cell.getOverWorkTimeHour() + "小时");
                }
            }
            data.add(detail1);

            //包工-按量记
            List<String> detail2= Arrays.asList(new String[dayOfMonth+3]);
            //工人名称
            detail2.set(0,tableVo.getWorkName());
            detail2.set(1,"包工-按量记");
            detail2.set(2,tableVo.getContractQuantityTotal()+"元");
            for (AttendanceTableCell cell : cells) {
                if (BigDecimal.ZERO.compareTo(cell.getContractQuantity())==-1) {
                    int day = cell.getRecordDate().getDayOfMonth();
                    detail2.set(day + 2, cell.getContractQuantity() + "元");
                }
            }
            data.add(detail2);

            //包工-按天记
            List<String> detail3= Arrays.asList(new String[dayOfMonth+3]);
            //工人名称
            detail3.set(0,tableVo.getWorkName());
            detail3.set(1,"包工-按天记");
            detail3.set(2,tableVo.getTotalWorkNumberContract()+"个工\n"+tableVo.getTotalOverWorkTimeContract()+"小时");
            for (AttendanceTableCell cell : cells) {
                if (BigDecimal.ZERO.compareTo(cell.getWorkNumberContract())==-1||BigDecimal.ZERO.compareTo(cell.getOverWorkTimeContract())==-1) {
                    int day = cell.getRecordDate().getDayOfMonth();
                    detail3.set(day + 2, cell.getWorkNumberContract()+"个工\n"+cell.getOverWorkTimeContract()+"小时");
                }
            }
            data.add(detail3);

            //借支
            List<String> detail4= Arrays.asList(new String[dayOfMonth+3]);
            //工人名称
            detail4.set(0,tableVo.getWorkName());
            detail4.set(1,"借支");
            detail4.set(2,tableVo.getBorrowingTotal()+"元");
            for (AttendanceTableCell cell : cells) {
                if (BigDecimal.ZERO.compareTo(cell.getBorrowing())==-1) {
                    int day = cell.getRecordDate().getDayOfMonth();
                    detail4.set(day + 2, cell.getBorrowing() + "元");
                }
            }
            data.add(detail4);
        }


        int[] l={0};
        EasyExcel.write("F:\\easyExcel\\test.xlsx")
                .head(header).sheet("测试")
                // 这里放入动态头
                .registerWriteHandler(EasyExcelUtil.getHorizontalCellStyleStrategy())
                .registerWriteHandler(new ExcelFillCellMergeStrategy(1,l))
//                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(data);


    }


}

