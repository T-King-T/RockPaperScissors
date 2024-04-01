/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rps;

import java.io.Serializable;

/**
 *
 * @author tskin
 */
public class rpsCard implements Serializable
{
    private String suit;
    private int num;
    
    public rpsCard(String s, int n)
    {
        suit = s;
        num = n;
    }
    
    public String getSuit()
    {
        return suit;
    }
    
    public int getNum()
    {
        return num;
    }
    
    public String toString()
    {
        return num+" of "+suit;
    }
}

    

