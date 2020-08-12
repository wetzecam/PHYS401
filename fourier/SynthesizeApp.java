
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.FunctionDrawer;
import org.opensourcephysics.frames.DisplayFrame;



public class SynthesizeApp extends AbstractCalculation {
  DisplayFrame frame = new DisplayFrame("x","f(x)", "Fourier Synthesis");

  public void calculate() {
    double xmin = control.getDouble("xmin");
    double xmax = control.getDouble("xmax");
    int N = control.getInt("N");
    double period = control.getDouble("period");
    double[] sinCoefficients = (double[]) control.getObject("sin coefficients");
    double[] cosCoefficients = (double[]) control.getObject("cos coefficients");
    FunctionDrawer functionDrawer = new FunctionDrawer(new Synthesize(period, 0, cosCoefficients, sinCoefficients));
    functionDrawer.initialize(xmin, xmax, N, false);
    frame.addDrawable(functionDrawer);
  }

  public void reset() {
    control.setValue("xmin", -1);
    control.setValue("xmax", 1);
    control.setValue("N", 300);
    control.setValue("period", 1);
    control.setValue("sin coefficients", new double[] {1.0, 0, 1.0/3.0, 0, 1.0/5.0, 0, 0});
    control.setValue("cos coefficients", new double[] {0, 0, 0, 0, 0, 0, 0});
    calculate();
  }

  public static void main(String[] args) {
    CalculationControl.createApp(new SynthesizeApp());
  }
}
