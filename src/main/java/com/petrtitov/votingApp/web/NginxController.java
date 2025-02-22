package com.petrtitov.votingApp.web;

import com.petrtitov.votingApp.service.VoteService;
import com.petrtitov.votingApp.to.VoteTo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Controller
//    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Nginx Controller")
public class NginxController {
    static final String URL = "/nginx";
    private static final String TARGET_SERVICE_URL = "http://comfort-soft"; // замените /endpoint на нужный путь
    private final VoteService voteService;
    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping(URL)
    public void getToday(@AuthenticationPrincipal @ApiIgnore AuthUser authUser,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        ResponseEntity<String> targetResponse = restTemplate.getForEntity(TARGET_SERVICE_URL, String.class);
        log.info("Requested {}. Forwarding request to {}", URL, TARGET_SERVICE_URL);

        // Возвращаем ответ от другого сервиса клиенту
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(targetResponse.getBody());
    }
}