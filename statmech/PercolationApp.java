import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.LatticeFrame;

public class PercolationApp extends AbstractCalculation implements InteractiveMouseHandler {
  LatticeFrame lattice = new LatticeFrame("Percolation");
  Random random = new Random();
  int L;
  int clusterNumber;  // used to color clusters

  public PercolationApp(){
    lattice.setInteractiveMouseHandler(this);
    lattice.setIndexedColor(-2,Color.BLACK);
    lattice.setIndexedColor(-1,Color.RED);
    lattice.setIndexedColor(0,Color.GREEN);
    lattice.setIndexedColor(1,Color.YELLOW);
    lattice.setIndexedColor(2,Color.BLUE);
    lattice.setIndexedColor(3,Color.CYAN);
    lattice.setIndexedColor(4,Color.MAGENTA);
    lattice.setIndexedColor(5,Color.PINK);
    lattice.setIndexedColor(6,Color.LIGHT_GRAY);
  }

  // uses mouse click events to identify an occupied site
  // and identify its cluster
  public void handleMouseAction(InteractivePanel panel, MouseEvent evt){
    panel.handleMouseAction(panel,evt);
    if(panel.getMouseAction()==InteractivePanel.MOUSE_PRESSED){
      int site = lattice.indexFromPoint(panel.getMouseX(), panel.getMouseY());
      // test if a valid site was clicked (index non-negative),
      //  and if site is occupied but not yet cluster colored (value -1)
      if(site>=0&&lattice.getAtIndex(site)==-1){
        // color cluster to which site it belongs
        colorCluster(site);
        // cycle through 7 cluster colors
        clusterNumber = (clusterNumber+1)%7;
        lattice.repaint();
      }
    }
  }

  public void calculate() {
    L = control.getInt("Lattice size");
    lattice.resizeLattice(L,L);
    random.setSeed(control.getInt("seed"));
    double p = control.getDouble("Site occupation probability");
    // occupy lattice w probability p
    for(int i=0;i<L*L;i++){
      lattice.setAtIndex(i,random.nextDouble()<p? -1 : -2);
    }
    // first cluster will have color GREEN
    clusterNumber = 0;
  }

    // returns jth neighbor
    // j=0 (left) j=1(right)  j=2(down) j=3(up)
    //    -1 if boundary  NON-Periodic
    int getNeighbor(int s, int j) {
      switch(j) {
        case 0:           // LEFT
          if(s%L==0){
            return -1;
          } else {
            return s-1;
          }

        case 1:           // RIGHT
        if(s%L==L-1){
          return -1;
        } else {
          return s+1;
        }

        case 2:           // DOWN
        if(s/L==0){
          return -1;
        } else {
          return s-L;
        }

        case 3:           // UP
        if(s/L==L-1){
          return -1;
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
      control.setValue("Lattice size", 32);
      control.setValue("Site occupation probability", 0.5927);
      control.setValue("seed", 420);
      calculate();
    }

    public static void main(String[] args){
      CalculationControl control = CalculationControl.createApp(new PercolationApp());
    }
}
