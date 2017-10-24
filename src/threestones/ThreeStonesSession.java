/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threestones;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Philippe
 */
public class ThreeStonesSession {
    
    private boolean playAgain;
    private boolean gameOver;
    private Socket clientSocket;
    private ThreeStonesPacket packet;
    private InputStream in;
    private OutputStream out;
    private Scanner reader;

    public ThreeStonesSession(InputStream in, OutputStream out) {
        this.playAgain = true;
        this.gameOver = false;
        this.in = in;
        this.out = out;
    }
    
    public void playSession(){
        
    }
}
