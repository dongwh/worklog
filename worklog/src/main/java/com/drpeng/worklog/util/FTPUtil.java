package com.drpeng.worklog.util;

import com.drpeng.common.ftp.Ftp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dongwh on 16/6/14.
 */
public class FTPUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(FTPUtil.class);

    /**
     * FTP客户端
     */
    public static FTPClient ftpClient = null;

    /**
     * 服务器连接
     * @param ip 服务器IP
     * @param port 服务器端口
     * @param user 用户名
     * @param password 密码
     * @author dongwh
     * @date   2016-6-14
     */
    public static FTPClient connect(String ip, int port, String user, String password, String connectType) {
        LOGGER.info("【连接文件服务器】ip = " + ip + " , port : " + port + " , user = " + user + " , password = "+ password);
        ftpClient = new FTPClient();
        try {
            // ip：FTP服务器的IP地址；user:登录FTP服务器的用户名
            // password：登录FTP服务器的用户名的口令；path：FTP服务器上的路径
            // 连接
            ftpClient.connect(ip, port);
            // 登录
            boolean loginFlag = ftpClient.login(user, password);
            if(!loginFlag){
                LOGGER.error("【登录FTP服务器失败,服务器地址ip="+ip+"端口="+port+"】");
                return null;
            }
            if("PASV".equals(connectType)){
                ftpClient.enterLocalPassiveMode();  //连接FTP用被动模式
            }else{
                ftpClient.enterLocalActiveMode();   //连接FTP用主动模式
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 看返回的值是不是200，如果是，表示登录成功
            int reply = ftpClient.getReplyCode();
            // 判断文件服务器是否可用
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())+"true or false");
                closeConnection(ftpClient);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("【连接文件服务器失败】", e);
            throw new RuntimeException("连接文件服务器失败");
        }
        return ftpClient;
    }

    /**
     * 切换工作路径
     * @param path 服务器路径,(文件夹,空/null代表根目录)
     */
    public static void changeDirectory(String path) throws IOException {
        //path是ftp服务下主目录的子目录
        ftpClient.changeWorkingDirectory(path);
    }

    /**
     * 得到某以目录下的所有文件
     * @param path
     * @param fileType  文件类型: null表示所有文件
     * @return
     * @throws IOException
     */
    public static List<String> getFileList(String path, String fileType) throws IOException {
        FTPFile[] ftpFiles= ftpClient.listFiles(path);

        //得到path路径下的所有文件(包括文件夹)
        List<String> retList = new ArrayList<String>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) { //判断是否是文件
                String fileName = ftpFile.getName();
                if(null!=fileType && !"".equals(fileType)){
                    String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
                    if(fileType.equals(prefix)){
                        retList.add(fileName);
                        continue;
                    }else{
                        continue;
                    }
                }
                retList.add(fileName);
            }
        }
        return retList;
    }

    /**
     * 关闭连接，使用完连接之后，一定要关闭连接，否则服务器会抛出 Connection reset by peer的错误
     * @throws Exception
     */
    public static void closeConnection(FTPClient ftpClient) {
        LOGGER.info("【关闭文件服务器连接】");
        if (null == ftpClient) {
            return;
        }
        try {
            ftpClient.disconnect();
        } catch (Exception e) {
            LOGGER.error("【关闭连接失败】", e);
            throw new RuntimeException("关闭连接失败");
        }
    }

    /**
     * 下载文件到指定目录
     * @param ftpFile 文件服务器上的文件地址
     * @param localFile 输出文件的路径和名称
     * @throws Exception
     */
    public static boolean downLoad(String ftpFile, String localFile) throws Exception {
        LOGGER.info("【下载文件到指定目录ftpFile = " + ftpFile + " , localFile = " + localFile+"】");
        boolean downLoadFlag = false;
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        if (StringUtils.isBlank(ftpFile)) {
            LOGGER.warn("【参数ftpFile为空】");
            throw new RuntimeException("【参数ftpFile为空】");
        }
        if (StringUtils.isBlank(localFile)) {
            LOGGER.warn("【参数localFile为空】");
            throw new RuntimeException("【参数localFile为空】");
        }
        File file = new File(localFile);
        FileOutputStream fos = new FileOutputStream(file);
        downLoadFlag = ftpClient.retrieveFile(ftpFile, fos);
        fos.flush();
        fos.close();
        LOGGER.info("【下载文件到指定目录ftpFile = " + ftpFile + " , localFile = " + localFile+"】,下载"+(downLoadFlag==true?"成功":"失败"));
        return downLoadFlag;
    }

    public static  boolean rename(String name, String remote) throws IOException
    {

        ftpClient.enterLocalPassiveMode();

        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


        FTPFile[] files = ftpClient.listFiles(remote);
        if (files.length == 1)
        {
            boolean status = ftpClient.rename(remote, name);
            return status;
        }else{
            return false;
        }
    }

    /**
     * ftp上传文件
     * @throws Exception
     */
    public static String upload(String fileName, InputStream input, Ftp ftp) throws Exception {
        String targetFile ="";
        try {

            int reply;
            ftpClient.connect(ftp.getIpAddr(), ftp.getPort());//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(ftp.getUserName(), ftp.getPwd());//登录
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return null;
            }
            ftpClient.changeWorkingDirectory(ftp.getRemoteDir());
            ftpClient.storeFile(fileName, input);
            String newFileName = getNewFileName(fileName);
            rename(newFileName,ftp.getRemoteDir()+fileName);
            targetFile = newFileName;
            input.close();
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return targetFile;
    }

    /**
     * 本地移动
     * 移动文件到新的目录下
     * @param localDirPath  原路径
     * @param newDirPath    新路径
     * @param fileName   文件名称
     */
    public static boolean removeToAnotherDir(String localDirPath, String newDirPath, String fileName){
        boolean tag = false;
        try {
            File localDirFile = new File(localDirPath+"/"+fileName);
            File newDir = new File(newDirPath);
            if(!newDir.exists())
                newDir.mkdirs();
            File newDirFile = new File(newDirPath+"/"+fileName);
            tag = localDirFile.renameTo(newDirFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tag;
    }

    /**
     * 移动FTP服务器上文件路径
     * @return
     */
    public static boolean removeToAnotherDir (String remoteFilePath, String newFilePath){
        boolean tag = false;
        try {
            tag = ftpClient.rename(remoteFilePath, newFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tag;
    }

    public static String getNewFileName(String fileName){
        String fileExt = fileName.substring(fileName.indexOf("."), fileName.length());//扩展名
        String originName = fileName.substring(0,fileName.indexOf("."));
        String newName = originName+ "_"+ DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + fileExt;//文件按时间重命名

        return newName;
    }

}
