package com.yeji.board.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jasypt.encryptor")
public class JasyptProperties {

    private String password;

    // 추가 설정 (기본값 지정)
    private String algorithm = "PBEWithMD5AndTripleDES";
    private int keyObtentionIterations = 10000;
    private String stringOutputType = "base64";

}
