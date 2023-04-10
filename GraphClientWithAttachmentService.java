package com.example.mailSender.service;


import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.AttachmentCollectionResponse;
import com.microsoft.graph.requests.GraphServiceClient;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GraphClientWithAttachmentService {
    private static final List<String> graphApiScopes = Arrays.asList("Mail.Send");


    public GraphServiceClient getGraphClient() {


        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("2cbed30c-91fa-477b-b68a-093a8e0946e7")
                .clientSecret("C248Q~.eDq_wP2yoixhm34uPirXG8fGkUBYJ3beE")
                .tenantId("273f45e0-e235-4dde-ab7a-fd3e631a88e0")
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(graphApiScopes, clientSecretCredential);

//        final GraphServiceClient graphServiceClient =
//                GraphServiceClient
//                        .builder()
//                        .authenticationProvider(tokenCredentialAuthProvider)
//                        .buildClient();
//
//        final User me = graphServiceClient.me().buildRequest().get();




        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();

        Message message = new Message();
        //subject
        message.subject = "Meet for lunch?";
        //Body Content
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "The new cafeteria is open.";
        message.body = body;
        //Recipient address
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();

        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "carapplicationcare@gmail.com";

        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;

        //Attachment for email
        LinkedList<Attachment> attachmentsList = new LinkedList<Attachment>();
        FileAttachment attachments = new FileAttachment();
        attachments.name = "C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt";

      //  attachments.contentBytes = Base64.getDecoder().
       //         decode("SGVsbG8gV29ybGQh");

        attachmentsList.add(attachments);

        AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
        attachmentCollectionResponse.value = attachmentsList;
        AttachmentCollectionPage attachmentCollectionPage = new AttachmentCollectionPage(attachmentCollectionResponse, null);
        message.attachments = attachmentCollectionPage;

        graphClient.me()
                        .

                sendMail(UserSendMailParameterSet
                        .newBuilder()
                                .

                        withMessage(message)
                                .

                        withSaveToSentItems(null)
                                .

                        build())
                        .

                buildRequest()
                        .

                post();
        return graphClient;
    }
}

