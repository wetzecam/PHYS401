import java.util.Random;
import java.io.*;

public class Integrate{

  public static double integrand_1(double x){
    return 4.0/(1 + Math.pow(x,2));
  }

  public static double transform(double x, double a, double b){
    return (x*(b - a)/2.0) + ((a+b)/2.0);
  }

  public static double integrate(double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*integrand_1(transform(x[i],a,b));
    }
    return A*sum;
  }


  public static void main(String[] args){
    double[] x_gauss2 = {-1.0/Math.sqrt(3.0), 1.0/Math.sqrt(3.0)};
    double[] w_gauss2 = {1.0, 1.0};
    double sum_gauss2 = 0.0;
    double sum_gauss2_split = 0.0;

    double[] x_gauss3 = {-Math.sqrt(3.0/5.0), 0.0, Math.sqrt(3.0/5.0)};
    double[] w_gauss3 = {(5.0/9.0), (8.0/9.0), (5.0/9.0)};
    double sum_gauss3 = 0.0;
    double sum_gauss3_split = 0.0;

    double[] x_simp3 = {-1.0, 0.0, 1.0};
    double[] w_simp3 = {(1.0/3.0), (4.0/3.0), (1.0/3.0)};
    double sum_simp3 = 0.0;
    double sum_simp3_split = 0.0;

    double a = 0.0;
    double b = 1.0; // bounds of integration

    sum_gauss2 = integrate(x_gauss2, w_gauss2, a, b);
    sum_gauss3 = integrate(x_gauss3, w_gauss3, a, b);
    sum_simp3 = integrate(x_simp3, w_simp3, a, b);

    sum_gauss2_split = integrate(x_gauss2, w_gauss2, 0.0,0.5) + integrate(x_gauss2, w_gauss2, 0.5,1.0);
    sum_gauss3_split = integrate(x_gauss3, w_gauss3, 0.0,0.5) + integrate(x_gauss3, w_gauss3, 0.5,1.0);
    sum_simp3_split = integrate(x_simp3, w_simp3, 0.0,0.5) + integrate(x_simp3, w_simp3, 0.5,1.0);


    System.out.println("Gauss Quadrature [2]   "+sum_gauss2);
    System.out.println("Gauss Quadrature [3]   "+sum_gauss3);
    System.out.println("Simpsons Rule          "+sum_simp3);

    System.out.println("\nSplit Intevrals:");
    System.out.println("Gauss Quadrature [2]   "+sum_gauss2_split);
    System.out.println("Gauss Quadrature [3]   "+sum_gauss3_split);
    System.out.println("Simpsons Rule          "+sum_simp3_split);
    return;
  }
}
