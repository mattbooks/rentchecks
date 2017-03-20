package org.mattbooks.rentchecks.settings;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.util.Base64;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Settings {
  private static final String LOB_API_KEY = "LOB_API_KEY";
  private static final String TO_EMAIL = "TO_EMAIL";
  private static final String FROM_EMAIL = "EMAIL";
  private static final String FROM_ADDRESS_TOKEN = "FROM_ADDRESS_TOKEN";
  private static final String TO_ADDRESS_TOKEN = "TO_ADDRESS_TOKEN";
  private static final String RENT_AMOUNT = "RENT_AMOUNT";
  private static final String BANK_ACCOUNT_TOKEN = "BANK_ACCOUNT_TOKEN";
  private static final String FROM_EMAIL_PASSWORD = "FROM_EMAIL_PASSWORD";

  public static String bankAccountToken() {
    return decrypt(BANK_ACCOUNT_TOKEN);
  }

  public static int rentAmount() {
    return Integer.valueOf(decrypt(RENT_AMOUNT));
  }

  public static String toAddressToken() {
    return decrypt(TO_ADDRESS_TOKEN);
  }

  public static String fromAddressToken() {
    return decrypt(FROM_ADDRESS_TOKEN);
  }

  public static String lobApiKey() {
    return decrypt(LOB_API_KEY);
  }

  public static String statusToEmail() {
    return decrypt(TO_EMAIL);
  }

  public static String statusFromEmail() {
    return decrypt(FROM_EMAIL);
  }

  public static String statusFromEmailPassword() {
    return decrypt(FROM_EMAIL_PASSWORD);
  }

  private static String decrypt(String key) {
    byte[] encryptedKey = Base64.decode(System.getenv(key));

    AWSKMS client = AWSKMSClientBuilder.defaultClient();

    DecryptRequest request = new DecryptRequest()
        .withCiphertextBlob(ByteBuffer.wrap(encryptedKey));

    ByteBuffer plainTextKey = client.decrypt(request).getPlaintext();

    return new String(plainTextKey.array(), Charset.forName("UTF-8"));
  }
}
