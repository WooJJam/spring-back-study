package WooJJam.backstudy.week2.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    @Value("${kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${kakao.rest.api.key}")
    private String KAKAO_REST_API_KEY;

    private final RestTemplate restTemplate;
    public String kakaoGetToken(String code) throws Exception {
        String host = "https://kauth.kakao.com/oauth/token";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("grant_type", "authorization_code");
            body.add("client_id", KAKAO_REST_API_KEY);
            body.add("redirect_uri", KAKAO_REDIRECT_URI);
            body.add("code", code);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    host,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            return (String) jsonObj.get("access_token");
        }catch(Exception e) {
            throw new Exception(e);
        }

    }

    public void accessTokenInfo(String token) {
        String host = "https://kapi.kakao.com/v1/user/access_token_info";
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization","Bearer "+token);

        RestTemplate rt = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params,headers);

        ResponseEntity<String> response = rt.exchange(
                host,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

    }
    public String getUserInfo(String token) throws Exception {
        String host = "https://kapi.kakao.com/v2/user/me";
        try {
            System.out.println("token = " + token);
            HttpHeaders headers2 = new org.springframework.http.HttpHeaders();
            headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            headers2.add("Authorization","Bearer "+token);
            RestTemplate rt = new RestTemplate();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params,headers2);
            ResponseEntity<String> response = rt.exchange(
                    host,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
            JSONObject kakaoAccount = (JSONObject) jsonObj.get("kakao_account");
            return (String) kakaoAccount.get("email");

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void kakaoLogout(String token) {
        String host = "https://kapi.kakao.com/v1/user/logout";

        HttpHeaders headers2 = new org.springframework.http.HttpHeaders();
        headers2.add("Authorization","Bearer "+token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers2);
        ResponseEntity<String> response = rt.exchange(
                host,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        System.out.println("logout = " + response);
    }
}
