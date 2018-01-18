package com.jeeplus.modules.business.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {

	private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	private static final String METHOD_GET = "GET" ;
	private static final String METHOD_POST = "POST" ;
	
	private static class SingletonHolder {
		final static HttpClient instance = new HttpClient();
	}

	private HttpClient() {

	}

	public static HttpClient getInstance() {
		return SingletonHolder.instance;
	}

	public String getErrorStack(Exception e, int length) {
		String error = null;
		if (e != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				e.printStackTrace(ps);
				error = baos.toString();
				if (length > 0) {
					if (length > error.length()) {
						length = error.length();
					}
					error = error.substring(0, length);
				}
				baos.close();
				ps.close();
			} catch (Exception e1) {
				error = e.toString();
			}
		}
		return error;
	}

	public static HttpURLConnection getUrlConnection(String url, int bufferSize)throws Exception {
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setConnectTimeout(3000);
		conn.setReadTimeout(3000);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod(METHOD_POST);
		conn.setRequestProperty("conn", "Keep-Alive");
		conn.setChunkedStreamingMode(bufferSize);
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("contentType", "UTF-8");
		conn.setRequestProperty("timenow", String.valueOf(System.currentTimeMillis()));
		return conn;
	}
	
	/**
	 * POST发送，参数json/xml格式
	 * @param url
	 * @param postXml
	 * @param contentType
	 * @return
	 */
	public String sendPostReq(String url, String postXml, String contentType) {
		String uuid = System.currentTimeMillis() + "";
		logger.info("req_uuid_" + uuid + "_url：" + url + "_postJson:" + postXml
				+ "_contentType:" + contentType);
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		OutputStreamWriter out = null;
		try {
			HttpURLConnection con = getUrlConnection(url, postXml.length());
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", contentType);
			out = new OutputStreamWriter(con.getOutputStream());
			out.write(new String(postXml.getBytes("UTF-8")));
			out.flush();
			out.close();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes());
				sb.append(lines);
			}
			reader.close();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			logger.info("res_uuid_" + uuid + "_error:" + sw.toString());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (url != null) {
			logger.info("http_res_uuid_" + uuid + "_res:" + sb.toString());
		} else {
			logger.info("http_res_uuid_" + uuid + "_res:ok");
		}
		return sb.toString();
	}
	
	
	/** 
	  * 上传图片 
	  * 
	  * @param urlStr 
	  * @param textMap 
	  * @param fileMap 
	  * @return 
	  */
	 public static String sendPostReqFormUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) { 
		  String res = ""; 
		  HttpURLConnection conn = null; 
		  String BOUNDARY = "------------file content---------------"; //boundary就是request头和上传文件内容的分隔符 
		  try { 
			   URL url = new URL(urlStr); 
			   conn = (HttpURLConnection) url.openConnection(); 
			   conn.setConnectTimeout(5000); 
			   conn.setReadTimeout(30000); 
			   conn.setDoOutput(true); 
			   conn.setDoInput(true); 
			   conn.setUseCaches(false); 
			   conn.setRequestMethod("POST"); 
			   conn.setRequestProperty("Connection", "Keep-Alive"); 
			   conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)"); 
			   conn.setRequestProperty("Content-Type",  "multipart/form-data; boundary=" + BOUNDARY); 
			   OutputStream out = new DataOutputStream(conn.getOutputStream()); 
			   // text 
			   if (textMap != null) { 
				    StringBuffer strBuf = new StringBuffer(); 
				    Iterator iter = textMap.entrySet().iterator(); 
				    while (iter.hasNext()) { 
					     Map.Entry entry = (Map.Entry) iter.next(); 
					     String inputName = (String) entry.getKey(); 
					     String inputValue = (String) entry.getValue(); 
					     if (inputValue == null) { 
					    	 continue; 
					     } 
					     strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n"); 
					     strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n"); 
					     strBuf.append(inputValue); 
				    } 
				    out.write(strBuf.toString().getBytes()); 
			   } 
			   // file 
			   if (fileMap != null) { 
				     Iterator iter = fileMap.entrySet().iterator(); 
				     while (iter.hasNext()) { 
					     Map.Entry entry = (Map.Entry) iter.next(); 
					     String inputName = (String) entry.getKey(); 
					     String inputValue = (String) entry.getValue(); 
					     if (inputValue == null) { 
					    	 continue; 
					     } 
					     File file = new File(inputValue); 
					     String filename = file.getName(); 
					     String contentType = new MimetypesFileTypeMap().getContentType(file); 
					     if (filename.endsWith(".png")) { 
					    	 contentType = "image/png"; 
					     } 
					     if (contentType == null || contentType.equals("")) { 
					    	 contentType = "application/octet-stream"; 
					     } 
					     StringBuffer strBuf = new StringBuffer(); 
					     strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n"); 
					     strBuf.append("Content-Disposition: form-data; name=\""
					    		 		+ inputName + "\"; filename=\"" + filename 
					    		 		+ "\"\r\n"); 
					     strBuf.append("Content-Type:" + contentType + "\r\n\r\n"); 
					     out.write(strBuf.toString().getBytes()); 
					     DataInputStream in = new DataInputStream(new FileInputStream(file)); 
					     int bytes = 0; 
					     byte[] bufferOut = new byte[1024]; 
					     while ((bytes = in.read(bufferOut)) != -1) { 
					    	 out.write(bufferOut, 0, bytes); 
					     } 
					     in.close(); 
				    } 
			   } 
			   byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(); 
			   out.write(endData); 
			   out.flush(); 
			   out.close(); 
			   // 读取返回数据 
			   StringBuffer strBuf = new StringBuffer(); 
			   BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			   String line = null; 
			   while ((line = reader.readLine()) != null) { 
				   strBuf.append(line).append("\n"); 
			   } 
			   res = strBuf.toString(); 
			   reader.close(); 
			   reader = null; 
		   } catch (Exception e) { 
			   logger.error("发送POST请求出错。" + urlStr + e.getMessage());
		  } finally { 
			  if (conn != null) { 
				  conn.disconnect(); 
				  conn = null; 
			  } 
		  } 
		  return res; 
	 }
	
	public static HttpURLConnection getUrlConnection(String url)throws Exception {
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setConnectTimeout(3000);
		conn.setReadTimeout(3000);
		conn.setUseCaches(false);
		conn.setRequestMethod(METHOD_GET);
		conn.setRequestProperty("conn", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("contentType", "UTF-8");
		conn.setRequestProperty("timenow", String.valueOf(System.currentTimeMillis()));
		return conn;
	}
	
	/**
	 * get请求
	 * @param url 完整的url，附带参数
	 * @return
	 */
	public static String sendGetReq(String url) {
        String result = "";
        BufferedReader in = null;
        try {
        	HttpURLConnection conn = getUrlConnection(url);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            	logger.error(e.getMessage());
            }
        }
        return result;
    }
	
	
}
