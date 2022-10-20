package com.charminseok.accountbook.repository;

import com.charminseok.accountbook.domain.AccountBook;
import com.charminseok.accountbook.dto.RequestAccountBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountBookMapper {
    List<AccountBook> selectAccountBookList(int id);

    void insertMoneyInfo(RequestAccountBookDto requestAccountBookDto);

    void updateMoneyInfo(RequestAccountBookDto requestAccountBookDto);

    void deleteAccountBook(RequestAccountBookDto requestAccountBookDto);

    List<AccountBook> selectDeletedAccountBook(int id);

    void restoreAccountBook(int accountBookId);

    AccountBook selectAccountBook(RequestAccountBookDto requestAccountBookDto);
}
