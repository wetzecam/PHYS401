import java.util.Random;

public class PathIntegral{
  int numberOfBins = 1000;  // for wave Function
  double[] phi0;            // estimate of ground state wave Function
  double ds;                // histogram bin size
  double xmin;              // minimum x
  double[] xv;              // x values for computing phi0

  double[] x;               // position of beads
  int N_beads;              // actual number of beads
  double Time;              // Total Time
  double dt;                // time interval

  double eAccum;            // accumulation of energy values
  int mcs;

  int N_accepted, N_trial;  // for computing the Acceptance ratio
  double Accept_Ratio;
  double A = 1.0;           // Std for Gaussian generator
  int N_ucorr = 100;        // Number of uncorrelated steps for calculating E0

  public void initialize(){
    x = new double[N_beads];
    phi0 = new double[numberOfBins];
    xv = new double[numberOfBins];
    xmin = -ds*numberOfBins/2.0;
    double binEdge = xmin;
    for(int i=0; i<numberOfBins; i++){
      xv[i] = binEdge;
      binEdge += ds;
    }

    // initial width for location of walkers
    double initialWidth = 1;
    for(int i=0; i<N_beads; i++){
      // initial random location of walkers
      x[i] = (2*Math.random()-1)*initialWidth;
    }

    eAccum = 0;
    mcs = 0;
    N_accepted = 0;
    N_trial = 0;
    Accept_Ratio = 0.0;
    dt = Time / ((double)(N_beads));
  }

  double Action(double x, double y){
    double S = (x-y)*(x-y)/(2.0*dt);
    S += (x*x)*(dt/4.0);
    return S;
  }

  double Path_Action(){
    double S = Action(x[x.length-1],x[0]);
    for(int i=0; i<(x.length-1); i++){
      S += Action(x[i],x[i+1]);
    }
    return S;
  }

  void Trial_Step(int i){
    N_trial+=1;
    double dr = 2.0*ds*(Math.random()-0.5);   // Trial Move
    double x_old = x[i];
    double prev_S = Path_Action();
    x[i] = x[i] + dr;
    double new_S = Path_Action();
    double change_S = new_S - prev_S;
    if(change_S <= 0.0){
      N_accepted += 1;
    }
    else{
      if(Math.random() < Math.exp(-change_S)){ // accept
        N_accepted += 1;
      }
      else{                                   // reject
        x[i] = x_old;
      }
    }
  }

  double local_Energy(double x, double y){
    double A = (1.0/dt) + (dt/2.0);
    double B = x*A - y/dt;
    double C = x*x;
    return (A - B*B + C)/2.0;
  }

  double Path_Energy(){
    double E = local_Energy(x[x.length-1],x[0]);
    for(int i=0;i<(x.length-1);i++){
      E += local_Energy(x[i], x[i+1]);
    }
    return E/((double)(x.length));
  }

  void doMCS(){
    for(int i=0;i<100;i++){
      for(int j=0;j<N_beads;j++){
        Trial_Step(j);
      }
    }
    for(int i=0;i<x.length;i++){
      int bin = (int) Math.floor((x[i]-xmin)/ds); // bin index
      if(bin>=0 && bin<numberOfBins){
        phi0[bin]++;
      }
    }
    eAccum += Path_Energy();
    mcs += 1;

  }

}
