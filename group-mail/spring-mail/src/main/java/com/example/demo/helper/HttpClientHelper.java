package com.example.demo.helper;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/6/2021 9:22 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Component
public class HttpClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

    private CloseableHttpClient httpClient;

    private RequestConfig requestConfig;

    public HttpClientHelper(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    public String get(String url, HashMap<String, Object> paramMap,
                      HashMap<String, Object> headMap) {
        String result = "";
        if (StringUtils.isEmpty(url)) {
            return result;
        }

        HttpGet httpGet = new HttpGet(url);
        // 配置连接参数
        httpGet.setConfig(requestConfig);

        try {

            // 设置参数
            if (paramMap != null && !paramMap.isEmpty()) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(),
                            URLEncoder.encode(entry.getValue().toString(), "UTF-8")));
                }
                String strParams = EntityUtils.toString(new UrlEncodedFormEntity(params));
                // 防止多参数时，分隔符","被转义
                String realParams = URLDecoder.decode(strParams, "UTF-8");
            }
            // 设置头
            if (headMap != null && !headMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : headMap.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }


        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 执行 Request
            result = parseResponse(response);
        } catch (Exception e) {
            String logMsg = "url: " + url;
            logger.error(logMsg, e);
        }

        return result;
    }


    public String post(String url, HashMap<String, Object> paramMap, HashMap<String, Object> header) {
        String result = null;
        if ("".equals(url)) {
            return result;
        }
        // 创建一个request对象
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            // 配置连接参数
            httpPost.setConfig(requestConfig);
            // 设置参数
            if (paramMap != null && paramMap.size() > 0) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                HttpEntity entity = new UrlEncodedFormEntity(params);
                httpPost.setEntity(entity);
            }
            // 设置头
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            // 执行request请求
            response = httpClient.execute(httpPost);
            result = reponseHandle(response);
        } catch (Exception e) {
            logger.error("url : "+ url +", msg : " + e.getMessage());
            httpPost.abort();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String postJSON(String url, String json_str, HashMap<String, Object> header) {
        String result = null;
        if ("".equals(url)) {
            return result;
        }
        // 创建一个request对象
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            // 配置连接参数
            httpPost.setConfig(requestConfig);
            // 设置参数
            if (json_str != null && !"".equals(json_str)) {
                StringEntity entity = new StringEntity(json_str, ContentType.APPLICATION_JSON);
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            // 设置头
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            // 执行request请求
            response = httpClient.execute(httpPost);
            result = reponseHandle(response);

        } catch (Exception e) {
            logger.error("url : "+ url +", msg : " + e.getMessage()+", param : " +json_str);
            httpPost.abort();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String uploadFile(String url, String filePath, String fileParam, Map<String, Object> params) {
        File file = new File(filePath);
        if (!(file.exists() && file.isFile())) {
            throw new RuntimeException("file : file is null");
        }
        String result = null;
        if ("".equals(url)) {
            return result;
        }
        // 创建一个request对象
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            // 配置连接参数
            httpPost.setConfig(requestConfig);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(fileParam, file, ContentType.DEFAULT_BINARY, file.getName());
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    builder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.create("text/plain", Consts.UTF_8));
                }
            }
            HttpEntity requestEntity = builder.build();
            httpPost.setEntity(requestEntity);
            // 执行request请求
            response = httpClient.execute(httpPost);
            result = reponseHandle(response);

        } catch (Exception e) {
            httpPost.abort();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    private String parseResponse(CloseableHttpResponse response) {
        String result = "";
        // 获取响应体
        HttpEntity httpEntity = null;
        InputStream inputStream = null;
        try {
            // 获取响应状态
            int statusCode = response.getStatusLine().getStatusCode();
            // 没有正常响应
            if (statusCode < HttpStatus.SC_OK || statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new RuntimeException("statusCode : " + statusCode);
            }
            // 获取响应体
            httpEntity = response.getEntity();
            if (httpEntity != null) {
                inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                result = sb.toString();
            }

        } catch (Exception e) {
            logger.error("HttpClientHelper parseResponse error", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 如果httpEntity没有被完全消耗，那么连接无法安全重复使用，将被关闭并丢弃
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String reponseHandle(CloseableHttpResponse response) {
        String result = "";
        // 获取响应体
        HttpEntity httpEntity = null;
        try {
            // 获取响应状态
            int statusCode = response.getStatusLine().getStatusCode();
            // 没有正常响应
            if (statusCode < HttpStatus.SC_OK || statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new RuntimeException("statusCode : " + statusCode);
            }
            // 获取响应体
            httpEntity = response.getEntity();
            if (httpEntity !=null) {
                result = EntityUtils.toString(httpEntity);
            }

        } catch (Exception e) {
            logger.error("HttpClientHelper reponseHandle error", e);
        } finally {
            // 如果httpEntity没有被完全消耗，那么连接无法安全重复使用，将被关闭并丢弃
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
