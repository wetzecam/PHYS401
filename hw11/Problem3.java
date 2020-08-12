import java.util.Random;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class Problem3 extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("Alpha","Average Energy","1D Harmonic Potential Monte Carlo");
  double E_0;
  public void reset(){
    frame.setMarkerSize(0,5);
    frame.setConnected(true);
  }

  public static double[] BM_3D(double dT){
    double[] r = new double[3];
    double u,v,w;
    while(true){
      double s = 0.0;

      u = Math.random()*2.0 - 1.0;
      v = Math.random()*2.0 - 1.0;
      w = Math.random()*2.0 - 1.0;
      s = u*u+w*w+v*v;

      if(s <= 1.0){

        r[0] = u*Math.sqrt(-2.0*dT*Math.log(s)/s);;
        r[1] = v*Math.sqrt(-2.0*dT*Math.log(s)/s);
        r[2] = w*Math.sqrt(-2.0*dT*Math.log(s)/s);
        return r;
      }
    }
  }

  public static double local_Energy(double[] x, double a){
    double s = 0.0;
    for(int i=0;i<3;i++){
      s+= x[i]*x[i];
    }
    //System.out.println(s);
    return 3.0*a - (2.0*a*a*s + Math.sqrt(1.0/s));
  }

  public static double MonteCarlo(double a){
    double fnc = 0.0;
    double Isum = 0.0;
    int n = 1;
    double[] rnd = new double[3];
    double dT = 1.0/(a*4.0);
    while(n <= 1000000){  // Monte carlo loop

      fnc = local_Energy(BM_3D(dT), a);
      Isum += fnc;
      n++;
    }
    return (Isum/(double)(n));
  }



  public void calculate(){
      E_0 = 0.02;
      while(E_0 <0.52){
        double MCresult = MonteCarlo(E_0);
        frame.append(0, E_0, MCresult);
        E_0 += 0.0200000;
      }

    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    return;
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new Problem3());
  }

}
