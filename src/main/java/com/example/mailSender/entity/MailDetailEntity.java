package com.example.mailSender.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "MDE")
public class MailDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String recipient;
    private String subject;
    private String message;
    private String path;


}
