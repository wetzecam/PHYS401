import java.awt.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.numerics.*;

public class AnalyzeApp extends AbstractCalculation {
  PlotFrame frame = new PlotFrame("frequency", "coefficients", "Fourier analysis");

  public AnalyzeApp(){
    frame.setMarkerShape(0, Dataset.POST);
    frame.setMarkerColor(0, new Color(255, 0, 0, 128));
    frame.setMarkerShape(1, Dataset.POST);
    frame.setMarkerColor(1, new Color(0, 0, 255, 128));
    frame.setXYColumnNames(0, "frequency", "cos");
    frame.setXYColumnNames(1, "frequency", "sin");
  }

  public void calculate() {
    double delta = control.getDouble("delta");
    int N = control.getInt("N");
    int numberOfCoefficients = control.getInt("number of coefficients");
    String fStr = control.getString("f(t)");
    Function f = null;
    try{
      f = new ParsedFunction(fStr, "t");
    } catch(ParserException ex){
      control.println("Error parsing function string: "+fStr);
      return;
    }
    Analyze analyze = new Analyze(f, N, delta);
    double f0 = 1.0/(N*delta);
    for(int i=0; i < numberOfCoefficients; i++){
      frame.append(0, i*f0, analyze.getCosineCoefficient(i));
      frame.append(1, i*f0, analyze.getSineCoefficient(i));
    }
  }

  public void reset() {
    control.setValue("f(t)", "sin(pi*t/10)");
    control.setValue("delta", "0.1");
    control.setValue("N", 200);
    control.setValue("number of coefficients", 10);
    calculate();
  }

  public static void main(String[] args){
    CalculationControl.createApp(new AnalyzeApp());
  }
}
