package com.shong91.app.shortener;

import com.shong91.app.common.ResponseDto;
import com.shong91.app.common.ResultCode;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/url-shortener")
@RequiredArgsConstructor
@Slf4j
public class UrlController {

  private final UrlService urlService;

  @PostMapping
  public ResponseDto<String> encode(@RequestParam String originUrl) {
    return new ResponseDto<>(ResultCode.OK, urlService.encode(originUrl));
  }

  @GetMapping("{encodedId}")
  public ResponseEntity<Object> decode(@PathVariable String encodedId) throws URISyntaxException {
    URI redirectUri = new URI(urlService.decode(encodedId));
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(redirectUri);
    return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
  }

  @GetMapping("/redirect-url")
  public String redirect() {
    return "redirect here!";
  }

}
