import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

public class SplitOrderApp extends AbstractSimulation {
  Complex2DFrame xFrame = new Complex2DFrame("x", "y", "Wave function");
  //Complex2DFrame pFrame = new Complex2DFrame("x", "y", "Wave function");
  SplitOrder wavefunction;
  double time;

  public SplitOrderApp(){
    xFrame.setShowGrid(false);
    xFrame.setSize(1200,1200);
    return;
  }

  public void initialize() {
    time =0;
    xFrame.setMessage("t = "+0);

    String fStr = control.getString("f(|r|)");
    Function f = null;
    try{
      f = new ParsedFunction(fStr, "r");
    } catch(ParserException ex){
      control.println("Error parsing function string: "+fStr);
      return;
    }
    int xMode = control.getInt("x mode");
    int yMode = control.getInt("y mode");
    double xmin = control.getDouble("xmin");
    double xmax = control.getDouble("xmax");
    double ymin = control.getDouble("ymin");
    double ymax = control.getDouble("ymax");
    int nx = control.getInt("Nx");
    int ny = control.getInt("Ny");
    double xinit = control.getDouble("x init");
    double yinit = control.getDouble("y init");
    double aa = control.getDouble("slit width");
    double ww = control.getDouble("slit separation");

    wavefunction = new SplitOrder(f, xMode, yMode, xmin, xmax, ymin, ymax, nx, ny, xinit, yinit, aa, ww);

    xFrame.clearData();
    xFrame.setPreferredMinMax(xmin,xmax,ymin,ymax);
    xFrame.resizeGrid(nx,nx);
    xFrame.setAll(wavefunction.Psi);


  }

  public void doStep(){
    time += wavefunction.step();
    xFrame.setAll(wavefunction.Psi);
    xFrame.setVisible(true);
    xFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    xFrame.setMessage("t = "+decimalFormat.format(time));
  }

  public void reset(){
    control.setValue("slit width", "0.25");
    control.setValue("slit separation", "0.75");
    control.setValue("f(|r|)", "exp(-r*r/2)");
    control.setValue("x mode", 7);
    control.setValue("y mode", 0);
    control.setValue("xmin", "-4*pi");
    control.setValue("xmax",  "4*pi");
    control.setValue("ymin", "-4*pi");
    control.setValue("ymax",  "4*pi");
    control.setValue("Nx", 512);
    control.setValue("Ny", 512);
    control.setValue("x init", -2.0);
    control.setValue("y init", 0.0);

    // multiple computation steps per animatoin step
    setStepsPerDisplay(50);
    enableStepsPerDisplay(true);
    initialize();
  }

  public static void main(String[] args){
    SimulationControl.createApp(new SplitOrderApp());
  }
}
