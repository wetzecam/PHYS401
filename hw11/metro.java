import java.util.Random;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class metro extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("Alpha","Average Energy","1D Harmonic Potential Monte Carlo");
  double alpha;
  public void reset(){
    frame.setMarkerSize(0,8);
    frame.setMarkerSize(1,8);
    frame.setConnected(0,true);
  }

  double Expected_Energy(double a){
    return (0.5*a*a)-a;
  }

  double local_Energy(double[] r, double a){
    double R = 0.0;
    for(int i=0;i<3;i++){
      R += r[i]*r[i];
    }
    R = Math.sqrt(R);
    return ((a-1.0)/R) - 0.5*a*a;
  }

  double change_in_Action(double r_new, double r_old, double a){
    return 2.0*a*(r_new - r_old);
  }

  double[] Metropolis(double r_max, double a){
    double[] r = new double[3]; // position vector
    r[0] = 0.0; r[1] = 0.0; r[2] = 0.0;
    int N = 500; // intermediate metropolis trials
    int N_MC = 10000000;  // number montecarlo steps to take
    int nmc = 0;

    double sum_MC = 0.0, var_MC = 0.0;
    int accepted = 0, num_trial = 0;

    while(nmc < N_MC){

      double r_old, r_new;

      for(int i=0;i<N;i++){
        num_trial++;
        double[] dr = new double[3];
        dr[0] = r_max*(Math.random()-0.5);
        dr[1] = r_max*(Math.random()-0.5);
        dr[2] = r_max*(Math.random()-0.5);

        r_old = 0.0; r_new = 0.0;

        for(int j=0;j<3;j++){
          r_old += r[j]*r[j];
          r_new += (r[j]+dr[j])*(r[j]+dr[j]);
        }
        r_old = Math.sqrt(r_old);
        r_new = Math.sqrt(r_new);
        double dS = change_in_Action(r_new, r_old, a);

        if(dS <= 0.0){
          r[0] += dr[0];
          r[1] += dr[1];
          r[2] += dr[2];
          accepted++;
        }
        else{
          double u = Math.random();
          if(u <= Math.exp(-dS)){
            r[0] += dr[0];
            r[1] += dr[1];
            r[2] += dr[2];
            accepted++;
          }
        }
      }

      double E_val = local_Energy(r,a);
      sum_MC += E_val;
      var_MC += E_val*E_val;
      nmc++;
    }
    System.out.println("Acceptance Ratio = "+((double)(accepted))/((double)(num_trial)));
    sum_MC = sum_MC/((double)(N_MC));
    var_MC = var_MC/((double)(N_MC));
    var_MC = var_MC - sum_MC*sum_MC;
    double[] out = new double[5];
    out[0] = sum_MC;
    out[1] = var_MC;  // variance
    out[2] = Math.sqrt(var_MC); // std deviation
    out[3] = Math.sqrt(var_MC/((double)(N_MC))); // std error?
    out[4] = var_MC/Math.sqrt(N_MC);            // alternate std error??
    return out;
  }

  public void calculate(){
    try{
      FileWriter ofile = new FileWriter("Metrodata_new.txt");
      BufferedWriter oss = new BufferedWriter(ofile);

      alpha = 0.5;
      while(alpha <= 1.52){
        double r_max = -Math.log(0.3)/(6.0*alpha);
        r_max = 6.298-2.7*(alpha);
        double[] MCresult = Metropolis(r_max,alpha);
        frame.append(0, alpha, MCresult[0]);
        frame.append(1, alpha, Expected_Energy(alpha));

        oss.write(alpha+" "+Expected_Energy(alpha)+" "+MCresult[0]+" "+MCresult[1]+" "+MCresult[2]+" "+MCresult[3]+" "+MCresult[4]+" "+r_max);
        oss.newLine();

        alpha += 0.0500000;
      }
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
    CalculationControl control = CalculationControl.createApp(new metro());
  }

}
