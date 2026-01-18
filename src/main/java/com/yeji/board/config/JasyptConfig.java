package com.yeji.board.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.util.Objects;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    private static final Logger log = LoggerFactory.getLogger(JasyptConfig.class);

    private final JasyptProperties props;

    public JasyptConfig(JasyptProperties props) {
        this.props = Objects.requireNonNull(props, "JasyptProperties must not be null");
    }

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        // 우선순위: 환경변수 JASYPT_ENCRYPTOR_PASSWORD > application property (props.password)
        String envPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        String password = envPassword != null && !envPassword.isEmpty() ? envPassword : props.getPassword();

        if (password == null || password.isEmpty()) {
            String msg = "Jasypt encryptor password not set. Set environment variable JASYPT_ENCRYPTOR_PASSWORD or jasypt.encryptor.password in application.yml";
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        log.info("Initializing Jasypt StringEncryptor (algorithm={}, iterations={}, outputType={})",
                props.getAlgorithm(), props.getKeyObtentionIterations(), props.getStringOutputType());

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(props.getAlgorithm());
        encryptor.setKeyObtentionIterations(props.getKeyObtentionIterations());
        encryptor.setPassword(password);
        encryptor.setSaltGenerator(new RandomSaltGenerator());

        // IV generator 설정은 런타임 환경에 따라 다르므로 안전하게 시도
        try {
            encryptor.setIvGenerator(new RandomIvGenerator());
        } catch (Throwable t) {
            // 일부 jasypt 버전에서는 지원되지 않을 수 있으므로 경고만 남깁니다.
            log.debug("IV generator not set (may be unsupported by Jasypt version): {}", t.toString());
        }

        if (props.getStringOutputType() != null && !props.getStringOutputType().isEmpty()) {
            encryptor.setStringOutputType(props.getStringOutputType());
        }

        return encryptor;
    }

}
