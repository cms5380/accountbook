package com.charminseok.accountbook.controller;

import com.charminseok.accountbook.domain.AccountBook;
import com.charminseok.accountbook.dto.RequestAccountBookDto;
import com.charminseok.accountbook.service.AccountBookService;
import com.charminseok.common.dto.ResponseDto;
import com.charminseok.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account-book")
public class AccountBookController {
    private final AccountBookService accountBookService;

    @GetMapping
    public ResponseEntity<?> getAccountBookList() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountBookService.getAccountBookList(user.getId());
    }

    @GetMapping("/{accountBookId}")
    public ResponseEntity<?> getAccountBook(@PathVariable("accountBookId") int accountBookId) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RequestAccountBookDto requestAccountBookDto = new RequestAccountBookDto();
        requestAccountBookDto.setAccountBookId(accountBookId);
        requestAccountBookDto.setUserId(user.getId());

        return accountBookService.getAccountBook(requestAccountBookDto);
    }


    @PostMapping
    public ResponseEntity<?> inputMoneyInfo(@RequestBody RequestAccountBookDto requestAccountBookDto) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestAccountBookDto.setUserId(user.getId());

        return accountBookService.inputMoneyInfo(requestAccountBookDto);
    }

    @PutMapping("/{accountBookId}")
    public ResponseEntity<?> updateMoneyInfo(@PathVariable("accountBookId") int accountBookId,
                                             @RequestBody RequestAccountBookDto requestAccountBookDto) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        requestAccountBookDto.setAccountBookId(accountBookId);
        requestAccountBookDto.setUserId(user.getId());

        return accountBookService.updateMoneyInfo(requestAccountBookDto);
    }

    @DeleteMapping("/{accountBookId}")
    public ResponseEntity<?> deleteAccountBook(@PathVariable("accountBookId") int accountBookId) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RequestAccountBookDto requestAccountBookDto = new RequestAccountBookDto();
        requestAccountBookDto.setAccountBookId(accountBookId);
        requestAccountBookDto.setUserId(user.getId());

        return accountBookService.deleteAccountBook(requestAccountBookDto);

    }

    @GetMapping("/deleted")
    public ResponseEntity<?> getDeletedAccountBook() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return accountBookService.getDeletedAccountBook(user.getId());
    }

    @PatchMapping("/restore/{accountBookId}")
    public ResponseEntity<?> restoreAccountBook(@PathVariable("accountBookId") int accountBookId) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return accountBookService.restoreAccountBook(accountBookId, user.getId());
    }
}