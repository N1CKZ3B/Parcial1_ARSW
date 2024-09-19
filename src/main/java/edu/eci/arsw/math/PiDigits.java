package edu.eci.arsw.math;

import java.util.LinkedList;
import java.util.Scanner;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    private static LinkedList<CalcDigits> threads = new LinkedList<>();

    private static int total;

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) throws InterruptedException {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }
        int COMIENZO = start;
        int CONTEO_FINAL = count;
        int partir = count / N;
        for (int i = 0 ; i < N ; i++){
            if (i == N-1){
                start = partir * (N-1);
                count = CONTEO_FINAL;
            }else if (i == 0){
                start = COMIENZO;
                count = partir;
            }else{
                start = partir * i;
                count = partir * (i+1);
            }

            CalcDigits t = new CalcDigits(start,count);
            threads.add(t);
        }

        for (CalcDigits t : threads){
            t.start();

        }

        total = 0;
        byte[] b = new byte[0];
        while( b.length < CONTEO_FINAL){
            Thread.sleep(5000);
            for (CalcDigits t : threads){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String s = b.toString()+t.getBytes().toString();

                b = s.getBytes();
            }
            System.out.println("Se han encontrado " + b.length +"bytes");

            for (CalcDigits t : threads){
                t.start();
            }

            Scanner sc = new Scanner("Ingrese enter para continuar ...");
        }


        return b;
    }

}
