package br.com.igorcarvalho.pdfgen.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerStatusResponseDto {
    String appName;
    String status;
    String profile;
}
