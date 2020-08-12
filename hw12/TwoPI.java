import java.util.Random;
import java.io.*;
import java.util.Random;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class TwoPI extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("Alpha","Average Energy","1D Harmonic Potential Monte Carlo");
  double GAMMA = 4.0;
  int NMC = 10000;

  public void reset(){
    frame.setMarkerSize(0,8);
    frame.setMarkerSize(1,8);
    frame.setConnected(0,true);
  }

  double Potential(double x){
    return x*x*x*x/4.0;
  }

  double Action(double x, double y, double dt){
    return (x-y)*(x-y)/(2.0*dt) + 0.5*dt*(Potential(x)+Potential(y));
  }

  double Change_in_Action(double[] bead, int i, double dr, double dt){
    double x = bead[i];
    double x_prev, x_next;
    if(i==0){
      x_prev = bead[bead.length-1];
      x_next = bead[i+1];
    }
    else if(i==(bead.length-1)){
      x_prev = bead[i-1];
      x_next = bead[0];
    }
    else{
      x_prev = bead[i-1];
      x_next = bead[i+1];
    }

    double S1 = Action(x+dr,x_next,dt) - Action(x,x_next,dt);
    double S2 = Action(x_prev,x+dr,dt) - Action(x_prev,x,dt);

    return S1+S2;
  }

  double[] Metropolis_Step(double[] bead, double dt){
    int N_accepted = 0;
    int N_trial = 0;
    for(int i=0; i<100;i++){
      for(int j=0;j<bead.length;j++){
        N_trial++;
        int J = (int)Math.random()*(bead.length-1);
        double dr = (Math.random() -0.5)*GAMMA;
        double dS = Change_in_Action(bead, J, dr, dt);
        if(dS < 0){
          N_accepted += 1;
          bead[J] += dr;
        }
        else{
          if(Math.random() < Math.exp(-dS)){
            N_accepted += 1;
            bead[J] += dr;
          }
        }
      }
    }
    //System.out.println("Acceptance Rate = "+((double)(N_accepted)/(double)(N_trial)));
    return bead;
  }

  double local_Energy(double x, double y, double dt){
    double A = (1.0/dt) + x*x*3.0*(dt/2.0);
    double B = dt*0.5*x*x*x + (x - y)/dt;
    double C = x*x*x*x/2.0;
    return (A - B*B + C)/2.0;
  }

  double Local_Energy_Sum(double[] bead, double dt){
    double E = local_Energy(bead[bead.length-1],bead[0],dt);

    for(int i=0;i<(bead.length-1);i++){
      E += local_Energy(bead[i],bead[i+1],dt);
    }

    return E/((double)(bead.length));

  }

  double MC_Path_Integral(double Time, int N_beads, int nmcs){
    double dt = Time/((double)(N_beads));
    double[] bead = new double[N_beads];
    double DX = 0.4/((double)(bead.length-1));
    for(int i=0;i<bead.length;i++){
      bead[i] = DX*i-0.2;
    }
    double AcceptRate = 0.0;
    int N_trial=0,N_accepted=0;

    // Monte Carlo Loop
    double eAccum = 0.0;
    for(int i=0;i<nmcs;i++){

      for(int XX=0; XX<100;XX++){
        for(int j=0;j<bead.length;j++){
          N_trial++;
          int J = (int)Math.random()*(bead.length);
          double dr = (Math.random() -0.5)*GAMMA;
          double dS = Change_in_Action(bead, J, dr, dt);
          if(dS < 0){
            N_accepted += 1;
            bead[J] += dr;
          }
          else{
            if(Math.random() < Math.exp(-dS)){
              N_accepted += 1;
              bead[J] += dr;
            }
          }
        }
      }


      eAccum += Local_Energy_Sum(bead,dt);
    }
    System.out.println("Acceptance Rate = "+((double)(N_accepted)/(double)(N_trial)));

    return (eAccum)/((double)(nmcs));
  }


  double[] MC_Path_Integral_v(double Time, int N_beads, int nmcs){
    double[] out = new double[2];
    double dt = Time/((double)(N_beads));
    double[] bead = new double[N_beads];
    double DX = 0.4/((double)(bead.length-1));
    for(int i=0;i<bead.length;i++){
      bead[i] = DX*i-0.2;
    }
    double AcceptRate = 0.0;
    int N_trial=0,N_accepted=0;

    // Monte Carlo Loop
    double eAccum = 0.0;
    double eAccum2 = 0.0;
    for(int i=0;i<nmcs;i++){

      for(int XX=0; XX<100;XX++){
        for(int j=0;j<bead.length;j++){
          N_trial++;
          int J = (int)Math.random()*(bead.length-1);
          double dr = (Math.random() -0.5)*GAMMA;
          double dS = Change_in_Action(bead, J, dr, dt);
          if(dS < 0){
            N_accepted += 1;
            bead[J] += dr;
          }
          else{
            if(Math.random() < Math.exp(-dS)){
              N_accepted += 1;
              bead[J] += dr;
            }
          }
        }
      }


      double EL = Local_Energy_Sum(bead,dt);
      eAccum += EL;
      eAccum2 += EL*EL;
    }
    System.out.println("Acceptance Rate = "+((double)(N_accepted)/(double)(N_trial)));

    out[0] = (eAccum)/((double)(nmcs));
    double var_X = (eAccum2)/((double)(nmcs)) - out[0]*out[0];
    out[1] = var_X; // Math.sqrt(((double)(nmcs)));
    return out;
  }



  public double Anal_Solution(double time){
    return 0.5 + Math.exp(-time)/(1.0-Math.exp(-time));
  }

  public void calculate(){
    try{
      FileWriter ofile = new FileWriter("PIdata2_new.txt");
      BufferedWriter oss = new BufferedWriter(ofile);

      double Time = 1.0;
      while(Time < 10.5){
        double[] E_avg = MC_Path_Integral_v(Time, 2, NMC);
        System.out.println("Time = "+Time+"  E = "+E_avg[0]);
        oss.write(Time+" "+E_avg[0]+" "+E_avg[1]+" "+Anal_Solution(Time));
        oss.newLine();
        Time += 0.5;
      }
      oss.close();
      ofile.close();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);



    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
    try{
      FileWriter ofile = new FileWriter("PIdata4_new.txt");
      BufferedWriter oss = new BufferedWriter(ofile);

      double Time = 1.0;
      while(Time < 10.5){
        double[] E_avg = MC_Path_Integral_v(Time, 4, NMC);
        System.out.println("Time = "+Time+"  E = "+E_avg[0]);
        oss.write(Time+" "+E_avg[0]+" "+E_avg[1]+" "+Anal_Solution(Time));
        oss.newLine();
        Time += 0.5;
      }
      oss.close();
      ofile.close();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);



    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
    try{
      FileWriter ofile = new FileWriter("PIdata8_new.txt");
      BufferedWriter oss = new BufferedWriter(ofile);

      double Time = 1.0;
      while(Time < 10.5){
        double[] E_avg = MC_Path_Integral_v(Time, 8, NMC);
        System.out.println("Time = "+Time+"  E = "+E_avg[0]);
        oss.write(Time+" "+E_avg[0]+" "+E_avg[1]+" "+Anal_Solution(Time));
        oss.newLine();
        Time += 0.5;
      }
      oss.close();
      ofile.close();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);



    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
    return;
  }


  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new TwoPI());
  }

}
