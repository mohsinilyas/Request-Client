/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestclient;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author IL
 */
public class RequestClient {
    
    static List<String> arguments = new ArrayList<>();
    UDPConnection conn;
    static InetAddress serverAddress;
    static int serverPort;
    static String hash;
    Message message;
    int magicNo = 15440;
    
    public RequestClient() throws SocketException {
        this.conn = new UDPConnection();
        this.message = new Message(magicNo);
    }
    
    public static void setArguments(List<String> arg) throws UnknownHostException {

        serverAddress = serverAddress.getByName(arg.get(0));
        serverPort = Integer.parseInt(arg.get(1));
        hash = arg.get(2);
    }

    public void bcrypt(String password, String salt) {

        String hashedPassword = md5(salt + password);
        hash = hashedPassword;
    }
    
    public String md5(String input) {

        String md5 = null;

        if (null == input) {
            return null;
        }

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex) 
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
    
    public void sendData() throws IOException {
        
        byte[] b;
        message.setCommandNo(CommandType.HASH.getCode());
        message.setHash(hash.toCharArray());
        b = message.convertMessageObjectIntoBytes();
        conn.send(b, serverAddress, serverPort);
        
    }
    
    public void receiveData() throws IOException {
        byte[] b = new byte[1024];
        DatagramPacket dp = conn.receive(b);
        message.parseBytesIntoMessageObject(dp.getData());
        if( message.getCommandNo() == CommandType.DONE_NOT_FOUND.getCode()) {
            System.out.println("NOT FOUND");
        }
        else if ( message.getCommandNo() == CommandType.DONE_FOUND.getCode()) {
            System.out.println("FOUND: Password: "+message.getKey_range_start()+"; Hash: "+message.getHash());
        } 
    }
    
    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     * @throws java.net.SocketException
     */
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, InterruptedException {
        
        // TODO code application logic here
        RequestClient requestClient = new RequestClient();
       
        for (int i=0 ; i < args.length; i++) {
            RequestClient.arguments.add(args[i]);
            System.out.println("String "+arguments.get(i));
        }
        int i = 2;
        byte b = (byte)i;
        
        System.out.println("2 size "+b);
        System.out.println("10 size "+(byte)10);
        
       
        RequestClient.setArguments(arguments);
        requestClient.bcrypt(requestClient.hash, "aa");
        System.out.println("hash "+RequestClient.hash);
        requestClient.sendData();
        requestClient.receiveData();
    }

}
