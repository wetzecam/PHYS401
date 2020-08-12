

public class NewtonIteration{

  public static double ffp(double a){
    //return a*(1.0 - a*(Math.pi/4.0) - Math.exp(-a))/((1.0+a)*Math.exp(-a) - 1.0);
    //return a*(Math.exp(a) - 1.0 - a*Math.PI/2.0)/(1.0 + (a-1.0)Math.exp(a));
  }


  public double f_1(double x, double a){
    return Math.exp(a*x)/(1.0+Math.pow(x,2));
  }
  public double f_2(double x, double a){
    return x*f_1(x,a);
  }
  public double f_3(double x, double a){
    return x*f_2(x,a);
  }

  public static double h(double x, double a){
    return 4.0*(1.0-Math.exp(-a))*f_1(x,a)/a;
  }

  public static double h_dot(double x, double a){
    return 4.0*(1.0-Math.exp(-a))*f_1(x,a)/a;
  }

  public static double h_dub_dot(double x, double a){
    return 0.0;
  }


  public static double integrate_1(double a){
    return 0.0;
  }



  public static void main(String[] args){
    double x_o = Double.valueOf(args[0]);
    double epsilon = 1.0;
    int N=0;
    while(Math.abs(epsilon) >= Math.pow(10,-13)){
      epsilon = Math.cos(x_o) / Math.sin(x_o);
      x_o = x_o + epsilon;
      N++;
    }

    System.out.println("PI ~ "+x_o);
    System.out.println("Espilon = "+epsilon);
    System.out.println("Iterations: "+N);
  }
}
