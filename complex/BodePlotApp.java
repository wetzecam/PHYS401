import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class BodePlotApp extends AbstractCalculation{
  Complex2DFrame frame = new Complex2DFrame("x","y","Complex field");
  ComplexPlotFrame bode = new ComplexPlotFrame("x", "H(x)","BodePlot");
  PlotFrame Bode = new PlotFrame("w","H(jw)", "Bode");
  int Nres;
  double[][][] field;
  double[][] zeros;
  double[][] poles;

  public void reset(){
    Nres = 1000;
    frame.clearData();
    frame.setPreferredMinMax(-100.0,100.0,-100.0,100.0);
    frame.setSize(700, 700);
    bode.clearData();
    bode.setSize(700, 700);
    Bode.clearData();
    Bode.setSize(700, 700);

    field = new double[2][Nres][Nres];
    frame.setAll(field);
    zeros = new double[2][2];
    zeros[0][0] = 5.0;
    zeros[1][0] = 0.0;
    zeros[0][1] = -5.0;
    zeros[1][1] = 0.0;

    poles = new double[2][2];
    poles[0][0] = 2.5;
    poles[1][0] = 2.5;
    poles[0][1] = 4.5;
    poles[1][1] = 4.5;
  }

  public void calculate(){
    double[] omega = new double[Nres];
    double[] H_jw = new double[2*Nres];

    for(int i=0, nx = field[0].length; i<nx; i++){
      double x = frame.indexToX(i);
      //x = Math.pow(10.0,x);
      for(int j=0, ny = field[0][0].length; j<ny;j++){
        double y = frame.indexToY(j);
        //y = Math.pow(10.0,y);

        double[] H = new double[2];
        double[] P = Polynomial(x,y,zeros);
        double[] Q = Polynomial(x,y,poles);

        double r = Math.pow(Q[0],2) + Math.pow(Q[1],2);

        H[0] = (P[0]*Q[0] + P[1]*Q[1])/r;
        H[1] = (P[1]*Q[0] - P[0]*Q[1])/r;

        H = ComplexLog(H);

        H[0] *= 20.0;
        H[1] *= 20.0;

        field[0][i][j] = H[0]; // real component
        field[1][i][j] = H[1]; // complex component

        if(Math.abs(x)<=1.0){
          omega[j] = y;
          H_jw[2*j] = H[0];
          H_jw[2*j+1] = H[1];
        }
      }
    }

    bode.append(omega, H_jw);
    bode.setVisible(true);

    frame.setAll(field);
    frame.setVisible(true);
    frame.convertToSurfacePlot();
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    return;
  }

  public double[] Polynomial(double u, double v, double[][] z){
    double w[] = new double[2];
    w[0] = u - z[0][0];
    w[1] = v - z[1][0];
    for(int i=1; i<z[0].length; i++){
      double p0 = u - z[0][i];
      double p1 = v - z[1][i];
      double a = w[0]*p0 - w[1]*p1;
      double b = w[0]*p1 + w[1]*p0;
      w[0] = a;
      w[1] = b;
    }

    return w;
  }

  public double[] ComplexLog(double[] z){
    double r = Math.sqrt(Math.pow(z[0],2.0) + Math.pow(z[1],2.0));
    double arg = Math.atan(z[1]/z[0]);
    z[0] =20.0* Math.log(r);
    z[1] = 20.0*arg;
    return z;
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new BodePlotApp());
  }

}
