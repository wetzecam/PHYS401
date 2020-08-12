import java.util.Random;
import java.io.*;

public class Integration{

  public static double integrand_1(double x){
    return 4.0/(1 + Math.pow(x,2));
  }

  public static double invP(double r, double a){
    double A = a/(1.0 - Math.exp(-a));

    return -Math.log(1.0 - r*a/A)/a;
  }

  public static void main(String[] args){
    try{
      FileWriter ofile = new FileWriter("MCdata.txt");
      BufferedWriter oss = new BufferedWriter(ofile);

      double fnc = 0.0;
      double Isum = 0.0;
      int n = 1;
      double rnd;

      // Bounds of integration
      double a = 0.0;
      double b = 1.0;

      while(n <= 20000){  // Monte carlo loop
        rnd = Math.random();
        fnc = integrand_1((b-a)*rnd + a);
        Isum += fnc;
        oss.write(n+" "+fnc+" "+(Isum/(double)(n))+" "+Isum+" "+Math.PI+'\n');
        n++;
      }


      ofile.close();
    }catch (Exception e){//Catch exception if any
     System.err.println("Error: " + e.getMessage());
    }
    System.out.println();

    // weighted Montecarlo
    try{
      FileWriter ofile = new FileWriter("wMCdata.txt");
      BufferedWriter oss = new BufferedWriter(ofile);

      double fnc = 0.0;
      double Isum = 0.0;
      int n = 1;
      double rnd;

      // Bounds of integration
      double a = 0.0;
      double b = 1.0;

      double exp = Double.valueOf(args[0]);
      double A = exp/(1.0 - Math.exp(-exp));

      while(n <= 20000){  // Monte carlo loop

        rnd = invP(Math.random(), exp);
        fnc = integrand_1((b-a)*rnd + a)/(A*Math.exp(-rnd*exp));
        Isum += fnc;

        oss.write(n+" "+fnc+" "+(Isum/(double)(n))+" "+Isum+" "+Math.PI+'\n');
        n++;
      }


      ofile.close();
    }catch (Exception e){//Catch exception if any
     System.err.println("Error: " + e.getMessage());
    }

    return;
  }
}
