package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.Collator;

class Comparaa extends Comparator<T>{
       public static void main(String[] args) {
           Collator comp = Collator.getInstance();
            comp.setStrength(Collator.PRIMARY);
       }
}