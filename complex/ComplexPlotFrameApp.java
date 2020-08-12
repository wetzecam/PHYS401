import org.opensourcephysics.frames.ComplexPlotFrame;

public class ComplexPlotFrameApp {
  public static void main(String[] args){
    ComplexPlotFrame frame = new ComplexPlotFrame("x", "Psi(x)","Complex function");
    int n = 128;
    double xmin = -2*Math.PI, xmax = Math.PI;
    double x = xmin, dx = (xmax - xmin)/n;
    double[] xdata = new double[n];
    double[] zdata = new double[2*n];

    int mode = 4;
    for(int i=0; i<n; i++){
      double a = Math.exp(-x*x/4);
      zdata[2*i] = a*Math.cos(mode*x);
      zdata[2*i+1] = a*Math.sin(mode*x);
      xdata[i] = x;
      x += dx;
    }
    frame.append(xdata, zdata);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
  }
}
