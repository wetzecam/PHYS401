import java.awt.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

public class Ising{
  // physics simulation Containers
  public LatticeFrame lattice;
  public int L;
  public double beta;    // thermodynamic = 1/kT
  public double J;       // neighbor interaction energy
  public double B;       // external Magnetic Field strength
  public double E;       // System Energy

  // MonteCarlo Trackers
  public int mcs;
  public int acceptedMoves;
  public double energyAccumulator;    // E
  public double energy2Accumulator;   // E^2
  public double magnetAccumulator;    // M
  public double magnet2Accumulator;   // M^2



  public Ising(LatticeFrame displayFrame){
    lattice = displayFrame;
  }

  public void initialize(int N) {
    this.L = N;
    lattice.resizeLattice(L,L);
    lattice.setIndexedColor(1,Color.red);
    lattice.setIndexedColor(-1,Color.blue);

    for(int i=0;i<N;i++){
      for(int j=0;j<N;j++){
        lattice.setValue(i,j,1);    // initialize all spin up ^
      }
    }


  }

  public double Temperature(){
    return 0.0;
  }

  public void resetData(){

  }

  public void doMCStep(){
    // Metropolis Algorithm

  }
}
