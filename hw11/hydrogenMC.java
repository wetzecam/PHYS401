import java.util.Random;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class hydrogenMC extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("Alpha","Average Energy","1D Harmonic Potential Monte Carlo");
  double alpha;
  public void reset(){
    frame.setMarkerSize(0,8);
    frame.setConnected(true);
  }

  // Returns 3 Gaussian distributed variable mu = 0; var = dT
  public static double[] BoxMuller_3D(double dT){
    // Generates 3D gaussian Random Variable, with variance = dT
    // outputs in 3-dim array
    double[] r = new double[3];
    double u,v,w,x;
    while(true){
      u = Math.random()*2.0 - 1.0;
      v = Math.random()*2.0 - 1.0;
      w = Math.random()*2.0 - 1.0;
      x = Math.random()*2.0 - 1.0;
      double s_1 = u*u + v*v;
      double s_2 = w*w + x*x;
      if(s_1 <= 1.0 && s_2 <= 1.0 && s_1 > 0.0 && s_2 > 0.0){
        double gaussScale_1 = Math.sqrt(-2.0*dT*Math.log(s_1)/s_1);
        double gaussScale_2 = Math.sqrt(-2.0*dT*Math.log(s_2)/s_2);
        r[0] = u*gaussScale_1;
        r[1] = v*gaussScale_1;
        r[2] = w*gaussScale_2;
        return r;
      }
    }

  }
  // Returns 3 Gaussian distributed variable mu = 0; var = dT
  public static double[] BoxMuller_2D(double dT){
    // Generates 3D gaussian Random Variable, with variance = dT
    // outputs in 3-dim array
    double[] r = new double[2];
    double s = 0.0;
    while(true){  // returning scaled r when s<1 is exit condition
      s = 0.0;
      for(int i=0;i<r.length;i++){
        r[i] = Math.random()*2.0 - 1.0;
        s = s + r[i]*r[i];
      }
      if(s <= 1.0 && s>0){
        double gaussScale = -(2.0*dT*Math.log(s)/s);
        gaussScale = Math.sqrt(gaussScale);
        for(int i=0;i<r.length;i++){
          r[i] = r[i]*gaussScale;
        }
        return r;
      }
    }

  }

  public void numGenCheck(double a){
    int N = 1000000;
    double mu_x=0.0, mu_y=0.0, mu_z=0.0;
    double sig_x=0.0, sig_y=0.0, sig_z=0.0;
    double[] r = new double[3];

    double dT = 0.25/(a);


    System.out.println("a = "+a);
    System.out.println("dT = "+dT);
    for(int i=0;i<N;i++){
      r = BoxMuller_3D(dT);

      mu_x = mu_x + r[0];
      sig_x = sig_x + r[0]*r[0];

      mu_y += r[1];
      sig_y += r[1]*r[1];

      mu_z += r[2];
      sig_z += r[2]*r[2];
    }

    mu_x = mu_x/((double)(N));
    sig_x = sig_x/((double)(N));
    sig_x = sig_x - mu_x*mu_x;

    mu_y = mu_y/((double)(N));
    sig_y = sig_y/((double)(N));
    //sig_y = sig_y - mu_y*mu_y;

    mu_z = mu_z/((double)(N));
    sig_z = sig_z/((double)(N));


    System.out.println("Avg. X = "+mu_x+"     Var. X = "+sig_x);
    System.out.println("Avg. Y = "+mu_y+"     Var. Y = "+sig_y);
    System.out.println("Avg. Z = "+mu_z+"     Var. Z = "+sig_z);

  }
  public void numGenCheck2D(double a){
    int N = 1000000;
    double mu_x=0.0, mu_y=0.0, mu_z=0.0;
    double sig_x=0.0, sig_y=0.0, sig_z=0.0;
    double[] r = new double[3];

    double dT = 0.25/(a);


    System.out.println("a = "+a);
    System.out.println("dT = "+dT);
    for(int i=0;i<N;i++){
      r = BoxMuller_2D(dT);

      mu_x = mu_x + r[0];
      sig_x = sig_x + r[0]*r[0];

      mu_y += r[1];
      sig_y += r[1]*r[1];

    }

    mu_x = mu_x/((double)(N));
    sig_x = sig_x/((double)(N));
    sig_x = sig_x - mu_x*mu_x;

    mu_y = mu_y/((double)(N));
    sig_y = sig_y/((double)(N));
    //sig_y = sig_y - mu_y*mu_y;


    System.out.println("Avg. X = "+mu_x+"     Var. X = "+sig_x);
    System.out.println("Avg. Y = "+mu_y+"     Var. Y = "+sig_y);
    //System.out.println("Avg. Z = "+mu_z+"     Var. Z = "+sig_z);
  }

  double local_Energy(double[] r, double a){
    double R = 0.0;
    double R_2 = 0.0;
    for(int i=0;i<3;i++){
      R_2 += r[i]*r[i];
    }
    R = Math.sqrt(R_2);

    return 3.0*a - 2.0*a*a*R_2 - 1.0/R;
  }

  double MonteCarlo(double a){
    int N = 1000000;
    int n = 0;
    double sum = 0.0;
    double dT = 0.25/a;

    while(n < N){
      sum += local_Energy(BoxMuller_3D(dT), a);
      n++;
    }
    sum = sum /((double)(N));
    return sum;
  }


  public void calculate(){
      alpha = 0.02;
      while(alpha <= 0.52){
        double MCresult = MonteCarlo(alpha);
        frame.append(0, alpha, MCresult);
        //numGenCheck(alpha);
        alpha += 0.0200000;
      }

    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    return;
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new hydrogenMC());
  }

}
