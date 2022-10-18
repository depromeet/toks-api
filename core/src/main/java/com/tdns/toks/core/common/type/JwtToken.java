package com.tdns.toks.core.common.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Builder
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name="QRRequest", description="QR인증 요청 모델")
public class JwtToken {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "accessToken", description = "jwt accessToken")
    private String accessToken;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "refreshToken", description = "jwt refreshToken")
    private String refreshToken;

    public JwtToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
