import java.util.Random;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class Problem2 extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("Alpha","Average Energy","1D Harmonic Potential Monte Carlo");
  double E_0;
  public void reset(){

  }

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
    return a + (0.5 - 2.0*a*a)*x*x;
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



  public void calculate(){
      E_0 = 0.2;
      while(E_0 <0.81){
        double MCresult = MonteCarlo(E_0);
        frame.append(0, E_0, MCresult);
        E_0 += 0.0500000;
      }

    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    return;
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new Problem2());
  }

}
