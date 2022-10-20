package com.charminseok.accountbook.service;

import com.charminseok.accountbook.domain.AccountBook;
import com.charminseok.accountbook.dto.RequestAccountBookDto;
import com.charminseok.accountbook.repository.AccountBookMapper;
import com.charminseok.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookService {
    private final AccountBookMapper accountBookMapper;

    public ResponseEntity<?> getAccountBookList(int userId){
        try {
            List<AccountBook> accountBooks = accountBookMapper.selectAccountBookList(userId);
            return ResponseDto.response(HttpStatus.OK, "", accountBooks);
        } catch (Exception e){
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "error", Collections.EMPTY_LIST);
        }
    }

    public ResponseEntity<?> getAccountBook(RequestAccountBookDto requestAccountBookDto){
        try {
            AccountBook accountBook = accountBookMapper.selectAccountBook(requestAccountBookDto);
            if(accountBook.getUserId() != requestAccountBookDto.getUserId()){
                return ResponseDto.response(HttpStatus.FORBIDDEN, "권한 없음", Collections.EMPTY_LIST);
            }
            return ResponseDto.response(HttpStatus.OK, "", accountBook);
        } catch (Exception e) {
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "error", Collections.EMPTY_LIST);
        }
    }

    public ResponseEntity<?> inputMoneyInfo(RequestAccountBookDto requestAccountBookDto) {
        try {
            accountBookMapper.insertMoneyInfo(requestAccountBookDto);
            return ResponseDto.response(HttpStatus.OK, "입력 완료", Collections.EMPTY_LIST);
        } catch (Exception e) {
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "입력 실패", Collections.EMPTY_LIST);
        }
    }

    public ResponseEntity<?> updateMoneyInfo(RequestAccountBookDto requestAccountBookDto) {
        try {
            accountBookMapper.updateMoneyInfo(requestAccountBookDto);
            return ResponseDto.response(HttpStatus.OK, "수정 완료", Collections.EMPTY_LIST);
        } catch (Exception e) {
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "수정 실패", Collections.EMPTY_LIST);
        }
    }

    public ResponseEntity<?> deleteAccountBook(RequestAccountBookDto requestAccountBookDto) {
        try {
            requestAccountBookDto.setAccountBookId(requestAccountBookDto.getAccountBookId());
            AccountBook accountBook = accountBookMapper.selectAccountBook(requestAccountBookDto);

            if(accountBook.getUserId() != requestAccountBookDto.getUserId()){
                return ResponseDto.response(HttpStatus.FORBIDDEN, "권한 없음", Collections.EMPTY_LIST);
            }

            accountBookMapper.deleteAccountBook(requestAccountBookDto);

            return ResponseDto.response(HttpStatus.OK, "삭제 완료", Collections.EMPTY_LIST);
        } catch (Exception e) {
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패", Collections.EMPTY_LIST);
        }
    }

    public ResponseEntity<?> getDeletedAccountBook(int userId) {
        try {
            List<AccountBook> accountBooks = accountBookMapper.selectDeletedAccountBook(userId);
            return ResponseDto.response(HttpStatus.OK, "", accountBooks);
        } catch (Exception e){
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "error", Collections.EMPTY_LIST);
        }
    }

    public ResponseEntity<?> restoreAccountBook(int accountBookId, int userId) {
        try {

            RequestAccountBookDto requestAccountBookDto = new RequestAccountBookDto();
            requestAccountBookDto.setAccountBookId(accountBookId);
            AccountBook accountBook = accountBookMapper.selectAccountBook(requestAccountBookDto);
            if(accountBook.getUserId() != userId){
                return ResponseDto.response(HttpStatus.FORBIDDEN, "권한 없음", Collections.EMPTY_LIST);
            }

            accountBookMapper.restoreAccountBook(accountBookId);
            return ResponseDto.response(HttpStatus.OK, "복원 완료", Collections.EMPTY_LIST);
        } catch (Exception e){
            return ResponseDto.response(HttpStatus.INTERNAL_SERVER_ERROR, "error", Collections.EMPTY_LIST);
        }

    }
}
