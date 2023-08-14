package com.shong91.app.shortener;

import com.shong91.app.common.ResultCode;
import com.shong91.app.exception.CustomRuntimeException;
import com.shong91.app.util.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UrlService {

  private final UrlRepository urlRepository;

  /**
   * long URL -> short URL 으로 변환
   *
   * @param originUrl 원본 URL
   * @return 인코딩 된 URL ID
   */
  public String encode(String originUrl) {
    Url url = new Url(originUrl);
    urlRepository.save(url);

    return Base62.encode(url.getId());
  }

  /**
   * short URL -> long URL 으로 변환
   *
   * @param encodedId 인코딩 된 URL ID
   * @return 원본 URL
   */
  public String decode(String encodedId) {

    int id = Base62.decode(encodedId);
    Url url = urlRepository.findById(id)
        .orElseThrow(() -> new CustomRuntimeException(ResultCode.NO_SUCH_ELEMENT));

    return url.getOriginUrl();
  }
}
