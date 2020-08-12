import java.awt.*;
import org.opensourcephysics.frames.*;

public class IsingModelTwo {
  // Simulation Ising model (Spin interactions) on a 2D Lattice
  // Metropolis algorithm for Sampling
  public LatticeFrame lattice;   // Container
  int N;                          // number of nodes

  public int mcs = 0;             // uncorrelated samples

  public double mAccum, m2Accum, absMAccum;
  public double eAccum, e2Accum;
  public int acceptedMoves = 0, trialMoves = 0;
  public double Beta = 1.0;   //   Thermodynamic Beta (k=1)
  public double Bmag = 0.0;   // External Magnetic Field
  public double Jspin = 1.0;  // spin interaction strength

  public double avgE;
  public double varE;
  public double avgM;
  public double varM;

  public double specHeat;
  public double magSucc;


  public void updateStats(){
    avgE = eAccum / ((double)(mcs));
    varE = e2Accum/((double)(mcs));
    varE = varE - avgE*avgE;
    varE = varE;

    avgM = absMAccum / ((double)(mcs));
    varM = m2Accum / ((double)(mcs));
    varM = varM - avgM*avgM;

    specHeat = Beta*Beta*varE / (double)(N*N);
    magSucc = Beta*varM /  (double)(N*N);
  }

  public IsingModelTwo(LatticeFrame displayFrame){
    lattice = displayFrame;
  }

  public void initialize(int N) {
    this.N = N;
    lattice.resizeLattice(N,N);
    lattice.setIndexedColor(1,Color.red);
    lattice.setIndexedColor(-1,Color.blue);

    // Initialize Spin lattice
    for(int j=0; j<N;j++){
      for(int i=0; i<N; ++i){
        lattice.setValue(i,j,1);
      }
    }
    resetData();
  }

  public int sumNN(int I, int J){
    int sum = lattice.getValue((I+1)%N,J);  // Right
    sum += lattice.getValue((I-1+N)%N,J);   // Left
    sum += lattice.getValue(I,(J-1+N)%N);   // Up
    sum += lattice.getValue(I,(J+1)%N);     // Down
    return sum;
  }

  public double getMag(){
    double Mag = 0.0;
    for(int I=0;I<N;I++){
      for(int J=0; J<N;J++){
        Mag += (double)(lattice.getValue(I,J));

      }
    }
    return Mag;
  }

  public double getEnergy(double Mag){
    double Energy=0.0;

    for(int I=0;I<N;I++){
      for(int J=0; J<N;J++){
        int s = lattice.getValue(I,J);
        int sum = sumNN(I,J);
        Energy += (double)(s*sum)*Jspin;
      }
    }
    return -Energy;
  }

  public void takeSample(){
    double Mag = getMag();
    double Energy = getEnergy(Mag);
    mAccum += Mag;
    m2Accum += Mag*Mag;
    absMAccum += Math.abs(Mag);
    eAccum += Energy;
    e2Accum += Energy*Energy;
    mcs++;
    return;
  }

  public void setTemperature(double T){
    Beta = 1.0/T;
    return;
  }

  public void resetData(){
    mcs = 0;
    mAccum = 0.0;
    m2Accum = 0.0;
    absMAccum = 0.0;
    eAccum = 0.0;
    e2Accum = 0.0;
    acceptedMoves = 0;
    trialMoves = 0;
    return;
  }

  public double deltaAction(int I, int J){
    double Act = 2.0*(double)(lattice.getValue(I,J));
    Act = Act * (sumNN(I,J)*Jspin + Bmag);
    Act = Act * Beta;
    return Act;
  }

  public void Flip(int I, int J){
    int S = lattice.getValue(I,J);
    lattice.setValue(I,J,(-1)*S);
  }

  public void doMetropolisStep(){
    for(int I=0;I<N;I++){
      for(int J=0; J<N;J++){
        trialMoves++;
        int i = (int)(N*Math.random());
        int j = (int)(N*Math.random());
        double dE = deltaAction(i,j);
        if(dE < 0.0){
          Flip(i,j);
          acceptedMoves++;
        }
        else if(Math.random() <= Math.exp(0.0-dE)){
          Flip(i,j);
          acceptedMoves++;
        }
      }
    }
    return;
  }

  public void doMCStep(){
    for(int i=0;i<200;i++){
      doMetropolisStep();
    }
    takeSample();
  }

}
