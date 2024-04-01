/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tskin
 */
public class rpsClient implements Runnable
{
    //variables
    private String ip;
    private int port;
    Socket con;
    private int count = 0;
    private int points = 10;
    ArrayList<rpsCard> hand = new ArrayList<rpsCard>(5);
    DataInputStream fromServer;
    DataOutputStream out;
    ObjectInputStream fromS;
    ObjectOutputStream toS;
    
    //constructor
    public rpsClient(String i, int p)
    {
        ip = i;
        port = p;
        try 
        {
            System.out.println("Connecting to server");
            con = new Socket(ip, port);
            out = new DataOutputStream(con.getOutputStream());
            fromS = new ObjectInputStream(con.getInputStream());
            toS = new ObjectOutputStream(con.getOutputStream());
        }
        catch(IOException e)
        {
            System.out.println("ERROR connecting as a client" + e.getMessage());
        }
    }

    @Override
    public void run() 
    {
        
        while(true)
        {
            try
            {
                for(int i = 0; i < 5; i++){
                    int y = i+1;
                hand.add((rpsCard) fromS.readObject());
                System.out.println("Card # "+y+": "+hand.get(i));
                }
            } catch (IOException ex) {
                Logger.getLogger(rpsClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(rpsClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(" Select Your cards");
            System.out.println("Points remaining: "+points);
            Scanner in = new Scanner(System.in);
            try
            {
                
                while(points > 0){
                    int x = in.nextInt();
                    int y = x-1;
                    toS.writeObject(hand.get(y));
                    points = points - hand.get(y).getNum();
                    hand.remove(y);
                    for(int i = 0; i < hand.size(); i++){
                    System.out.println("Card # "+(i+1)+": "+hand.get(i));
                    }
                    System.out.println("Points remaining: "+points);
                }
            }
            catch(IOException e)
            {
                System.out.println("help pls");
            }
            
            try
            {
                fromServer = new DataInputStream(con.getInputStream());
                System.out.println(fromServer.readUTF());
            } catch (IOException ex) {
                Logger.getLogger(rpsClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    }
}


   