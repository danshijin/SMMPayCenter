package com.smm.payCenter.controller.capitalData;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.payCenter.bo.SendMailBO;
import com.smm.payCenter.bo.SystemDataQueryBo;
import com.smm.payCenter.controller.PageUtils.PageBean;
import com.smm.payCenter.domain.SystemData;

/**
 * @author hanfeihu 系统数据查询
 */
@Controller
@RequestMapping("/capitalData")
public class SystemDataQueryController {

    private static Logger     logger = Logger.getLogger(SystemDataQueryController.class.getName());
    @Resource
    private SystemDataQueryBo systemDataQueryBo;
    @Resource
    private SendMailBO        sendMailBO;

    @RequestMapping("/systemDataQuery")
    public ModelAndView accountList(Integer pno, String companyName, String companyMail

    , String startTrDate, String endTrDate, String selectConditions, String startOperator, String startValue,
                                    String endOperator, String endValue) {
        ModelAndView mav = new ModelAndView();

        //参数
        Map<String, String> map = new HashMap<String, String>();
        map.put("companyName", companyName);
        map.put("companyMail", companyMail);
        map.put("startTrDate", startTrDate);
        map.put("endTrDate", endTrDate);
        map.put("selectConditions", selectConditions);
        map.put("startOperator", startOperator);
        map.put("startValue", startValue);
        map.put("endOperator", endOperator);
        map.put("endValue", endValue);
        try {
            int totalRecords = this.systemDataQueryBo.querySystemDataListTotalRecords(map);
            if (pno == null) {
                pno = 1;
            }
            PageBean page = new PageBean(20, pno, totalRecords);
            int startNum = page.getStartNum();
            int endNum = page.getEndNum();
            map.put("startNum", "" + startNum);
            map.put("endNum", "" + endNum);
            List<SystemData> listMap = systemDataQueryBo.querySystemDataList(map);
            mav.addObject("listMap", listMap);

            mav.addObject("totalRecords", totalRecords);
            mav.addObject("totalPage", page.getTotalPages());// 总页数
            mav.addObject("search", map);

            mav.setViewName("/capitalData/systemDataQuery");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return mav;
    }

    @RequestMapping("/systemDataExport")
    @ResponseBody
    public void systemDataExport(HttpServletRequest request, HttpServletResponse response, String companyName,
                                 String companyMail

    , String startTrDate, String endTrDate, String selectConditions, String startOperator, String startValue,
                                 String endOperator, String endValue) {

        response.setContentType("application/vnd.ms-excel");
        String codedFileName = null;
        OutputStream fOut = null;
        try {

            //参数
            Map<String, String> map = new HashMap<String, String>();
            map.put("companyName", companyName);
            map.put("companyMail", companyMail);
            map.put("startTrDate", startTrDate);
            map.put("endTrDate", endTrDate);
            map.put("selectConditions", selectConditions);
            map.put("startOperator", startOperator);
            map.put("startValue", startValue);
            map.put("endOperator", endOperator);
            map.put("endValue", endValue);
            List<SystemData> listMap = systemDataQueryBo.querySystemDataList(map);
            // 进行转码，使其支持中文文件名  
            codedFileName = java.net.URLEncoder.encode("系统数据", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            //  response.addHeader("Content-Disposition", "attachment;   filename=" + codedFileName + ".xls");
            // 产生工作簿对象  
            HSSFWorkbook workbook = new HSSFWorkbook();
            //产生工作表对象  
            Map<String, String> headr = new LinkedHashMap<String, String>();
            headr.put("trDate", "日期");
            headr.put("note", "交易备注");
            headr.put("companyName", "企业名称");
            headr.put("oppositCompanyName", "对方企业");
            headr.put("borrow", "借");
            headr.put("loan", "贷");
            headr.put("freezeMoney", "冻结金额");
            headr.put("userMoney", "可用账户余额");
            headr.put("totalMoney", "账户总余额");

            HSSFSheet sheet = workbook.createSheet();
            HSSFRow row1 = sheet.createRow(0);//从第二行开始
            int idx1 = 0;
            for (Map.Entry<String, String> entry : headr.entrySet()) {
                HSSFCell csell = row1.createCell(idx1);//创建一列
                csell.setCellType(HSSFCell.CELL_TYPE_STRING);
                csell.setCellValue("" + entry.getValue());
                idx1++;
            }
            //写入内容
            for (int i = 0; i < listMap.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);//从第二行开始
                SystemData sd = listMap.get(i);
                Class userCla = (Class) sd.getClass();
                int idx = 0;
                for (Map.Entry<String, String> entry : headr.entrySet()) {

                    String methodName = entry.getKey();
                    String first = methodName.substring(0, 1).toUpperCase();
                    String rest = methodName.substring(1, methodName.length());
                    methodName = new StringBuffer(first).append(rest).toString();

                    Method m1 = userCla.getDeclaredMethod("get" + methodName);
                    Object val = m1.invoke(sd);
                    if (val == null) {
                        val = "";
                    }
                    // System.out.println("name:" + f.getName() + "/t value = " + val);
                    HSSFCell cell1 = row.createCell(idx);//创建一列
                    cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell1.setCellValue("" + val);
                    idx++;
                }

            }
            fOut = response.getOutputStream();
            workbook.write(fOut);
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }

    }

}
