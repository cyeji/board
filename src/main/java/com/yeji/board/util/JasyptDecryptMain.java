package com.yeji.board.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptDecryptMain {
    public static void main(String[] args) {
        String envPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        String defaultPassword = "myencryptionpassword"; // application.yml에 설정된 값
        String passwordToUse = envPassword != null && !envPassword.isEmpty() ? envPassword : defaultPassword;

        String encUrl = "FSbWYKlpzlWzmNOMKKdS/zoV8dUEorU3ViUCrC93hiwZD2GSPIz9J1u1wZQwZ2TonXZQzWCw177bB90eEo5BLaAShauUkANUC1hdAO/rqhkmB4lrt/XO8g==";
        String encUsername = "cBcbVtAz48/oXcJDdtFJSw==";
        String encPassword = "TSOG29lrFkXL4mLbLPHtfCteoP56uPPY";

        if (args.length > 0 && "encrypt".equalsIgnoreCase(args[0])) {
            if (args.length < 2) {
                System.err.println("Usage: run --args='encrypt plaintext' (uses JASYPT_ENCRYPTOR_PASSWORD or default from application.yml)");
                System.exit(1);
            }
            String plain = args[1];
            StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
            enc.setAlgorithm("PBEWithMD5AndDES");
            enc.setKeyObtentionIterations(1000);
            enc.setPassword(passwordToUse);
            String cipher = enc.encrypt(plain);
            System.out.println("Using password: " + passwordToUse);
            System.out.println("Encrypted value: " + cipher);
            return;
        }

        System.out.println("Trying decrypt with environment/app password: " + (envPassword != null ? envPassword : defaultPassword));

        String[] algorithms = new String[] {
            "PBEWithMD5AndDES",
            "PBEWithMD5AndTripleDES",
            "PBEWithSHA1AndDESede",
            "PBEWithSHA256AndAES_128",
            "PBEWithHmacSHA512AndAES_256"
        };
        int[] iterations = new int[] {1000, 10000, 100, 5000};
        String[] passwordsToTry = new String[] {passwordToUse, "changeit", "password", "myencryptionpassword"};

        for (String pwd : passwordsToTry) {
            for (String alg : algorithms) {
                for (int iter : iterations) {
                    StandardPBEStringEncryptor e = new StandardPBEStringEncryptor();
                    try {
                        e.setAlgorithm(alg);
                        e.setKeyObtentionIterations(iter);
                        e.setPassword(pwd);
                        String du = null;
                        String uu = null;
                        String pp = null;
                        try { du = e.decrypt(encUrl); } catch (Exception ex) { /* ignore */ }
                        try { uu = e.decrypt(encUsername); } catch (Exception ex) { /* ignore */ }
                        try { pp = e.decrypt(encPassword); } catch (Exception ex) { /* ignore */ }
                        if (du != null || uu != null || pp != null) {
                            System.out.println("SUCCESS with pwd='" + pwd + "', alg='" + alg + "', iter=" + iter);
                            System.out.println("  URL  => " + du);
                            System.out.println("  User => " + uu);
                            System.out.println("  Pass => " + pp);
                            return;
                        }
                    } catch (Throwable t) {
                        // ignore and continue
                    }
                }
            }
        }

        // 마지막으로, 단일 설정으로 복호화 시도해 본다 (원래 빈과 동일 설정)
        try {
            StandardPBEStringEncryptor e = new StandardPBEStringEncryptor();
            e.setAlgorithm("PBEWithMD5AndDES");
            e.setKeyObtentionIterations(1000);
            e.setPassword(passwordToUse);
            System.out.println("Default-decrypt URL => " + e.decrypt(encUrl));
            System.out.println("Default-decrypt User => " + e.decrypt(encUsername));
            System.out.println("Default-decrypt Pass => " + e.decrypt(encPassword));
        } catch (Exception ex) {
            System.err.println("Default decrypt failed: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        }

        System.err.println("No successful decryption found with tested combos. If you know the original encryption password or algorithm, run encrypt with that password or re-encrypt values with a known password and update application.yml.");
    }
}
