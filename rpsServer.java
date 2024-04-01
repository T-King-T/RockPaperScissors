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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tskin
 */
public class rpsServer implements Runnable
{
    private Socket clientCon1, clientCon2;
    private ArrayList<rpsCard> deck;
    private ArrayList<rpsCard> p1_choice = new ArrayList<rpsCard>(5);
    private ArrayList<rpsCard> p2_choice = new ArrayList<rpsCard>(5);
    private int p1R,p1P,p1S, p2R, p2P, p2S;
    private int p1_points, p2_points;
    ObjectOutputStream top1, top2;
    ObjectInputStream fromp1, fromp2;
    DataOutputStream toP1D, toP2D;
    DataInputStream fromP1D, fromP2D;
    
    public rpsServer(Socket c1, Socket c2, ArrayList<rpsCard> al)
    {
        clientCon1 = c1;
        clientCon2 = c2;
        deck = al;
        try
        {
            top1 = new ObjectOutputStream(clientCon1.getOutputStream());
            fromp1 = new ObjectInputStream(clientCon1.getInputStream());
            top2 = new ObjectOutputStream(clientCon2.getOutputStream());
            fromp2 = new ObjectInputStream(clientCon2.getInputStream());
            toP1D = new DataOutputStream(clientCon1.getOutputStream());
            toP2D = new DataOutputStream(clientCon2.getOutputStream());
            fromP1D = new DataInputStream(clientCon1.getInputStream());
            fromP2D = new DataInputStream(clientCon2.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() 
    {
        if(clientCon1.isConnected())
                System.out.println("Player 1 connected");
        if(clientCon2.isConnected())
                System.out.println("Player 2 connected");
        while(clientCon1.isConnected() && clientCon2.isConnected())
        {
            try
            {
                for(int i = 0; i < 5; i++)
                {
                    top1.writeObject(deck.get(0));
                    deck.remove(0);
                    top2.writeObject(deck.get(0));
                    deck.remove(0);
                }
            }  catch(IOException ex)
                        {
                            System.out.println("help");
                        }
            
            
            try
            {
                p1_choice.add((rpsCard) fromp1.readObject());
                p2_choice.add((rpsCard) fromp2.readObject());
            }
            catch(IOException e)
            {
            } 
            catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("player 1 selected: " + p1_choice.toString());
            System.out.println("player 2 selected: " + p2_choice.toString());
            
            while(!p1_choice.isEmpty())
            {
                //if(null != p1_choice.get(0).getSuit())
                switch (p1_choice.get(0).getSuit()) {
                    case "Rock":
                        p1_choice.remove(0);
                        p1R++;
                        break;
                    case "Paper":
                        p1_choice.remove(0);
                        p1P++;
                        break;
                    case "Scissors":
                        p1_choice.remove(0);
                        p1S++;
                        break;
                    default:
                        break;
                }
            }
            while(!p2_choice.isEmpty())
            {
                //if(null != p2_choice.get(0).getSuit())
                switch (p2_choice.get(0).getSuit()) {
                    case "Rock":
                        p2_choice.remove(0);
                        p2R++;
                        break;
                    case "Paper":
                        p2_choice.remove(0);
                        p2P++;
                        break;
                    case "Scissors":
                        p2_choice.remove(0);
                        p2S++;
                        break;
                    default:
                        break;
                }
            }
           //p1 R beat S
           if(p2S > 0 && p1R <= p2S)
           {
               p1_points += p1R;
           }
           else if (p2S > 0 && p1R > p2S)
           {
               p1_points += p2S;
           }
          
           //p2 R beat S
           if(p1S > 0 && p2R <= p2S)
           {
               p2_points += p2R;
           }
           else if (p1S > 0 && p2R > p1S)
           {
               p2_points += p1S;
           }
           
           //p1 S beat P
           if(p2P > 0 && p1S <= p2P)
           {
               p1_points += p1S;
           }
           else if(p2P > 0 && p1S > p2P)
           {
               p1_points += p2P;
           }
           
           //p2 S beat P
           if(p1P > 0 && p2S <= p1P)
           {
               p2_points += p2S;
           }
           else if(p1P > 0 && p2S > p1P)
           {
               p2_points += p1P;
           }
           
           //p1 P beat R
           if(p2R > 0 && p1P <= p2R)
           {
               p1_points += p1P;
           }
           else if(p2R > 0 && p1P > p2R)
           {
               p1_points += p2R;
           }
           
           //p2 P beat R
           if(p1R > 0 && p2P <= p1R)
           {
               p2_points += p2P;
           }
           else if(p1R > 0 && p2P > p1R)
           {
               p2_points += p1R;
           }
           
           if(p1_points > p2_points)
           {
               System.out.println("Player 1 wins!");
                try {
                    toP1D.writeUTF("You Win! Play Again (YES or NO)");
                } catch (IOException ex) {
                    Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    toP2D.writeUTF("You Lose. Play Again (YES or NO)");
                } catch (IOException ex) {
                    Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           else if(p1_points < p2_points)
           {
               System.out.println("Player 2 wins!");
                try {
                    toP1D.writeUTF("You Lose. Play Again (YES or NO)");
                } catch (IOException ex) {
                    Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    toP2D.writeUTF("You Win! Play Again (YES or NO)");
                } catch (IOException ex) {
                    Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           else
           {
               try {
                    toP1D.writeUTF("You Tied. Play Again (YES or NO)");
                } catch (IOException ex) {
                    Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    toP2D.writeUTF("You Tied. Play Again (YES or NO)");
                } catch (IOException ex) {
                    Logger.getLogger(rpsServer.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        }
    }
}
