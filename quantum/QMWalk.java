
public class QMWalk {
  int numberOfBins = 1000;  // for wave Function
  double[] x;               // position of walkers
  double[] phi0;            // estimate of ground state wave Function
  double[] xv;              // x values for computing phi0
  int N0;                   // desired number of walkers
  int N;                    // actual number of walkers
  double ds;                // step size
  double dt;                // time interval
  double vave = 0;          //  mean Potential
  double vref = 0;          // reference Potential
  double eAccum;            // accumulation of energy values
  double xmin;              // minimum x
  int mcs;

  public void initialize(){
    N0 = N;
    x = new double[2*numberOfBins];
    phi0 = new double[numberOfBins];
    xv = new double[numberOfBins];
    // minimum location of computing phi0
    xmin = -ds*numberOfBins/2.0;
    double binEdge = xmin;
    for(int i=0; i<numberOfBins; i++){
      xv[i] = binEdge;
      binEdge += ds;
    }
    // initial width for location of walkers
    double initialWidth = 1;
    for(int i=0; i<N; i++){
      // initial random location of walkers
      x[i] = (2*Math.random()-1)*initialWidth;
      vref += Potential(x[i]);
    }
    vave = 0;
    vref = 0;
    eAccum = 0;
    mcs = 0;
    dt = ds*ds;
  }

  void walk() {
    double vsum = 0;
    for(int i = N-1; i>=0; i--){
      if(Math.random()<0.5){  // move walker
        x[i] += ds;
      } else {
        x[i] -= ds;
      }
      double pot = Potential(x[i]);
      double dv = pot - vref;
      double r = Math.random();
      vsum += pot;
      double Pb = Math.exp(-dv*dt)-1.0;
      int n = (int) Pb;
      Pb -= n;
      if(Pb > r){n++;}
      if(dv<0&&(N<x.length)){               // decide to add or delete walker
        n = Math.min(n, x.length-N);
         for(int j=0; j<n; j++){
            x[N] = x[i];        // new walker at current location
            vsum += pot;        // add energy of new walker
            N++;
          }

      } else {
        if((r<dv*dt)&&(N>0)) {
          N--;
          // relabel last walker to deleted walker index
          x[i] = x[N];
          vsum -= pot;        // subtract energy of deleted walker
        }
      }
    }
    vave = (N==0) ? 0 // if no walkers potential = 0
                  : vsum/N;
    vref = vave - (N-N0)/N0/dt;
    mcs++;
  }

  void doMCS() {
    walk();
    eAccum += vave;
    for(int i = 0; i<N; i++){
      int bin = (int) Math.floor((x[i]-xmin)/ds); // bin index
      if(bin>=0 && bin<numberOfBins){
        phi0[bin]++;
      }
    }
  }

  void resetData() {
    for(int i=0; i<numberOfBins; i++){
      phi0[i] = 0;
    }
    eAccum = 0;
    mcs = 0;
  }

  public double Potential(double x){
    return 0.5*x*x;
  }
}
