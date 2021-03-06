package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import sample.Common.*;
import sample.Server.ClientConnection;
import sample.Server.ServerPublishThread;
import sample.cilent.Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class IRC extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    static ArrayList<ClientConnection> clientConnection = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(args[0]);

        if(args[0].equals("server")) {

            ServerSocket socket = new ServerSocket(800);
            Thread workerThread = new Thread(new ServerPublishThread());
            workerThread.start();
            while (true) {
                System.out.println("Before accept ");
                ClientConnection client = new ClientConnection(socket.accept());
                System.out.println("after accept ");
                clientConnection.add(client);
            }
        }
            else if(args[0].equals("client")){
                Controller controller = new Controller();
                launch(args);
                Thread.sleep(5000);
                Socket Clientsocket = new Socket("localhost",800);
                System.out.println("Before creating client ");
                Client client = new Client(Clientsocket);
                client.sendMessage(controller.getChannel(),"Text..");
                //client.joinChannel(controller.getCheckBox_Channel1());
                //client.sendMessage("ch1","Text ...");
                client.shutdown();
            }
    }
}
