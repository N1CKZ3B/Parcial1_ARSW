package edu.eci.arsw.math;

public class CalcDigits extends Thread{
    private int start;
    private int count;

    private int longitud;

    private byte[] bytes;
    public CalcDigits(int start, int count){
        this.start = start;
        this.count = count;

    }

    public void run(){
       bytes = PiDigits.getDigits(start,count);
       longitud= PiDigits.getDigits(start,count).length;
        System.out.println(Main.bytesToHex(bytes));
    }

}