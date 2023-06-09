package com.example.mailSender.service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class GraphClientSimpleMail {
    private static final List<String> graphApiScopes = Arrays.asList("https://graph.microsoft.com/.default");
//    String[] _scopes = new String[] {
//            "https://graph.microsoft.com/Mail.Send"};


    public void simpleGraphApiMail() {

//        final AuthorizationCodeCredential authCodeCredential = new AuthorizationCodeCredentialBuilder()
//                .clientId("2cbed30c-91fa-477b-b68a-093a8e0946e7")
//                .clientSecret("C248Q~.eDq_wP2yoixhm34uPirXG8fGkUBYJ3beE") //required for web apps, do not set for native apps
//                .tenantId("273f45e0-e235-4dde-ab7a-fd3e631a88e0")
//                .authorizationCode(authorizationCode)
//                .redirectUrl(" http://localhost:8080")                    //("https://localhost:8080/mailsend")
//                .build();
//
//        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(graphApiScopes, authCodeCredential);

        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("2cbed30c-91fa-477b-b68a-093a8e0946e7")
                .clientSecret("A6Y8Q~43TVgu0RApN_KPaqctkDD3M1c8rN2PubBb")
                .tenantId("273f45e0-e235-4dde-ab7a-fd3e631a88e0")
                .build();
        System.out.println("this ");
        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(graphApiScopes, clientSecretCredential);


        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();

        Message message = new Message();
        //subject
        message.subject = "Meet for lunch?";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "The new cafeteria is open.";
        message.body = body;

        //recipient
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "carapplicationcare@gmail.com";
        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;

        boolean saveToSentItems = false;

        graphClient.me()
                .sendMail(UserSendMailParameterSet
                        .newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(saveToSentItems)
                        .build())
                .buildRequest()
                .post();
    }
}
