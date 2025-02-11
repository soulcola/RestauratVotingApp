package com.petrtitov.votingApp.web.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.petrtitov.votingApp.model.User;
import com.petrtitov.votingApp.to.UserTo;
import com.petrtitov.votingApp.util.UsersUtil;
import com.petrtitov.votingApp.web.AuthUser;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

import static com.petrtitov.votingApp.util.validation.ValidationUtil.assureIdConsistent;
import static com.petrtitov.votingApp.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return super.get(authUser.id());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal @ApiIgnore AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = repository.prepareAndSave(UsersUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal @ApiIgnore AuthUser authUser) {
        log.info("update {} with id={}", userTo, authUser.id());
        assureIdConsistent(userTo, authUser.id());
        User user = get(userTo.id());
        repository.prepareAndSave(UsersUtil.updateFromTo(user, userTo));
    }
}