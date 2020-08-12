import java.awt.*;
import org.opensourcephysics.frames.*;

public class IsingModel {
  // Simulation Ising model (Spin interactions) on a Lattice
  //    Using Demon Algoritm
  public int[] demonEnergyDistribution;
  public LatticeFrame lattice;   // Container
  int N;                          // number of nodes
  public int systemEnergy;
  public int demonEnergy = 0;
  public int mcs = 0;
  public double systemEnergyAccumulator = 0;
  public double demonEnergyAccumulator = 0;
  public int magnetization = 0;
  public double mAccumulator, m2Accumulator;
  public int acceptedMoves = 0;

  public IsingModel(LatticeFrame displayFrame){
    lattice = displayFrame;
  }

  public void initialize(int N) {
    this.N = N;
    lattice.resizeLattice(N,1);
    lattice.setIndexedColor(1,Color.red);
    lattice.setIndexedColor(-1,Color.blue);
    demonEnergyDistribution = new int[N*2];     // range [0, Max(E)]
    for(int i=0; i<N; ++i){
      lattice.setValue(i,0,1);
    }
    int tries = 0;
    int E0 = -N;         // Ground state
    magnetization = N;  // All spin UP
    demonEnergy = 0;
    // try up to 10*N random flips to get system to desired energy
    while((E0<systemEnergy)&&(tries<10*N)){
      int k = (int)(N*Math.random());
      int dE = 2*lattice.getValue(k,0)
                *(lattice.getValue((k+1)%N,0) + lattice.getValue((k-1+N)%N,0));
      if(dE > 0){
        E0 += dE;
        int newSpin = -lattice.getValue(k,0);
        lattice.setValue(k,0,newSpin);
        magnetization += 2*newSpin;
      }
      tries++;
    }
    systemEnergy = E0;
    resetData();
  }

  public double Temperature(){
    return 4.0/Math.log(1.0+4.0/(demonEnergyAccumulator/(mcs*N)));
  }

  public void resetData(){
    mcs = 0;
    systemEnergyAccumulator = 0;
    demonEnergyAccumulator = 0;
    mAccumulator = 0;
    m2Accumulator = 0;
    acceptedMoves = 0;
  }

  public void doMCStep(){
    for(int j=0;j<N;j++) {
      int i = (int)(N*Math.random());
      int dE = 2*lattice.getValue(i,0)
                *(lattice.getValue((i+1)%N,0)
                +lattice.getValue((i-1+N)%N,0));;
      if(dE <= demonEnergy) {
        int newSpin = -lattice.getValue(i,0);
        lattice.setValue(i,0,newSpin);
        acceptedMoves++;
        systemEnergy += dE;
        demonEnergy -= dE;
        magnetization += 2*newSpin;
        System.out.println(dE);
      }
      systemEnergyAccumulator += systemEnergy;
      demonEnergyAccumulator += demonEnergy;
      mAccumulator += magnetization;
      m2Accumulator += magnetization*magnetization;

      demonEnergyDistribution[demonEnergy]++;
    }
    mcs++;
  }
}
