package xyz.mariosm.bank.http;

import lombok.*;
import xyz.mariosm.bank.data.AccountTypes;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class AccountDetailsRequest {
    private @NonNull String username;
    private @NonNull String password;
    private @NonNull AccountTypes type;
}
