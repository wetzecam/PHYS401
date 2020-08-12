import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.LatticeFrame;

public class RandomWalk extends AbstractSimulation implements InteractiveMouseHandler {
  LatticeFrame lattice = new LatticeFrame("Percolation");
  Random random = new Random(420);
  int L;
  int clusterNumber;  // used to color clusters

  int[] walkers;
  int N_walkers;
  double p[];       // probability of steps

  static final int INFECTED = -1;
  static final int HEALTHY = 0;
  static final int IMMUNE = 1;

  public RandomWalk(){
    lattice.setInteractiveMouseHandler(this);

    lattice.setIndexedColor(INFECTED,Color.GREEN);    // Infected
    lattice.setIndexedColor(HEALTHY,Color.PINK);      // Healthy
    lattice.setIndexedColor(IMMUNE,Color.YELLOW);    // Immune
    p = new double[4];
    p[0] = 0.25;    // up
    p[1] = 0.25;    // down
    p[2] = 0.25;    // left
    p[3] = 0.25;    // right
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

  public void infect(){}

  public void doStep(){
    for(int i=0;i<N_walkers; i++){
      double r = random.nextDouble();
      if(r<=p[0]){

      } else if(r <= (p[0]+p[1])){

      } else if(r <= (p[0]+p[1]+[2])){

      } else {

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
      SimulationControl control = SimulationControl.createApp(new RandomWalk());
    }
}
