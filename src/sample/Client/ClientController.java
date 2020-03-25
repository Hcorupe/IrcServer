package sample.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Common.ChatMsg;
import sample.Common.JoinChannelMsg;

import java.net.*;
import java.io.IOException;

public class ClientController implements ClientObserver,ClientJoinMsgObserver {

    @FXML
    Menu changeCh;
    @FXML
    TextField ipAddress;
    @FXML
    TextField sendBox;
    @FXML
    TextArea outputUI;
    @FXML
    Button sendButton;

    String channel;
    String username;
    String ipAdd;
    String currentMessage;


    Socket clientSocket = new Socket("localhost",8000);
    Client client = new Client(clientSocket);

    public ClientController() throws IOException{
        System.out.println("Adding OBServe");
        client.addObserver(this);
        client.addJoinChannelMsg( this); //
    }

    public void initData(String name, String ch,String ip) throws IOException {
        username = name;
        channel = ch;
        ipAdd = ip;
        ipAddress.setText(ipAdd);
        client.joinChannel(channel,username); //

    }

    public void send() throws IOException {
        currentMessage = sendBox.getText();
        client.sendMessage(channel,currentMessage,username);
        sendBox.setText("");
    }
    public void disconnect() throws IOException {
        sendButton.setDisable(true);
        sendBox.setDisable(true);
        client.shutdown();
    }
    public void changeChannel(ActionEvent Event) throws IOException {   //Changes the channel on the second UI
        MenuItem clickedButton = (MenuItem) Event.getTarget();
        channel = clickedButton.getText();
        changeCh.setText(channel);
        client.joinChannel(channel,username); //
        outputUI.setText(null);
    }

    public void displayMsg(String message,String whoSentIt){
        outputUI.appendText(whoSentIt + ": " + message + "\n");
    }

    public void displayJoinChannelMsg(String channel,String whoSentIt){
        outputUI.appendText( whoSentIt +" : Joined the channel");
    }

    @Override
    public void update(ChatMsg msg) {
        //Add msg text to text box
        displayMsg(msg.getData(),msg.getUser());
    }

    @Override
    public void updateJoinChannel(JoinChannelMsg msg) {
        displayJoinChannelMsg(msg.getChannel() , msg.getUserName());
    }


}
