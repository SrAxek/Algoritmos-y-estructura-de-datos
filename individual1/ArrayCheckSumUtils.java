package aed.individual1;

public class ArrayCheckSumUtils {

  // a no es null, podria tener tamaño 0, n>0
	
  public static int[] arrayCheckSum(int[] arr, int n) {
	  
	  int contador = n;
	  int checksum = 0;
	  int iteracion = 0;
	  int size;
	  
	  //Ajustamos el tamaño del array en función de si la longitud del array es múltiplo de n o no
	  
	  if(arr.length%n!=0) size = arr.length + arr.length/n + 1;
	  else size = arr.length + arr.length/n;
	  final int[] res = new int[size];
	  
	  while(contador <  res.length) {
		  
		  for(int i = contador - n; i <= contador; i++) {
		  	if(i < contador) {
			  	res[i] = arr[i - iteracion];
			  	checksum += arr[i - iteracion];
		  	}
		  	else res[i] = checksum;
		  }
		  
		  checksum = 0;
		  iteracion++;
		  contador += n + 1;
		  
	  } 
	  
	  //Si la longitud del array no es múltiplo de n, hacemos un último recorrido
	  
	  if(arr.length%n!=0) {
		  
		  for(int i = contador - n; i < res.length; i++) {
			  if(i < res.length - 1) {
				  res[i] = arr[i - iteracion];
				  checksum += arr[i - iteracion];
			  }
			  else res[i] = checksum;
		  }
		  
	  }
	  
    return res;
    
}
}
