package com.example.mailSender.service;

import com.example.mailSender.entity.MailDetailEntity;
import com.example.mailSender.repository.MailDetailRepository;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Service
public class FinalMailService {


    @Autowired
    MailDetailRepository repository;
    @Autowired
    FinalGraphMailService finalGraphMailService;
    @Autowired
    IfsUtil ifsUtil;


    public void sendMailToAll() throws IOException, AS400SecurityException {
        List<MailDetailEntity> listMail = repository.findAll();

        for (MailDetailEntity list : listMail) {

            IFSFile file = ifsUtil.getIfsFile(list.getPath());
            String fileString = readFromInputStream(file);

            finalGraphMailService.getGraphClient(fileString, list.getSubject(), list.getMessage(), list.getRecipient());

        }


    }


    // read IFS File and store in String
    private String readFromInputStream(IFSFile inputStream) throws IOException, AS400SecurityException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new IFSFileReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }


}
