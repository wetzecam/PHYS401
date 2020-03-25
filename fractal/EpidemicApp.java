import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.LatticeFrame;

public class EpidemicApp extends AbstractSimulation implements InteractiveMouseHandler {
  LatticeFrame lattice = new LatticeFrame("Percolation");
  Random random = new Random(420);
  int L;
  int clusterNumber;  // used to color clusters

  int[] infected;
  int N_infected;
  double p;       // probability of infection

  static final int INFECTED = -1;
  static final int HEALTHY = 0;
  static final int IMMUNE = 1;

  public EpidemicApp(){
    lattice.setInteractiveMouseHandler(this);

    lattice.setIndexedColor(INFECTED,Color.GREEN);    // Infected
    lattice.setIndexedColor(HEALTHY,Color.PINK);      // Healthy
    lattice.setIndexedColor(IMMUNE,Color.YELLOW);    // Immune
  }

  // uses mouse click events to identify an occupied site
  // and identify its cluster
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt){
    panel.handleMouseAction(panel,evt);
    if(panel.getMouseAction()==InteractivePanel.MOUSE_PRESSED){
      int site = lattice.indexFromPoint(panel.getMouseX(), panel.getMouseY());
      // test if a valid site was clicked (index non-negative),
      //  and if site is occupied but not yet infected (value 0)
      if(site>=0&&lattice.getAtIndex(site)==0){
        infect(site);
      }
    }
  }

  public void initialize(){
    L = control.getInt("Lattice size");
    p = control.getDouble("Infection probability");
    infected = new int[L*L];
    N_infected = 0;
    lattice.resizeLattice(L,L);
  }

  public void infect(int i){
    lattice.setAtIndex(i, INFECTED);
    infected[N_infected++] = i;
  }

  public void doStep(){
    for(int i=(N_infected-1); i>=0;i--){
      int site = infected[i];
      for(int j=0;j<4;j++){
        int neighbor = getNeighbor(site,j);
        if((lattice.getAtIndex(neighbor) == 0)){
          if(random.nextDouble()<p){
            infect(neighbor);
          } else {
            lattice.setAtIndex(neighbor, 1);  // imunn
          }
        }
      }

    }
  }
    // returns jth neighbor
    // j=0 (left) j=1(right)  j=2(down) j=3(up)
    //    -1 if boundary  NON-Periodic
    int getNeighbor(int s, int j) {
      int r = s/L;
      int c = s%L;
      switch(j) {
        case 0:           // LEFT
          if(s%L==0){
            return L*(r) + (L-1);
          } else {
            return s-1;
          }

        case 1:           // RIGHT
        if(s%L==L-1){
          return L*r;
        } else {
          return s+1;
        }

        case 2:           // DOWN
        if(s/L==0){
          return (L-1)*L + c;
        } else {
          return s-L;
        }

        case 3:           // UP
        if(s/L==L-1){
          return c;
        } else {
          return s+L;
        }
        default :
        return -1;
      }
    }

    void colorCluster(int initialSite) { // Color all sites in cluster
      //cluster sites whose neighbos have not yet been examined
      int[] sitesToTest = new int[L*L];
      int numSitesToTest = 0;
      lattice.setAtIndex(initialSite, clusterNumber);
      sitesToTest[numSitesToTest++] = initialSite;
      // grow cluster until numSitesToTest = 0
      while(numSitesToTest>0) {
        // get next site to test and remove it from list
        int site = sitesToTest[--numSitesToTest];
        for(int j=0;j<4;j++){
          int neighborSite = getNeighbor(site,j);
          //test if neighbor is occupied
          if(neighborSite>=0&&lattice.getAtIndex(neighborSite)==-1) {
            lattice.setAtIndex(neighborSite,clusterNumber);
            sitesToTest[numSitesToTest++] = neighborSite;
          }
        }
      }
    }

    public void reset() {
      control.setValue("Lattice size", 1000);
      control.setValue("Infection probability", 0.5927);
      enableStepsPerDisplay(true);
      initialize();
    }

    public static void main(String[] args){
      SimulationControl control = SimulationControl.createApp(new EpidemicApp());
    }
}
