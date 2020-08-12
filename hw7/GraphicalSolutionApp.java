import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.PlotFrame;

public class GraphicalSolutionApp extends AbstractSimulation {
  PlotFrame plotFrame = new PlotFrame("iterations","x","graphical solution");
  double r;
  int iterate;
  double x, y;
  double x0, y0;

  public GraphicalSolutionApp() {
    plotFrame.setPreferredMinMax(0,1,0,1);
    plotFrame.setSize(1080,1080);
    plotFrame.setConnected(true);
    plotFrame.setXPointsLinked(true);
    plotFrame.setMarkerShape(0,0);
    plotFrame.setMarkerShape(2,0);
  }

  public void reset(){
    control.setValue("r", 0.89);
    control.setValue("x", 0.6);
    control.setAdjustableValue("iterate", 1);
  }

  public void initialize(){
    r = control.getDouble("r");
    x = control.getDouble("x");
    iterate = control.getInt("iterate");
    x0 = x;
    y0 = y;
    clear();
  }

  public void startRunning() {
    if(iterate != control.getInt("iterate")){
      iterate = control.getInt("iterate");
      clear();
    }
    r = control.getDouble("r");
  }

  public void doStep() {
    y = f(x, r, iterate);
    plotFrame.append(1, x0, y0);
    plotFrame.append(1, x0, y);
    plotFrame.append(1, x,  y);
    x = x0 = y0 = y;
    control.setValue("x",x);
  }

  public void drawFunction() {
    int nplot = 200;
    double delta = 1.0/nplot;
    double x=0;
    double y=0;
    for(int i=0; i<=nplot; i++){
      y = f(x,r,iterate);
      plotFrame.append(0,x,y);
      x+=delta;
    }
  }

  public void drawFunction2() {
    int nplot = 500;
    double delta = 1.0/nplot;
    double x=x0;
    double y=0;
    for(int i=0; i<=nplot; i++){
      x = f(x,r,iterate);
      plotFrame.append(0,i,x);

    }
  }

  public void drawLine() {
    for(double x=0; x<1; x+= 0.001){
      plotFrame.append(2,x,x);
    }
  }

  public double f(double x, double r, int iterate){
    if(iterate>1){
      double y = f(x,r, iterate-1);
      return 4*r*y*(1-y);
    } else {
      return 4*r*x*(1-x);
    }
  }

  public void clear() {
    plotFrame.clearData();
    drawFunction2();
    //drawLine();
    plotFrame.repaint();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new GraphicalSolutionApp());
    control.addButton("clear", "Clear", "Clears the Trajectory");
  }

}
