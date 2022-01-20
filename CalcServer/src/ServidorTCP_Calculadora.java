import java.io.*;
import java.net.*;

class ServidorTCP_Calculadora{
	public static void main(String args[]) {
		String opcaoCliente;
		ServerSocket socketRecepcao = null;

		try{
			socketRecepcao = new ServerSocket(6789);

			System.out.println("Servidor aguardando conexão...");
			Socket socketConexao = socketRecepcao.accept();

			System.out.println("Conectado com o cliente ["+socketConexao.getInetAddress()+"]...");

			BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));
			DataOutputStream paraCliente = new DataOutputStream(socketConexao.getOutputStream());

			while(true){
				
				//envia opções para o cliente
				System.out.println("Enviando opções para o cliente...");
				String opcoes = "[1]Soma   [2]Subtracao   [3]Multiplicacao   [4]Divisao   [5]Sair\n";
				paraCliente.writeBytes(opcoes);
				
				//recebe opção
				System.out.println("Aguardando opção do cliente...");
				opcaoCliente = doCliente.readLine();
				System.out.println("Opção do cliente: ["+opcaoCliente+"]");

				if(opcaoCliente.equals("5"))
					break;
				
				//recebe os valores
				double valor1 = Double.parseDouble(doCliente.readLine());
				double valor2 = Double.parseDouble(doCliente.readLine());

				//cases para cada operação
				switch(opcaoCliente){
				case "1": 
					System.out.println("Somando "+valor1+" + "+valor2);
					paraCliente.writeBytes((valor1+valor2)+"\n");
					break;
				case "2":
					paraCliente.writeBytes((valor1-valor2)+"\n");
					break;
				case "3":
					paraCliente.writeBytes((valor1*valor2)+"\n");
					break;
				case "4":
					String retorno = valor2 == 0 ? "[ERRO] Divisao por zero!\n" : (valor1/valor2)+"\n";
					paraCliente.writeBytes(retorno);
					break;
				default: 
					paraCliente.writeBytes("[ERRO] Opcao invalida!\n");
				}
			}
			System.out.println("Encerrando conexao com ["+socketConexao.getInetAddress()+"]");
			socketConexao.close();
			System.out.println("Encerrando servidor...");
			socketRecepcao.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}