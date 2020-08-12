import java.util.Random;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class PathIntegral_HO extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("Alpha","Average Energy","1D Harmonic Potential Monte Carlo");
  double alpha;

  public double[] beads;
  public double Time;
  public double N;

  public double local_Energy(double x, double y, double dt){
      double tmp = (1.0/dt) + (dt/2.0);
      tmp -= (((x-y)/dt) + (dt/2.0))*(((x-y)/dt) + (dt/2.0));
      tmp += x*x;
      return tmp;
  }

  public double deltaS(double x, double y, double dt){
    return (x-y)*(x-y)/(2.0*dt) + dt*(x*x + y*y)/(4.0);
  }

  public double Change_in_Action(double[] bead, int changed, double x_old, double x_new, double dt){
      int next = (changed+1)%bead.length;
      int prev = (changed-1);
      if(prev < 0){ prev = bead.length - 1; }

      double y = bead[next];
      double z = bead[prev];

      double S_old = deltaS(x_old, y, dt) + deltaS(x_old, z, dt);
      double S_new = deltaS(x_new, y, dt) + deltaS(x_new, z, dt);
      return (S_new - S_old);
  }


  public void MC_step


  public void reset(){
    frame.setMarkerSize(0,8);
    frame.setMarkerSize(1,8);
    frame.setConnected(0,true);
    N = 2;
    Time = 1.0;
    beads = new double[N];
    for(int i=0;i<beads.length;i++){
      beads[i] = 2.0*Math.Random() - 1.0;
    }
  }


  public void calculate(){
    try{
      FileWriter ofile = new FileWriter("Metrodata_new.txt");
      BufferedWriter oss = new BufferedWriter(ofile);




      oss.close();
      ofile.close();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);


      return;
    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new PathIntegral_HO());
  }

}
