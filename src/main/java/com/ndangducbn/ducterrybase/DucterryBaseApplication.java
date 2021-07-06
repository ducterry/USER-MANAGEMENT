package com.ndangducbn.ducterrybase;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping
@Api(tags = "01. Heath-Check", description = "Kiểm tra hoạt động Server")
public class DucterryBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DucterryBaseApplication.class, args);
	}

	@GetMapping("/healthcheck")
	public ResponseEntity<?> healthcheck() {
		return new ResponseEntity<>("AWS Health Check OK !!!", HttpStatus.OK);
	}

}
