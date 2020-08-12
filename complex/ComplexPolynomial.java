

public class ComplexPolynomial{
  int Nroots;
  public double[][] roots;

  public ComplexPolynomial(double[][] ROOT){
    Nroots = ROOT[0].length;
    roots = new double[2][Nroots];
    for(int i=0;i<Nroots;i++){
      roots[0][i] = ROOT[0][i];
      roots[1][i] = ROOT[1][i];
    }
  }

  public void addRoot(double u, double v){
    double[][] newRoots = new double[2][Nroots+1];
    newRoots[0][Nroots] = u;
    newRoots[1][Nroots] = v;
    for(int i=0; i<Nroots;i++){
      newRoots[0][i] = roots[0][i];
      newRoots[1][i] = roots[1][i];
    }
    roots = newRoots;
    Nroots++;
  }

  //public void removeRoot(int i){}

  double[] eval(double u, double v){
    double w[] = new double[2];
    w[0] = u - roots[0][0];
    w[1] = v - roots[1][0];
    for(int i=1; i<roots[0].length; i++){
      double p0 = u - roots[0][i];
      double p1 = v - roots[1][i];
      double a = w[0]*p0 - w[1]*p1;
      double b = w[0]*p1 + w[1]*p0;
      w[0] = a;
      w[1] = b;
    }
    return w;
  }



}
