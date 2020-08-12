public class Killingbeck{
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

  public double HydrogenPotential(double r){ return -1.0/r; }
  public double HarmonicOscillator(double r){ return r*r/2.0; }


  public double f(double r){
    return 2.0*(HydrogenPotential(r)-Energy) + ll_1/(r*r);
  }

  public double g(double r){
    return 1.0 - dr2*f(r)/12.0;
  }

  public void VerletStep(){
    double u_next = (2.0+ dr2*f(R))*un - un_1;
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
    un_1 = un;
    un = u_next;
    R+=dr;
    return;
  }

  public double[] appendArray(double[] arr, double x){
    double[] out = new double[arr.length+1];
    for(int i=0;i<arr.length;i++){
      out[i] = arr[i];
    }
    out[arr.length] = x;
    return out;
  }

  public double[] Integrate(double u_0, double u_1, double r_max, int L, double E){
    l = L;
    ll_1 = L*(L+1);
    Energy = E;
    R = dr;
    un = u_1;
    un_1 = u_0;
    double[] C_vals = new double[1];
    Boolean first = true;

    while(R < r_max){
      VerletStep();
      //System.out.println(un);
      if(un*un_1 < 0 && !first){
        double C = R - dr/2.0;
        C_vals = appendArray(C_vals, C);
      }else if(un*un_1 < 0 && first){
        C_vals[0] = R - dr/2.0;
        first = false;
      }

    }
    if(C_vals[0] == 0.0){return new double[0];}
    return C_vals;
  }

}
