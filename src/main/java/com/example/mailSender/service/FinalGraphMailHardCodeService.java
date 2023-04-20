package com.example.mailSender.service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.AttachmentCollectionResponse;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class FinalGraphMailHardCodeService {
    private static final List<String> graphApiScopes = Arrays.asList("https://graph.microsoft.com/.default");

    public GraphServiceClient getGraphClient() throws IOException {


        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder().clientId("2cbed30c-91fa-477b-b68a-093a8e0946e7").clientSecret("A6Y8Q~43TVgu0RApN_KPaqctkDD3M1c8rN2PubBb").tenantId("273f45e0-e235-4dde-ab7a-fd3e631a88e0").build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(graphApiScopes, clientSecretCredential);

        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();

        Message message = new Message();
        //subject//
        message.subject = "From Graph Api";
        //Body Content//
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "THIS IS GRAPH API EMAIL";
        message.body = body;
        //Recipient address//
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();

        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "carapplicationcare@gmail.com";

        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;

        //File Attachment//
        FileAttachment attachment = new FileAttachment();
        attachment.name = "smile.pdf";
        Path path = Paths.get("C:/Users/AbhishekJadhav/Desktop/Software Design Specification final.pdf");
        attachment.oDataType = "#microsoft.graph.fileAttachment";

        attachment.contentBytes = Files.readAllBytes(path);
        List<Attachment> attachmentsList = new LinkedList<Attachment>();
        attachmentsList.add(attachment);


        AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
        attachmentCollectionResponse.value = attachmentsList;
        AttachmentCollectionPage attachmentCollectionPage = new AttachmentCollectionPage(attachmentCollectionResponse, null);
        message.attachments = attachmentCollectionPage;

        graphClient.users("abhishek.jadhav@programmers.io").sendMail(UserSendMailParameterSet.newBuilder().withMessage(message).withSaveToSentItems(null).build()).buildRequest().post();


        return graphClient;
    }


}
