import org.opensourcephysics.frames.Complex2DFrame;

public class Complex2DFrameApp{
  public static void main(String[] args){
    Complex2DFrame frame = new Complex2DFrame("x","y","Complex field");
    frame.setPreferredMinMax(-1.5,1.5,-1.5,1.5);
    double[][][] field = new double[2][32][32];;
    frame.setAll(field);
    for(int i=0, nx = field[0].length; i<nx; i++){
      double x = frame.indexToX(i);
      for(int j=0, ny = field[0][0].length; j<ny;j++){
        double y = frame.indexToY(j);
        double a = Math.exp(-4*(x*x + y*y));
        field[0][i][j] = a*Math.cos(5*x); // real component
        field[1][i][j] = a*Math.sin(5*x); // complex component
      }
    }
    frame.setAll(field);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
  }

}
