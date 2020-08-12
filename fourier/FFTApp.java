import java.text.DecimalFormat;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.numerics.FFT;
import org.opensourcephysics.frames.ComplexPlotFrame;

public class FFTApp extends AbstractCalculation {
  ComplexPlotFrame psiFrame = new ComplexPlotFrame("x", "|Psi|", "Wave function");

  public void calculate(){
    DecimalFormat decimal = new DecimalFormat("0.0000");
    // Number fourier cosCoefficients
    int N = 100;
    // Array to be transformed
    double[] z = new double[2*N];
    double[] XX = new double[N];
    // FFT for N points
    FFT fft = new FFT(N);
    // harmonic of e^(i*x)
    int mode = control.getInt("mode");
    double x = 0; double delta = 2*Math.PI/N;
    for(int i=0; i < N; i++){
      z[2*i] = Math.cos(mode*x);
      z[2*i+1] = Math.sin(mode*x);
      XX[i] = x;
      x+=delta;
    }
    fft.transform(z);
    fft.toNaturalOrder(z);
    XX = fft.getWrappedOmega(delta);
    psiFrame.append(XX, z);
  }

  public void reset() {
    control.setValue("mode", -1);
  }

  public static void main(String[] args){
    CalculationControl.createApp(new FFTApp());
  }
}
