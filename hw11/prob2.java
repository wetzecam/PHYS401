import java.util.Random;
import java.io.*;


public class prob2{

  public static double BM_1D(double dT){
    while(true){
      double u = 2.0*Math.random() - 1.0;
      double w = 2.0*Math.random() - 1.0;
      double s = u*u + w*w;
      if(s <= 1.0){
        return u*Math.sqrt(-2.0*dT*Math.log(s)/s);
      }
    }
  }

  public static double local_Energy(double x, double a){
    return a - (0.5 - 2.0*a*a)*x*x;
  }

  public static double MonteCarlo(double a){
    double fnc = 0.0;
    double Isum = 0.0;
    int n = 1;
    double rnd;
    double dT = 1.0/(a*4.0);
    while(n <= 1000000){  // Monte carlo loop
      rnd = BM_1D(dT);
      fnc = local_Energy(rnd, a);
      Isum += fnc;
      n++;
    }
    return (Isum/(double)(n));
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
      double a = 0.2;
      double b = 1.0;

      while(n <= 12){  // Monte carlo loop

        Isum = MonteCarlo(a);
        oss.write(Isum+" "+'\n');
        System.out.println(a+"  "+Isum);
        a += 0.05;
        n++;
      }


      ofile.close();
    }catch (Exception e){//Catch exception if any
     System.err.println("Error: " + e.getMessage());
    }


  }
}
