package org.bfsi.auth.controller;


import org.bfsi.auth.payload.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private static ResponseEntity<ResponseDto> getResponseEntityObject(HttpStatus status, String msg, Object data) {

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(status.toString())
                .message(msg)
                .data(data)
                .build(), status);
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/welcome")
    public ResponseEntity<ResponseDto> welcome() {
        return getResponseEntityObject(HttpStatus.OK,
                "Welcome User/Admin to Banking application", "{}");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity user() {
        return getResponseEntityObject(HttpStatus.OK,
                "Welcome User to Banking application", "{}");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity admin() {
        return getResponseEntityObject(HttpStatus.OK,
                "Welcome Admin to Banking application", "{}");
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/validate")
    public ResponseEntity validate() {
        return getResponseEntityObject(HttpStatus.OK,
                "Validation successful", "{}");
    }
}
