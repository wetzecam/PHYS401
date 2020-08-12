
public class RationalPolynomial{
  public ComplexPolynomial Numerator;
  public ComplexPolynomial Denominator;

  public RationalPolynomial(double[][] Poles, double[][] Zeros){ // expects first dimension >=2
    Numerator = new ComplexPolynomial(Zeros);
    Denominator = new ComplexPolynomial(Poles);
  }

  public double[] eval(double u, double v){
    double[] rval = new double[2];
    double[] P = Numerator.eval(u,v);
    double[] Q = Denominator.eval(u,v);
    double   mag = Q[0]*Q[0] + Q[1]*Q[1];
    rval[0] = (P[0]*Q[0] + P[1]*Q[1])/mag;
    rval[1] = (P[1]*Q[0] - P[0]*Q[1])/mag;
    return rval;
  }

  public double[] logEval(double u, double v){
    double[] z = this.eval(u,v);
    double zMag = Math.sqrt(z[0]*z[0] + z[1]*z[1]);
    double newMag = 10*Math.log(zMag*zMag);
    z[0] *= newMag/zMag;
    z[1] *= newMag/zMag;
    return z;
  }

  public void addPole(double u, double v){ Denominator.addRoot(u,v); }
  public void addZero(double u, double v){ Numerator.addRoot(u,v); }
}
