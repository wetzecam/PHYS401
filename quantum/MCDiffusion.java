import java.util.Random;

public class MCDiffusion {
  double[] x;   // Walkers x Positions
  double[] w;   // Walker weights
  int mcs, N;      // Monte Carlo step, Number of Walkers
  double vref;  // reference voltage
  double vavg;  // average voltage
  double eAccum;
  double dt;    // time step
  double std;
  Random r;     // Random Number Generator

  public void initialize(){
    r = new Random(420);
    x = new double[N];
    w = new double[N];
    for(int i=0;i<N;i++){
      w[i] = 1.0;
      x[i] = r.nextGaussian();
    }
    eAccum = 0;
    vavg = 0;
    vref = 0;
    std = Math.sqrt(dt);
  }

  void walk() {
    for(int i=0; i<x.length; i++){
      double ds = r.nextGaussian()*std;
      double V = getV(x[i]);
      x[i] += ds;
      v += getV(x)
    }
  }
}
