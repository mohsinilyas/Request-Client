/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestclient;

/**
 *
 * @author IL
 */
public class Message {
    
    private int magicNo;
    private int clientID;
    private int commandNo;
    private char[] key_range_start = new char[5];
    private char[] key_range_end = new char[5];
    private char[] hash = new char[24];
    
    int sizeOfMagicNumber = 5;
    int sizeOfClientID = 4;
    int sizeofCommandCode = 1;
    int sizeOfKey_Range_Start = 4;
    int sizeOfKey_Range_End = 4;
    int sizeOfHash = 32;
    
    Message(int magicNo) {
        this.magicNo = magicNo;
        this.clientID = 0;
        this.commandNo = 0;
        this.hash = null;
        key_range_start = "0000".toCharArray();
        key_range_end = "0000".toCharArray();
    } 
    

    /**
     * @return the magicNo
     */
    public int getMagicNo() {
        return magicNo;
    }

    /**
     * @param magicNo the magicNo to set
     */
    public void setMagicNo(int magicNo) {
        this.magicNo = magicNo;
    }

    /**
     * @return the clientID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * @return the commandNo
     */
    public int getCommandNo() {
        return commandNo;
    }

    /**
     * @param commandNo the commandNo to set
     */
    public void setCommandNo(int commandNo) {
        this.commandNo = commandNo;
    }

    /**
     * @return the key_range_start
     */
    public String getKey_range_start() {
        return String.valueOf(key_range_start);
    }

    /**
     * @param key_range_start the key_range_start to set
     */
    public void setKey_range_start(char[] key_range_start) {
        this.key_range_start = key_range_start;
    }

    /**
     * @return the key_range_end
     */
    public String getKey_range_end() {
        return String.valueOf(key_range_end);
    }

    /**
     * @param key_range_end the key_range_end to set
     */
    public void setKey_range_end(char[] key_range_end) {
        this.key_range_end = key_range_end;
    }
    
    /**
     * @return the hash
     */
    public String getHash(){
        return String.valueOf(hash);
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(char[] hash) {
        this.hash = hash;
    }
    
    public byte[] convertMessageObjectIntoBytes(){

        String s = "";
        s += String.valueOf(getMagicNo());
        s += String.valueOf(getClientID());
        s += String.valueOf(getCommandNo());
        s += getKey_range_start();
        s += getKey_range_end();
        s += getHash();

        byte[] result = s.getBytes();
        System.out.println("results is "+new String(result, 0 ,result.length));
        return result;
    }
    
    public void parseBytesIntoMessageObject(byte[] bytes){

        String result;

        result = new String(bytes,0, sizeOfMagicNumber);
        this.setMagicNo(Integer.parseInt(result));

        result = new String(bytes,sizeOfMagicNumber, sizeofCommandCode);
        this.setCommandNo(Integer.parseInt(result));

        result = new String(bytes, sizeofCommandCode, sizeOfClientID);
        this.setClientID(Integer.parseInt(result));

        result = new String(bytes,sizeOfClientID, sizeOfKey_Range_Start);
        this.setKey_range_start(result.toCharArray());

        result = new String(bytes,sizeOfKey_Range_Start, sizeOfKey_Range_End);
        this.setKey_range_end(result.toCharArray());

        result = new String(bytes,sizeOfKey_Range_End, sizeOfHash);
        this.setHash(result.toCharArray());

    }
}
