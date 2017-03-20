package org.mattbooks.rentchecks.lob;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.lob.client.AsyncLobClient;
import com.lob.client.LobClient;
import com.lob.id.AddressId;
import com.lob.id.BankAccountId;
import com.lob.protocol.request.CheckRequest;
import com.lob.protocol.response.CheckResponse;
import com.ning.http.client.AsyncHttpClientConfig;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mattbooks.rentchecks.helpers.Time;
import org.mattbooks.rentchecks.settings.Settings;

public class LobCheckMailer implements RequestHandler<Request, Response> {
  private final LobClient lob;
  private final String fromAddressToken;
  private final String toAddressToken;
  private final String bankAccountToken;
  private final Money rentAmount;

  public LobCheckMailer() {
    Map<String, String> env = System.getenv();

    lob = AsyncLobClient.create(Settings.lobApiKey(),
        new AsyncHttpClientConfig.Builder().setConnectionTimeoutInMs(5000).build());
    fromAddressToken = Settings.fromAddressToken();
    toAddressToken = Settings.toAddressToken();
    bankAccountToken = Settings.bankAccountToken();
    rentAmount = Money.of(CurrencyUnit.USD, Settings.rentAmount());
  }

  public Response handleRequest(Request request, Context context) {
    try {
      context.getLogger().log("Starting to mail the check");
      mailCheck(context);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return Response.success();
  }

  private void mailCheck(Context context) throws ExecutionException, InterruptedException {
    CheckResponse checkResponse = lob.createCheck(CheckRequest.builder()
        .amount(rentAmount)
        .bankAccount(BankAccountId.parse(bankAccountToken))
        .from(AddressId.parse(fromAddressToken))
        .to(AddressId.parse(toAddressToken))
        .memo(String.format("Rent for %s", Time.nextMonth()))
        .build())
        .get();

    context.getLogger().log(checkResponse.getMemo());
    context.getLogger().log(checkResponse.getExpectedDeliveryDate().toString());
    context.getLogger().log(checkResponse.getId().toString());
  }
}
