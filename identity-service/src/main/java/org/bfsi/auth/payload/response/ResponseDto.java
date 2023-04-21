package org.bfsi.auth.payload.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String message;
    private String statusCode;
    private Object data;

}
