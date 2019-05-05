package com.example.clothes;

import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;
//import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPageUtil {
    /*** 一个简单的类，用来获取网页内容* @author siqi**/

        /*** 调试*/
        private static boolean debug = false;
        /**
         * 消息头，包含http资源或者http服务器的一些属性，例如:<BR/>
         * HTTP/1.1 200 OK<BR/>
         * Server: nginx/0.7.68<BR/>
         * Date: Tue, 22 Jan 2013 10:55:21 GMT<BR/>
         * Content-Type: image/jpeg<BR/>
         * Content-Length: 6372<BR/>
         * Last-Modified: Sun, 29 Apr 2012 07:29:01 GMT<BR/>
         * Connection: close<BR/>
         * Expires: Mon, 18 Nov 2013 10:55:21 GMT<BR/>
         * Cache-Control: max-age=25920000<BR/>
         */
        private String msgHeader;
        /*** 消息头的字符缓存*/
        private StringBuffer msgHeaderBuffer = new StringBuffer();

        /*** 网页内容编码*/
        private Charset charset;

        /*** 缓存大小(单位：字节)*/
        private int buffer_size = 4096;

        /*** 网页内容*/
        private byte[] bytes = new byte[0];

//    /**
//     * 一个调用WebUtil的例子
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        try {
//            String url = "http://m.weathercn.com/common/province.jsp";
//            WebPageUtil webPageUtil = new WebPageUtil().processUrl(url);
//            System.out.println("=======Header :=======\r\n"+webPageUtil.getMsgHeader());
//            System.out.println("=======Content:=======\r\n"+webPageUtil.getWebContent());
//            webPageUtil.processUrl("http://www.baidu.com");
//            System.out.println("=======Header :=======\r\n"+webPageUtil.getMsgHeader());
//            System.out.println("=======Content:=======\r\n"+webPageUtil.getWebContent());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

        /*** 使用Socket请求（获取）一个網頁。<br/>
         * 例如:<br/>
         * processUrl("http://www.baidu.com/")會獲取百度首頁；<br/>*
         * @param url
         *            這個網頁或者網頁内容的地址
         * @throws Exception*/
        public WebPageUtil processUrl(String url) throws Exception {

            //清空上次獲取的數據
            this.msgHeader = "";
            this.msgHeaderBuffer.setLength(0);
            this.bytes = new byte[0];

            url = formatUrl(url);

            // 設置要連接的伺服器地址
            Socket socket = new Socket(getHost(url), getPort(url));
            socket.setSoTimeout(3000);

            // 要求連線，详情请参考HTTP协议(RFC2616)
            String request = String.format("GET %s HTTP/1.0\r\n", getSubUrl(url));
            request += String.format("HOST: %s \r\n\r\n", getHost(url));

            if(debug) {
                System.out.println("request:\r\n"+request);
            }

            // 發送請求
            socket.getOutputStream().write(request.getBytes());

            // 設置缓存，最好跟系统的socket接收缓存一样
            this.buffer_size = socket.getReceiveBufferSize();
            byte[] bytesBuffer = new byte[buffer_size];// 缓存InputStream的原始數據
            char[] charsBuffer = new char[buffer_size];// 缓存InputStream的字符數據

            // 来自伺服器(InputStream)
            InputStream is = socket.getInputStream();

            // 局部變量，讀取到的內容長度
            int bytesLength = 0;
            // 局部變量，判断消息是否讀取完畢
            boolean headerComplete = false;

            // 从InputStream中讀取網頁的内容，如果讀取到的内容
            // 長度為-1，則讀取完畢
            while ((bytesLength = is.read(bytesBuffer, 0, buffer_size)) != -1) {
                if (headerComplete) {
                    SaveBytes(bytesBuffer, 0, bytesLength);
                } else {
                    int bufferLength = msgHeaderBuffer.length();
                    msgHeaderBuffer.append(
                            bytes2chars(bytesBuffer, charsBuffer, bytesLength), 0,
                            bytesLength);
                    int msgEndIndex = msgHeaderBuffer.indexOf("\r\n\r\n");
                    if (msgEndIndex != -1) {
                        headerComplete = true;
                        msgHeader = "Url: " + url + "\r\n"
                                + msgHeaderBuffer.substring(0, msgEndIndex);
                        int temp = msgEndIndex - bufferLength + 4;
                        SaveBytes(bytesBuffer, temp, bytesLength - temp);
                    }
                }
            }

            socket.close();

            //獲取網頁編碼
            this.getCharset();

            return this;
        }

        /*** 根據網址獲取伺服器port。<br/>
         * http port為80<br/>
         * https port為443
         * @param url
         * @return*/
        public static int getPort(String url) {
            int port = 0;
            if (url.startsWith("https://")) {
                port = 443;
            } else if (url.startsWith("http://")) {
                port = 80;
            }

            if(debug) {
                System.out.println("port: "+port);
            }

            return port;
        }

        /*** 根據網址，獲取伺服器地址<br/>
         * 例如：<br/>
         * http://m.weathercn.com/common/province.jsp<p>
         * 返回：<br/>
         * m.weathercn.com
         * @param url 網址
         * @return*/
        public static String getHost(String url) {
            String host = "";
            Matcher mat = Pattern.compile("(?<=https?://).+?(?=/)").matcher(url);
            if(mat.find()) {
                host = mat.group();
            }

            if(debug) {
                System.out.println("host: "+host);
            }

            return host;
        }

        /*** 根據網址，獲取網頁
         * 例如：<br/>
         * http://m.weathercn.com/common/province.jsp<p>
         * 返回：<br/>
         * /common/province.jsp
         * @param url
         * @return 如果没有獲取到網頁，返回"";*/
        public static String getSubUrl(String url) {
            String subUrl = "";
            Matcher mat = Pattern.compile("https?://.+?(?=/)").matcher(url);
            if(mat.find()) {
                subUrl = url.substring(mat.group().length());
            }

            if(debug) {
                System.out.println("subUrl: "+subUrl);
            }

            return subUrl;
        }

        /**
         * 在網址上加个"/"<br/>
         * 例如：<br/>
         * http://www.baidu.com<br/>
         * 返回：<br/>
         * http://www.baidu.com/<p>
         * 例如：<br/>
         * http://www.baidu.com/xxxx<br/>
         * 返回：(没有加"/")<br/>
         * http://www.baidu.com/xxxx<br/>
         * @param url
         * @return*/
        public static String formatUrl(String url) {
            Matcher mat = Pattern.compile("https?://[^/]+").matcher(url);
            if (mat.find() && mat.group().equals(url)) {
                return url + "/";
            } else {
                return url;
            }
        }

        /*** 把输入流中讀取到的數據保存到bytes數據中，
         * 每次都創建一个新的byte[]来儲存原来bytes[]數據中的數據和
         * 新讀取到的b中的數據。
         * @param b 儲存内容的byte[]
         * @param start 内容的起始位置，从0开始
         * @param length 內容的長度
         * @throws Exception*/
        private void SaveBytes(byte[] b, int start, int length) throws Exception {
            //do some check
            if(start<0 || length<0) {
                throw new Exception("start/length is incorrect.");
            }
            //新建一个byte数组
            byte[] newBytes = new byte[bytes.length+length];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            System.arraycopy(b, start, newBytes, bytes.length, length);

            bytes = newBytes;

        }

        /*** 將數據轉換成字串
         * @param srcBytes
         * @param dstChars
         * @param length
         * @return*/
        private char[] bytes2chars(byte[] srcBytes, char[] dstChars, int length) {
            for (int i = 0; i < length; i++) {
                dstChars[i] = (char) srcBytes[i];
            }

            return dstChars;
        }

        /***獲取網頁（文件）的前部分，裡面包含伺服器和資源的一些屬性*/
        public String getMsgHeader() {
            return msgHeader;
        }

        /*** 獲取網頁或網頁資源的編碼，如果在前裡面沒有找到編碼，那么就返回系统預設編碼。* @return*/
        public Charset getCharset() {
            String header = this.msgHeader.toUpperCase();
            Matcher mat = Pattern.compile("CHARSET=.+").matcher(header);
            if(mat.find()) {
                this.charset = Charset.forName(mat.group().split("=")[1]);
            }else{
                this.charset = Charset.defaultCharset();
            }
            return charset;
        }

        /**
         * 獲取網頁內容* @return*/
        public String getWebContent() {
            return new String(bytes, charset);
        }

}
