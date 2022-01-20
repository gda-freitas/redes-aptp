import java.io.*;
import java.net.*;

class ClienteTCP_Calculadora {
	public static void main(String argv[]) {
		String opcao;
		String respostaServidor;
		BufferedReader doUsuario = new BufferedReader(new InputStreamReader(System.in));

		try{
			Socket socketCliente = new Socket("localhost", 6789);
			DataOutputStream paraServidor = new DataOutputStream(socketCliente.getOutputStream());
			BufferedReader doServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

			while(true){
				
				//recebe opções
				respostaServidor = doServidor.readLine();				
				System.out.println(respostaServidor);
				
				//envia selecionada
				opcao = doUsuario.readLine();				
				paraServidor.writeBytes(opcao + '\n');
				
				if(opcao.equals("5"))
					break;
				
				//digita valores
				System.out.println("Digite o primeiro valor:");
				String valor1 = doUsuario.readLine();
				System.out.println("Digite o segundo valor:");
				String valor2 = doUsuario.readLine();
				
				//envia valores
				paraServidor.writeBytes(valor1 + '\n');
				paraServidor.writeBytes(valor2 + '\n');
				
				//recebe resposta e exibe
				System.out.println("Aguardando resposta do servidor...");				
				respostaServidor = doServidor.readLine();				
				System.out.println("Resultado: " + respostaServidor);
			}
			System.out.println("Encerrando conexão...");
			socketCliente.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}