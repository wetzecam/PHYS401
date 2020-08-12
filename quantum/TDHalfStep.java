
public class TDHalfStep {
  double[] x, realPsi, imagPsi, potential;
  double dx, dx2;
  double dt = 0.001;

  public TDHalfStep(GaussianPacket packet, int numberOfPoints, double xmin, double xmax){
    realPsi = new double[numberOfPoints];
    imagPsi = new double[numberOfPoints];
    potential = new double[numberOfPoints];
    x = new double[numberOfPoints];
    dx = (xmax - xmin)/(numberOfPoints-1);
    dx2 = dx*dx;
    double x0 = xmin;
    for(int i=0, n=realPsi.length;i<n;i++){
      x[i] = x0;
      potential[i] = getV(x0);
      realPsi[i] = packet.getReal(x0);
      imagPsi[i] = packet.getImaginary(x0);
      x0 += dx;
    }
    dt = getMaxDt();
    // advance imaginary part by 1/2 step at start
    for(int i=1, n=realPsi.length -1; i<n;i++){
      // deltaRe = changin in real part of psi in 1/2 step
      double deltaRe = potential[i]*realPsi[i]-0.5*(realPsi[i+1] - 2*realPsi[i] + realPsi[i-1])/dx2;
      imagPsi[i] -= deltaRe*dt/2;
    }
  }

  double getMaxDt(){
    double dt = Double.MAX_VALUE;
    for(int i=0, n=potential.length;i<n;i++){
      if(potential[i]<0){
        dt = Math.min(dt,-2/potential[i]);
      }
      double a = potential[i]+2/dx2;
      if(a>0){
        dt = Math.min(dt,2/a);
      }
    }
    return dt;
  }

  double step() {
    for(int i=1, n=imagPsi.length-1;i<n;i++){
      double imH = potential[i]*imagPsi[i]-0.5*(imagPsi[i+1]-2*imagPsi[i]+imagPsi[i-1])/dx2;
      realPsi[i] += imH*dt;
    }
    for(int i=1, n=realPsi.length-1;i<n;i++){
      double reH = potential[i]*realPsi[i]-0.5*(realPsi[i+1]-2*realPsi[i]+realPsi[i-1])/dx2;
      imagPsi[i] -= reH*dt;
    }
    return dt;
  }

  public double getV(double x){
    if(x<0 || x > 2){
      return 0;
    }
    return 5;
  }

}
