

public class HardWall{
  int Nr_max = 10;  // store only first few eigen values
  int l=0, ll_1=0;
  double Energy = -0.6;
  double dE = 0.00001;
  double R = 0.003;
  double dr = 0.003;
  double dr2 = (0.003)*(0.003);
  double un_1=0.0;
  double un=0.01;


  public void resetIntegrator(){
    R = 0.003;
    dr = 0.003;
    dr2 = (0.003)*(0.003);
    un_1=0.0;
    un=0.01;
  }

  public void reset(){
    // Resets to Default initial conditions
    l=0;
    ll_1=0;
    Energy = -0.6;
    dE = 0.00001;
    R = 0.003;
    dr = 0.003;
    dr2 = (0.003)*(0.003);
    un_1=0.0;
    un=0.01;
  }

  public double HydrogenPotential(double r){ return 1.0/r; }
  public double HarmonicOscillator(double r){ return r*r/2.0; }


  public double f(double r){
    return 2.0*(HydrogenPotential(r)-Energy) + ll_1/(r*r);
  }

  public double g(double r){
    return 1.0 - dr2*f(r)/12.0;
  }

  public void VerletStep(){
    double u_next = (2+dr2*f(R))*un - un_1;
    un_1 = un;
    un = u_next;
    R+=dr;
    return;
  }

  public void NumerovStep(){
    double G_next = g(R+dr);
    double G = g(R);
    double G_prev = g(R-dr);

    double u_next = (((12.0-10.0*G)*un)/G_next) - (G_prev*un_1)/G_next;
    un_1 = un;System.arraycopy(C_vals[iter_E],0, C_s,0,C_s.length);
    un = u_next;
    R+=dr;
    return;
  }

  public double[][] Cscan(int L, double R_MAX, double E_0){
    l = L;
    ll_1 = l*(l+1);
    Energy = (E_0<0)?E_0:-E_0;
    double[][] C_vals = new double[Math.abs((int)(E_0/dE))+2][Nr_max];

    int iter_E=0;
    while(Energy <= 0.0){
      double[] C_s = new double[0];
      while(R <= R_MAX){
        VerletStep();
        if(un*un_1<0){
          System.out.println(R-dr/2.0);
          double[] tmp = new double[C_s.length+1];
          tmp[C_s.length] = R - dr/2.0;
          for(int i=0;i<C_s.length;i++){
            tmp[i] = C_s[i];
          }
          C_s = new double[tmp.length];
          for(int i=0;i<tmp.length;i++){
            C_s[i] = tmp[i];
          }
        }
      }
      System.arraycopy(C_vals[iter_E],0, C_s,0,C_s.length);
      Energy += dE;
      iter_E++;
      resetIntegrator();
    }

    return C_vals;
  }

}
