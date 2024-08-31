package xyz.mariosm.bank.http;

import lombok.*;
import xyz.mariosm.bank.data.AccountTypes;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class ChangeTypeRequest {
    private @NonNull AccountTypes type;
}
