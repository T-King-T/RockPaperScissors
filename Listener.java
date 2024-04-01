/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author tskin
 */
public class Listener implements Runnable
{
    ServerSocket serve;
    int maxNum;
    ExecutorService connections;
    ArrayList<rpsCard> deck;
    
    public Listener(int port, int max, ArrayList<rpsCard> al)
    {
        connections = Executors.newFixedThreadPool(max);
        maxNum = max;
        deck = al;
        try
        {
            serve = new ServerSocket(port);
        }
        catch(IOException e)
        {
            System.out.println("Error creating server " + e.getMessage());
        }
    }
    

    @Override
    public void run() 
    {
        while(true)
        {
            System.out.println("Listening for Connection");
            try
            {
                Socket p1 = serve.accept();
                Socket p2 = serve.accept();
                System.out.println("Client Recieved, spawning new server");
                connections.execute(new rpsServer(p1, p2, deck));
            }
            catch(IOException e)
            {
                System.out.println("Listener Failed" + e.getMessage());
                try
                {
                    serve.close();
                }
                catch(IOException f)
                {
                    System.out.println("ERROR: Listener was already closed" + f.getMessage());
                }
                break;
            }
        }
        System.out.println("Server is no longer listening");
    }
    
    public int getMaxConnecitons()
    {
        return maxNum;
    }
    
    
}

    

