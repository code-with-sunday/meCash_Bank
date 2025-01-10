package com.sunday.mecashbank.repository;

import com.sunday.mecashbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_AccountNumberOrderByTransactionDateDesc(String accountNumber);

}
