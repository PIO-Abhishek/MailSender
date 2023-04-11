package com.example.mailSender.repository;


import com.example.mailSender.entity.MailDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailDetailRepository extends JpaRepository<MailDetailEntity,Integer> {
}
