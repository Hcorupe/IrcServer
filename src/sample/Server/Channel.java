package sample.Server;

import sample.Client.ClientJoinMsgObserver;
import sample.Client.ClientObserver;
import sample.Common.ChatMsg;
import sample.Common.JoinChannelMsg;
import java.io.IOException;
import java.util.ArrayList;

public class Channel{
    ArrayList<ClientConnection> clients = new ArrayList<ClientConnection>();
    ArrayList<ClientObserver> myobservers = new ArrayList<>();
    ArrayList<ClientJoinMsgObserver> JoinMsgObservers = new ArrayList<>();
    public Channel() {
    }

    public void addClient(JoinChannelMsg msg) throws IOException {
        System.out.println("Adding Client");
        if(clients.contains(msg.getClient())){
            return; // prevent client from adding multiple times
        }
        for(ClientConnection c : clients){
            c.sendMessage(msg);
        }
        clients.add(msg.getClient());
    }
    public void deleteClient(ClientConnection client) {
        System.out.println("Remove client connection");
        clients.remove(client);
    }
    public void PublishToChannel(ChatMsg msg){
        System.out.println("PUBLISHING TO CHANNELS");
        for(ClientConnection c : clients){
            c.sendMessage(msg);
        }
        System.out.println(clients.size());
    }

}
