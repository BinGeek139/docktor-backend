package com.techasians.doctor.subscriber.service.impl;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techasians.doctor.subscriber.exception.ValidateException;
import com.techasians.doctor.subscriber.service.SMSService;


import com.techasians.doctor.subscriber.utils.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class SMSServiceImpl implements SMSService {
    @Value("${subscriber.sms.url}")
    String url;

    @Value("${subscriber.sms.brandname}")
    String brandName;

    @Value("${subscriber.sms.apikey}")
    String apiKey;

    @Value("${subscriber.sms.secretKey}")
    String secretKey;

    @Value("${subscriber.sms.smsType}")
    String smsType;

    @Value("${subscriber.sms.sandbox}")
    String sandbox;

    @Value("${subscriber.sms.content}")
    String content;

    public static final Logger LOGGER = LoggerFactory.getLogger(SMSServiceImpl.class);

    @Override
    public Boolean sendSMS(String phone, String content) {
      return sendSmsWithESMS(phone, apiKey, secretKey, smsType, sandbox, content);

    }

    public Boolean sendSmsWithESMS(String phone, String apiKey, String secretKey, String smsType, String sandbox, String content)
        throws ValidateException {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
            uriBuilder.setParameter("Phone", phone)
                .setParameter("ApiKey", apiKey)
                .setParameter("SecretKey", secretKey)
                .setParameter("Brandname", brandName)
                .setParameter("SmsType", smsType)
                .setParameter("Sandbox", sandbox)
                .setParameter("Content", content)
                .setParameter("RequestId", String.valueOf(System.currentTimeMillis()));
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(httpGet);
            String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(result);
            JsonElement codeElement = jsonObject.get("CodeResult");
            String code = codeElement.getAsString();
            if ("100".equals(code)) {
                return true;
            } else if ("99".equals(code)) {
                throw new ValidateException(Constants.MESSAGE_INVALID_PHONE_NUMBER);
            } else {
                return false;
            }

        } catch (URISyntaxException uriSyntaxException) {
            LOGGER.error(uriSyntaxException.getMessage(), uriSyntaxException);
            return false;
        } catch (ClientProtocolException clientProtocolException) {
            LOGGER.error(clientProtocolException.getMessage(), clientProtocolException);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }


        return null;
    }

}
