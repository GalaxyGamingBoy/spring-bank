package xyz.mariosm.bank.http;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import xyz.mariosm.bank.data.AccountTypes;

@Data
@NoArgsConstructor
public class AccountDetailsRequest {
    private @NonNull String username;
    private @NonNull String password;
    private @NonNull AccountTypes type;
}
