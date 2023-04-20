package com.example.mailSender.service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.FileAttachment;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.AttachmentCollectionResponse;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Service
public class ItextService {

    private static final List<String> graphApiScopes = Arrays.asList("https://graph.microsoft.com/.default");

    public GraphServiceClient getGraphClient(byte[] bytes) {
        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("2cbed30c-91fa-477b-b68a-093a8e0946e7")
                .clientSecret("A6Y8Q~43TVgu0RApN_KPaqctkDD3M1c8rN2PubBb")
                .tenantId("273f45e0-e235-4dde-ab7a-fd3e631a88e0")
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(graphApiScopes, clientSecretCredential);

        final GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();

        Message message = new Message();
        //subject
        message.subject = "Meet for lunch?";
        //Body Content
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "The new cafeteria is open.";
        message.body = body;
        //Recipient address
        LinkedList<Recipient> toRecipientsList = new LinkedList<>();

        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "carapplicationcare@gmail.com";

        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;
        LinkedList<Attachment> attachmentsList = new LinkedList<>();

        FileAttachment attachments = new FileAttachment();
        attachments.oDataType = "#microsoft.graph.fileAttachment";
        attachments.name = "New Text Document.pdf";

        String encodedString = Base64.getEncoder().encodeToString(bytes);
        attachments.contentBytes = Base64.getDecoder().decode(encodedString);
        attachmentsList.add(attachments);

        AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
        attachmentCollectionResponse.value = attachmentsList;
        message.attachments = new AttachmentCollectionPage(attachmentCollectionResponse, null);

        graphClient.users("abhishek.jadhav@programmers.io").
                sendMail(UserSendMailParameterSet.newBuilder().
                        withMessage(message).
                        withSaveToSentItems(null).
                        build()).
                buildRequest().
                post();

        return graphClient;
    }


}
