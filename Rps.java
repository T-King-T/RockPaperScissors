/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author tskin
 */
public class Rps {

    public static void main(String[] args) 
    {
        ArrayList<rpsCard> deck = new ArrayList<rpsCard>();
        Thread driver;
        
        Scanner s = new Scanner(System.in);
        System.out.println("Enter 'S' for Server or 'C' for Client.");
        String ans = s.next();
         
        if(ans.compareTo("S") == 0)
        {
            //Array of cards    
        
        int i = 0;
        deck.ensureCapacity(18);
        while(i<=18)
        {
            if (i < 6)
            {
                int y = i+1;
                rpsCard x = new rpsCard("Rock", i);
                deck.add(i,  x);
                i++;
            }
            
            else if(i >= 6 && i < 12)
            {
                int y = (i%6)+1;
                rpsCard x = new rpsCard("Paper", y);
                deck.add(i, x);
                i++;
            }
            else
            {
                int y = (i%6)+1;
                rpsCard x = new rpsCard("Scissors", y);
                deck.add(i, x);
                i++;
            }
        }
        
        Collections.shuffle(deck, new Random(3));
        System.out.println(deck.toString());
        
//start server            
        System.out.println("Starting Server...");
            driver = new Thread(new Listener(9000, 100, deck));  
        }
        else if(ans.compareTo("C")==0)
        {
            System.out.println("Starting Client");
            driver = new Thread(new rpsClient("127.0.0.1", 9000));
            
            
        }
        else
        {
            System.out.println("Incorrect input!");
            return;
        }
    driver.start();
    
     try
        {
            driver.join();
           
        }
        catch(InterruptedException e)
        {
        System.out.println("Error waiting on thread! "+e.getMessage());
         }
    }
}
