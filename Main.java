public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o peso da encomenda em kg: ");
        double peso;
        try {
            peso = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um valor numérico válido para o peso.");
            return;
        }

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.next();

        System.out.print("Digite o CEP de destino: ");
        String cepDestino = scanner.next();

        InformacaoFrete informacaoFrete = new InformacaoFrete();
        informacaoFrete.setPeso(peso);
        informacaoFrete.setCpf(cpf);
        informacaoFrete.setCep(cepDestino);

        String estadoDestino = consultarEstadoPorCEP(informacaoFrete.getCep());

        if (!estadoDestino.isEmpty()) {
            double valorFrete = calcularFrete(informacaoFrete.getPeso(), estadoDestino);
            System.out.println("O valor do frete é R$ " + valorFrete);
        } else {
            System.out.println("Não foi possível calcular o frete.");
        }

        scanner.close();
    }