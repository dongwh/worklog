package com.drpeng.worklog.util;

import com.csvreader.CsvReader;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaoyp on 2016/5/20.
 */
public class CSVUtil {

    /**
     * 读取csv格式
     * @param fileInpuStream
     * @param isReadTitle
     * @param charset
     * @return
     */
    public static List<String[]> readFile(InputStream fileInpuStream, boolean isReadTitle, String charset) {
        List<String[]> result = new ArrayList<String[]>();
        CsvReader reader = null;
        try {
            //初始化CsvReader并指定列分隔符和字符编码
            // reader = new CsvReader(filePath, ',', Charset.forName(charset));
            reader  = new CsvReader(fileInpuStream,',', Charset.forName(charset));
            int i = 0;
            while (reader.readRecord()) {
                //读取每行数据以数组形式返回
                if (!isReadTitle && i == 0) {
                    i++;
                    continue;
                }
                String[] str = reader.getValues();
                if (str != null && str.length > 0) {
                    result.add(str);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
            if (reader != null)
                //关闭CsvReader
                reader.close();
        }
        return result;
    }

    /**
     * 文件导出csv格式
     * @param response
     * @param reportName  文件名
     * @param headerInfo  文件列头属性 (List集合里放表头属性)
     * @param contentInfo 文件内容信息 (List集合里放单行数据信息,单行数据信息也放到list里存储)
     * @param  isLong 第几列是数字格式 从0开始
     * @return
     */
    public static boolean downloadCSVFile(HttpServletResponse response, String reportName, List<String> headerInfo, List<List<String>> contentInfo, List<Integer> isLong){
        boolean write = false;
        try {
            //设置格式
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+reportName+".csv");

            //表头信息
            ArrayList<String> rows = new ArrayList<String>();
            String headerStr = "";
            for(String headerName : headerInfo){
                headerStr +=headerName+",";
            }
            headerStr = headerStr.substring(0,headerStr.length()-1);
            rows.add(headerStr);
            rows.add("\n");

            //内容
            for(List<String> content : contentInfo){
                String contentStr = "";
                for (int i =0;i<content.size();i++){
                    boolean isString=true;
                    for (int j=0;j<isLong.size();j++){
                        if(i==isLong.get(j)){
                            isString=false;
                            break;
                        }
                    }
                    //是否是字符串格式
                    if(isString){
                        contentStr += content.get(i) + "\t,";
                    }else{
                        contentStr += content.get(i) + ",";
                    }
                }
                contentStr = contentStr.substring(0, contentStr.length()-1);
                rows.add(contentStr);
                rows.add("\n");
            }

            //写入
            OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));  //设置BOM文件头 解决用excel打开乱码问题
            Iterator<String> iter = rows.iterator();
            while (iter.hasNext()) {
                String outputString = iter.next();
                osw.write(outputString);
            }

            //关闭
            osw.close();
            write = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return write;
    }
    /**
     * 文件导出csv格式
     * @param response
     * @param reportName  文件名
     * @param headerInfo  文件列头属性 (List集合里放表头属性)
     * @param contentInfo 文件内容信息 (List集合里放单行数据信息,单行数据信息也放到list里存储)
     * @return
     */
    public static boolean downloadCSVFile(HttpServletResponse response, String reportName, List<String> headerInfo, List<List<String>> contentInfo){
        boolean write = false;
        try {
            //设置格式
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+reportName+".csv");

            //表头信息
            ArrayList<String> rows = new ArrayList<String>();
            String headerStr = "";
            for(String headerName : headerInfo){
                headerStr +=headerName+",";
            }
            headerStr = headerStr.substring(0,headerStr.length()-1);
            rows.add(headerStr);
            rows.add("\n");

            //内容
            for(List<String> content : contentInfo){
                String contentStr = "";
                for(String val : content){
                    contentStr += val + "\t,";
                }
                contentStr = contentStr.substring(0, contentStr.length()-1);
                rows.add(contentStr);
                rows.add("\n");
            }

            //写入
            OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));  //设置BOM文件头 解决用excel打开乱码问题
            Iterator<String> iter = rows.iterator();
            while (iter.hasNext()) {
                String outputString = iter.next();
                osw.write(outputString);
            }

            //关闭
            osw.close();
            write = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return write;
    }

    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param outPutPath 文件路径
     * @param fileName   文件名称
     * @return
     */

    public static File createCSVFile(List exportData, String outPutPath,
                                     String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            //System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);

            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                String row = (String) iterator.next();

                csvFileOutputStream.write(row);
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param outPutPath 文件路径
     * @param fileName   文件名称
     * @return
     */

    public static File createCSVFiles(List<List<String>> exportData, String outPutPath,
                                      String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            //System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);

            if (!exportData.isEmpty()) {
                // 写入文件内容
                for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                    List<String> row = (List<String>) iterator.next();
                    int size = row.size();
                    for (int i = 0; i < size - 1; i++) {
                        csvFileOutputStream.write(row.get(i));
                        csvFileOutputStream.write(",");
                    }
                    csvFileOutputStream.write(row.get(size - 1));
                    if (iterator.hasNext()) {
                        csvFileOutputStream.newLine();
                    }
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }


    public static void main(String[] args) {
//        List exportData = new ArrayList<String>();
//        exportData.add("1111111");
//        exportData.add("2222222");
//        exportData.add("3333333");
//        exportData.add("4444444");
//        String path = "c:/export/";
//        String fileName = "文件导出";
//        File file = createCSVFile(exportData, path, fileName);
//        String fileName2 = file.getName();
//        System.out.println("文件名称：" + fileName2);
//
//        List exportData = new ArrayList<List<String>>();
//        for (int i = 1; i < 10; i++) {
//            List<String> list = new ArrayList<String>();
//            list.add("MAC" + i);
//            list.add("SN" + i);
//            exportData.add(list);
//        }
//        String path = "c:/export/";
//        String fileName = "文件导出";
//        File file = createCSVFiles(exportData, path, fileName);
//        String fileName2 = file.getName();
//        System.out.println("文件名称：" + fileName2);

        //List<String[]> result = readFile("C:\\export\\SN,MAC批量入库1708292600104583830.csv", true, "GBK");
       /* for (String[] s : result) {
            StringBuffer mac = new StringBuffer(s[0]);
            StringBuffer sn = new StringBuffer(s[1]);
            System.out.println(mac);
            System.out.println(sn);
        }*/
    }
}
